/*
 * Clauses.java	1.0 04/09/06
 *
 * Copyright 2004-2005 Positronic Software.
 *
 *
 */
 /**
 * An iterator over all possible Clauses.
 *
 * To use this class, one passes a collection of BooleanVariables to one of the
 * constructors. The Clauses object produced is an Iterator, and one may use its
 * hasNext and next methods to produce all possible clauses over the given
 * collection. Each application of the next method produces an instance of the
 * IClause class; this instance contains a clause over the given collection.
 *
 * @author  Kerry Michael Soileau
 * ksoileau@yahoo.com
 * http://kerrysoileau.com/index.html
 * @version 1.0, 04/09/06
 * @see ArrayList
 * @see Collection
 * @see Iterator
 * @see Vector
 */

package positronic.satisfiability.elements;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Vector;

import positronic.satisfiability.exceptions.BooleanLiteralException;

public class Clauses implements Iterator<IClause>
{
  private IClause[] array;
  private int[] serial;

  public Clauses(ArrayList<IClause> a)
  {
    this((IClause[])a.toArray(new IClause[0]));
  }

  public Clauses(Collection<IClause> c)
  {
	  this((IClause[])c.toArray(new IClause[0]));
  }

  public Clauses(IClause[] array)
  {
    this.array=array;
    this.serial=new int[this.array.length];
  }

  public Clauses(Object[] bv) 
  {
	  this.array=new IClause[bv.length];
	  int i=0;
	  for(Object o : bv)
		  this.array[i++]=(IClause)o;
  }

  public Clauses(Vector<IClause> v)
  {
	  this((IClause[])v.toArray(new IClause[0]));
  }

/**
     * Returns <tt>true</tt> if the Clauses iterator has more IClause objects.
     * (In other words, returns <tt>true</tt> if <tt>next</tt> would return a
     * IClause rather than throwing an exception.)
     *
     * @return <tt>true</tt> if the Clauses iterator has more IClause objects.
     */
  public boolean hasNext()
  {
    for(int i=0;i<this.serial.length;i++)
      if(this.serial[i]==0 || this.serial[i]==1)
        return true;
    return false;
  }
  
  public IClause next()
  {
  	try
  	{
  		return nextClause();
  	}
  	catch(Exception err)
  	{
  		System.out.println("The method Clauses.next() failed.");
  		return null;
  	}
  }

  /**
     * Returns the next subset in the Clauses.
     *
     * @return the next subset in the Clauses.
   * @throws Exception 
     * @exception NoSuchElementException Clauses has no more subsets.
     */
  public IClause nextClause() throws Exception
  {
    boolean ok=false;
    for(int i=0;i<this.serial.length;i++)
      if(this.serial[i]==0 || this.serial[i]==1)
      {
        ok=true;
        break;
      }
    if(!ok)
      throw(new NoSuchElementException("The next method was called when no more objects remained."));
    else
    {
      int n=0;
      this.serial[0]++;
      boolean carry=(this.serial[0]==3);
      if(carry)
        this.serial[0]=0;

      while(n+1<this.serial.length)
      {
        n++;
        if(carry)
        {
          this.serial[n]++;
          carry=(this.serial[n]==3);
          if(carry)
            this.serial[n]=0;
        }
        else break;
      }
      IClause vec=new Clause();
      for(int i=0;i<this.serial.length;i++)
      {
        try
        {
        if(this.serial[i]==1)
          vec.add((BooleanLiteral)BooleanLiteral.getBooleanLiteral((IBooleanVariable)(this.array[i]),false));
        if(this.serial[i]==2)
          vec.add((BooleanLiteral)BooleanLiteral.getBooleanLiteral((IBooleanVariable)(this.array[i]),true));
        }
        catch(BooleanLiteralException err)
        {
        }
      }

      return vec;
    }
  }

  /**
     *
     * Not supported by this class.
     *
     * @exception UnsupportedOperationException because the <tt>remove</tt>
     *		  operation is not supported by this Iterator.
     */
  public void remove()
  {
    throw new UnsupportedOperationException("The Clauses class does not support the remove method.");
  }

  public IClause[] toArray()
  {
    ArrayList<IClause> res=new ArrayList<IClause>();
    while(this.hasNext())
      res.add(this.next());
    return (IClause[])res.toArray(new Clause[0]);
  }
}