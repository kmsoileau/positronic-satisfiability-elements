/*
 * BooleanVariable.java	1.01 06/01/04
 *
 * Copyright 2004-2006 Positronic Software.
 *
 *
 */
 /**
 * A class which represents a Boolean variable. BooleanVariable is essentially a
 * named boolean variable.
 * NOTE: Alternative implementations of BooleanVariable must not only implement
 * IBooleanVariable, but must also ensure that any two instances of
 * BooleanVariable are equal if and only if they have the same name.
 *
 * @author  Kerry Michael Soileau
 * <blockquote><pre>
 * ksoileau@yahoo.com
 * http://kerrysoileau.com/index.html
 * </pre></blockquote>
 * @version 1.0, 06/01/04
 * @see Arrays
 * @see Collections
 * @see HashSet
 * @see List
 * @see Set
 */

package positronic.satisfiability.elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import positronic.satisfiability.exceptions.BooleanVariableException;

public class BooleanVariable implements IBooleanVariable
{
  private static long boolCount;
  private static HashSet<IBooleanVariable> instances=new HashSet<IBooleanVariable>();
  private static boolean verbose;
  private static String prefix=IBooleanVariable.DEFAULTPREFIX;
  
  public static long getBoolCount()
  {
	  return boolCount;
  }
	
  public static IBooleanVariable getBooleanVariable() throws Exception
  {
    return new BooleanVariable();
  }
	
  public static IBooleanVariable getBooleanVariable(boolean x) throws Exception
  {
    IBooleanVariable res=BooleanVariable.getBooleanVariable();
    res.setValue(x);
    return res;
  }
  
  public static IBooleanVariable getBooleanVariable(String n) throws Exception
  {
  	if(n==null || "".compareTo(n)==0)
  		throw new BooleanVariableException("Empty string or null was passed to getBooleanVariable method.");
  	
  	Object[] inarray=BooleanVariable.getInstances().toArray();
  	for(int i=0;i<inarray.length;i++)
  	{
  		IBooleanVariable nx=(IBooleanVariable)(inarray[i]);
  		if(n.compareTo(nx.getName())==0)
  			return nx;
  	}
  	return new BooleanVariable(n);
  }

  public static IBooleanVariable getBooleanVariable(String n, boolean x) throws Exception
  {
  	if(n==null || "".compareTo(n)==0)
  		throw new BooleanVariableException(
  				"Null or empty String passed to getBooleanVariable method.");
  	else
  	{
  		IBooleanVariable res=BooleanVariable.getBooleanVariable(n);
  		res.setValue(x);
  		return res;
  	}
  }

  public static HashSet<IBooleanVariable> getInstances() 
  {
    return instances;
  }
  
  public static String getPrefix() 
  {
	return prefix;
  }

  public static void listVariables()
  {
	  HashSet<IBooleanVariable> s=BooleanVariable.getInstances();
	  IBooleanVariable[] ary=(BooleanVariable[]) s.toArray(new BooleanVariable[0]);
	  List<IBooleanVariable> lis=Arrays.asList(ary);
	  Collections.sort(lis);
	  System.out.println(lis);
  }

  public static String[] mathematicaVariables()
  {
	  ArrayList<String> srt=new ArrayList<String>();
	  for(IBooleanVariable curr : BooleanVariable.getInstances())
		  //if(curr.getName().charAt(1)=='_')
			  srt.add(curr.getName());
	  String[] q = srt.toArray(new String[0]);
	  Arrays.sort(q);
	  return q;
  }

  public static void setPrefix(String s) 
  {
	prefix=s;
  }
  
  public static void setVerbose(boolean verbose)
  {
	BooleanVariable.verbose = verbose;
  }

  private String name;

  private boolean value;
  
  
private BooleanVariable() throws Exception
  {
	  this(BooleanVariable.getPrefix()+""+(boolCount),false);
	  boolCount++;
  }

private BooleanVariable(String n) throws Exception
  {
	  this(n,false);
  }

  private BooleanVariable(String n, boolean x) throws Exception
  {
  	if(n==null || "".compareTo(n)==0)
  		throw new BooleanVariableException("Null or empty string was passed to a constructor.");
  	this.setName(n);
    this.setValue(x);
    BooleanVariable.getInstances().add((IBooleanVariable)this);
    if(this.isVerbose())
	  	System.out.println(this.getName());
  }

  public int compareTo(Object o)
  {
    String thisName=this.getName();
    String oName=((IBooleanVariable)o).getName();
    return thisName.compareTo(oName);
  }

  /**
	 * Two IBooleanVariables are considered equal if and only if they have the same
	 * name. Their respective values are unimportant.
	 */
  public boolean equals(Object anObject)
  {
  	if(anObject==null)
  		return false; //this is never equal to null.
    if(anObject instanceof IBooleanVariable)
    {
      BooleanVariable bv=(BooleanVariable)anObject;
      if(this.getName().compareTo(bv.getName())==0) return true;
      else return false;
    }
    else return false;
  }

  public String getName()
  {
    return this.name;
  }

  public boolean getValue()
  {
    return this.value;
  }
  
  public boolean isVerbose()
  {
	  return verbose;
  }

  public void setDefaultPrefix(String s) 
  {
	  prefix=s;
  }

  public void setName(String name) throws BooleanVariableException
  {
	  if(name==null || "".compareTo(name)==0)
		  throw new BooleanVariableException(
		  	"Null or empty String passed to setName method.");
	  this.name = name;
  }
	
  public void setValue(boolean x)
  {
	  this.value=x;
  }

  public String toString()
  {
	  return "<"+getName()+"="+this.getValue()+">";
  }
}