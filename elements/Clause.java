/*
 * Clause.java	1.01 05/04/22
 *
 * Copyright 2004-2005 Positronic Software.
 *
 *
 */

package positronic.satisfiability.elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import positronic.satisfiability.exceptions.ClauseException;

/**
 * A class which represents a satisfiability clause. IClause is essentially an
 * ArrayList of IBooleanLiteral objects, and additionally provides several useful
 * methods for manipulating and evaluating the truth value of an IClause.
 *
 * @author  Kerry Michael Soileau
 * <blockquote><pre>
 * ksoileau@yahoo.com
 * http://kerrysoileau.com/index.html
 * </pre></blockquote>
 * @version 1.01, 05/04/22
 * @see ArrayList
 * @see Arrays
 * @see Iterator
 * @see List
 * @see Map
 */

public class Clause extends ArrayList<IBooleanLiteral> implements IClause
{
	private static final long serialVersionUID = -9088489304501148454L;
	
	/**
	   * This static method returns a new IClause.
	   * 
	   * @return IClause
	   */
	  public static IClause newClause()
	  {
	    return new Clause();
	  }

/**
   * This static method returns a new IClause made up of a random selection of IBooleanLiterals created using
   * IBooleanVariables in the given IBooleanVariable[].
   * 
   * @return IClause
   * @throws Exception
   */
  public static IClause randomClause(IBooleanVariable[] bv) throws Exception
  {
    IClause ret=Clause.newClause();
    int number=bv.length;
    for(int i=0;i<number;i++)
    {
      double r=Math.random();
      int choice=(int)(3*r);
      if(choice==0) ;
      if(choice==1) ret.add((BooleanLiteral)BooleanLiteral.getBooleanLiteral(bv[i],false));
      if(choice==2) ret.add((BooleanLiteral)BooleanLiteral.getBooleanLiteral(bv[i],true));
    }
    return ret;
  }
  
  /**
   * This method adds a IBooleanLiteral to this.
   * 
   * @return boolean
 * @throws ClauseException
 * @throws Exception 
   */
  public boolean add(BooleanLiteral b) throws Exception
  {
  	if(b==null)
  		throw new ClauseException("A null IBooleanLiteral was passed to the add method.");
    if(!this.contains(b))
    {
      super.add(b);
      return true;
    }
    else
      return false;
  }
  
  /**
   * This method adds a IBooleanLiteral to this.
   * 
   * @throws ClauseException
   */
  public void addLiteral(IBooleanLiteral bl) throws Exception
  {
  	if(bl==null)
  		throw new ClauseException("Null IBooleanLiteral was passed to addLiteral method.");
    this.add(bl);
  }

  /**
   * This method returns an exact copy of this.
   * 
   * @return Object
   */
  public Object clone()
  {
  	try
		{
			return this.duplicate();
		} 
  	catch(Exception e)
		{
			System.out.println("Attempt failed to use method clone.");
			e.printStackTrace();
			return null;
		}
  }

  /**
   * This method compares this to an IClause. The comparison is first done according to size, then on the 
   * "lowest" IBooleanLiteral in each IClause.
   * 
   * @return int
   * @throws ClauseException
   */
  public int compareTo(IClause o) throws Exception
  {
  	if(o==null)
  		throw new ClauseException("A null Object was passed to the compareTo method.");
    
    int d=super.size()-((ArrayList<?>)o).size();
    if(d!=0)
      return d;
    else
    {
      Object[] thisIt=this.toArray();
      Arrays.sort(thisIt);
      IBooleanLiteral thisFirst=(IBooleanLiteral)(thisIt[0]);
      Object[] oIt=((IClause)o).toArray();
      Arrays.sort(oIt);
      IBooleanLiteral oFirst=(IBooleanLiteral)(oIt[0]);
      return thisFirst.compareTo(oFirst);
    }
  }

  /**
   * This method compares this to an Object. The comparison is first done according to size, then on the 
   * "lowest" IBooleanLiteral in each IClause.
   * 
   * @return int
   * @throws ClauseException
   */
  public int compareTo(Object o)
  {
  	try
		{
			return compareTo((IClause)o);
		} 
  	catch (Exception e)
		{
			System.out.println("The compareTo method failed on Object "+o+".");
			e.printStackTrace();
			return Integer.MAX_VALUE;
		}
  }

  /**
   * This method determines whether a given IBooleanLiteral is a member of this. The comparison is first done according to size, then on the 
   * "lowest" IBooleanLiteral in each IClause.
   * 
   * @return int
   * @throws ClauseException
   */
  public boolean contains(IBooleanLiteral bl) throws Exception
  {
  	if(bl==null)
  		throw new ClauseException("A null IBooleanLiteral was passed to the contains method.");
    Iterator<IBooleanLiteral> it=this.iterator();
 
    while(it.hasNext())
    {
      Object obj=it.next();
      IBooleanLiteral blc=(IBooleanLiteral)obj;
      if(blc.equals(bl))
        return true;
    }
    return false;
  }

  /**
   * This method returns true if and only if the following conditions are all true:
   * 1. this.size()==c.size()
   * 2. this.minus(c) contains exactly one literal
   * 3. c.minus(this) contains exactly one literal
   * 4. this.minus(c).getBooleanVariable()==c.minus(this).getBooleanVariable()
   * 
   * @return IBooleanLiteral
   * @throws Exception
   */
  public IBooleanLiteral differsSinglyFrom(IClause c) throws Exception
  {
    if(c==null) return null;
    if(this.size()!=c.size()) return null;
    IClause dminusc=this.minus(c);
    if(dminusc.size()!=1) return null;
    IClause cminusd=c.minus(this);
    if(cminusd.size()!=1) return null;
    IBooleanLiteral dminuscbl=dminusc.getLiteralAt(0);
    IBooleanVariable dminuscbv=dminuscbl.getBooleanVariable();
    IBooleanVariable cminusdbv=cminusd.getLiteralAt(0).getBooleanVariable();
    if(!dminuscbv.equals(cminusdbv)) return null;
    return dminuscbl;
  }

  /**
   * An IClause A dominates an IClause B if and only if every IBooleanLiteral in A can be found in B. In that 
   * case, A & B <--> A. Practically speaking, if A dominates B, then B is redundant and may be deleted from
   * any IProblem which includes the IClause A.
   * 
   * @return boolean
   * @throws Exception 
   */
  public boolean dominates(IClause clause) throws Exception
  {
    if(clause==null) return true;
    if(this.isEmpty()) return true; //An empty IClause dominates every other IClause
    //if(this.size()>clause.size()) return false;
    for(int i=0;i<this.size();i++)
      if(!clause.contains(this.getLiteralAt(i))) return false;
    return true;
  }
  
  /**
   * This method returns an exact copy of this.
   * 
   * @return Object
   * @throws Exception
   */
  public Object duplicate() throws Exception
  {
    IClause cl=new Clause();
    for(int i=0;i<this.size();i++)
      cl.add((BooleanLiteral)this.getLiteralAt(i));
    return cl;
  }

  /**
   * This method determines whether a given IClause is equal to this.
   * 
   * @return boolean
   * @throws Exception 
   */
  public boolean equals(IClause o) throws Exception
  {
  	if(o==null)
  		throw new ClauseException("Null IClause was passed to method equals.");
  	if (o == this)
      return true;
  	//if (!(o instanceof IClause))
    //  return false;
  	
    

    //if(this.size()!=((ArrayList)o).size())
    //  return false;

    /*for(int i=0;i<this.size();i++)
    {
      if(!this.contains(((IClause)o).getLiteralAt(i)))
        return false;
    }*/
    
    IClause ic=(IClause)o;
    if(!((IClause)this).dominates(ic))
    	return false;
    if(!ic.dominates((IClause)this))
    	return false;
    return true;
  }

  /**
   * This method determines whether a given IClause is true.
   * 
   * @return boolean 
   */
  public boolean evaluate()
  {
    Iterator<IBooleanLiteral> it=this.iterator();

    while(it.hasNext())
      if(((IBooleanLiteral)(it.next())).evaluate())
        return true;

    return false;
  }
  
  /**
   * This method return an Object[] of the IBooleanVariables appearing in this. 
   * 
   * @return Object[]
   */
  public IBooleanVariable[] getBooleanVariables()
  {
    ArrayList<IBooleanVariable> res=new ArrayList<IBooleanVariable>();
    Object[] bl=this.toArray();
    for(int i=0;i<bl.length;i++)
    {
    	IBooleanVariable curr=((IBooleanLiteral)bl[i]).getBooleanVariable();
      if(!res.contains(curr))
      	res.add(curr);
    }

    return res.toArray(new IBooleanVariable[0]);
  }
  
  /**
   * This method writes to the given List, the IBooleanVariables appearing in this. 
   * 
   */
  public void getBooleanVariables(List<IBooleanVariable> hs) throws Exception
  {
  	if(hs==null)
  		throw new ClauseException("A null List was passed to the getBooleanVariables method.");
    
  	IBooleanLiteral[] bl=this.toArray(new IBooleanLiteral[0]);
    for(int i=0;i<bl.length;i++)
      hs.add(((IBooleanLiteral)bl[i]).getBooleanVariable());
  }

  /**
   * This method returns the IBooleanLiteral at a given index position in this.
   * 
   * @return IBooleanLiteral
   * @throws ClauseException 
   */
  public IBooleanLiteral getLiteralAt(int n) throws ClauseException
  {
  	if(n<0)
  		throw new ClauseException("A negative number was passed to getLiteralAt method.");
    return (IBooleanLiteral)(super.get(n));
  }

  public boolean isEmpty()
  {
    return (this.size()==0);
  }

  public boolean isMemberOf(List<IClause> h) throws Exception
  {
  	if(h==null)
  		throw new ClauseException("A null List was passed to isMemberOf method.");
    Iterator<IClause> it=h.iterator();
    while(it.hasNext())
    {
      Object obj=it.next();
      IClause curr=(IClause)obj;
      if(curr.equals(this))
        return true;
    }
    return false;
  }

  public boolean isSatisfied()
  {
    return this.evaluate();
  }

  public boolean isSensitiveTo(IBooleanVariable bv) throws ClauseException
  {
  	if(bv==null)
  		throw new ClauseException("A null IBooleanVariable was passed to isSensitiveTo method.");
  	
    Iterator<IBooleanLiteral> it=this.iterator();

    while(it.hasNext())
    {
      IBooleanLiteral bl=(IBooleanLiteral)(it.next());
      if(bv.equals(bl.getBooleanVariable()))
      {
        boolean startValue=this.evaluate();
        bv.setValue(!bv.getValue());
        boolean endValue=this.evaluate();
        bv.setValue(!bv.getValue());
        if(startValue!=endValue)
          return true;
      }
    }
    return false;
  }

  public boolean isSingleton()
  {
    return(this.size()==1);
  }

  public Iterator<IBooleanLiteral> iterator()
  {
    return super.iterator();
  }

  public IClause minus(IClause o) throws Exception
  {
    //if (o == this)
    //  return new Clause();
    //if (!(o instanceof IClause))
    //  return this;
  	if(o==null)
  		throw new ClauseException("Null IClause was passed to minus method.");
    
    IClause ret=(IClause)this.clone();
    for(int i=0;i<o.size();i++)
      ret.remove(o.getLiteralAt(i));
    return ret;
  }

  public IClause nor(IBooleanVariable bv) throws Exception
  {
  	return orNot(bv);
  }

  public IClause or(IBooleanVariable bv) throws Exception
  {
  	if(bv==null)
  		throw new ClauseException("A null IBooleanVariable was passed to the or method.");
    
    this.addLiteral(BooleanLiteral.getBooleanLiteral(bv,false));
    return this;
  }
  
  public IClause orNot(IBooleanVariable bv) throws Exception
  {
  	if(bv==null)
  		throw new ClauseException("A null IBooleanVariable was passed to the orNot method.");
    
    this.addLiteral(BooleanLiteral.getBooleanLiteral(bv,true));
    return this;
  }

  public boolean remove(IBooleanLiteral b)
  {
  	return super.remove(b);
  }

  public IBooleanLiteral remove(int i)
  {
  	return super.remove(i);
  }

  public IClause resolve(IBooleanLiteral ib) throws Exception
  {
  	if(ib==null)
  		throw new ClauseException("Null IBooleanLiteral was passed to resolve method.");
    
    return this.resolve(ib.getBooleanVariable(),!ib.isBarred());
  }

  // An empty IClause ={} cannot be satisfied.
  // A null IClause =null is satisfied trivially.
  public IClause resolve(IBooleanVariable b, boolean value) throws Exception
  {
  	if(b==null)
  		throw new ClauseException("Null IBooleanVariable was passed to resolve method.");
    IClause c=(IClause)(this.clone());
    Iterator<IBooleanLiteral> it=this.iterator();
    while(it.hasNext())
    {
      Object obj=it.next();
      IBooleanLiteral bl=(IBooleanLiteral)obj;
      if(bl.getBooleanVariable().equals(b))
        if(bl.isBarred()==value)
          c.remove(bl);
        else
        {
          c=null;
          break;
        }
    }
    return c;
    /*
    if(!(c.isEmpty()))
      return c;
    else
      return null;
    */
  }

  public int size()
  {
  	return super.size();
  }
  
  public IClause substitute(Map<IBooleanLiteral,IBooleanLiteral> h) throws Exception
  {
  	if(h==null)
  		throw new ClauseException("Null java.util.Map was passed to substitute method.");
    
    boolean didsomething=true;
    while(didsomething)
    {
      didsomething=false;
      for(int i=0;i<this.size();i++)
      {
        IBooleanLiteral ib=this.getLiteralAt(i);
        Object tr=h.get(ib);
        if(tr!=null && tr instanceof IBooleanLiteral && !((IBooleanLiteral)tr).equals(ib))
        {
          //System.out.println("Substituting "+(IBooleanLiteral)tr+" for "+this.get(i));
          this.remove(i);
          this.add((IBooleanLiteral)tr);
          didsomething=true;
        }
      }
    }
    return this;
  }
  
  public IClause substitute(Map<IBooleanLiteral,IBooleanLiteral> h, IClause c) throws Exception
  {
  	if(h==null)
  		throw new ClauseException("Null java.util.Map was passed to substitute method.");
    
    boolean didsomething=true;
    while(didsomething)
    {
      didsomething=false;
      for(int i=0;i<c.size();i++)
      {
        IBooleanLiteral ib=c.getLiteralAt(i);
        Object tr=h.get(ib);
        if(tr!=null && tr instanceof IBooleanLiteral && !((IBooleanLiteral)tr).equals(ib))
        {
          //System.out.println("Substituting "+(IBooleanLiteral)tr+" for "+this.get(i));
          c.remove(i);
          c.add((BooleanLiteral)tr);
          didsomething=true;
        }
      }
    }
    return this;
  }
  
  public IProblem ThreeSATProblem() throws Exception
  {
  	if(this.size()<4)
  		return (IProblem)(new Problem(new IClause[]{this}));
  	
  	Clause left=new Clause();
  	left.add(this.getLiteralAt(0));
  	left.add(this.getLiteralAt(1));
  	Clause right=new Clause();
  	for(int i=2;i<this.size();i++)
  	{
  		right.add(this.getLiteralAt(i));
  	}
  	IBooleanVariable bv=BooleanVariable.getBooleanVariable();
  	IBooleanLiteral blunbarred=BooleanLiteral.getBooleanLiteral(bv,false);
  	IBooleanLiteral blbarred=BooleanLiteral.getBooleanLiteral(bv,true);
  	left.add(blunbarred);
  	right.add(blbarred);
  	IProblem problem=new Conjunction(left.ThreeSATProblem(),right.ThreeSATProblem());
  	return problem;
  }
  
  /*public static boolean dominates(IClause c, IClause d)
  {
    if(d.size()>=c.size()) return false;
    for(int i=0;i<d.size();i++)
    {
      IBooleanLiteral curr=d.getLiteralAt(i);
      if(!c.contains(curr)) return false;
    }
    return true;
  }*/
  
  /*public static IBooleanLiteral differsSinglyFrom(IClause c, IClause d)
  {
    if(c==null) return null;
    if(d.size()!=c.size()) return null;
    IClause dminusc=d.minus(c);
    if(dminusc.size()!=1) return null;
    IClause cminusd=c.minus(d);
    if(cminusd.size()!=1) return null;
    IBooleanLiteral dminuscbl=dminusc.getLiteralAt(0);
    IBooleanVariable dminuscbv=dminuscbl.getBooleanVariable();
    IBooleanVariable cminusdbv=cminusd.getLiteralAt(0).getBooleanVariable();
    if(!dminuscbv.equals(cminusdbv)) return null;
    return dminuscbl;
  }*/
  
  public IBooleanLiteral[] toArray()
  {
    return super.toArray(new IBooleanLiteral[0]);
  }

  //Implementation-specific methods

  public String toCode() throws ClauseException
  {
	if(this.size()<1)
		return null;
	String ret=((BooleanLiteral) this.getLiteralAt(0)).toCode();
	for(int i=1;i<this.size();i++)
		ret+="*"+((BooleanLiteral) this.getLiteralAt(i)).toCode();
	return ret;
  }

  public IBooleanLiteral[] toSortedArray()
  {
	IBooleanLiteral[] obj=this.toArray(new IBooleanLiteral[0]);
    Arrays.sort(obj);
    return obj;
  }

  public String toString()
  {
    String res="{";
    Object[] obj=this.toSortedArray();
    for(int j=0;j<obj.length;j++)
        res+=obj[j];
    return res+"}";
  }
}