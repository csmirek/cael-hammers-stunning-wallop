package chsw.pwmkr;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class Settings extends Activity
{
	private EditText redit, cedit, dedit;
	private CheckBox regen;
	
	@Override
    public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		File check = getApplicationContext().getFileStreamPath(Globals.filename);
	    
		redit = (EditText)findViewById(R.id.redit);
		cedit = (EditText)findViewById(R.id.cedit);
		dedit = (EditText)findViewById(R.id.dedit);
		regen = (CheckBox)findViewById(R.id.regen);
		
		if(!check.exists())
	    {
	    	//Toast.makeText(this, "no settings exist", Toast.LENGTH_LONG).show();
	    }
		else
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
					String[] t2 = lines[3].split(": ");
					if(t2[1].equals("Yes"))
					{
						//Toast.makeText(this, "hi", Toast.LENGTH_LONG).show();
						Globals.regen = true;
					}
				}
				catch(Exception e)
				{
					Toast.makeText(this,"malformed settings file, using default values", Toast.LENGTH_LONG).show();
					Globals.rows = 1000;
					Globals.columns = 1000;
					Globals.defSeed = 0;
					Globals.regen = false;
				}
				//Toast.makeText(this, mystring, Toast.LENGTH_LONG).show();
			}
			catch (Exception e)
			{
				//Toast.makeText(this, "no settings exist", Toast.LENGTH_LONG).show();
			}
		}
		redit.setText(Globals.rows.toString());
		cedit.setText(Globals.columns.toString());
		dedit.setText(Globals.defSeed.toString());
		regen.setChecked(Globals.regen);
	}
	
	public void actFinish(View v) 
	{
		Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
	}
	
	public void save(View v)
	{
		try
		{
			int reditVal = Integer.parseInt(redit.getText().toString());
			int ceditVal = Integer.parseInt(cedit.getText().toString());
			
			if(reditVal == 0 || ceditVal == 0)
				throw new Exception();
			
			FileOutputStream fos = openFileOutput(Globals.filename, Context.MODE_PRIVATE);
			fos.write(("Rows: " + redit.getText().toString() + "\n").getBytes());
			fos.write(("Columns: " + cedit.getText().toString() + "\n").getBytes());
			fos.write(("RNG Seed: " + dedit.getText().toString() + "\n").getBytes());
			if(regen.isChecked())
			{
				fos.write(("Regen File: Yes\n").getBytes());
			}
			else
			{
				fos.write(("Regen File: No\n").getBytes());
			}
			fos.close();
		}
		catch (Exception e)
		{
			Toast.makeText(this, "Incorrect settings detected, make sure rows/columns are both greater than 0", Toast.LENGTH_LONG).show();
			return;
		}
		actFinish(v);
	}
}
