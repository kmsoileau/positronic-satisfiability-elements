/*
 * BitFixer.java	1.1 04/10/05
 *
 * Copyright 2004-2005 Positronic Software.
 *
 *
 */

package positronic.satisfiability.elements;

 /**
 * An extension of the Problem class which implements a Boolean constraint. This
 * constraint is satisfied if and only if the IBooleanVariable x has a given
 * truth value.
 *
 * For example, when the Problem instance p defined by
 *
 * <p><tt>Problem p=new BitFixer(x,true);</tt></p>
 *
 * is satisfied, the following truth equation will be satisfied:
 *
 * <p><tt>x==true</tt></p>.
 * 
 * Similarly, when the Problem instance p defined by
 *
 * Problem p=new BitFixer(x,false);
 *
 * is satisfied, the following truth equation will be satisfied:
 *
 * <p><tt>x==false</tt></p>.
 *
 * @author  Kerry Michael Soileau
 * ksoileau@yahoo.com
 * http://kerrysoileau.com/index.html
 * @version 1.1, 04/10/05
 * @see IBooleanVariable
 * @see IClause
 * @see Problem
 */
public class BitFixer extends Problem implements IProblem
{
  private static final long serialVersionUID = -3066870443417568106L;

	public BitFixer(IBooleanVariable x) throws Exception
  {
    this(x,x.getValue());
  }

  public BitFixer(IBooleanVariable x, boolean v) throws Exception
  {
    this.addClause(Clause.newClause());

    if(v)
      this.getClause(0).or(x);
    else
      this.getClause(0).orNot(x);
  }
}