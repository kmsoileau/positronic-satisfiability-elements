/*
 * IBooleanVariable.java
 *
 * Copyright 2004-2014 Positronic Software.
 *
 */

package positronic.satisfiability.elements;

import positronic.satisfiability.exceptions.BooleanVariableException;

/** The <code>IBooleanVariable</code> interface must be implemented by any class 
 * definition of <code>BooleanVariable</code> contempleted as an alternative to 
 * the <code>BooleanVariable</code> class provided by this package.
 * 
 * @author  Kerry Michael Soileau
 * @version 1.11, 05/10/24
 */
public interface IBooleanVariable extends Comparable<Object>
{
  String DEFAULTPREFIX = "BooleanVariable$";

  int compareTo(Object o);
  
  /**
   * Two IBooleanVariables x and y are equal if and only if x.getName().compareTo(y.getName())==0.
   * 
   * @return  <code>true</code> if this.getName().compareTo(o.getName())==0.
   */
  boolean equals(Object o);
	
  /**
   * Returns the name as String.
   *
   * @return  the name as String.
   */
  String getName();
  
  /**
   * Returns true if the logical value is true, otherwise returns false.
   *
   * @return  logical value: <code>true</code> or <code>false</code>.
   */
  boolean getValue();
  
  /**
   * Sets the prefix for names of BooleanVariables created without
   * a name.
   */
  void setDefaultPrefix(String s);
  
  /**
   * Sets the name.
   */
  void setName(String name) throws BooleanVariableException;
  
  /**
   * Sets the logical value: <code>true</code> or <code>false</code>.
   */
  void setValue(boolean x);
  
  String toString();
}


