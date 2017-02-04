/*
 * IBooleanLiteral.java
 *
 * Copyright 2004-2005 Positronic Software.
 *
 */

package positronic.satisfiability.elements;

import positronic.satisfiability.exceptions.BooleanLiteralException;

/** The <code>IBooleanLiteral</code> interface must be implemented by any class
  * definition of <code>BooleanLiteral</code> contemplated as an alternative to
  * the <code>BooleanLiteral</code> class provided by this package.
  *
  * @author  Kerry Michael Soileau
  * @version 1.1, 05/07/22
  */
public interface IBooleanLiteral extends Comparable<Object>
{
  /**
     * Constructs a new IBooleanLiteral with the same internal IBooleanVariable
     * but with opposite barring; that is, if this IBooleanLiteral is barred,
     * its complement will be unbarred, if not then its complement will be
     * barred.
     *
     * @return  IBooleanLiteral with the same internal IBooleanVariable but with
     * opposite barring.
     */
  IBooleanLiteral complement() throws BooleanLiteralException;

  /**
     * Returns <code>true</code> if
     * this.getBooleanVariable().getValue()==<code>true</code> and
     * this.isBarred()==<code>false</code> or
     * if this.getBooleanVariable().getValue()==<code>false</code> and
     * this.isBarred()==<code>true</code>.
     *
     * @return  <code>true</code> or <code>false</code>.
     */
  boolean evaluate();

  /**
     * Returns the IBooleanVariable internal to this IBooleanLiteral.
     *
     * @return  IBooleanVariable internal to this IBooleanLiteral.
     */
  IBooleanVariable getBooleanVariable();

  /**
     * Returns <code>true</code> if this IBooleanLiteral consists of a barred
     * IBooleanVariable; otherwise it returns <code>false</code>.
     *
     * @return  <code>true</code> or <code>false</code>.
     */
  boolean isBarred();

  /**
     * Set the value of this.getBooleanVariable() such that this IBooleanLiteral
     * evaluates to <code>true</code>.
     */
  void load();
}