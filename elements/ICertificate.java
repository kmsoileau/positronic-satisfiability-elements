/*
 * ICertificate.java
 *
 * Copyright 2004-2005 Positronic Software.
 *
 */

package positronic.satisfiability.elements;

import positronic.satisfiability.exceptions.BooleanLiteralException;
import positronic.util.ArrayListSet;

 /** The <code>ICertificate</code> interface must be implemented by any class
  * definition of <code>Certificate</code> contempleted as an alternative to the
  * <code>Certificate</code> class provided by this package.
  *
  * @author  Kerry Michael Soileau
  * @version 1.1, 05/07/22
  */
public interface ICertificate
{
  void add(IBooleanVariable x, boolean val) throws BooleanLiteralException;
  boolean containsBooleanVariable(IBooleanVariable x) throws BooleanLiteralException;
  ArrayListSet<IBooleanLiteral> getBooleanLiterals() throws BooleanLiteralException;
  boolean getValue(IBooleanVariable x) throws BooleanLiteralException;
  void setBooleanLiterals(ArrayListSet<IBooleanLiteral> booleanLiterals);
  String toString();
}