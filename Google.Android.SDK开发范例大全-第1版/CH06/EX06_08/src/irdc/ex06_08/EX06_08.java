package irdc.ex06_08;

import java.io.File;
import java.text.DecimalFormat;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class EX06_08 extends Activity
{
  private Button myButton;
  private ProgressBar myProgressBar;
  private TextView myTextView;

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    myButton = (Button) findViewById(R.id.myButton);
    myProgressBar = (ProgressBar) findViewById(R.id.myProgressBar);
    myTextView = (TextView) findViewById(R.id.myTextView);

    myButton.setOnClickListener(new Button.OnClickListener()
    {

      @Override
      public void onClick(View arg0)
      {
        showSize();
      }

    });

  }

  private void showSize()
  {
    /* 將TextView及ProgressBar設定為空值及0 */
    myTextView.setText("");
    myProgressBar.setProgress(0);
    /* 判斷記憶卡是否插入 */
    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
    {
      /* 取得SD CARD檔案路徑一般是/sdcard */
      File path = Environment.getExternalStorageDirectory();

      /* StatFs看檔案系統空間使用狀況 */
      StatFs statFs = new StatFs(path.getPath());
      /* Block的size */
      long blockSize = statFs.getBlockSize();
      /* 總Block數量 */
      long totalBlocks = statFs.getBlockCount();
      /* 已使用的Block數量 */
      long availableBlocks = statFs.getAvailableBlocks();

      String[] total = fileSize(totalBlocks * blockSize);
      String[] available = fileSize(availableBlocks * blockSize);

      /* getMax取得在main.xml裡ProgressBar設定的最大值 */
      int ss = Integer.parseInt(available[0]) * myProgressBar.getMax()
          / Integer.parseInt(total[0]);

      myProgressBar.setProgress(ss);
      String text = "總共" + total[0] + total[1] + "\n";
      text += "可用" + available[0] + available[1];
      myTextView.setText(text);

    } else if (Environment.getExternalStorageState().equals(
        Environment.MEDIA_REMOVED))
    {
      String text = "SD CARD已移除";
      myTextView.setText(text);
    }
  }

  /* 回傳為字串陣列[0]為大小[1]為單位KB或MB */
  private String[] fileSize(long size)
  {
    String str = "";
    if (size >= 1024)
    {
      str = "KB";
      size /= 1024;
      if (size >= 1024)
      {
        str = "MB";
        size /= 1024;
      }
    }

    DecimalFormat formatter = new DecimalFormat();
    /* 每3個數字用,分隔如：1,000 */
    formatter.setGroupingSize(3);
    String result[] = new String[2];
    result[0] = formatter.format(size);
    result[1] = str;

    return result;
  }

}