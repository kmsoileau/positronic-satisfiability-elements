/*
 * BitNander.java	1.0 05/04/13
 *
 * Copyright 2004-2005 Positronic Software.
 *
 *
 */

package positronic.satisfiability.elements;

 /**
 * An extension of the Problem class which imposes a Boolean relation on three
 * IBooleanVariables. For example, the Problem instance p defined by
 *
 * <p><tt>Problem p=new BitNander(x,y,z);</tt></p>
 *
 * is satisfied if and only if the following Boolean relation is satisfied:
 *
 * <p><tt>z == !( x ^ y )</tt></p>
 *
 * @author  Kerry Michael Soileau
 * ksoileau@yahoo.com
 * http://kerrysoileau.com/index.html
 * @version 1.0, 05/04/13
 * @see IBooleanVariable
 * @see IClause
 * @see Problem
 */
public class BitNander extends Problem implements IProblem
{
	private static final long serialVersionUID = 1056411650725455500L;

	public BitNander(IBooleanVariable x, IBooleanVariable y, IBooleanVariable z)
    throws Exception
  {
    this.setClauses(new IClause[]
    {
      Clause.newClause().or(x).or(y).or(z),
      Clause.newClause().or(x).orNot(y).or(z),
      Clause.newClause().orNot(x).or(y).or(z),
      Clause.newClause().orNot(x).orNot(y).orNot(z)
    });
  }
}