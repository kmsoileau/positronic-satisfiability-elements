/*
 * Certificate.java	2.0 05/04/19
 *
 * Copyright 2004-2005 Positronic Software.
 *
 *
 */
/**
 * A class which represents a certificate. A certificate is a mapping of a set
 * of BooleanVariables into {true,false}. A certificate is used to represent a
 * solution of a satisfiability problem.
 *
 * @author  Kerry Michael Soileau
 * <blockquote><pre>
 * ksoileau@yahoo.com
 * http://kerrysoileau.com/index.html
 * </pre></blockquote>
 * @version 2.0, 05/04/19
 * @see ICertificate
 * @see HashMap
 * @see IBooleanVariable
 * @see Iterator
 * @see BooleanLiteralException
 * @see List
 */

package positronic.satisfiability.elements;

import positronic.satisfiability.exceptions.BooleanLiteralException;
import positronic.util.ArrayListSet;

public class Certificate implements ICertificate
{
	private ArrayListSet<IBooleanLiteral> booleanLiterals;

	public void add(IBooleanVariable x, boolean val) throws BooleanLiteralException
	{
		this.getBooleanLiterals().add(BooleanLiteral.getBooleanLiteral(x,!val));
	}

	public boolean containsBooleanVariable(IBooleanVariable x) throws BooleanLiteralException
	{
		if(!(x instanceof IBooleanVariable))
			return false;
		return this.getBooleanLiterals().contains(x);
	}

	public ArrayListSet<IBooleanLiteral> getBooleanLiterals() throws BooleanLiteralException
	{
		if(this.booleanLiterals==null)
			this.booleanLiterals=new ArrayListSet<IBooleanLiteral>();
		return this.booleanLiterals;
	}

	public boolean getValue(IBooleanVariable x) throws BooleanLiteralException
	{
		if(!this.containsBooleanVariable(x))
			return false; //default if IBooleanVariable x is not in Certificate
		int ind=this.getBooleanLiterals().indexOf(x);
		return !((IBooleanLiteral)this.getBooleanLiterals().get(ind)).isBarred();
	}

	public void setBooleanLiterals(ArrayListSet<IBooleanLiteral> booleanLiterals)
	{
		this.booleanLiterals = booleanLiterals;
	}

	public String toString()
	{
		try
		{
			return this.getBooleanLiterals().toString();
		}
		catch(BooleanLiteralException err)
		{
			return null;
		}
	}
}