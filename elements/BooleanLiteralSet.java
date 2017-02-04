package positronic.satisfiability.elements;

import java.util.Arrays;
import java.util.HashSet;

public class BooleanLiteralSet extends HashSet<IBooleanLiteral>
{
	private static final long serialVersionUID = 3711001432817310677L;

	public BooleanLiteralSet(IBooleanLiteral[] array)
	{
		for(IBooleanLiteral ibl : array)
			this.add(ibl);
	}
	
	public BooleanLiteralSet(IClause clause)
	{
		for(int i=0;i<clause.size();i++)
			try
			{
				this.add(clause.getLiteralAt(i));
			} catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public boolean contains(BooleanLiteralSet b)
	{
		for(IBooleanLiteral bl : b)
			if(!this.contains(bl))
				return false;
		return true;
	}
	
	public boolean contains(IBooleanLiteral bl)
	{
		for(IBooleanLiteral o : this)
			if(o.equals(bl))
				return true;
		return false;
	}
	
	public boolean properlycontains(BooleanLiteralSet b)
	{
		if(!b.contains(this) && this.contains(b))
			return true;
		return false;
	}
	
	public String toString()
	{
		Object[] sorted=this.toArray();
		Arrays.sort(sorted);
		String res="";
		for(Object bl : sorted)
			res+="$"+((IBooleanLiteral)bl).toString()+"$";
		return res;
	}
}
