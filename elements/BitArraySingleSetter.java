package positronic.satisfiability.elements;

import positronic.satisfiability.exceptions.BitArraySingleSetterException;

public class BitArraySingleSetter extends Problem implements IProblem 
{
	private static final long serialVersionUID = -704445761860954074L;

	public BitArraySingleSetter(IBooleanVariable[] array) throws Exception 
	{
		if(array==null)
			throw new BitArraySingleSetterException("Null array passed to constructor");
		if(array.length==0)
			throw new BitArraySingleSetterException("Array of length zero passed to constructor");
		
		IClause c=Clause.newClause();
		int bits=array.length;
		for(int i=0;i<bits;i++)
			c=c.or(array[i]);
		IProblem problem=Problem.trivialProblem();
		problem.addClause(c);
		
		for(int i=0;i<bits;i++)
			for(int j=i+1;j<bits;j++)
				problem.addClause(Clause.newClause().orNot(array[i]).orNot(array[j]));
		
		this.setClauses(problem.getClauses());
	}
}
