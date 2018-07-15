import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class Main {

	public static void main(String[] args) 
	{
		Question_2();
	}
	
	public static void Question_2()
	{
		String inputFileName = "input.txt";
		int N;
		ArrayList<ComplexNumber> vector = new ArrayList<ComplexNumber>();
		N = readFile(vector, inputFileName);
		ArrayList<ComplexNumber> Wanswers = FFT(vector,N);
		System.out.println(Wanswers);
		writeToFile(Wanswers);
	}

	
	public static void Q3()
	{
		int i;
		String inputFileName2 = "input2.txt"; // first vector
		String inputFileName3 = "input3.txt"; // second vector
		ArrayList<ComplexNumber> vector2 = new ArrayList<ComplexNumber>();
		ArrayList<ComplexNumber> vector3 = new ArrayList<ComplexNumber>();
		ArrayList<ComplexNumber> MultiplyW2AndW3 = new ArrayList<ComplexNumber>();
		
		//read the 2 files
		int N2 = readFile(vector2, inputFileName2);
		int N3 = readFile(vector3,inputFileName3);
		
		System.out.println("vector2 = " + vector2);
		System.out.println("vector3 = " + vector3);
		
		//do the fft on both answers
		ArrayList<ComplexNumber> Wanswers2 = FFT(vector2,N2);
		ArrayList<ComplexNumber> Wanswers3 = FFT(vector3,N3);
		
		System.out.println("Wanswers2 = " + Wanswers2);
		System.out.println("Wanswers3 = " + Wanswers3);
		
		System.out.println();
		
		//Multiply the two answers 
		System.out.println("Multiply the two answers ");
		for(i=0;i<Wanswers2.size();i++)
		{
			MultiplyW2AndW3.add(Wanswers2.get(i).multiply(Wanswers3.get(i)));
			System.out.println(i + ") " +MultiplyW2AndW3.get(i).toString());
		}
		
		System.out.println();
		//reverse the MultiplyW2AndW3
		ArrayList<ComplexNumber> vector4 = new ArrayList<ComplexNumber>();
		
		for(i=MultiplyW2AndW3.size() - 1;i >=0 ; i--)
		{
			vector4.add(MultiplyW2AndW3.get(i));
		}
		
		System.out.println("before the reverse the MultiplyW2AndW3");
		System.out.println(MultiplyW2AndW3);
		System.out.println("after the reverse the MultiplyW2AndW3");
		System.out.println(vector4);
		
		
		//do fft to the Multiply
		ArrayList<ComplexNumber> Wanswers4 = FFT(vector4,N2);
		System.out.println("fft on the Wanswers4 = " + Wanswers4);
			
		//Divide by N
		
		for(i=0; i< Wanswers4.size(); i++)
		{
			ComplexNumber x = new ComplexNumber(N2, 0);
			Wanswers4.set(i, Wanswers4.get(i).divides(x));
		}
		
		System.out.println();	
		System.out.println("Divide by " + N2);
		System.out.println(Wanswers4);
		
		//Multiply any coordinate in the root
		System.out.println();
		
		

		for(i=0; i< Wanswers4.size(); i++)
		{
			double kth = 2 * i * Math.PI / Wanswers4.size();
			ComplexNumber x = new ComplexNumber(Math.cos(kth), Math.sin(kth));
			System.out.println("Root unit number " + i + " is " + x.toString());
			Wanswers4.set(i, Wanswers4.get(i).multiply(x));
		}
		System.out.println("Multiply any coordinate in the root ");
		System.out.println(Wanswers4);
		
		
		System.out.println("\nreverse :");
		
		for(i=Wanswers4.size()-1;i>=0;i--)
		{
			System.out.print(Wanswers4.get(i) + " , ");
		}
		
	}
	
	public static int readFile(ArrayList<ComplexNumber> vector,String fileName)
	{	
		int N =0;
		BufferedReader br = null;
		FileReader fr = null;

		try {

			fr = new FileReader(fileName);
			br = new BufferedReader(fr);

			String sCurrentLine = br.readLine();
			N = Integer.parseInt(sCurrentLine);
			
			sCurrentLine = br.readLine();
			 ParseLine(sCurrentLine,vector);
			
		} catch (IOException e) {

			e.printStackTrace();

		} finally 
		{

			try {

				if (br != null)
					br.close();

				if (fr != null)
					fr.close();

			} catch (IOException ex) 
			{

				ex.printStackTrace();

			}


		}
		
		
		return N;
	}

	public static void ParseLine(String sCurrentLine, ArrayList<ComplexNumber> vector)
	{
		int i;
		String[] SplitRoots = sCurrentLine.split(",");
		String[] SplitNumber;
		String[] SplitI;
		ComplexNumber c;
		double re,im;
		
		for(i=SplitRoots.length - 1;i >= 0 ;i--)
		{
			SplitI =  SplitRoots[i].split("i");
			if(SplitI[0].contains("+"))
			{
				SplitNumber = SplitI[0].split(Pattern.quote("+"));
				re = Double.parseDouble(SplitNumber[0]);
				im = Double.parseDouble(SplitNumber[1]);
				c = new ComplexNumber(re, im);
				vector.add(c);
			}
			else
			{
				SplitNumber = SplitI[0].split(Pattern.quote("-"));
				
				
				re = 0;
				im = 0;
				
				if(SplitNumber.length == 2)
				{
					re = Double.parseDouble(SplitNumber[0]);
					im = Double.parseDouble(SplitNumber[1]);
					im = im*-1;
				}
				else if(SplitNumber.length == 3)
				{
					re = Double.parseDouble(SplitNumber[1]);
					re = re*-1;
					im = Double.parseDouble(SplitNumber[2]);
					im = im*-1;
				}

				
				c = new ComplexNumber(re, im);
				vector.add(c);
				
			}


		}
		
		
	}
	

	public static ArrayList<ComplexNumber> FFT(ArrayList<ComplexNumber> vector,int N)
	{
		//crate the fft tree
		FFTTree ffttree = new FFTTree(vector,N);
		//find the root
		ArrayList<ComplexNumber> Wanswers = ffttree.fft();
		return Wanswers;
	}
	
	public static void writeToFile(ArrayList<ComplexNumber> Wanswers)
	{
		int i;
		String fileName = "output.txt";
		FileWriter fileWriter;
		PrintWriter printWriter;
		try {
			fileWriter = new FileWriter(fileName);
			printWriter = new PrintWriter(fileWriter);
			
			String Line = "";
			for(i= 0; i < Wanswers.size();i++ )
			{
				if(i == Wanswers.size() - 1)
				{
					Line += Wanswers.get(i).toString();
				}
				else
				{
					Line += Wanswers.get(i).toString() + ",";
				}
			}
			printWriter.print(Line + '\n');
			printWriter.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    
	}
	
}
