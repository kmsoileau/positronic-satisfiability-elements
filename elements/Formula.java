/*
 * Formula.java	1.0 05/04/14
 *
 * Copyright 2004-2005 Positronic Software.
 *
 *
 */

package positronic.satisfiability.elements;

/**
 * A class which represents a collection of IClause objects, and which amounts
 * to a satisfiability problem. Formula is essentially a ArrayList of IClause
 * objects, and additionally provides several useful methods for combining
 * Formula objects, especially performing logical operations such as
 * <tt>and</tt> and <tt>or</tt> on such objects.
 *
 * This class is the superclass of numerous generic satisfiability problems.
 *
 * @author  Kerry Michael Soileau
 * <blockquote><pre>
 * ksoileau@yahoo.com
 * http://kerrysoileau.com/index.html
 * </pre></blockquote>
 * @version 1.0, 05/04/14
 * @see IProblem
 * @see Problem
 * @see IFormula
 */

public class Formula extends Problem implements IFormula
{
	private static final long serialVersionUID = -1899133473223413375L;
}
