package chsw.pwmkr;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;

public class GenerateFile
{
	private static Pattern pat;
	private static Matcher myMatch;
	private static Random rand;
	
	private GenerateFile() {}

	public static void defaultGen(String name, String characterClass, int rows,
			int columns, Context app)
	{
		pat = Pattern.compile(characterClass);
		rand = new Random();

		generateFile(name, rows, columns, app);
	}

	public static void seedGen(String name, String characterClass, int rows,
			int columns, int seed, Context app)
	{
		pat = Pattern.compile(characterClass);
		rand = new Random(seed);

		generateFile(name, rows, columns, app);
	}

	private static void generateFile(String filename, int rows, int columns, Context app)
	{
		int[] intArray = new int[rows * columns];
		for (int i = 0; i < rows * columns; i++)
		{
			intArray[i] = nextIntWrap(rand);
		}

		try
		{
			FileOutputStream fos = app.openFileOutput(filename + ".txt", Context.MODE_PRIVATE);
			for (int j = 0; j < rows; j++)
			{
				for (int k = 0; k < columns; k++)
				{
					fos.write((char) intArray[j * rows + k]);
				}
				fos.write('\n');
			}
			fos.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private static int nextIntWrap(Random rand)
	{
		int next;
		char nextChar;

		do
		{
			next = rand.nextInt(128);
			nextChar = (char) next;
			String nextCharStr = new String("" + nextChar);
			myMatch = pat.matcher(nextCharStr);
		}
		while (!myMatch.matches());

		return next;
	}
}