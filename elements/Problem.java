/*
 * Problem.java	1.42 14/09/22
 *
 * Copyright 2004-2014 Positronic Software.
 *
 *
 */

package positronic.satisfiability.elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.sat4j.minisat.SolverFactory;
import org.sat4j.specs.ISolver;

import positronic.math.EquivalenceRelation;
import positronic.satisfiability.bitstring.IBitString;
import positronic.satisfiability.exceptions.ClauseException;
import positronic.satisfiability.exceptions.ProblemException;
import positronic.satisfiability.naturalnumber.INaturalNumber;
import positronic.satisfiability.naturalnumber.NaturalNumber;
import positronic.satisfiability.sat4j.KSatReader;

/**
 * A class which represents a collection of IClause objects, and which amounts
 * to a satisfiability problem. Problem is essentially a ArrayList of IClause
 * objects, and additionally provides several useful methods for combining
 * Problem objects, especially performing logical operations such as
 * <tt>and</tt> and <tt>or</tt> on such objects.
 *
 * This class is the superclass of numerous generic satisfiability problems.
 *
 * @author  Kerry Michael Soileau
 * <blockquote><pre>
 * ksoileau@yahoo.com
 * http://kerrysoileau.com/index.html
 * </pre></blockquote>
 * @version 1.3, 05/10/07
 * @see IClause
 * @see IBooleanVariable
 * @see IBooleanLiteral
 * @see NaturalNumber
 * @see BitAnder
 * @see BitEqualityIndicator
 * @see BitEqualizer
 * @see BitFixer
 * @see BitInequalityIndicator
 * @see BitNoter
 * @see BitOrer
 * @see BitXorer
 */

public class Problem extends ArrayList<IClause> implements IProblem
{
	private static int problemCount;
	private static final long serialVersionUID = 3875190018670130568L;
	private static PrintStream stream = System.out;

	public static ISolver defaultSolver() 
	{
		return SolverFactory.newMiniSATHeap();
	}

	public static IProblem newProblem()
	{
	  return (IProblem)new Problem();
	}

	public static IProblem randomProblem(IBooleanVariable[] bv, int n) throws Exception
	{
	  Problem p=new Problem();
	  for(int i=0;i<n;i++)
	    p.addClause(Clause.randomClause(bv));
	  for(int i=0;i<p.numberOfClauses();i++)
	    if(p.getClause(i).isEmpty())
	      p.removeClause(i);
	  //p.sortClauses();
	  return p;
	}
	
	public static IProblem trivialProblem()
	{
		return new Problem(new IClause[]{null});
	}

	public static IProblem unsolvableProblem()
	{
		return new Problem(new IClause[]{new Clause()});
	}
	
	/**
   	* Constructs an empty Problem, that is, an instance of Problem 
   	* which contains no IClauses.
   	* 
   	*/
	public Problem()
	{
  	}

	/**
   	* Constructs an instance of Problem which contains the IClauses found in the
   	* parameter v.
   	* @param v the ArrayList of IClauses to comprise the instance of Problem.
   	*/
	public Problem(ArrayList<IClause> v)
	{
	  	if(v!=null)
	  	{
	    	for(int i=0;i<v.size();i++)
	    	{
	    		IClause o=v.get(i);
	    		if(o instanceof IClause)
	    		{
	    			IClause c=(IClause)o;
	    			this.addClause(c);
	    		}
	    	}
	  	}
	}

	/**
	 * Constructs an instance of Problem which contains the IClauses 
	 * found in the parameter clause.
	 * @param clause the array of IClauses to comprise the instance of Problem.
	 */
	public Problem(IClause[] clause)
	{
	  this.setClauses(clause);
	}

	public Problem(IProblem iproblem)
	{
	  this.setClauses(iproblem.getClauses());
	}

	public boolean addClause(IClause c)
	{
  		if(c==null)
  			return false;
  		if(!this.contains(c))
    	{
  			super.add(c);
  			return true;
    	}
    	else
    		return false;
	}

	public void addClauseVoid(IClause c) 
	{
		this.addClause(c);
	}

	public void addClauseVoid(IClause[] c)
  	{
    	if(c==null || c.length==0) return;
    	for(int i=0;i<c.length;i++)
    		this.addClause(c[i]);
  	}

  	public IProblem and(IProblem p)
  	{
    	return new Conjunction(new IProblem[]{p,this});
  	}

  	private Split bestSplit() throws Exception 
	{
		Split bestSplit=new Split();
		bestSplit.var=null;
		bestSplit.prob1=null;
		bestSplit.prob2=null;
		bestSplit.measure=Integer.MAX_VALUE;
		
		for(IBooleanVariable ivar : this.getBooleanVariables())
		{
			BooleanVariable var=(BooleanVariable)ivar;
			Problem prob1=(Problem)this.substitute(var,true);
			Problem prob2=(Problem)this.substitute(var,false);
			int psize1;
			int psize2;
			if(prob1!=null)
				psize1=bulk(prob1);
			else
				psize1=0;
			if(prob2!=null)
				psize2=bulk(prob2);
			else
				psize2=0;
			int measure=-1;
			measure = psize1+psize2;
			
			if(measure<bestSplit.measure)
			{
				bestSplit.var=var;
				bestSplit.prob1=prob1;
				bestSplit.prob2=prob2;
				bestSplit.measure=measure;
			}
		}
		return bestSplit;
	}

  	public EquivalenceRelation buildEquivalenceRelation()
  	{
    	EquivalenceRelation e=new EquivalenceRelation();

	    for(int i=0;i<this.numberOfClauses();i++)
	    {
	      Object[] objary=this.getClause(i).getBooleanVariables();
	      for(int j=0;j<objary.length;j++)
	        for(int k=0;k<objary.length;k++)
	          e.add(objary[j],objary[k]);
	    }
	    return e;
  	}

  	private int bulk(IProblem prob1) 
	{
		if(prob1==null || prob1.size()==0)
			return 0;
		int ret=0;
		for(int i=0;i<prob1.size();i++)
			ret+=prob1.getClause(i).size();
		return ret;
	}

  	public Object clone()
  	{
	    Object[] cobj=this.getClauses();
	    IClause[] clauses=new IClause[cobj.length];
	    for(int i=0;i<cobj.length;i++)
	      clauses[i]=(IClause)(cobj[i]);
	    IProblem res=new Problem(clauses);
	    return res;
  	}

  public IProblem combineSinglyMatchingClauses() throws Exception
{
  	int psize=this.size();
  	IProblem ret=Problem.newProblem();
  	for(int i=0;i<psize;i++)
  	{
  		IClause clausei=this.getClause(i);
  		boolean newclausecreated=false;
  		for(int j=0;j<psize;j++)
  		{
  			IClause clausej=this.getClause(j);
  			IBooleanLiteral diff=clausei.differsSinglyFrom(clausej);
  			if(diff!=null)
  			{
  				IClause newclause=(IClause)clausei.clone();
  				newclause.remove(diff);
  				ret.addClause(newclause);
  				newclausecreated=true;
  			}
  		}
  		if(!newclausecreated) ret.addClause(clausei);
  	}
  	return ret;
}

  public boolean contains(IClause c)
{
	if(c==null)
      return false;
    for(int i=0;i<this.numberOfClauses();i++)
      if(this.getClause(i).equals(c))
        return true;
    return false;
}
  
  public boolean containsAnEmptyClause()
{
    for(int i=0;i<this.numberOfClauses();i++)
      if(this.getClause(i).isEmpty())
        return true;
    return false;
}
  
  public IProblem eliminateComplementaryPairClauses() throws Exception
  {
    IProblem dup=Problem.newProblem();
    for(int i=0;i<this.numberOfClauses();i++)
    {
    	//Get the ith clause in the IProblem p
      IClause c=this.getClause(i);
      boolean removeClause=false;
      //Iterate through the IClause, looking for complementary IBooleanLiterals
      for(int j=0;j<c.size();j++)
      {
        IBooleanLiteral ib=c.getLiteralAt(j);
        if(!c.contains(ib.complement()))//Detected no complementary IBooleanLiterals
          continue;//Continue searching the IClause
        removeClause=true;//Detected a pair of complementary IBooleanLiterals
        break;
      }
      if(!removeClause)//No complementary IBooleanLiterals were found in the IClause
        dup.addClause(c);//Add the IClause to the IProblem dup
    }
    return dup;
  }
  
  public void eliminateEmptyClauses() 
{
	for(int n=0;n<this.size();n++)
	{
		if(this.getClause(n).isEmpty())
			this.removeClause(n);
	}
}
  
  public boolean equals(List<IBooleanLiteral> p)
  {
    if(!(p instanceof List))
      return false;
    if(this.containsAll((List<IBooleanLiteral>)p) && ((List<?>)p).containsAll(this))
      return true;
    return false;
  }
  
  public String factorization() throws Exception
{
	  if(this.size()==0)
		  return "";
	if(this.size()==1)
		if(this.containsAnEmptyClause())
			return "";
		else
			return this.getClause(0).getLiteralAt(0).toString();
	Split split=this.bestSplit();
	if(split.prob1!=null && split.prob2!=null && split.prob2.factorization()!=null)
		return split.var.getName()+"("+split.prob1.factorization()+
				")\n+$"+split.var.getName()+"("+split.prob2.factorization()+")";
	if(split.prob1!=null && split.prob2!=null && split.prob2.factorization()==null)
		return split.var.getName()+"("+split.prob1.factorization()+
				")\n+$"+split.var.getName();
	
	
	if(split.prob1!=null && split.prob2==null && split.prob1.factorization()!=null)
		return split.var.getName()+"("+split.prob1.factorization()+
				")+\n$"+split.var.getName();
	if(split.prob1!=null && split.prob2==null && split.prob1.factorization()==null)
		return split.var.getName()+
				"+\n$"+split.var.getName();
	
	
	if(split.prob1==null && split.prob2!=null && split.prob2.factorization()!=null)
		return "$"+split.var.getName()+"("+split.prob2.factorization()+")";
	if(split.prob1==null && split.prob2!=null && split.prob2.factorization()==null)
		return "$"+split.var.getName();
	return "";
}
  
  public ArrayList<IBooleanLiteral> findModel() throws Exception
  {
  	return findModel(Problem.defaultSolver());
  }
  
  public ArrayList<IBooleanLiteral> findModel(ISolver solver) throws Exception
  {
  	KSatReader reader=new KSatReader(solver);
		org.sat4j.specs.IProblem sat4jproblem=reader.parseInstance(this);
		if(!sat4jproblem.isSatisfiable())
  		return new ArrayList<IBooleanLiteral>();
		ArrayList<IBooleanLiteral> rl=reader.toBooleanLiterals(sat4jproblem.model());
  	IProblem test=this.resolve(rl);
  	if(test.size()>0)
  		return new ArrayList<IBooleanLiteral>();
  	return rl;
  }
  
  public List<IBooleanLiteral> findModelList() throws Exception
{
	return findModelList(Problem.defaultSolver());
}

  public List<IBooleanLiteral> findModelList(ISolver s) throws Exception 
{
	return (List<IBooleanLiteral>) findModel(s);
}

  public ArrayList<?>[] findTwoModels(IBitString b) throws Exception
  {
  	return findTwoModels(b.getBVArray());
  }

  public ArrayList<?>[] findTwoModels(IBooleanVariable b) throws Exception
  {
  	ArrayList<?>[] res=new ArrayList<?>[2];
  	if(!this.getBooleanVariables().contains(b))
  		throw new ProblemException("The given IProblem does not depend upon the given IBooleanVariable.");
  	else
  	{
  		IProblem p1=new Conjunction(this,new BitFixer(b,false));
  		IProblem p2=new Conjunction(this,new BitFixer(b,true));
  		res[0]=(ArrayList<?>) p1.findModel(Problem.defaultSolver());
  		res[1]=(ArrayList<?>) p2.findModel(Problem.defaultSolver());
  	}
  	return res;
  }

  public ArrayList<?>[] findTwoModels(IBooleanVariable b,IProblem problem) throws Exception
  {
  	return new Conjunction(this,problem).findTwoModels(b);
  }

  public ArrayList<?>[] findTwoModels(IBooleanVariable[] b) throws Exception
  {
  	IProblem res[]=new IProblem[b.length];
  	for(int i=0;i<res.length;i++)
  	{
  		ArrayList<?>[] ret=findTwoModels(b[i]);
  		if(ret!=null & ret.length==2 
  				&& ret[0]!=null && ret[0].size()>0
  				&& ret[1]!=null && ret[1].size()>0)
  		return ret;
  	}
  	return null;
  }

  public ArrayList<?>[] findTwoModels(INaturalNumber n) throws Exception
  {
  	return findTwoModels(n.getBVArray());
  }

  public ArrayList<IBooleanVariable> getBooleanVariables() throws Exception
  {
	  ArrayList<IBooleanVariable> hs=new ArrayList<IBooleanVariable>();
	  for(int i=0;i<this.numberOfClauses();i++)
	  {
		  IClause curr=this.getClause(i);
		  if(curr!=null)
		  {
      		IBooleanVariable[] currObjArray=curr.getBooleanVariables();
      		for(int j=0;j<currObjArray.length;j++)
      			if(!hs.contains(currObjArray[j]))
      				hs.add(currObjArray[j]);
      }
    }
    return hs;
  }

  public IClause getClause(int n)
  {
    return (IClause)(super.get(n));
  }

  public IClause[] getClauses()
  {
    IClause[] clauses=new IClause[super.size()];
    for(int i=0;i<clauses.length;i++)
      clauses[i]=(IClause)(super.get(i));
    return clauses;
  }

  public PrintStream getStream() 
  {
    return stream;
  }

  public boolean isEmpty()
  {
    return (this.numberOfClauses()==0);
  }

  public boolean isSatisfied()
  {
    for(int i=0;i<this.numberOfClauses();i++)
      if(!this.getClause(i).isSatisfied())
        return false;
    return true;
  }

  public IBooleanVariable newBooleanVariable() throws Exception
  {
    return BooleanVariable.getBooleanVariable();
  }
  
  public int numberOfClauses()
  {
    return super.size();
  }

  public int occurrences(IBooleanLiteral bl) throws Exception
  {
    int count=0;
    for (int i = 0; i < this.numberOfClauses(); i++)
    {
      IClause c=this.getClause(i);
      for(int j=0;j<c.size();j++)
      {
        IBooleanLiteral bq=c.getLiteralAt(j);
        if(bl.equals(bq))
          count++;
      }
    }
    return count;
  }
  
  public IProblem or(IProblem pfalse, IBooleanVariable b) throws Exception
  {
    return new Disjunction(this,pfalse,b);
  }
  
  /*@Override
public Stream<IClause> parallelStream() {
	// TODO Auto-generated method stub
	return null;
}*/

  public void removeAllClauses()
  {
    super.clear();
  }
  
  public void removeClause(int n)
  {
    super.remove(n);
  }
  
  public IProblem resolve(IBooleanVariable b, boolean value) throws Exception
  {
    IClause[] c=new IClause[this.numberOfClauses()];
    for(int i=0;i<this.numberOfClauses();i++)
      c[i]=this.getClause(i).resolve(b,value);
    IProblem ret=Problem.newProblem();
    ret.setClauses(c);
    return ret;
  }
  
  public IProblem resolve(List<IBooleanLiteral> ib) throws Exception
  {
    IProblem res=(IProblem)this.clone();
    for(int i=0;i<res.numberOfClauses();i++)
    {
      IClause c=res.getClause(i);
      
      for(int j=0;j<ib.size();j++)
      {
      	if(c==null)
      		break;
        IBooleanLiteral ibcurr=(IBooleanLiteral)ib.get(j);
        IClause newcl=null;
        try
        {
        newcl=c.resolve(ibcurr.getBooleanVariable(),!ibcurr.isBarred());
        }
        catch(NullPointerException err)
        {
        }
        
        c=newcl;
      }
      res.setClause(i,c);
    }
    
    int pos=0;
    while(pos<res.size())
    {
      if(res.getClause(pos)!=null)
      	pos++;
      else
      	((Problem) res).removeClause(pos);
    }
    
    return res;
  }
  
  public void setClause(int n, IClause cl)
  {
    super.set(n,cl);
  }
  
  public void setClauses(IClause[] cl)
  {
    this.removeAllClauses();
    if(cl!=null)
    	for(int i=0;i<cl.length;i++)
    		this.addClause(cl[i]);
  }
  
  public void setStream(PrintStream stream) 
  {
    Problem.stream = stream;
  }
  
  public List<IBooleanLiteral> solveList() throws Exception
  {
  	if(this.isEmpty())
  		throw new ProblemException("Empty IProblem was passed to method solveList.");
  	return this.findModel();
  }
  
  public void sort()
  {
    IClause[] ary=(IClause[])this.toArray(new IClause[0]);
    Arrays.sort(ary);
    this.setClauses(ary);
  }

/*@Override
public Stream<IClause> stream() {
	// TODO Auto-generated method stub
	return null;
}*/

	public IProblem substitute(IBooleanVariable b, boolean value) throws Exception
	  {
	    ArrayList<IClause> h=new ArrayList<IClause>();
	    for(int i=0;i<this.numberOfClauses();i++)
	    {
	      IClause cr=(this.getClause(i)).resolve(b,value);
	      if(cr!=null && !(cr.isMemberOf(h)))
	        h.add(cr);
	    }
	    
	    IProblem res=new Problem();
	    Iterator<IClause> it=h.iterator();
	    while(it.hasNext())
	    {
	      res.addClause((it.next()));
	    }
	    if(res.numberOfClauses()>0)
	      return res;
	    else
	      return null;
	  }

	public IProblem substitute(Map<IBooleanLiteral,IBooleanLiteral> h) throws Exception
	  {
	    for(int i=0;i<this.numberOfClauses();i++)
	    {
	      IClause c=this.getClause(i);
	      c.substitute(h);
	    }
	    return this;
	  }
	
	public IProblem substitute(Object[] b) throws Exception
	  {
	    IProblem res=(IProblem)this.clone();
	    for(int i=0;i<res.numberOfClauses();i++)
	    {
	      IClause c=res.getClause(i);
	      IClause newc=(IClause)c.clone();
	      for(int j=0;j<b.length;j++)
	      {
	        if(newc==null)break;
	        newc=newc.resolve((IBooleanLiteral)b[j]);
	      }
	      res.setClause(i,newc);
	    }
	    return res;
	  }

	public String toCode() throws ClauseException
	  {
		if(this.size()<1)
			return null;
		String ret=((Clause) this.get(0)).toCode();
		for(int i=1;i<this.size();i++)
			ret+="+"+((Clause) this.get(i)).toCode();
		return ret;
	  }

	public long toFile(String s)
	  {
	    File f=new File(s);
	    PrintStream fos=null;
	    try
	    {
	      f.createNewFile();
	      fos=new PrintStream(new FileOutputStream(f));
	      fos.println(this.toString());
	      fos.close();
	    }
	    catch(Exception err)
	    {
	      err.printStackTrace();
	    }
	
	    return f.length();
	  }

	public String toSatSimTable() throws Exception
	  {
		  String ret="{";
		  for(int clauseindex=0;clauseindex<this.numberOfClauses()-1;clauseindex++)
		  {
			  IClause currentClause = this.getClause(clauseindex);
			  ret+="{";
			  for(int literalindex=0;literalindex<currentClause.size()-1;literalindex++)
			  {
				  IBooleanLiteral currentLiteral = currentClause.getLiteralAt(literalindex);
				  ret+="{"+(currentLiteral.isBarred()?1:0)+","+currentLiteral.getBooleanVariable().getName().toString()+"},";
			  }
			  IBooleanLiteral currentLiteral = currentClause.getLiteralAt(currentClause.size()-1);
			  ret+="{"+(currentLiteral.isBarred()?1:0)+","+currentLiteral.getBooleanVariable().getName().toString()+"}},";
		  }
		  IClause currentClause = this.getClause(this.numberOfClauses()-1);
		  ret+="{";
		  for(int literalindex=0;literalindex<currentClause.size()-1;literalindex++)
		  {
			  IBooleanLiteral currentLiteral = currentClause.getLiteralAt(literalindex);
			  ret+="{"+(currentLiteral.isBarred()?1:0)+","+currentLiteral.getBooleanVariable().getName().toString()+"},";
		  }
		  IBooleanLiteral currentLiteral = currentClause.getLiteralAt(currentClause.size()-1);
		  ret+="{"+(currentLiteral.isBarred()?1:0)+","+currentLiteral.getBooleanVariable().getName().toString()+"}}}";

		  ret=ret.replaceAll("$","");
		  ret=ret.replaceAll("\\$","");
		  ret=ret.replaceAll("_","");
		  ret=ret.replaceAll("$","");
		  ret=ret.replaceAll("-","");
		  
		  return ret;
	  }

	public String toString()
	  {
	    String res="***************************************";
	    res+="\n*** IProblem-"+ problemCount++ ;
	    res+="\n***************************************";
	    for(int i=0;i<this.numberOfClauses();i++)
	    {
	      if(this.getClause(i)!=null)
	        res+="\n*** "+"\t"+this.getClause(i).toString();
	      else
	        res+="\n*** "+"\t"+"null";
	    }
	    res+="\n***************************************";
	    res+="\n*****\t"+this.numberOfClauses()+" clauses generated.";
	    res+="\n***************************************";
	    return res;
	  }

	public IProblem toThreeSatProblem() throws Exception
	  {
	  	if(this.size()==0)
	  		return this;
	  	IProblem problem=null;
	  	if(this.getClause(0)!=null)
	  		problem=((Clause)this.getClause(0)).ThreeSATProblem();
	  	for(int i=1;i<this.size();i++)
	  		if(this.getClause(i)!=null)
	  			problem=new Conjunction(problem,((Clause)this.getClause(i)).ThreeSATProblem());
	  	return problem;
	  }
	
	/*
	  <?xml version="1.0" encoding="UTF-8" ?>
	  <!ELEMENT Literal EMPTY >
	  <!ATTLIST Literal variable NMTOKEN #REQUIRED >
	  <!ATTLIST Literal barred ( false | true ) #REQUIRED >
	  <!ELEMENT Problem ( Clause+ ) >
	  <!ELEMENT Clause ( Literal+ ) >
	  */
	  public String toXML()
	  {
	    String res="<Problem>\n";
	    for(int i=0;i<this.numberOfClauses();i++)
	    {
	      res+="\t<Clause>\n";
	      Object[] obary=(this.getClause(i)).toArray();
	      for(int j=0;j<obary.length;j++)
	      {
	        IBooleanLiteral b=(IBooleanLiteral)(obary[j]);
	        res+="\t\t<Literal variable=\""+b.getBooleanVariable().getName()+"\" barred=\"";
	        if(b.isBarred())
	          res+="true\"/>\n";
	        else
	          res+="false\"/>\n";
	      }
	      res+="\t</Clause>\n";
	    }
	    res+="</Problem>";
	    return res;
	  }
	
	public long toXML(String filename)
	  {
	    File f=new File(filename);
	    PrintStream fos=null;
	    try
	    {
	      f.createNewFile();
	      fos=new PrintStream(new FileOutputStream(f));
	      fos.println(this.toXML());
	      fos.close();
	    }
	    catch(Exception err)
	    {
	      err.printStackTrace();
	    }
	
	    return f.length();
	  }
  
  public String toMathematicaCode() throws ClauseException
  {
	  String[] vars = BooleanVariable.mathematicaVariables();
	  if(vars==null || vars.length==0)
		  return ""; //$NON-NLS-1$
	  String s=this.toCode().replaceAll(Messages.getString("Problem.0"), "bv")/*+"`"*/; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	  //s=s.replace('_','$');
	  String ret="f[{"+vars[0].replaceAll("BV$", "bv")+"_"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	  for(int i=1;i<vars.length;i++)
		  ret+=","+vars[i].replaceAll("BV$", "bv")+"_"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	  ret+="}]:="+s; //$NON-NLS-1$
	  return ret;
  }
}

