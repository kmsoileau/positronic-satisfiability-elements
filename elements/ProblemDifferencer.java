/*
 * ProblemDifferencer.java	1.0 05/10/18
 *
 * Copyright 2005 Positronic Software.
 *
 *
 */

package positronic.satisfiability.elements;

import positronic.satisfiability.exceptions.BooleanLiteralException;
import positronic.satisfiability.exceptions.ProblemDifferencerException;

 /**
 * An extension of the Problem class which expresses the logical difference
 * of two given IProblems. More specifically, the IProblem p defined by
 *
 * <p><tt>IProblem p=new ProblemDifferencer(first,second);</tt></p>
 *
 * is satisfied if and only if the IProblem first is satisfied and the 
 * IProblem second is not satisfied.
 *
 * @author  Kerry Michael Soileau
 * ksoileau@yahoo.com
 * http://kerrysoileau.com/index.html
 * @version 1.0, 05/10/18
 * @see BooleanLiteralException
 * @see IClause
 * @see IProblem
 * @see Problem
 */
public class ProblemDifferencer extends Problem implements IProblem
{
  private static final long serialVersionUID = 1L;

	public ProblemDifferencer(IProblem first,IProblem second) throws Exception
  {
		if(first==null || second==null)
			throw new ProblemDifferencerException("Null IProblem was passed to constructor.");
		else
		{
			IProblem denial=new ProblemDenier(second);
			IProblem res=new Conjunction(first,denial);
			this.setClauses(res.getClauses());
		}
  }
}