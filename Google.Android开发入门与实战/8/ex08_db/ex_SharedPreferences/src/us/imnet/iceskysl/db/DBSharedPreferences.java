package us.imnet.iceskysl.db;
//Download by http://www.codefans.net
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

public class DBSharedPreferences extends Activity {
	public static final String SETTING_INFOS = "SETTING_Infos";
	public static final String NAME = "NAME";
	public static final String PASSWORD = "PASSWORD";
	
	private EditText field_name;
	private EditText filed_pass;


	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //Find VIew
        field_name = (EditText) findViewById(R.id.name);
        filed_pass = (EditText) findViewById(R.id.password);
        // Restore preferences
        SharedPreferences settings = getSharedPreferences(SETTING_INFOS, 0);
		String name = settings.getString(NAME, "");
		String password = settings.getString(PASSWORD, "");
		//Set value
		field_name.setText(name);
		filed_pass.setText(password);

    }
    
    @Override
    protected void onStop(){
        super.onStop();
		SharedPreferences settings = getSharedPreferences(SETTING_INFOS, 0);
		settings.edit()
			.putString(NAME, field_name.getText().toString())
			.putString(PASSWORD, filed_pass.getText().toString())
			.commit();
    }
}