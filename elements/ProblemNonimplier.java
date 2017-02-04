/*
 * ProblemImplier.java	1.0 07/09/16
 *
 * Copyright 2007 Positronic Software.
 *
 *
 */

package positronic.satisfiability.elements;

import positronic.satisfiability.exceptions.BooleanLiteralException;
import positronic.satisfiability.exceptions.ProblemNonimplierException;

 /**
 * An extension of the Problem class which expresses a logical ordering
 * of two given IProblems. More specifically, the IProblem p defined by
 *
 * <p><tt>IProblem p=new ProblemDifferencer(first,second);</tt></p>
 *
 * is satisfied if and only if every certificate which satisfies first, 
 * also satisfies second.
 *
 * @author  Kerry Michael Soileau
 * ksoileau@yahoo.com
 * http://kerrysoileau.com/index.html
 * @version 1.0, 07/09/16
 * @see BooleanLiteralException
 * @see IClause
 * @see IProblem
 * @see Problem
 */
public class ProblemNonimplier extends Problem implements IProblem
{
  private static final long serialVersionUID = 124532070113915117L;

	public ProblemNonimplier(IProblem first,IProblem second) throws Exception
  {
		if(first==null || second==null)
			throw new ProblemNonimplierException("Null IProblem was passed to constructor.");
		else
		{
			IProblem res=new ProblemDifferencer(first,second);
			this.setClauses(res.getClauses());
		}
  }
}