package positronic.satisfiability.elements;

import java.util.HashMap;
import java.util.Iterator;

import positronic.satisfiability.bitstring.BitStringFixer;
import positronic.satisfiability.bitstring.IBitString;
import positronic.satisfiability.naturalnumber.INaturalNumber;
import positronic.satisfiability.naturalnumber.NaturalNumberFixer;

public class HashMapper extends Problem implements IProblem
{
	private static final long serialVersionUID = 6587394316120960856L;

	public HashMapper(HashMap<Long,Long> h, INaturalNumber X, INaturalNumber Y) throws Exception
	{
		this(X,Y,h);
	}
	
	public HashMapper(HashMap<String,String> h, IBitString X, IBitString Y) throws Exception
	{
		this(X,Y,h);
	}
	
	public HashMapper(IBitString X, IBitString Y, HashMap<String,String> h) throws Exception
	{
	    IBooleanVariable[] b=new IBooleanVariable[h.size()-1];
	    for(int i=0;i<b.length;i++)
	    	b[i]=BooleanVariable.getBooleanVariable();
	    
	    Iterator<String> it=h.keySet().iterator();
	    
	    ProblemPair[] pp=new ProblemPair[h.size()];
	    int count=0;
	    while(it.hasNext())
	    {
	    	String key=it.next();
	    	pp[count++]=new ProblemPair(new BitStringFixer(X,key),new BitStringFixer(Y,h.get(key)));
	    }
	    
	    this.setClauses(new Mapper(pp,b).getClauses());
	}
	
	public HashMapper(INaturalNumber X, INaturalNumber Y, HashMap<Long,Long> h) throws Exception
	{
	    IBooleanVariable[] b=new IBooleanVariable[h.size()-1];
	    for(int i=0;i<b.length;i++)
	    	b[i]=BooleanVariable.getBooleanVariable();
	    
	    Iterator<Long> it=h.keySet().iterator();
	    
	    ProblemPair[] pp=new ProblemPair[h.size()];
	    int count=0;
	    while(it.hasNext())
	    {
	    	Long key=it.next();
	    	pp[count++]=new ProblemPair(new NaturalNumberFixer(X,key),new NaturalNumberFixer(Y,h.get(key)));
	    }
	    
	    this.setClauses(new Mapper(pp,b).getClauses());
	}
}
