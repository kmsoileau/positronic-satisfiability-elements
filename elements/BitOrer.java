/*
 * BitOrer.java	1.11 04/11/24
 *
 * Copyright 2004-2005 Positronic Software.
 *
 *
 */
 /**
 * An extension of the Problem class which imposes a Boolean relation on three
 * IBooleanVariables. For example, the Problem instance p defined by
 *
 * <p><tt>Problem p=new BitOrer(x,y,z);</tt></p>
 *
 * is satisfied if and only if the following Boolean relation is satisfied:
 *
 * <p><tt>z == ( x | y )</tt></p>
 *
 * @author  Kerry Michael Soileau
 * ksoileau@yahoo.com
 * http://kerrysoileau.com/index.html
 * @version 1.11, 04/11/24
 * @see IBooleanVariable
 * @see IClause
 * @see Problem
 */

package positronic.satisfiability.elements;

public class BitOrer extends Problem implements IProblem
{
  private static final long serialVersionUID = 5069389297817201811L;

	public BitOrer(IBooleanVariable x, IBooleanVariable y, IBooleanVariable z) throws Exception
  {
    this.setClauses(new IClause[]
    {
      Clause.newClause().or(x).or(y).orNot(z),
      Clause.newClause().or(x).orNot(y).or(z),
      Clause.newClause().orNot(x).or(y).or(z),
      Clause.newClause().orNot(x).orNot(y).or(z)
    });
  }
}