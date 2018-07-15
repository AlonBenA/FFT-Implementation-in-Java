import java.util.ArrayList;
import java.util.HashMap;


public class FFTTree {
	private FFTTree evenBranch;
	private FFTTree oddBranch;
	private ArrayList<ComplexNumber> polynomial = new ArrayList<ComplexNumber>();
	private HashMap<ComplexNumber,ComplexNumber> answers = new HashMap<ComplexNumber,ComplexNumber>();
	private int RootsOfUnity;
	
	public FFTTree(ArrayList<ComplexNumber> vector, int RootsOfUnity)
	{
		this.RootsOfUnity = RootsOfUnity;
		initializeTree(vector,RootsOfUnity);			
	}
	
	
	private void initializeTree(ArrayList<ComplexNumber> vector,int RootsOfUnity)
	{
		int i;
		ArrayList<ComplexNumber> even = new ArrayList<>();
		ArrayList<ComplexNumber> odd = new ArrayList<>();
		
		if(vector.size() == 1)
		{
			polynomial.add(vector.get(0));
		}
		else
		{
			ComplexNumber c = new ComplexNumber(0, 0);
			//initialize the W answers 
			this.polynomial = vector;
			
	        for (i = 0; i < vector.size(); i+=2) {
	            even.add(vector.get(i));
	        }
	        
	        for (i = 1; i < vector.size(); i+=2) {
	            odd.add(vector.get(i));
	        }
	        
	        evenBranch = new FFTTree(even,RootsOfUnity/2);
	        oddBranch= new FFTTree(odd,RootsOfUnity/2);
			
		}
	}

	public void print()
	{
		if(polynomial.size() == 1)
		{
			System.out.println(polynomial.get(0));
		}
		else
		{
			System.out.println(polynomial);
			System.out.println("even :");
			evenBranch.print();
			System.out.println("odd :");
			oddBranch.print();
		}
	}

	public ArrayList<ComplexNumber> fft()
	{
		ArrayList<ComplexNumber> Wanswers = new ArrayList<ComplexNumber>();
		int i;
		
		for(i=0; i< RootsOfUnity; i++)
		{
			double kth = 2 * i * Math.PI / RootsOfUnity;
			ComplexNumber x = new ComplexNumber(Math.cos(kth), Math.sin(kth));
			ComplexNumber c = calculateWAnswers(x);
			Wanswers.add(c);
		}
		return Wanswers;
	}
	
	
	private ComplexNumber calculateWAnswers(ComplexNumber x)
	{
		
		if(polynomial.size() == 1)
		{
			return polynomial.get(0);
		}
		else if(answers.containsKey(x))
		{
			 return answers.get(x);
		}
		else
		{
				 ComplexNumber x2 = x.multiply(x);
				 ComplexNumber evenb = evenBranch.calculateWAnswers(x2);
				 
				 ComplexNumber oddb = oddBranch.calculateWAnswers(x2);
				 oddb = oddb.multiply(x);
				 
				 ComplexNumber c = evenb.add(oddb);
				 answers.put(x, c);
				 return c;
		}	
		
	}
}
