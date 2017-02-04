package positronic.satisfiability.elements;

public class ThreeBitAdderOptimized extends Problem implements IProblem
{
	private static final long serialVersionUID = -2792117105147014354L;

	public ThreeBitAdderOptimized(
			IBooleanVariable w, 
			IBooleanVariable x, 
			IBooleanVariable y, 
			IBooleanVariable z, 
			IBooleanVariable c) throws Exception
	{
		this.setClauses(
				new IClause[]
				{
				  Clause.newClause().or(w).or(x).or(y).or(z).orNot(c),
				  Clause.newClause().or(w).or(x).or(y).orNot(z),
				  Clause.newClause().or(w).or(x).orNot(y).or(z),
				  Clause.newClause().or(w).or(x).orNot(y).orNot(z).orNot(c),
				  Clause.newClause().or(w).orNot(x).or(y).or(z),
				  Clause.newClause().or(w).orNot(x).orNot(z).orNot(c),
				  Clause.newClause().or(w).orNot(x).orNot(y).or(c),
				  Clause.newClause().orNot(w).or(x).or(y).or(z),
				  Clause.newClause().orNot(w).or(x).orNot(z).orNot(c),
				  Clause.newClause().orNot(w).or(x).orNot(y).or(c),
				  Clause.newClause().orNot(w).orNot(x).or(y).or(z).or(c),
				  Clause.newClause().orNot(w).orNot(x).or(y).orNot(z),
				  Clause.newClause().orNot(w).orNot(x).orNot(y).or(z),
				  Clause.newClause().orNot(w).orNot(x).orNot(y).orNot(z).or(c)
				});
	}
}
