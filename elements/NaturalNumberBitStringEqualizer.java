package positronic.satisfiability.elements;

/**
 * <p>Title: NaturalNumberBitStringEqualizer</p>
 * <p>Description: Constrains an INaturalNumber X to be that corresponding 
 * to an IBitString Y.</p>
 * <p>Copyright (c) 2004</p>
 * <p>Company: Positronic Software</p>
 * @author Kerry Michael Soileau
 * @version 1.0
 */
import positronic.satisfiability.bitstring.IBitString;
import positronic.satisfiability.exceptions.NaturalNumberException;
import positronic.satisfiability.naturalnumber.INaturalNumber;

public class NaturalNumberBitStringEqualizer extends Problem implements IProblem
{
  private static final long serialVersionUID = 1L;

  public NaturalNumberBitStringEqualizer(INaturalNumber X, IBitString Y) throws Exception
  {
		if(X==null || Y==null)
			throw new NaturalNumberException("A null INaturalNumber was passed to a constructor.");
    if(X.size()!=Y.size())
      throw new NaturalNumberException("NaturalNumberBitStringEqualizer: X.size()!=Y.size().");
    int commonsize=X.size();
    BitEqualizer[] thba=new BitEqualizer[commonsize];
    for(int i=0;i<commonsize;i++)
      thba[i]=new BitEqualizer(X.getBooleanVariable(i),Y.getBooleanVariable(i));
    IProblem p1=new Conjunction(thba);
    this.setClauses(p1.getClauses());
  }
}