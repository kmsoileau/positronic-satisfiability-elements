/*
 * ProblemDenier.java	1.0 05/10/18
 *
 * Copyright 2005 Positronic Software.
 *
 *
 */

package positronic.satisfiability.elements;

import positronic.satisfiability.exceptions.BooleanLiteralException;





 /**
 * An extension of the Problem class which expresses the denial of a given 
 * IProblem. More specifically, the IProblem p defined by
 *
 * <p><tt>IProblem p=new ProblemDenier(problem);</tt></p>
 *
 * is satisfied by an ICertificate c if and only if the IProblem problem is not satisfied
 * by c. It should be noted that this does not say anything conclusive about the 
 * satisfiability of problem, it is useful mainly in constraining an ICertificate away from those
 * ICertificates which satisfy problem.
 *
 * @author  Kerry Michael Soileau
 * ksoileau@yahoo.com
 * http://kerrysoileau.com/index.html
 * @version 1.01, 05/12/26
 * @see BooleanLiteralException
 * @see IClause
 * @see IProblem
 * @see Problem
 */
public class ProblemDenier extends Problem implements IProblem
{
  private static final long serialVersionUID = 7106759746681117418L;
  
  public ProblemDenier(IProblem problem) throws Exception
  {
		if(problem.numberOfClauses()==0)
			this.setClauses(Problem.unsolvableProblem().getClauses());
		else
		{
			IProblem res=new ClauseDenier(problem.getClause(0));
			for(int i=1;i<problem.numberOfClauses();i++)
			{
				IClause cls=problem.getClause(i);
				IProblem ip=new ClauseDenier(cls);
				res=new Disjunction(res,ip);
			}
			this.setClauses(res.getClauses());
		}
  }
}