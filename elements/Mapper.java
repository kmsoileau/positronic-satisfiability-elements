/*
 * Mapping.java	1.01 05/11/02
 *
 * Copyright 2004-2005 Positronic Software.
 *
 *
 */

package positronic.satisfiability.elements;

import java.util.ArrayList;
import java.util.Vector;

import positronic.satisfiability.bitstring.BitStringFixer;
import positronic.satisfiability.bitstring.IBitString;
import positronic.satisfiability.exceptions.MapperException;
import positronic.satisfiability.naturalnumber.INaturalNumber;
import positronic.satisfiability.naturalnumber.NaturalNumber;
import positronic.satisfiability.naturalnumber.NaturalNumberFixer;

/**
 * <p>Title: <tt>Mapper</tt> class</p>
 * <p>Description: This class, an extension of <tt>Problem</tt>, is created from
 * pairs of instances of <tt>Problem</tt> passed to one of the <tt>Mapper</tt> 
 * constructors. An instance of <tt>Mapper</tt> is satisfied if and only if for 
 * some pair of <tt>Problem</tt>'s, both <tt>Problem</tt>'s are satisfied. For 
 * instance, suppose we define 
 * <p>
 * <tt>...</tt>
 * <p><tt>IProblem[] ipr1=new IProblem[]{p7,p9,p2};</tt>
 * <p><tt>IProblem[] ipr2=new IProblem[]{p3,p8,p1};
 * <p><tt>IBooleanVariable[] bool=new IBooleanVariable[]{b1,b2};
 * <p><tt>IProblem map1=new Mapper(ip1,ipr2,bool);
 * <p><tt>...</tt>
 * </p>
 * <p>Then <tt>map1</tt> will be satisfied when <p>(1) <tt>p7</tt> and <tt>p3</tt> are 
 * both satisfied, or <p>(2) <tt>p9</tt> and <tt>p8</tt> are both satisfied, or <p>(3) 
 * <tt>p2</tt> and <tt>p1</tt> are both satisfied.
 * <p>Copyright (c) 2005</p>
 * <p>Company: Positronic Software</p>
 * @author Kerry Michael Soileau
 * @version 1.01
 */

public class Mapper extends Problem implements IProblem
{
	private static final long serialVersionUID = 4913368323750298820L;

	private static String binCount(int bitsrequired, long count) throws Exception
	{
		return reverse(new NaturalNumber(count).toBits()).substring(NaturalNumber.getLength()-bitsrequired);
	}

	private static IProblem map(IProblem[] p1, IProblem[] p2) throws Exception
	{
	    if(p1==null || p2==null) 
	    	throw new MapperException("A null IProblem[] was passed to a constructor.");
	    if(p1.length==0 || p2.length==0) 
	    	throw new MapperException("An IProblem[] of zero length was passed to a constructor.");
	    if(p1.length!=p2.length) 
	    	throw new MapperException("IProblem[]'s of differing lengths were passed to a constructor.");
	    IBooleanVariable[] b = new IBooleanVariable[p1.length-1];
	    for(int i=0;i<b.length;i++)
	      b[i]=BooleanVariable.getBooleanVariable();
	    return map(p1,p2,b);
	}
	  
	private static IProblem map(IProblem[] p1, IProblem[] p2, IBooleanVariable[] b) throws Exception
	{
	    if(p1==null || p2==null || b==null) 
	    	throw new MapperException("A null IProblem[] was passed to a constructor.");
	    if(p1.length==0 || p2.length==0) 
	    	throw new MapperException("An IProblem[] of zero length was passed to a constructor.");
	    if(p1.length!=p2.length) 
	    	throw new MapperException("IProblem[]'s of differing lengths were passed to a constructor.");
	    if(b.length!=p1.length-1) 
	    	throw new MapperException("IBooleanVariable[] of improper length was passed to a constructor. Proper length in this case is "+(p1.length-1)+".");
	    IProblem[] d=new IProblem[p1.length];
	    for(int i=0;i<d.length;i++)
	      d[i]=new Conjunction(p1[i],p2[i]);
	    IProblem e=new Disjunction(d,b);
	    return e;
	  }
	
	private static String reverse(String bits)
	{
		if(bits.length()==1)
			return bits;
		return(reverse(bits.substring(1)).concat(bits.substring(0, 1)));
	}
	  
	public Mapper(ArrayList<Integer> integerArray, IBitString index, INaturalNumber valNN) throws Exception
	{
		this((Integer[])integerArray.toArray(new Integer[0]), index, valNN);
	}
	
	public Mapper(int[] integerArray, IBitString index, INaturalNumber valNN) throws Exception
	{
		int num=integerArray.length;
		ProblemPair[] pp=new ProblemPair[num];
		int bitsrequired=(int)(Math.ceil(Math.log(1d*num)/Math.log(2d)));
		for(int i=0;i<pp.length;i++)
			pp[i]=new ProblemPair(new BitStringFixer(index,binCount(bitsrequired, i)),new NaturalNumberFixer(valNN,integerArray[i]));
	
		this.setClauses(new Mapper(pp).getClauses());
	}
	
	public Mapper(Integer[] integerArray, IBitString index, INaturalNumber valNN) throws Exception
	{
		int num=integerArray.length;
		ProblemPair[] pp=new ProblemPair[num];
		int bitsrequired=(int)(Math.ceil(Math.log(1d*num)/Math.log(2d)));
		for(int i=0;i<pp.length;i++)
			pp[i]=new ProblemPair(new BitStringFixer(index,binCount(bitsrequired, i)),new NaturalNumberFixer(valNN,integerArray[i]));
	
		this.setClauses(new Mapper(pp).getClauses());
	}
	
	public Mapper(IProblem[] p1, IProblem[] p2) throws Exception
	{
	    IProblem p=map(p1,p2);
	    if(p!=null)
	      this.setClauses(p.getClauses());
    }
	
	public Mapper(IProblem[] p1, IProblem[] p2, IBooleanVariable[] b) throws Exception
	{
		IProblem p=map(p1,p2,b);
		if(p!=null)
			this.setClauses(p.getClauses());
	}
	
	public Mapper(Long[] integerArray, IBitString index, INaturalNumber valNN) throws Exception
	{
		int num=integerArray.length;
		ProblemPair[] pp=new ProblemPair[num];
		int bitsrequired=(int)(Math.ceil(Math.log(1d*num)/Math.log(2d)));
		for(int i=0;i<pp.length;i++)
			pp[i]=new ProblemPair(new BitStringFixer(index,binCount(bitsrequired, i)),new NaturalNumberFixer(valNN,integerArray[i]));
	
		this.setClauses(new Mapper(pp).getClauses());
	}
	
	public Mapper(ProblemPair[] pair) throws Exception
	{
	  	IProblem[] p1=new IProblem[pair.length];
	  	IProblem[] p2=new IProblem[pair.length];
	  	
	  	for(int i=0;i<pair.length;i++)
	  	{
	  		p1[i]=pair[i].getFirst();
	  		p2[i]=pair[i].getSecond();
	  	}
	  	this.setClauses(new Mapper(p1,p2).getClauses());
  	}
	
	public Mapper(ProblemPair[] pair, IBooleanVariable[] b) throws Exception
	{
		IProblem[] p1=new IProblem[pair.length];
	  	IProblem[] p2=new IProblem[pair.length];
	  	
	  	for(int i=0;i<pair.length;i++)
	  	{
	  		p1[i]=pair[i].getFirst();
	  		p2[i]=pair[i].getSecond();
	  	}
	  	this.setClauses(new Mapper(p1,p2,b).getClauses());
	}
	
	public Mapper(Vector<Integer> integerArray, IBitString index, INaturalNumber valNN) throws Exception
	{
		this((Integer[])integerArray.toArray(new Integer[0]), index, valNN);
	}
}