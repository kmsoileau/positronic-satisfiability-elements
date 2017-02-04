/*
 * BitOrderer.java 1.0 05/05/04
 *
 * Copyright 2005,2006 Positronic Software.
 *
 *
 */
 /**
 * An extension of the Problem class which imposes a Boolean relation on two
 * IBooleanVariables. For example, the Problem instance p defined by
 *
 * <p><tt>Problem p=new BitOrderer(x,y);</tt></p>
 *
 * is satisfied if and only if the following Boolean relation is satisfied:
 *
 * <p><tt>x implies y</tt></p>
 *
 * Equivalently, <p><tt>Problem p=new BitOrderer(x,y);</tt></p>
 *
 * is satisfied if and only if the following Boolean relation is satisfied:
 *
 * <p><tt>y==true or (y==false && x==false)</tt></p>.
 *
 * @author  Kerry Michael Soileau
 * ksoileau@yahoo.com
 * http://kerrysoileau.com/index.html
 * @version 1.0, 05/05/04
 * @see BitFixer
 * @see Conjunction
 * @see Disjunction
 */
package positronic.satisfiability.elements;

public class BitOrderer extends Problem implements IProblem
{
  private static final long serialVersionUID = -3918521182493099543L;

	public BitOrderer(IBooleanVariable x, IBooleanVariable y) throws Exception
  {
    IProblem compare=new Disjunction(new BitFixer(y,true),
      new Conjunction(new BitFixer(y,false),new BitFixer(x,false)));
    this.setClauses(compare.getClauses());
  }
}