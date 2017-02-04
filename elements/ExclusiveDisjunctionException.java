package positronic.satisfiability.elements;

/**
 * <p>Title: ProblemDifferencerException</p>
 * <p>Description: TBS</p>
 * <p>Copyright (c) 2007</p>
 * <p>Company: Positronic Software</p>
 * @author Kerry Michael Soileau
 * @version 1.0
 */

public class ExclusiveDisjunctionException extends Exception
{ 
  private static final long serialVersionUID = 812624742747428310L;

	public ExclusiveDisjunctionException(String s)
  {
    super(s);
  }
}