package positronic.satisfiability.elements;

/*
 * Disjunction.java	1.21 07/08/22
 *
 * Copyright 2004-2007 Positronic Software.
 *
 *
 */

/**
 * An extension of the Problem class which creates a new IProblem object from a
 * collection of IProblem objects. Given several IProblems p_1, p_2, ..., p_n,
 * to satisfy the IProblem given by
 * new Disjunction(new IProblem[]{p_1, p_2, ..., p_n})
 * is to satisfy at least one of the p_1, p_2, ..., p_n.
 *
 * To use this class, one passes several IProblem objects (or an IProblem[]
 * array) to the appropriate constructor. The object produced is an IProblem,
 * and one may manipulate it using any of the methods provided by the Problem
 * class.
 *
 * For example, when the IProblem instance p defined by
 * <blockquote><pre>
 * IProblem p=new Disjunction(new IProblem[]{p_1, p_2, p_3});
 * </pre></blockquote>
 * is satisfied, the following truth equation will be satisfied:
 * <blockquote><pre>
 * p_1 is satisfied
 * or
 * p_2 is satisfied
 * or
 * p_3 is satisfied.
 * </pre></blockquote>
 * @author  Kerry Michael Soileau
 * ksoileau@yahoo.com
 * http://kerrysoileau.com/index.html
 * @version 1.21, 07/08/22
 * @see IProblem
 * @see Problem
 */

public class Disjunction extends Problem implements IProblem
{
  private static final long serialVersionUID = 1L;
  
  /**
  * This or() method takes two Problems ptrue and pfalse and creates a new
  * Problem, let's call it p. If b is true, p is equivalent to the Problem
  * ptrue. Likewise, if b is false, p is equivalent to the Problem pfalse.
   * @throws Exception 
  */
  private static IProblem or(IProblem ptrue, IProblem pfalse, IBooleanVariable b) throws Exception
  {
    if(pfalse==null) return Disjunction.or(new IProblem[]{ptrue},new IBooleanVariable[]{b});
    if(ptrue==null) return Disjunction.or(new IProblem[]{pfalse},new IBooleanVariable[]{b});
    Object[] clfalse=pfalse.getClauses();
    IClause[] clfalseNew=new IClause[clfalse.length];

    for(int i=0;i<clfalse.length;i++)
    {
      IClause o1=((IClause)clfalse[i]);
      clfalseNew[i]=(IClause)(o1.clone());
      clfalseNew[i]=clfalseNew[i].or(b);
    }

    Object[] cltrue=ptrue.getClauses();
    IClause[] cltrueNew=new IClause[cltrue.length];
    for(int i=0;i<cltrue.length;i++)
    {
      cltrueNew[i]=(IClause)(((IClause)cltrue[i]).clone());
      cltrueNew[i]=cltrueNew[i].orNot(b);
    }

    return new Conjunction(new Problem(clfalseNew),new Problem(cltrueNew));
  }

  private static IProblem or(IProblem[] p) throws Exception
  {
    if(p==null) return null;
    int N=p.length;
    if(N==0) return null;
    if(N==1) return p[0];
    if(N>1)
    {
      IBooleanVariable[] b = new IBooleanVariable[N-1];
      for(int i=0;i<b.length;i++)
        b[i]=BooleanVariable.getBooleanVariable();
      return Disjunction.or(p,b);
    }
    return null;
  }

  private static IProblem or(IProblem[] p, IBooleanVariable[] b) throws Exception
  {
    if(p==null || b==null) return null;
    int N=p.length;
    if(N==0) return null;
    if(N==1) return p[0];
    if(N != b.length + 1) return null;
    if(N>1)
    {
      IProblem[] r=new IProblem[N-1];
      r[N-2]=Disjunction.or(p[N-2],p[N-1],b[N-2]);
      for(int i=N-3;i>=0;i--)
        r[i]=Disjunction.or(p[i],r[i+1],b[i]);
      return r[0];
    }
    return null;
  }
  
  public Disjunction(IProblem p) throws Exception
  {
  	this.setClauses(p.getClauses());
  }

  public Disjunction(IProblem p1,IProblem p2) throws Exception
  {
    this(new IProblem[]{p1,p2});
  }

  public Disjunction(IProblem ptrue, IProblem pfalse, IBooleanVariable b) throws Exception
  {
    IProblem p=Disjunction.or(ptrue,pfalse,b);
    if(p!=null)
      this.setClauses(p.getClauses());
  }

  public Disjunction(IProblem p1,IProblem p2,IProblem p3) throws Exception
  {
    this(new IProblem[]{p1,p2,p3});
  }

  public Disjunction(IProblem p1,IProblem p2,IProblem p3,IProblem p4) throws Exception
  {
    this(new IProblem[]{p1,p2,p3,p4});
  }

  public Disjunction(IProblem[] group) throws Exception
  {
    IProblem p=Disjunction.or(group);
    if(p!=null)
      this.setClauses(p.getClauses());
  }

  public Disjunction(IProblem[] group, IBooleanVariable[] b) throws Exception
  {
    IProblem p=Disjunction.or(group,b);
    if(p!=null)
      this.setClauses(p.getClauses());
  }
}