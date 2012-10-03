package chsw.pwmkr;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class mainActivity extends Activity
{   
	private Integer iinput1,iinput2,length;
	private String sinput1,sinput2;
	private EditText iiText1, iiText2, lengthText, sinText1, sinText2, output;
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main);
	    File check = this.getFileStreamPath(Globals.filename);
	    iiText1 = (EditText)findViewById(R.id.iinput1);
	    iiText2 = (EditText)findViewById(R.id.iinput2);
	    sinText1 = (EditText)findViewById(R.id.sinput1);
	    sinText2 = (EditText)findViewById(R.id.sinput2);
	    lengthText = (EditText)findViewById(R.id.length);
	    output = (EditText)findViewById(R.id.output);
	    //output.setKeyListener(null);
	    if(!check.exists())
	    {
	    	settingsDialog("No Settings Exist, Would You Like to Edit Settings Now?");
	    }
	    else
	    {
	    	readSettings();
	    }
	}

	private void settingsDialog(String title)
	{
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

    	alert.setTitle(title);
    	
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
	
	public void readSettings()
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
			String[] lines = mystring.toString().split("\n");
			
			try
			{
				for(int i=0; i<3; i++)
				{
					String[] temp = lines[i].split(": ");
					int val = Integer.parseInt(temp[1]);
					
					switch(i)
					{
						case 0:
							Globals.rows = val;
							break;
						case 1:
							Globals.columns = val;
							break;
						case 2:
							Globals.defSeed = val;
							break;
					}
				}
			}
			catch(Exception e)
			{
				settingsDialog("Settings Are Malformed, Would You Like to Edit Settings Now?");
			}
		}
		catch (Exception e) { }
	}

	public void close(View v)
	{
		finish();
		System.exit(0);
	}
	
	public void getPW(View v)
	{
		readSettings();
		GenerateFile.seedGen("symbols", Globals.symbolClass, Globals.rows, Globals.columns, Globals.defSeed, this);
		try
		{
			iinput1 = Integer.parseInt(iiText1.getText().toString());
			iinput2 = Integer.parseInt(iiText2.getText().toString());
			sinput1 = sinText1.getText().toString();
			sinput2 = sinText2.getText().toString();
			length = Integer.parseInt(lengthText.getText().toString());
			String pw = Algorithm.getPW("symbols", sinput1, sinput2, iinput1, iinput2, length, this);
			Toast.makeText(this, pw, Toast.LENGTH_LONG).show();
			output.setText(pw);
		}
		catch (FileNotFoundException e) { }
	}
}