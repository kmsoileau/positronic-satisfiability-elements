/*
 * BitAnder.java	1.2 05/02/09
 *
 * Copyright 2004-2005 Positronic Software.
 *
 *
 */

package positronic.satisfiability.elements;

 /**
 * An extension of the Problem class which implements a Toffoli 
 * gate. For example, the IProblem p defined by
 *
 * <p><tt>IProblem p=new ToffoliGater(A,B,C,P,Q,R);</tt></p>
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
 * 1 0 1 | 1 0 1
 * 1 1 0 | 1 1 1
 * 1 1 1 | 1 1 0
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
public class ToffoliGater extends Problem implements IProblem
{
  private static final long serialVersionUID = 2417242703176785998L;

	public ToffoliGater(IBooleanVariable A, IBooleanVariable B, IBooleanVariable C, 
  		IBooleanVariable P, IBooleanVariable Q, IBooleanVariable R) throws Exception
  {
		//p=a, q=b and r=c XOR (a AND b).
  	//P=A
  	IProblem p1=new BitEqualizer(A,P);
		//Q=B
  	IProblem p2=new BitEqualizer(B,Q);
  	//R=C XOR (A AND B)
  	IBooleanVariable X=BooleanVariable.getBooleanVariable("X");
  	IProblem p3=new BitAnder(A,B,X);
  	IProblem p4=new BitXorer(C,X,R);
  	this.setClauses(new Conjunction(p1,p2,p3,p4).getClauses());
  }
}