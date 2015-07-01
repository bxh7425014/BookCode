package net.blogjava.mobile;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.RatingBar.OnRatingBarChangeListener;

public class Main extends Activity implements OnRatingBarChangeListener
{
	private RatingBar smallRatingBar;
	private RatingBar indicatorRatingBar;
	private TextView textView;

	@Override
	public void onRatingChanged(RatingBar ratingBar, float rating,
			boolean fromUser)
	{
		smallRatingBar.setRating(rating);
		indicatorRatingBar.setRating(rating);
		if (ratingBar.getId() == R.id.ratingbar1)
			textView.setText("ratingbar1的分数：" + rating);
		else
			textView.setText("ratingbar2的分数：" + rating);
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		RatingBar ratingBar1 = (RatingBar) findViewById(R.id.ratingbar1);
		RatingBar ratingBar2 = (RatingBar) findViewById(R.id.ratingbar2);
		textView = (TextView) findViewById(R.id.textview);

		ratingBar1.setOnRatingBarChangeListener(this);
		ratingBar2.setOnRatingBarChangeListener(this);
		
		smallRatingBar = (RatingBar) findViewById(R.id.smallRatingbar);
		indicatorRatingBar = (RatingBar) findViewById(R.id.indicatorRatingbar);
	}
}