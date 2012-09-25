package chsw.pwmkr;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

public class Algorithm {

	private Integer Aprime, Bprime, Cprime, Dprime;
	private BigInteger bfi;
	
	public Algorithm() { }
	
	private void setValues(String A, String B, Integer C, Integer D)
	{
		Aprime = Math.abs(A.hashCode());
		Bprime = Math.abs(B.hashCode());

		Cprime = Math.abs((B + C.toString()).hashCode());
		Dprime = Math.abs((A + D.toString()).hashCode());

		bfi = BigInteger.valueOf(Cprime);
		bfi = bfi.multiply(BigInteger.valueOf(Dprime));

		String bfiString = bfi.toString();

		while (bfiString.endsWith("0"))
		{
			bfiString = bfiString.substring(0, bfiString.length() - 1);
		}
		bfi = new BigInteger(bfiString);

		while (bfiString.length() < 500)
		{
			bfi = bfi.multiply(bfi);
			bfiString = bfi.toString();
		}
	}
	
	public String getPW(String filename, String input1, String input2, int row, int col,
			int length) throws FileNotFoundException
	{
		setValues(input1,input2,row,col);
		
		File readFile = new File(filename + ".txt");
		Scanner scan = new Scanner(readFile);
		ArrayList<String> inRAMFile = new ArrayList<String>();

		while (scan.hasNextLine())
		{
			inRAMFile.add(scan.nextLine());
		}
		scan.close();
		
		int rows = inRAMFile.size();
		int columns = inRAMFile.get(0).length();

		int nrow = Math.abs((Aprime * row) % rows);
		int ncol = Math.abs((Bprime * col) % columns);
		int nLength = Math.abs((Cprime * length) % (rows * columns));

		char[] offset = new char[nLength];
		for (int i = 0; i < nLength; i++)
		{
			offset[i] = inRAMFile.get((nrow + (i + ncol) / columns) % rows)
					.charAt((ncol + i) % columns);
		}

		String bfiString = bfi.toString();

		ArrayList<String> subs = new ArrayList<String>();

		for (int i = 0; i < length; i++)
		{
			subs.add(bfiString.substring(i * bfiString.length() / length, (i + 1)
					* bfiString.length() / length));
		}

		char[] pw = new char[length];

		for (int i = 0; i < length; i++)
		{
			BigInteger tempBig = new BigInteger(subs.get(i));
			tempBig = tempBig.mod(BigInteger.valueOf(nLength));
			Integer temp = new Integer(tempBig.toString());

			pw[i] = offset[temp];
		}
		String theSlab = new String(pw);
		return theSlab;
	}
}
