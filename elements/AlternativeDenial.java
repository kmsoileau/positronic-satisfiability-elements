/*
 * AlternativeDenial.java	1.0 07/08/20
 *
 * Copyright 2007 Positronic Software.
 *
 *
 */

package positronic.satisfiability.elements;

import java.util.List;

import positronic.satisfiability.exceptions.AlternativeDenialException;
import positronic.satisfiability.exceptions.BooleanLiteralException;

 /**
 * An extension of the Problem class which expresses the 
 * alternative denial (also called the NAND or Sheffer stroke)
 * of several given IProblems. More specifically, the IProblem p 
 * defined by
 *
 * <p><tt>IProblem p=new AlternativeDenial(first,second,third);</tt></p>
 *
 * is satisfied by an ICertificate c if and only if at least 
 * one of <tt>first</tt>, <tt>second</tt> or <tt>third</tt> 
 * is not satisfied by <tt>c</tt>. 
 * 
 * It is the logical opposite of a Conjunction.
 *
 * @author  Kerry Michael Soileau
 * ksoileau@yahoo.com
 * http://kerrysoileau.com/index.html
 * @version 1.0, 07/08/20
 * @see BooleanLiteralException
 * @see IClause
 * @see IProblem
 * @see Problem
 */
public class AlternativeDenial extends Problem implements IProblem
{
	private static final long serialVersionUID = -29389230886476794L;

	public AlternativeDenial(IProblem first,IProblem second) throws Exception
	{
		this(new IProblem[]{first,second});
	}
	
	public AlternativeDenial(IProblem first,IProblem second,IProblem third) throws Exception
	{
		this(new IProblem[]{first,second,third});
	}
	
	public AlternativeDenial(IProblem[] array) throws Exception
	{
		this.setClauses(new ProblemDenier(
				new Conjunction(array)).getClauses());
	}
	
	public AlternativeDenial(List<IProblem> list) throws Exception
  {
		if(list==null || list.size()==0)
			throw new AlternativeDenialException("Null IProblem was passed to constructor.");
		else
			this.setClauses(new AlternativeDenial(
					(IProblem[])list.toArray(new IProblem[0])).getClauses());
  }
}