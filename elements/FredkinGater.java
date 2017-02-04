/*
 * BitAnder.java	1.2 05/02/09
 *
 * Copyright 2004-2005 Positronic Software.
 *
 *
 */

package positronic.satisfiability.elements;

 /**
 * An extension of the Problem class which implements a Fredkin 
 * gate. For example, the IProblem p defined by
 *
 * <p><tt>IProblem p=new FredkinGater(A,B,C,P,Q,R);</tt></p>
 *
 * is satisfied if and only if the following Boolean relation is
 * satisfied:
 *
 * A B C | P Q R
 * ------|------
 * 0 0 0 | 0 0 0
 * 0 0 1 | 0 0 1
 * 0 1 0 | 0 1 0
 * 0 1 1 | 0 1 1
 * 1 0 0 | 1 0 0
 * 1 0 1 | 1 1 0
 * 1 1 0 | 1 0 1
 * 1 1 1 | 1 1 1
 * 
 * @author  Kerry Michael Soileau
 * <blockquote><pre>
 * ksoileau@yahoo.com
 * http://kerrysoileau.com/index.html
 * </pre></blockquote>
 * @version 1.0, 06/04/08
 * @see BitEqualizer
 * @see Conjunction
 * @see Disjunction
 */
public class FredkinGater extends Problem implements IProblem
{
  private static final long serialVersionUID = 8010562082282425070L;

	public FredkinGater(IBooleanVariable A, IBooleanVariable B, IBooleanVariable C, 
  		IBooleanVariable P, IBooleanVariable Q, IBooleanVariable R) throws Exception
  {
  	//A=P
  	IProblem p1=new BitEqualizer(A,P);
  	//A | (B=Q)&(C=R)
  	IProblem p2=new Disjunction(new BitFixer(A,true),new Conjunction(new BitEqualizer(B,Q),new BitEqualizer(C,R)));
  	//$A | (B=R)&(C=Q)
  	IProblem p3=new Disjunction(new BitFixer(A,false),new Conjunction(new BitEqualizer(B,R),new BitEqualizer(C,Q)));
  	
    this.setClauses(new Conjunction(p1,p2,p3).getClauses());
  }
}