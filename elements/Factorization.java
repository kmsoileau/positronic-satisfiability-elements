package positronic.satisfiability.elements;

public class Factorization 
{
	public static void main(String[] args) throws Exception
	{
		IBooleanVariable A=BooleanVariable.getBooleanVariable("A");
		IBooleanVariable B=BooleanVariable.getBooleanVariable("B");
		IBooleanVariable C=BooleanVariable.getBooleanVariable("C");
		IBooleanVariable D=BooleanVariable.getBooleanVariable("D");
		IBooleanVariable E=BooleanVariable.getBooleanVariable("E");
		IProblem prob=Problem.randomProblem(
				new IBooleanVariable[]{A,B,C,D,E},
				10);
		
		System.out.println(prob);
		
		String ret=((Problem)prob).factorization();
		System.out.println(" "+ret);
	}
	
	private IBooleanVariable root;
	private Factorization subGraph1;
	private Factorization subGraph2;
	
	public Factorization()
	{
	}
	
	public String toString()
	{
		String ret="";
		if(this.subGraph1!=null)
			ret+=">"+this.root.getName()+":"+this.subGraph1;
		if(this.subGraph2!=null)
			ret+="\n>$"+this.root.getName()+":"+this.subGraph2;
		return ret;
	}
}

class Split
{
	BooleanVariable var;
	Problem prob1;
	Problem prob2;
	int measure;
}
