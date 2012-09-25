package chsw.pwmkr;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GenerateFile
{
	private static Pattern pat;
	private static Matcher myMatch;
	private static Random rand;
	
	private GenerateFile() {}

	public static void defaultGen(String name, String characterClass, int rows,
			int columns)
	{
		pat = Pattern.compile(characterClass);
		rand = new Random();

		generateFile(name, rows, columns);
	}

	public static void seedGen(String name, String characterClass, int rows,
			int columns, int seed)
	{
		pat = Pattern.compile(characterClass);
		rand = new Random(seed);

		generateFile(name, rows, columns);
	}

	private static void generateFile(String name, int rows, int columns)
	{
		int[] intArray = new int[rows * columns];
		for (int i = 0; i < rows * columns; i++)
		{
			intArray[i] = nextIntWrap(rand);
		}

		try
		{
			PrintWriter fout = new PrintWriter(new FileWriter(name + ".txt"));
			for (int j = 0; j < rows; j++)
			{
				for (int k = 0; k < columns; k++)
				{
					fout.print((char) intArray[j * rows + k]);
				}
				fout.println();
			}
			fout.close();
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