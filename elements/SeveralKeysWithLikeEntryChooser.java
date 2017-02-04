package positronic.satisfiability.elements;

import positronic.satisfiability.bitstring.BitStringArrayDistincter;
import positronic.satisfiability.bitstring.IBitString;
import positronic.satisfiability.naturalnumber.INaturalNumber;

public class SeveralKeysWithLikeEntryChooser extends Problem implements IProblem
{
	private static final long serialVersionUID = -5002916168333052917L;

	public SeveralKeysWithLikeEntryChooser(int[] supply,IBitString[] key,INaturalNumber supplyEntry) throws Exception 
	{
		IProblem problem=new BitStringArrayDistincter(key);
		for(IBitString b : key)
			problem=new Conjunction(problem,new Mapper(supply, b, supplyEntry));
		this.setClauses(problem.getClauses());	
	}
}
