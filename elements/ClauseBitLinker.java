package positronic.satisfiability.elements;

public class ClauseBitLinker extends Problem implements IProblem
{
	private static final long serialVersionUID = 1L;

	/**
	 * This IProblem links an IBooleanVariable <tt>b</tt> with an IClause 
	 * <tt>c</tt> in the sense that <tt>b</tt> will equal <tt>true</tt> if and 
	 * only if <tt>c</tt> equals <tt>true</tt>. It's a way of equating the truth 
	 * value of an <tt>IClause</tt> with that of an <tt>IBooleanVariable</tt>.
	 * @throws Exception 
   */ 
	public ClauseBitLinker(IBooleanVariable b, IClause c) throws Exception
  {
  	IBooleanLiteral bUnbarred=BooleanLiteral.getBooleanLiteral(b,false);
  	IBooleanLiteral bBarred=BooleanLiteral.getBooleanLiteral(b,true);
  	IClause first=(IClause)c.clone();
  	first.add((BooleanLiteral)bBarred);
  	IClause[] cl=new Clause[c.size()];
  	for(int i=0;i<c.size();i++)
  	{
  		cl[i]=new Clause();
  		cl[i].add((BooleanLiteral)bUnbarred);
  		cl[i].add((BooleanLiteral)c.getLiteralAt(i).complement());
  	}
  	IProblem result=new Problem(cl);
  	result.addClause(first);
  	this.setClauses(result.getClauses());
  }
}
