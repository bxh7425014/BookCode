package net.blogjava.mobile;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class Main extends Activity implements OnClickListener
{
	private final int DIALOG_DELETE_FILE = 1;
	private final int DIALOG_SIMPLE_LIST = 2;
	private final int DIALOG_SINGLE_CHOICE_LIST = 3;
	private final int DIALOG_MULTI_CHOICE_LIST = 4;	
	private ListView lv = null;
	private String[] provinces = new String[]
	{ "辽宁省", "山东省", "河北省", "福建省", "广东省", "黑龙江省" };
	private ButtonOnClick buttonOnClick = new ButtonOnClick(1);

	@Override
	public void onClick(View view)
	{
		switch (view.getId())
		{
			case R.id.btnDeleteFile:
				showDialog(DIALOG_DELETE_FILE);
				break;
			case R.id.btnSimpleList:
				showDialog(DIALOG_SIMPLE_LIST);
				break;
			case R.id.btnSingleChoiceList:
				showDialog(DIALOG_SINGLE_CHOICE_LIST);
				break;
			case R.id.btnMultiChoiceList:
				showDialog(DIALOG_MULTI_CHOICE_LIST);
				break;
			case R.id.btnRemoveDialog:
				removeDialog(DIALOG_DELETE_FILE);
				removeDialog(DIALOG_SIMPLE_LIST);
				removeDialog(DIALOG_SINGLE_CHOICE_LIST);
				removeDialog(DIALOG_MULTI_CHOICE_LIST);
				break;
		}
	}

	@Override
	protected Dialog onCreateDialog(int id)
	{
		Log.d("dialog", String.valueOf(id));
		switch (id)
		{
			case DIALOG_DELETE_FILE:
				return new AlertDialog.Builder(this).setIcon(
						R.drawable.question).setTitle("是否删除文件")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener()
								{
									public void onClick(DialogInterface dialog,
											int whichButton)
									{
										new AlertDialog.Builder(Main.this)
												.setMessage("文件已经被删除.")
												.create().show();
									}
								}).setNegativeButton("取消",
								new DialogInterface.OnClickListener()
								{
									public void onClick(DialogInterface dialog,
											int whichButton)
									{

										new AlertDialog.Builder(Main.this)
												.setMessage(
														"您已经选择了取消按钮，该文件未被删除.")
												.create().show();
									}
								}).create();

			case DIALOG_SIMPLE_LIST:
				return new AlertDialog.Builder(this).setTitle("选择省份").setItems(
						provinces, new DialogInterface.OnClickListener()
						{
							public void onClick(DialogInterface dialog,
									int which)
							{
								final AlertDialog ad = new AlertDialog.Builder(
										Main.this).setMessage(
										"您已经选择了: " + which + ":"
												+ provinces[which]).show();
								android.os.Handler hander = new android.os.Handler();
								hander.postDelayed(new Runnable()
								{
									@Override
									public void run()
									{
										ad.dismiss();

									}
								}, 5 * 1000);

							}
						}).create();
			case DIALOG_SINGLE_CHOICE_LIST:
				return new AlertDialog.Builder(this).setTitle("选择省份")
						.setSingleChoiceItems(provinces, 1, buttonOnClick)
						.setPositiveButton("确定", buttonOnClick)
						.setNegativeButton("取消", buttonOnClick).create();
			case DIALOG_MULTI_CHOICE_LIST:
				AlertDialog ad = new AlertDialog.Builder(this).setIcon(
						R.drawable.image).setTitle("选择省份").setMultiChoiceItems(
						provinces, new boolean[]
						{ false, true, false, true, false, false },
						new DialogInterface.OnMultiChoiceClickListener()
						{
							public void onClick(DialogInterface dialog,
									int whichButton, boolean isChecked)
							{

							}
						}).setPositiveButton("确定",
						new DialogInterface.OnClickListener()
						{
							public void onClick(DialogInterface dialog,
									int whichButton)
							{
								int count = lv.getCount();
								String s = "您选择了:";
								for (int i = 0; i < provinces.length; i++)
								{

									if (lv.getCheckedItemPositions().get(i))
										s += i + ":"
												+ lv.getAdapter().getItem(i)
												+ "  ";

								}
								if (lv.getCheckedItemPositions().size() > 0)
								{
									new AlertDialog.Builder(Main.this)
											.setMessage(s).show();
								}
								else
								{
									new AlertDialog.Builder(Main.this)
											.setMessage("您未选择任何省份").show();

								}

							}
						}).setNegativeButton("取消", null).create();
				lv = ad.getListView();
				return ad;

		}

		return null;
	}

	private class ButtonOnClick implements DialogInterface.OnClickListener
	{
		private int index;

		public ButtonOnClick(int index)
		{
			this.index = index;
		}

		@Override
		public void onClick(DialogInterface dialog, int whichButton)
		{
			if (whichButton >= 0)
			{
				index = whichButton;
			}
			else
			{
				if (whichButton == DialogInterface.BUTTON_POSITIVE)
				{
					new AlertDialog.Builder(Main.this).setMessage(
							"您已经选择了： " + index + ":" + provinces[index]).show();
				}
				else if (whichButton == DialogInterface.BUTTON_NEGATIVE)
				{
					new AlertDialog.Builder(Main.this).setMessage("您什么都未选择.")
							.show();
				}
			}
		}
	}

	@Override
	protected void onPrepareDialog(int id, Dialog dialog)
	{
		super.onPrepareDialog(id, dialog);
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Button btnDeleteFile = (Button) findViewById(R.id.btnDeleteFile);
		Button btnSimpleList = (Button) findViewById(R.id.btnSimpleList);
		Button btnSingleChoiceList = (Button) findViewById(R.id.btnSingleChoiceList);
		Button btnMultiChoiceList = (Button) findViewById(R.id.btnMultiChoiceList);
		Button btnRemoveDialog = (Button)findViewById(R.id.btnRemoveDialog);
		btnDeleteFile.setOnClickListener(this);
		btnSimpleList.setOnClickListener(this);
		btnSingleChoiceList.setOnClickListener(this);
		btnMultiChoiceList.setOnClickListener(this);
		btnRemoveDialog.setOnClickListener(this);
	}
}
