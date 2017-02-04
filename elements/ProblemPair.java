package positronic.satisfiability.elements;


public class ProblemPair
{
	private IProblem first;
	private IProblem second;
	
	public ProblemPair(IProblem first, IProblem second)
	{
		this.first=first;
		this.second=second;
	}

	public IProblem getFirst()
	{
		return first;
	}

	public IProblem getSecond()
	{
		return second;
	}

	public void setFirst(IProblem first)
	{
		this.first = first;
	}

	public void setSecond(IProblem second)
	{
		this.second = second;
	}
}
