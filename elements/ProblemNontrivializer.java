/*
 * ProblemNontrivializer.java	1.0 07/09/14
 *
 * Copyright 2007 Positronic Software.
 *
 *
 */

package positronic.satisfiability.elements;


/**
 * 
 */

public class ProblemNontrivializer extends Problem implements IProblem
{
	private static final long serialVersionUID = 7374462474159541726L;

	public ProblemNontrivializer(final IProblem p) throws Exception
	{
		final IProblem problem=new ProblemNonequivalenter(p,Problem.trivialProblem());
		this.setClauses(problem.getClauses());
	}
}
