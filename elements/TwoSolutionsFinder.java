package positronic.satisfiability.elements;

import positronic.satisfiability.exceptions.TwoSolutionsFinderException;

/**
 * This class is deprecated, do not use it. Instead, consider using 
 * <code>public List[] findTwoModels(IBooleanVariable b)</code>
 * found in positronic.satisfiability.elements.Problem.
 *
 */
public class TwoSolutionsFinder extends Problem implements IProblem
{
	private static final long serialVersionUID = 8835586614098492981L;

	public TwoSolutionsFinder(IProblem problem, IBooleanVariable b) throws TwoSolutionsFinderException, Exception
	{
		System.out.println("This class is deprecated, do not use it. Instead consider using public List[] findTwoModels(IBooleanVariable b).");
		if(!problem.getBooleanVariables().contains(b))
			throw new TwoSolutionsFinderException("The given IProblem does not depend upon the given IBooleanVariable.");
		else
		{
			IProblem p1=new Conjunction(problem,new BitFixer(b,true));
			IProblem p2=new Conjunction(problem,new BitFixer(b,false));
			this.setClauses(new Conjunction(p1,p2).getClauses());
		}
	}
}
