package chsw.pwmkr;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.Toast;

public class mainActivity extends Activity implements OnLongClickListener, OnClickListener
{   
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main);
	    File check = this.getFileStreamPath(Globals.filename);
	    if(!check.exists())
	    {
	    	Toast.makeText(this, "no settings exist", Toast.LENGTH_LONG).show();
	    	noSettingsDialog();
	    }
	}

	private void noSettingsDialog()
	{
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

    	alert.setTitle("No Settings Exist, Would You Like to Edit Settings Now?");
    	
    	alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() 
	    	{
	        	public void onClick(DialogInterface dialog, int whichButton) 
	        	{
	        		Intent intent = new Intent(getBaseContext(), Settings.class);
	        		startActivityForResult(intent, 0);
	        	}
	        });

    	alert.setNegativeButton("No", new DialogInterface.OnClickListener() 
	    	{
				public void onClick(DialogInterface dialog, int whichButton) 
				{
					//Do nothing
				}
	    	});

    	alert.show();
	}
	
	public void goToSettings(View v)
	{
		Intent intent = new Intent(getBaseContext(), Settings.class);
		startActivityForResult(intent, 0);
	}
	
	public void onClick(View v)
	{
		try
		{
			FileInputStream fis = openFileInput(Globals.filename);
			int c;
			StringBuffer mystring = new StringBuffer();
			while((c = fis.read()) > -1)
			{
				mystring.append((char)c);
			}
			Toast.makeText(this, mystring, Toast.LENGTH_LONG).show();
		}
		catch (Exception e)
		{
			Toast.makeText(this, "no settings exist", Toast.LENGTH_LONG).show();
		}
	}

	public boolean onLongClick(View v) {
		// TODO Auto-generated method stub
		return false;
	}
}