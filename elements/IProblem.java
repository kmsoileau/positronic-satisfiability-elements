/*
 * IProblem.java
 *
 * Copyright 2004-2005 Positronic Software.
 *
 */

package positronic.satisfiability.elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.sat4j.specs.ISolver;

import positronic.satisfiability.bitstring.IBitString;
import positronic.satisfiability.exceptions.ClauseException;

 /** The <code>IProblem</code> interface must be implemented by any class
  * definition of <tt>Problem</tt> contempleted as an alternative to the
  * <tt>Problem</tt> class provided by this package.
  *
  * @author  Kerry Michael Soileau
  * @version 1.4, 06/04/10
  */

public interface IProblem extends List<IClause>
{
	boolean addClause(IClause c);  
	IProblem combineSinglyMatchingClauses() throws Exception;
	boolean containsAnEmptyClause();
	IProblem eliminateComplementaryPairClauses() throws Exception;
	void eliminateEmptyClauses() throws Exception;
	ArrayList<IBooleanLiteral> findModel() throws Exception;
	ArrayList<IBooleanLiteral> findModel(ISolver is) throws Exception;
	ArrayList<?>[] findTwoModels(IBitString adjroom) throws Exception;
	ArrayList<?>[] findTwoModels(IBooleanVariable b) throws Exception;
	ArrayList<IBooleanVariable> getBooleanVariables() throws Exception;
	IClause getClause(int n);
	IClause[] getClauses();
	int numberOfClauses();
	IProblem resolve(List<IBooleanLiteral> ib) throws Exception;
	void setClause(int n, IClause cl);
	void setClauses(IClause[] cl);
	void sort();
	IProblem substitute(IBooleanVariable b, boolean value) throws Exception;
	IProblem substitute(Map<IBooleanLiteral,IBooleanLiteral> h) throws Exception;
	String toCode() throws ClauseException;
	long toFile(String s);
	String toMathematicaCode() throws ClauseException;
	IProblem toThreeSatProblem() throws Exception;
	String toXML();
}