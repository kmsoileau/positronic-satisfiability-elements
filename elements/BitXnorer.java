/*
 * BitXnorer.java	1.0 05/04/13
 *
 * Copyright 2004-2005 Positronic Software.
 *
 *
 */

package positronic.satisfiability.elements;

/**
 * An extension of the Problem class which implements a Boolean function. This
 * function has the value false if the BooleanVariables x and y are both true;
 * otherwise it has the value true. The returned function value is found
 * in the IBooleanVariable z.
 *
 * To use this class, one passes BooleanVariables x, y, and z to the
 * constructor. The BitXnorer object produced is a Problem, and one may
 * manipulate it using any of the methods provided by the Problem class.
 *
 * For example, when the Problem instance p defined by
 *
 * <p><tt>Problem p=new BitXnorer(x,y,z);</tt></p>
 * 
 * is satisfied, the following truth equation will be satisfied:
 *
 * <p><tt>z == ( x & y ) | ( !x & !y )</tt></p>
 *
 * @author  Kerry Michael Soileau
 * ksoileau@yahoo.com
 * http://kerrysoileau.com/index.html
 * @version 1.0, 05/04/13
 * @see IBooleanVariable
 * @see IClause
 * @see Problem
 */

public class BitXnorer extends Problem implements IProblem
{
  private static final long serialVersionUID = -8095193134269406271L;

	public BitXnorer(IBooleanVariable x, IBooleanVariable y, IBooleanVariable z) throws Exception
  {
    this.setClauses(new IClause[]
    {
    	Clause.newClause().or(x).or(y).or(z),
    	Clause.newClause().or(x).orNot(y).orNot(z),
    	Clause.newClause().orNot(x).or(y).orNot(z),
    	Clause.newClause().orNot(x).orNot(y).or(z)
    });
  }
}