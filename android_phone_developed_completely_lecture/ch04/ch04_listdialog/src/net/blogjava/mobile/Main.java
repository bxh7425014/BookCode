package net.blogjava.mobile;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class Main extends Activity implements OnClickListener
{
	private String[] provinces = new String[]
	{ "辽宁省", "山东省", "河北省", "福建省", "广东省", "黑龙江省" };
	private ButtonOnClick buttonOnClick = new ButtonOnClick(1);
	private ListView lv = null;
	private void showListDialog()
	{
		new AlertDialog.Builder(this).setTitle("选择省份").setItems(provinces,
				new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int which)
					{
						final AlertDialog ad = new AlertDialog.Builder(
								Main.this).setMessage(
								"您已经选择了: " + which + ":" + provinces[which])
								.show();
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
				}).show();
	}

	private void showSingleChoiceDialog()
	{

		new AlertDialog.Builder(this).setTitle("选择省份").setSingleChoiceItems(
				provinces, 1, buttonOnClick).setPositiveButton("确定",
				buttonOnClick).setNegativeButton("取消", buttonOnClick).show();
		
	}


	private void showMultiChoiceDialog()
	{

		AlertDialog ad = new AlertDialog.Builder(this)
				.setIcon(R.drawable.image).setTitle("选择省份")
				.setMultiChoiceItems(provinces, new boolean[]
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
		ad.show();

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
				// dialog.cancel(); 				
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
	public void onClick(View view)
	{
		switch (view.getId())
		{
			case R.id.btnListDialog:
			{
				showListDialog();
				break;
			}
			case R.id.btnSingleChoiceDialog:
			{
				showSingleChoiceDialog();
				break;
			}
			case R.id.btnMultiChoiceDialog:
			{
				showMultiChoiceDialog();
				break;
			}
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Button btnListDialog = (Button) findViewById(R.id.btnListDialog);
		Button btnSingleChoiceDialog = (Button) findViewById(R.id.btnSingleChoiceDialog);
		Button btnMultiChoiceDialog = (Button) findViewById(R.id.btnMultiChoiceDialog);
		btnListDialog.setOnClickListener(this);
		btnSingleChoiceDialog.setOnClickListener(this);
		btnMultiChoiceDialog.setOnClickListener(this);
	}
}