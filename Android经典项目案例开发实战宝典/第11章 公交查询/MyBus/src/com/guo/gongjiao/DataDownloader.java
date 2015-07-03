package com.guo.gongjiao;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.util.Log;

class CountingInputStream extends BufferedInputStream {

	private long bytesReadMark = 0;  //用于存储文件位置标识
	private long bytesRead = 0;	     //当前读取文件字节数
	//构造一个BufferedInputStream，缓存大小为size
	public CountingInputStream(InputStream in, int size) {
		super(in, size);
	}
	//构造一个BufferedInputStream，缓存大小为8192bytes
	public CountingInputStream(InputStream in) {
		super(in);
	}
	//得到当前读取文件的字节数
	public long getBytesRead() {
		return bytesRead;
	}
	//每次读取一个字节，并将读取字节数加1
	//使用synchronized关键字，使该函数同时只能被同一个实例调用一次
	public synchronized int read() throws IOException {

		int read = super.read();
		if (read >= 0) {
			bytesRead++;
		}
		return read;
	}
	//读取不超过len长度的字节，存储在buffer b中从偏移量为off开始的位置，
	//read为实际读取到的字节数，并将当前读取自己数加上read
	public synchronized int read(byte[] b, int off, int len) throws IOException {

		int read = super.read(b, off, len);
		if (read >= 0) {
			bytesRead += read;
		}
		return read;
	}
	//跳过最多n字节，返回实际跳过的字节数，并将当前字节数加skipped
	public synchronized long skip(long n) throws IOException {

		long skipped = super.skip(n);
		if (skipped >= 0) {
			bytesRead += skipped;
		}
		return skipped;
	}
	//将当前位置保存到bytesReadMark这个变量中
	//若读取偏移量超过readlimit，则该mark的位置失效
	public synchronized void mark(int readlimit) {
		super.mark(readlimit);
		bytesReadMark = bytesRead;
	}
	//将当前文件位置恢复到bytesReadMark所指的位置
	//如果文件关闭，或者没有mark一个位置，或者之前mark的位置已经失效，将会抛出一个IOException
	public synchronized void reset() throws IOException {
		super.reset();
		bytesRead = bytesReadMark;
	}
}
//数据文件下载，主要是负责解压缩data.zip文件
class DataDownloader extends Thread
{
	public StatusWriter Status;//用于显示进度，额外信息等
	public boolean DownloadComplete = false;
	public boolean DownloadFailed = false;
	private MainActivity Parent;//用于传递主界面类，以进行界面相关操作
	private String outFilesDir = null;//输出文件目录
	class StatusWriter
	{
		private ProgressDialog Status;	//用于显示信息
		private MainActivity Parent;//用于与主界面交互
		private String oldText = "";
		//实例化成员变量，将主界面元素传递过来，方便进行更新
		public StatusWriter( ProgressDialog _Status, MainActivity _Parent )
		{
			Status = _Status;
			Parent = _Parent;
		}
		public void setParent( ProgressDialog _Status, MainActivity _Parent )
		{
			//锁定一个对象，当是同一个DataDownloader实例时是线程同步的
			//但是不同实例还是不同步，想要不同对象也同步，这个对象必须是静态对象
			synchronized(DataDownloader.this) {
				Status = _Status;
				Parent = _Parent;
				setText( oldText );//初始化的时候TextView显示空
			}
		}
		
		public void setText(final String str)
		{
			//用于更新TextView中的内容
			class Callback implements Runnable
			{
				public ProgressDialog Status;
				public String text;
				public void run()
				{
					Status.setMessage(text);
				}
			}
			synchronized(DataDownloader.this) {
				Callback cb = new Callback();
				oldText = new String(str);
				cb.text = new String(str);
				cb.Status = Status;
				//为了防止程序崩溃，价格判断是值得的
				if( Parent != null && Status != null )
					Parent.runOnUiThread(cb);//运行在UI线程中，以更新界面元素
			}
		}
		
	}
	//类DataDownloader的构造函数，传入主界面相应的元素，为更新界面信息作准备
	public DataDownloader( MainActivity _Parent, ProgressDialog _Status )
	{
		Parent = _Parent;
		Status = new StatusWriter( _Status, _Parent );//构建StatusWriter类，专门用于进行界面的更新操作
		//Status.setText( "Connecting to " + Globals.DataDownloadUrl );
		outFilesDir = Globals.DataDir;//初始化目标目录的路径
		DownloadComplete = false;	//初始化DownloadComplete
		this.start();		//运行该类的核心函数
	}
	
	public void setStatusField(ProgressDialog _Status)
	{
		synchronized(this) {
			Status.setParent( _Status, Parent );
		}
	}
	//核心函数
	@Override
	public void run()
	{	
		//检查目标目录的文件是否完整和正确，传入压缩文件名和要检查的标识文件名
		if( ! DownloadDataFile(Globals.DataDownloadUrl, "DownloadFinished.flag") )
		{
			DownloadFailed = true;
			return;
		}
		//如果运行到了这里，说明数据是正确的
		DownloadComplete = true;
		//初始化
		initParent();
	}

	public boolean DownloadDataFile(final String DataDownloadUrl, final String DownloadFlagFileName)
	{	
		//初始化资源实例
		Resources res = Parent.getResources();
		//检查目标文件是否包含指定的数据
		String path = getOutFilePath(DownloadFlagFileName);
		InputStream checkFile = null;
		try {
			checkFile = new FileInputStream( path );
		} catch( FileNotFoundException e ) {
		} catch( SecurityException e ) { };
		if( checkFile != null )
		{
			try {
				//构造一个比标准数据稍大的buffer，用于存储文件数据
				byte b[] = new byte[ Globals.DataDownloadUrl.getBytes("UTF-8").length + 1 ];
				int readed = checkFile.read(b);
				String compare = new String( b, 0, readed, "UTF-8" );  //DataDownloadUrl=data.zip
				boolean matched = false;
				//若compare=data.zip
				if( compare.compareTo(DataDownloadUrl) == 0 )
					matched = true;
				//如果不匹配，抛出异常，直接跳转到1所在的位置
				if( ! matched )
					throw new IOException();
				Status.setText( res.getString(R.string.download_unneeded) );
				return true;
			} catch ( IOException e ) {};
		}
		checkFile = null;  //----1
		//mkdirs 将会产生所有中间必要的目录
		try {
			(new File( outFilesDir )).mkdirs();
			OutputStream out = new FileOutputStream( getOutFilePath(".nomedia") );
			out.flush();
			out.close();
		}
		catch( SecurityException e ) {}
		catch( FileNotFoundException e ) {}
		catch( IOException e ) {};

		long totalLen = 0;
		CountingInputStream stream;
		byte[] buf = new byte[16384];

		String url = DataDownloadUrl;
		System.out.println("Unpacking from assets: '" + url + "'");
		try {
			//打开assets下的文件
			stream = new CountingInputStream(Parent.getAssets().open(url), 8192);
			//跳到文件末尾以确定文件大小
			while( stream.skip(65536) > 0 ) { };
			//将文件大小存储到totalLen
			totalLen = stream.getBytesRead();
			stream.close();
			//重新打开文件，以重置文件当前读取位置
			stream = new CountingInputStream(Parent.getAssets().open(url), 8192);
		} catch( IOException e ) {
			System.out.println("Unpacking from assets '" + url + "' - error: " + e.toString());
			Status.setText( res.getString(R.string.error_dl_from, url) );//载入出错
			return false;
		}
		ZipInputStream zip = new ZipInputStream(stream);
		
		while(true)
		{
			ZipEntry entry = null;
			try {
				//取得压缩文件的一个个子元素
				entry = zip.getNextEntry();
				if( entry != null )
					System.out.println("Reading from zip file '" + url + "' entry '" + entry.getName() + "'");
			} catch( java.io.IOException e ) {
				Status.setText( res.getString(R.string.error_dl_from, url) );
				System.out.println("Error reading from zip file '" + url + "': " + e.toString());
				return false;
			}
			//如果元素为空，表明压缩文件已经读取完毕
			if( entry == null )
			{
				System.out.println("Reading from zip file '" + url + "' finished");
				break;
			}
			//如果是目录，则创建相应目录结构，接着直接读取下一个元素
			if( entry.isDirectory() )
			{
				System.out.println("Creating dir '" + getOutFilePath(entry.getName()) + "'");
				try {
					(new File( getOutFilePath(entry.getName()) )).mkdirs();
				} catch( SecurityException e ) { };
				continue;
			}

			OutputStream out = null;
			path = getOutFilePath(entry.getName());
			//安全起见，创建目标文件所需要的目录结构，虽然这里不需要，但以后可能会需要
			try {
				(new File( path.substring(0, path.lastIndexOf("/") ))).mkdirs();
			} catch( SecurityException e ) { };
			
			try {
				//使用CRC32校验目标文件
				CheckedInputStream check = new CheckedInputStream( new FileInputStream(path), new CRC32() );
				while( check.read(buf, 0, buf.length) > 0 ) {};
				check.close();
				//检查校验和是否正确
				if( check.getChecksum().getValue() != entry.getCrc() )
				{
					File ff = new File(path);
					ff.delete();//校验和不正确则删除文件，重新创建文件
					throw new Exception(); //跳转到catch
				}
				System.out.println("File '" + path + "' exists and passed CRC check - not overwriting it");
				//若校验成功，则继续校验下一个文件
				continue;
			} catch( Exception e )
			{
			}
			try {
				out = new FileOutputStream( path );
			} catch( FileNotFoundException e ) {
				System.out.println("Saving file '" + path + "' - cannot create file: " + e.toString());
			} catch( SecurityException e ) {
				System.out.println("Saving file '" + path + "' - cannot create file: " + e.toString());
			};
			//如果创建文件失败
			if( out == null )
			{
				Status.setText( res.getString(R.string.error_write, path) );
				System.out.println("Saving file '" + path + "' - cannot create file");
				return false;
			}
			//将检查结果以百分比显示到progress dialog中
			float percent = 0.0f;
			if( totalLen > 0 )
				percent = stream.getBytesRead() * 100.0f / totalLen;
			Status.setText( res.getString(R.string.dl_progress, percent, path) );
			
			try {
				//读取当前元素
				int len = zip.read(buf);
				while (len >= 0)
				{
					if(len > 0)
						out.write(buf, 0, len);//将当前元素的内容拷贝到目标文件
					len = zip.read(buf);

					percent = 0.0f;
					if( totalLen > 0 )
						percent = stream.getBytesRead() * 100.0f / totalLen;
					Status.setText( res.getString(R.string.dl_progress, percent, path) );
				}
				out.flush();
				out.close();
				out = null;
			} catch( java.io.IOException e ) {
				Status.setText( res.getString(R.string.error_write, path) );
				System.out.println("Saving file '" + path + "' - error writing or downloading: " + e.toString());
				return false;
			}
			
			try {
				//拷贝完之后同样还要校验一下
				CheckedInputStream check = new CheckedInputStream( new FileInputStream(path), new CRC32() );
				while( check.read(buf, 0, buf.length) > 0 ) {};
				check.close();
				if( check.getChecksum().getValue() != entry.getCrc() )
				{
					File ff = new File(path);
					ff.delete();
					throw new Exception();
				}
			} catch( Exception e )
			{
				Status.setText( res.getString(R.string.error_write, path) );
				System.out.println("Saving file '" + path + "' - CRC check failed");
				return false;
			}
			System.out.println("Saving file '" + path + "' done");
		}

		OutputStream out = null;
		//全部完成之后将制定信息写入校验文件中
		path = getOutFilePath(DownloadFlagFileName);
		try {
			out = new FileOutputStream( path );
			out.write(DataDownloadUrl.getBytes("UTF-8"));
			out.flush();
			out.close();
		} catch( FileNotFoundException e ) {
		} catch( SecurityException e ) {
		} catch( java.io.IOException e ) {
			Status.setText( res.getString(R.string.error_write, path) );
			return false;
		};
		Status.setText( res.getString(R.string.dl_finished) );

		try {
			stream.close();
		} catch( java.io.IOException e ) {
		};

		return true;
	};
	//若前面的数据都正确，则执行界面的初始化，将数据文件以列表的形式呈现出来
	private void initParent()
	{
		class Callback implements Runnable
		{
			public MainActivity Parent;
			public void run()
			{
				Parent.getFileList();//获得目标目录的文件列表
				Log.e("guojs","initParent!");
			}
		}
		Callback cb = new Callback();
		synchronized(this) {
			cb.Parent = Parent;//传入主界面类的实例
			if(Parent != null)
				Parent.runOnUiThread(cb);//因为需要更新界面，因此需要运行在UI线程
		}
	}
	//返回目标输出文件的绝对路径
	private String getOutFilePath(final String filename)
	{
		return outFilesDir + "/" + filename;
	};
	

}

