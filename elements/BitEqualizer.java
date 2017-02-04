/*
 * BitEqualizer.java	1.1 04/10/05
 *
 * Copyright 2004-2005 Positronic Software.
 *
 *
 */

package positronic.satisfiability.elements;

import positronic.satisfiability.exceptions.BitEqualizerException;

 /**
 * An extension of the Problem class which imposes a Boolean relation on two
 * IBooleanVariables. For example, the Problem instance p defined by
 *
 * <p><tt>Problem p=new BitEqualizer(x,y);</tt></p>
 *
 * is satisfied if and only if the following Boolean relation is satisfied:
 *
 * <p><tt>x==y</tt></p>
 *
 * @author  Kerry Michael Soileau
 * ksoileau@yahoo.com
 * http://kerrysoileau.com/index.html
 * @version 1.11, 05/11/30
 * @see BitEqualizerException
 * @see Clause
 * @see IClause
 * @see Problem
 */
public class BitEqualizer extends Problem implements IProblem
{
  private static final long serialVersionUID = 2510527818977149000L;

	public BitEqualizer(IBooleanVariable x, IBooleanVariable y) throws Exception
  {
  	if(x==null || y==null)
  		throw new BitEqualizerException("BitEqualizer constructor was passed a null parameter.");
  	
  	if(x.equals(y))
  		this.setClauses(Problem.trivialProblem().getClauses());
  	else
  		this.setClauses(new IClause[]
  		      {
  						Clause.newClause().or(x).orNot(y),
  						Clause.newClause().orNot(x).or(y)
  		      });
  }
}