/*
 * BooleanLiteral.java	1.0 04/09/07
 *
 * Copyright 2004-2005 Positronic Software.
 *
 *
 */

 package positronic.satisfiability.elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import positronic.satisfiability.exceptions.BooleanLiteralException;

/**
 * A class which represents a Boolean literal. BooleanLiteral is essentially a
 * BooleanVariable together with a boolean indicating whether the
 * BooleanVariable is barred (negated) or not.
 * NOTE: Alternative implementations of BooleanLiteral must not only implement
 * IBooleanLiteral, but must also ensure that any two instances of
 * BooleanLiteral are equal if and only if they agree on the value of barred,
 * and contain equal instances of BooleanVariable.
 *
 * @author  Kerry Michael Soileau
 * <blockquote><pre>
 * ksoileau@yahoo.com
 * http://kerrysoileau.com/index.html
 * </pre></blockquote>
 * @version 1.0, 04/09/07
 * @see Comparable
 * @see BooleanVariable
 * @see IBooleanVariable
 * @see BooleanLiteral
 * @see IBooleanLiteral
 * @see String
 */

public class BooleanLiteral implements IBooleanLiteral
{
	private static HashSet<IBooleanLiteral> instances=new HashSet<IBooleanLiteral>();

	/**
  	* Rather than providing a public constructor for the BooleanLiteral
  	* class, a static factory method is instead provided. This is to avoid duplication 
  	* of a already-existing IBooleanLiteral. The <tt>HashSet instances</tt> is searched 
  	* to determine whether an IBooleanLiteral with the desired internal 
  	* IBooleanVariable and barred-ness already exists; if it does, the instance is 
  	* returned. If not, the private constructor is used to create the desired 
  	* IBooleanLiteral, which is then added to the <tt>HashSet instances</tt> and is 
  	* returned.
  	*/
	public static IBooleanLiteral getBooleanLiteral(IBooleanVariable bv, boolean barred) throws BooleanLiteralException
	{
		if(bv==null)
			throw new BooleanLiteralException("Null IBooleanVariable was passed to getBooleanLiteral method."); //$NON-NLS-1$
		else
		{
			Object[] inarray=BooleanLiteral.getInstances().toArray();

			for(int i=0;i<inarray.length;i++)
			{
				IBooleanLiteral nx=(IBooleanLiteral)(inarray[i]);
				if(nx.isBarred()==barred && nx.getBooleanVariable()!=null && nx.getBooleanVariable().equals(bv))
					return nx;
			}
			return new BooleanLiteral(bv,barred);
		}
	}
	
	public static HashSet<IBooleanLiteral> getInstances()
	{
		return BooleanLiteral.instances;
	}

	public static void interpret(List<?> list) throws BooleanLiteralException
	{
		if(list==null)
			throw new BooleanLiteralException("Null List was passed to interpret method."); //$NON-NLS-1$
		else
			for(int i=0;i<list.size();i++)
			{
				IBooleanLiteral ibool=(IBooleanLiteral)(list.get(i));
				try
				{
					ibool.load();
				}
				catch(Exception err)
				{
				}
			}
	}
  
	public static void listLiterals()
	{
		IBooleanLiteral[] ary=(IBooleanLiteral[])BooleanLiteral.getInstances().toArray(new BooleanLiteral[0]);
		List<IBooleanLiteral> lis=Arrays.asList(ary);
		Collections.sort(lis);
		System.out.println(lis);
	}
  
	private boolean barred;

	private IBooleanVariable BV;
	private BooleanLiteral(IBooleanVariable bv, boolean barred) throws BooleanLiteralException
	{
		if(bv==null)
			throw new BooleanLiteralException("Null IBooleanVariable was passed to constructor."); //$NON-NLS-1$
		this.BV=bv;
		this.barred=barred;
		BooleanLiteral.getInstances().add(this);
	}

	public int compareTo(Object o)
	{
		String thisName=this.getBooleanVariable().getName();
		String oName=((IBooleanLiteral)o).getBooleanVariable().getName();
		if(thisName.compareTo(oName)!=0)
			return thisName.compareTo(oName);
		if(this.isBarred()==((IBooleanLiteral)o).isBarred())
			return 0;
		if(this.isBarred() && !((IBooleanLiteral)o).isBarred())
			return -1;
		else
			return 1;
	}

	public IBooleanLiteral complement() throws BooleanLiteralException
	{
		return BooleanLiteral.getBooleanLiteral(this.getBooleanVariable(),!(this.barred));
	}

	/**
	 * If <tt>o</tt> is null, this method returns <tt>false</tt>.
	 * If <tt>o</tt> is not of type IBooleanLiteral, this method 
	 * returns <tt>false</tt>. If the Object passed has a different 
	 * internal IBooleanVariable, this method returns <tt>false</tt>. If
	 * the "barred-ness" of <tt>this</tt> and <tt>o</tt> is different, 
	 * this method returns <tt>false</tt>. Otherwise, this method 
	 * returns <tt>true</tt>.
	 */
	public boolean equals(Object o)
	{
		if(o==null)
			return false; //this is never equal to null.
		if(!(o instanceof IBooleanLiteral))
			return false;
		IBooleanLiteral bo=(IBooleanLiteral)o;
		IBooleanVariable bov=bo.getBooleanVariable();
		if(bov==null)
			return false;
		else
		{
			if(bo.isBarred()!=this.isBarred())
				return false;
			else
				if(bov.equals(this.getBooleanVariable()))
					return true;
				else
					return false;
		}
	}

	public boolean evaluate()
	  {
	    if(this.getBooleanVariable().getValue() && !(this.isBarred()))
	      return true;
	    if(!(this.getBooleanVariable().getValue()) && this.isBarred())
	      return true;
	    return false;
	  }

  public IBooleanVariable getBooleanVariable()
  {
    return BV;
  }

  public boolean isBarred()
  {
    return barred;
  }

  public void load()
  {
    this.getBooleanVariable().setValue(!this.isBarred());
  }

  public ArrayList<ArrayList<IBooleanLiteral>> multiply(
		  ArrayList<ArrayList<IBooleanLiteral>> a, ArrayList<ArrayList<IBooleanLiteral>> b)
  {
	  ArrayList<ArrayList<IBooleanLiteral>> ret=new ArrayList<ArrayList<IBooleanLiteral>>();
	  for(int m=0;m<a.size();m++)
	  {
		  ArrayList<IBooleanLiteral> x = a.get(m);
		  for(int n=0;n<b.size();n++)
		  {
			  ArrayList<IBooleanLiteral> y = b.get(n);
			  ArrayList<IBooleanLiteral> z = new ArrayList<IBooleanLiteral>();
			  z.addAll(x);
			  z.addAll(y);
			  ret.add(z);
		  }
	  }
	  return ret;
  }

  public void setBarred(boolean barred)
  {
    this.barred = barred;
  }

  public void setBooleanVariable(IBooleanVariable BV) throws BooleanLiteralException
  {
  	if(BV==null)
  		throw new BooleanLiteralException("Null IBooleanVariable was passed to setBooleanVariable method."); //$NON-NLS-1$
  	else
  		this.BV = BV;
  }

  public String toCode()
{
	String nm=this.getBooleanVariable().getName().replaceAll(Messages.getString("BooleanLiteral.0"), "bv"); //$NON-NLS-1$ //$NON-NLS-2$
	if(!this.barred)
		nm="(1-"+nm+")"; //$NON-NLS-1$ //$NON-NLS-2$
	return nm+"*"+nm; //$NON-NLS-1$
}
  
  public String toString()
  {
    if(this.isBarred())
      return " $"+this.getBooleanVariable().getName()+" "; //$NON-NLS-1$ //$NON-NLS-2$
    else
      return " "+this.getBooleanVariable().getName()+" "; //$NON-NLS-1$ //$NON-NLS-2$
  }
  
  public String toString(List<IBooleanLiteral> l) throws BooleanLiteralException
  {
  	if(l==null)
			throw new BooleanLiteralException("Null List was passed to toString method."); //$NON-NLS-1$
  	else
  	{
  		BooleanLiteral.interpret(l);
  		return this.toString();
  	}
  }
}