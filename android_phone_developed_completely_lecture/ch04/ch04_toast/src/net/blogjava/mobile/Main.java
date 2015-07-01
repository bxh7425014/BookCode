package net.blogjava.mobile;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends Activity implements OnClickListener
{

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
			case R.id.btnTextToast:
				Toast textToast = Toast.makeText(this, "今天的天气真好！\n哈，哈，哈！",
						Toast.LENGTH_LONG);

				// textToast.setText("现在是几点？");
				textToast.show();
				break;

			case R.id.btnImageToast:
				View view = getLayoutInflater().inflate(R.layout.toast, null);
				TextView textView = (TextView) view.findViewById(R.id.textview);
				textView.setText("今天的天气真好！\n哈，哈，哈！");
				Toast toast = new Toast(this);
				toast.setDuration(Toast.LENGTH_LONG);
				toast.setView(view);
				toast.show();

				break;
		}

	
	


		
		

	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Button btnTextToast = (Button) findViewById(R.id.btnTextToast);
		Button btnImageToast = (Button) findViewById(R.id.btnImageToast);
		btnTextToast.setOnClickListener(this);
		btnImageToast.setOnClickListener(this);
	}
}