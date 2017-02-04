/*
 * BitNoter.java	1.1 04/10/05
 *
 * Copyright 2004-2005 Positronic Software.
 *
 *
 */

package positronic.satisfiability.elements;

 /**
 * An extension of the Problem class which imposes a Boolean relation on two
 * IBooleanVariables. For example, the Problem instance p defined by
 *
 * <p><tt>Problem p=new BitNoter(x,y);</tt></p>
 *
 * is satisfied if and only if the following Boolean relation is satisfied:
 *
 * <p><tt>y == !x</tt></p>
 *
 * Note that BitNoter is equivalent to BitUnequalizer.
 *
 * @author  Kerry Michael Soileau
 * ksoileau@yahoo.com
 * http://kerrysoileau.com/index.html
 * @version 1.1, 04/10/05
 * @see IBooleanVariable
 * @see IClause
 * @see Problem
 */
public class BitNoter extends Problem implements IProblem
{
  private static final long serialVersionUID = -1802177453914677353L;

	public BitNoter(IBooleanVariable x, IBooleanVariable y) throws Exception
  {
    this.setClauses(new IClause[]
    {
      Clause.newClause().or(x).or(y),
      Clause.newClause().orNot(x).orNot(y)
    });
  }
}