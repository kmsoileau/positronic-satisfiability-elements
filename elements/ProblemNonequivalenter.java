/*
 * ProblemNonequivalenter.java	1.0 07/09/13
 *
 * Copyright 2007 Positronic Software.
 *
 *
 */

package positronic.satisfiability.elements;

import positronic.satisfiability.exceptions.BooleanLiteralException;

/**
 * An extension of the Problem class which expresses the nonequivalence
 * of two given IProblems. More specifically, the IProblem p defined by
 *
 * <p><tt>IProblem p=new ProblemNonequivalenter(first,second);</tt></p>
 *
 * is satisfied By an ICertificate c if and only if the IProblem first 
 * is satisfied by c and the IProblem second is not satisfied, or the 
 * IProblem second is satisfied by c and the IProblem first is not 
 * satisfied.
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

public class ProblemNonequivalenter extends Problem implements IProblem
{
	private static final long serialVersionUID = -3029911129600061583L;

	public ProblemNonequivalenter(IProblem p1, IProblem p2) throws Exception
	{
		IProblem p1minusp2=new ProblemDifferencer(p1,p2);
		IProblem p2minusp1=new ProblemDifferencer(p2,p1);
		IProblem problem=new Disjunction(p1minusp2,p2minusp1);
		this.setClauses(problem.getClauses());
	}
}
