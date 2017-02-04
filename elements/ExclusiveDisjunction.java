/*
 * ProblemDifferencer.java	1.01 07/08/22
 *
 * Copyright 2005-2007 Positronic Software.
 *
 *
 */

package positronic.satisfiability.elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import positronic.satisfiability.exceptions.BooleanLiteralException;
import positronic.satisfiability.exceptions.ExclusiveDisjunctionException;

 /**
 * An extension of the Problem class which expresses the exclusive disjunction
 * of two given IProblems. More specifically, the IProblem p defined by
 *
 * <p><tt>IProblem p=new ExclusiveDisjunction(first,second);</tt></p>
 *
 * is satisfied by an ICertificate c if and only if the IProblem <tt>first</tt> is satisfied by 
 * <tt>c</tt> 
 * and the IProblem <tt>second</tt> is not satisfied by <tt>c</tt> 
 * or 
 * the IProblem <tt>first</tt> is not satisfied by <tt>c</tt> and the IProblem <tt>second</tt> is 
 * satisfied by <tt>c</tt>.
 *
 * @author  Kerry Michael Soileau
 * ksoileau@yahoo.com
 * http://kerrysoileau.com/index.html
 * @version 1.01, 07/08/22
 * @see BooleanLiteralException
 * @see IClause
 * @see IProblem
 * @see Problem
 */
public class ExclusiveDisjunction extends Problem implements IProblem
{
	private static final long serialVersionUID = -1069858090767673835L;

	public ExclusiveDisjunction(IProblem problem) throws Exception
	{
		this.setClauses(problem.getClauses());
	}
	
	public ExclusiveDisjunction(IProblem first,IProblem second) throws Exception
  {
		if(first==null || second==null)
			throw new ExclusiveDisjunctionException("Null IProblem was passed to constructor.");
		else
			this.setClauses(new Disjunction(new ProblemDifferencer(first,second),
					new ProblemDifferencer(second,first)).getClauses());
  }
	
	public ExclusiveDisjunction(IProblem first,IProblem second,IProblem third) throws Exception
	{
		this(new IProblem[]{first,second,third});
	}
	
	public ExclusiveDisjunction(IProblem[] array) throws Exception
	{
		this(Arrays.asList(array));
	}
	
	public ExclusiveDisjunction(List<IProblem> list) throws Exception
  {
		if(list==null || list.size()==0)
			throw new ExclusiveDisjunctionException("Null IProblem was passed to constructor.");
		if(list.size()==1)
			this.setClauses(list.get(0).getClauses());
		else
		{
			IProblem problem1=list.get(0);
			IProblem problem2=new Problem(problem1);
			for(int i=1;i<list.size();i++)
				problem2=new Conjunction(problem2,new ProblemDenier(list.get(i)));
			
			IProblem problem3=new ProblemDenier(problem1);
			ArrayList<IProblem> list2=new ArrayList<IProblem>();
			for(int i=1;i<list.size();i++)
				list2.add(list.get(i));
			problem3=new Conjunction(problem3,new ExclusiveDisjunction(list2));
			
			IProblem problem=new Disjunction(problem2,problem3);
			this.setClauses(problem.getClauses());
		}
  }
}