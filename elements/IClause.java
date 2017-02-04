/*
 * IBooleanLiteral.java
 *
 * Copyright 2004-2005 Positronic Software.
 *
 */

package positronic.satisfiability.elements;

import java.util.List;
import java.util.Map;

 /** The <code>IClause</code> interface must be implemented by any class
  * definition of <code>Clause</code> contempleted as an alternative to
  * the <code>Clause</code> class provided by this package.
  *
  * @author  Kerry Michael Soileau
  * @version 1.1, 05/07/22
  */

public interface IClause extends Comparable<Object>
{
	boolean add(BooleanLiteral b) throws Exception;
	boolean add(IBooleanLiteral bl);
	Object clone();
	boolean contains(IBooleanLiteral bl) throws Exception;
	IBooleanLiteral differsSinglyFrom(IClause c) throws Exception;
	boolean dominates(IClause clause) throws Exception;
	IBooleanVariable[] getBooleanVariables();
	void getBooleanVariables(List<IBooleanVariable> hs) throws Exception;
	IBooleanLiteral getLiteralAt(int n) throws Exception;
	boolean isEmpty();
	boolean isMemberOf(List<IClause> h) throws Exception;
	boolean isSatisfied();
	boolean isSingleton();
	IClause minus(IClause o) throws Exception;
	IClause nor(IBooleanVariable bv) throws Exception;
	IClause or(IBooleanVariable bv) throws Exception;
	IClause orNot(IBooleanVariable bv) throws Exception;
	boolean remove(IBooleanLiteral b);
	IBooleanLiteral remove(int i);
	IClause resolve(IBooleanLiteral ib) throws Exception;
	IClause resolve(IBooleanVariable b, boolean value) throws Exception;
	int size();
	IClause substitute(Map<IBooleanLiteral,IBooleanLiteral> h) throws Exception;
	IBooleanLiteral[] toArray();
}