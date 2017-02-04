package positronic.satisfiability.elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sat4j.core.VecInt;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.IVecInt;

/**
 * A reader which is created using an object of type <tt>positronic.satisfiability.elements.ISolver</tt>
 * and has a method <tt>parseInstance</tt> which translates a <tt>positronic.satisfiability.elements.IProblem</tt> object into 
 * a corresponding <tt>org.sat4j.specs.IProblem</tt> object. 
 * 
 * For example, here's how a <tt>KSatReader</tt> might be used on a <tt>positronic.satisfiability.elements.IProblem</tt> 
 * called <tt>problem</tt>:
 *
 * <p><tt>...</tt></p>
 * <p><tt>ISolver solver = ... // some solver from a solver factory</tt></p>
 * <p><tt>KSatReader reader = new KSatReader(solver);</tt></p>
 * <p><tt>positronic.satisfiability.elements.IProblem problem = ... // some problem from KSat API</tt></p>
 * <p><tt>org.sat4j.specs.IProblem sat4jproblem = reader.parseInstance(problem);</tt></p>
 * <p><tt>if (sat4jproblem.isSatisfiable()) {</tt></p>
 * <p><tt>...</tt></p>
 * <p><tt>}</tt></p>
 * 
 * @author  Kerry Michael Soileau
 * ksoileau@yahoo.com
 * http://web.wt.net/$ksoileau/index.htm
 * @version 1.1, 06/01/29
 * @see org.sat4j.core.VecInt;
 * @see org.sat4j.specs.IVecInt;
 * @see positronic.satisfiability.elements.IBooleanLiteral;
 * @see positronic.satisfiability.elements.IBooleanVariable;
 * @see positronic.satisfiability.elements.IClause;
 * @see positronic.satisfiability.elements.IProblem;
 * @see positronic.satisfiability.elements.Problem;
 * @see positronic.satisfiability.exceptions.BooleanLiteralException;
 */
public class KSatReader
{
    private Map<IBooleanLiteral, Integer> BL2sat4jLiteral=new HashMap<IBooleanLiteral, Integer>();
    private List<IBooleanLiteral> booleanLiterals=new ArrayList<IBooleanLiteral>();
    private List<IBooleanVariable> booleanVariables=null;
    private Map<IBooleanVariable, Integer> BV2sat4jVariable=new HashMap<IBooleanVariable, Integer>();
    //private Set IVecIntSet=new HashSet();
    private int numberOfLiterals=0;
    private int numberOfVariables=0;
    private Map<Integer, IBooleanLiteral> sat4jLiteral2BL=new HashMap<Integer, IBooleanLiteral>();
    private IBooleanVariable[] sat4jVariable2BV=null;
    private ISolver solver=null;
    
    public KSatReader(ISolver solver)
    {
    	this.solver=solver;
    }
    
    //@SuppressWarnings("unchecked")
     
    public String decode(int[] dimacs)
    {
    	IClause cl=new Clause();
    	for(int i=0;i<dimacs.length;i++)
    	{
    		try 
    		{
					cl.add(this.getBL(dimacs[i]));
				} catch (Exception e) 
				{
					e.printStackTrace();
				}
    	}
    	return cl.toString();
    }
    
    public IBooleanLiteral getBL(int literal)
    {
        return (sat4jLiteral2BL.get(new Integer(literal)));
    }

    public List<IBooleanLiteral> getBooleanLiteralsArrayList()
    {
        return booleanLiterals;
    }

    public List<IBooleanVariable> getBooleanVariablesArrayList()
		{
			return booleanVariables;
		}
    
    public IBooleanVariable getBV(int variable)
    {
        return sat4jVariable2BV[variable-1];
    }
    
    /*public Set getIVecIntSet()
		{
			return IVecIntSet;
		}*/
    
    public int getNumberOfLiterals()
    {
        return numberOfLiterals;
    }
    
    public int getNumberOfVariables()
    {
        return numberOfVariables;
    }

    public int getSat4jLiteral(IBooleanLiteral il)
    {
        return (BL2sat4jLiteral.get(il)).intValue();
    }

    public int getSat4jVariable(IBooleanVariable ib)
    {
        return (BV2sat4jVariable.get(ib)).intValue();
    }

		public org.sat4j.specs.IProblem parseInstance(positronic.satisfiability.elements.IProblem problem) throws UnsolvableProblemException
    {
    	//Assign according to DIMACS format a unique integer to each positronic.satisfiability.elements.IBooleanVariable found in problem
      try
      {
      	ArrayList<IBooleanVariable> bvArrayList=new ArrayList<IBooleanVariable>();
        for(int i=0;i<problem.numberOfClauses();i++)
        {
        	IClause clause=problem.getClause(i);
          if(clause!=null)
          	for(int j=0;j<clause.size();j++)
          	{
          		IBooleanVariable bv=clause.getLiteralAt(j).getBooleanVariable();
          		if(!bvArrayList.contains(bv))
          				bvArrayList.add(bv);
          	}
        }
        this.booleanVariables=bvArrayList;
      } catch (Exception e)
      {
          e.printStackTrace();
      }
      
      this.numberOfVariables=getBooleanVariablesArrayList().size();
      sat4jVariable2BV=new IBooleanVariable[this.numberOfVariables];
      for(int i=0;i<this.numberOfVariables;i++)
      {
          BV2sat4jVariable.put(getBooleanVariablesArrayList().get(i),new Integer(i+1));
          sat4jVariable2BV[i]=getBooleanVariablesArrayList().get(i);
      }
      
      //Assign according to DIMACS format a unique integer to each positronic.satisfiability.elements.IBooleanLiteral found in problem
      for(int i=0;i<problem.getClauses().length;i++)
      {
          IClause currClause=problem.getClauses()[i];
          for(int j=0;j<currClause.size();j++)
          {
              IBooleanLiteral currBL=null;
              try
              {
                  currBL = currClause.getLiteralAt(j);
              }
              catch (Exception e)
              {
                  e.printStackTrace();
              }
              if(!getBooleanLiteralsArrayList().contains(currBL))
              	getBooleanLiteralsArrayList().add(currBL);
          }
      }
      this.numberOfLiterals=getBooleanLiteralsArrayList().size();
      for(int i=0;i<this.numberOfLiterals;i++)
      {
          IBooleanLiteral currBL=(getBooleanLiteralsArrayList().get(i));
          Integer objSat4jLiteral= new Integer((currBL.isBarred()?-1:1)*this.getSat4jVariable(currBL.getBooleanVariable()));
          BL2sat4jLiteral.put(currBL,objSat4jLiteral);
          sat4jLiteral2BL.put(objSat4jLiteral,currBL);
      }
      this.solver.newVar(this.numberOfVariables);
      for(int i=0;i<problem.size();i++)
      {
      	IClause currClause=problem.getClause(i);
      	IVecInt currIvi=new VecInt();
      	for(int j=0;j<currClause.size();j++)
      	{
      		IBooleanLiteral currBL=null;
      		try
					{
						currBL=currClause.getLiteralAt(j);
					} catch (Exception e)
					{
						e.printStackTrace();
					}
					currIvi.push(this.getSat4jLiteral(currBL));
      	}
      	
      	//System.out.println("About to add "+currIvi);
      	
      	try
				{
					this.solver.addClause(currIvi);
				} catch (ContradictionException e)
				{
					int[] d=new int[currIvi.size()];
					currIvi.copyTo(d);
					//System.out.println("An "+e.getClass().getName()+" exception was thrown " +
					//		"while trying to use the \naddClause method of " +
					//		"org.sat4j.specs.ISolver on the argument "+currIvi+" which is decoded as \n"+this.decode(d));
					//e.printStackTrace();
					//throw new UnsolvableProblemException("Problem is unsolvable.");
				}
      }
      return this.solver;
    }

		public List<IBooleanLiteral> toBooleanLiterals(int[] dimacs)
    {
    	List<IBooleanLiteral> cl=new ArrayList<IBooleanLiteral>();
    	for(int i=0;i<dimacs.length;i++)
    	{
    		try 
    		{
					cl.add(this.getBL(dimacs[i]));
				} 
    		catch (Exception e) 
				{
					e.printStackTrace();
				}
    	}
    	return cl;
    }
}

class UnsolvableProblemException extends Exception
{
	private static final long serialVersionUID = 6115321908560885729L;

	public UnsolvableProblemException(String s)
	{
		super(s);
	}
}

