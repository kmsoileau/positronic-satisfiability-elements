/*
 * BitStringEqualizer.java	1.0 05/04/15
 *
 * Copyright 2004-2005 Positronic Software.
 *
 *
 */

package positronic.satisfiability.elements;

import positronic.satisfiability.bitstring.IBitString;


public class BitStringSubstringer extends Problem implements IProblem
{
  private static final long serialVersionUID = -4171631543932102607L;

	public BitStringSubstringer(final IBitString X, final IBitString Y) throws Exception
  {
  	final int smaller=X.size();
    if(smaller>Y.size())
      this.setClauses(Problem.unsolvableProblem().getClauses());
    else
    {
    	IProblem problem=null;
    	for(int begin=0;begin<Y.size()-X.size()+1;begin++)
	    {
	      final IProblem[] p=new IProblem[smaller];
	      int count=0;
	      for(int i=0;i<smaller;i++)
	        p[count++]=new BitEqualizer(X.getBooleanVariable(i),Y.getBooleanVariable(begin+i));
	      problem=new Disjunction(problem,new Conjunction(p));
	    }
    	this.setClauses(problem.getClauses());
    }
  }
}