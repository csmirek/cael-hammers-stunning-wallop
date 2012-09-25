package chsw.pwmkr;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;

public class mainActivity extends Activity implements OnLongClickListener, OnClickListener{
    /** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main);
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

	public boolean onLongClick(View v) {
		// TODO Auto-generated method stub
		return false;
	}
}