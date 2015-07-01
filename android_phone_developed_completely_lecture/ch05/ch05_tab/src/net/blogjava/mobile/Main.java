package net.blogjava.mobile;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TabHost;

public class Main extends TabActivity implements OnClickListener
{

	@Override
	public void onClick(View view) 
	{
		//getTabHost().setCurrentTab(2);		
		getTabHost().setCurrentTabByTag("tab3");
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		TabHost tabHost = getTabHost();
		LayoutInflater.from(this).inflate(R.layout.main,
				tabHost.getTabContentView(), true);
		tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("«–ªª±Í«©")
				.setContent(R.id.tab1)); 

		tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("œ‡≤·",getResources().getDrawable(R.drawable.icon1))
				.setContent(new Intent(this, GalleryActivity.class)));
		tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("∆¿∑÷")
				.setContent(new Intent(this, RatingListView.class)));

		Button button = (Button)findViewById(R.id.button);
		button.setOnClickListener(this);
	}
}