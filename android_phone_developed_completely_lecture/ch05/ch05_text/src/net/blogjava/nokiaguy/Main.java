package net.blogjava.nokiaguy;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class Main extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        TextView textView = (TextView)findViewById(R.id.textview);
        //textView.setText("abc http://www.csdn.net <h2>aa</h2>");
      /*  textView.setText("<b>text2:</b> This is some other" + 
      "text, with a <a href='http://www.google.com'>link</a> specified" +       
      "via an &lt;a&gt; tag.  Use a \'tel:\' URL" + 
      "to <a href='tel:4155551212'>dial a phone number</a>.");
      */
       // textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setText(Html.fromHtml("<font color='#FF0000'>这段文字的字体颜色为红色。</font>"));
    }
}