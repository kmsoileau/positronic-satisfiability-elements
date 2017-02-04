package positronic.satisfiability.elements;

import java.util.List;

import positronic.math.EquivalenceRelation;

public class TwoBitAdder2Demo 
{
	public static void main(String[] args) throws Exception
	  {
	    IBooleanVariable[] bva=
	    {
	      BooleanVariable.getBooleanVariable("X"),
	      BooleanVariable.getBooleanVariable("Y"),
	      BooleanVariable.getBooleanVariable("Z"),
	      BooleanVariable.getBooleanVariable("C")
	    };
	    IProblem problem = new TwoBitAdder(bva[0],bva[1],bva[2],bva[3]);

	    System.out.println(problem);
	    
	    EquivalenceRelation er=((Problem)problem).buildEquivalenceRelation();
	    System.out.println(er.equivalenceClasses()+"\nFinis");
	    
	    problem=((Problem)problem).combineSinglyMatchingClauses();
	    problem.sort();
	    System.out.println(problem);
	    
	    problem=((Problem)problem).combineSinglyMatchingClauses();
	    problem.sort();
	    System.out.println(problem);
	    
	    //List s=PartialSolution.solveList(problem);
	    List<IBooleanLiteral> s=problem.findModel(Problem.defaultSolver());
			if(s!=null && s.size()>0)
	    {
	      BooleanLiteral.interpret(s);
	    	System.out.println("bva[0]= "+bva[0].getValue());
	    	System.out.println("bva[1]= "+bva[1].getValue());
	    	System.out.println("bva[2]= "+bva[2].getValue());
	    	System.out.println("bva[3]= "+bva[3].getValue());
	    }
	    else
	      System.out.println("No solution.");
			
			System.out.println(problem.toMathematicaCode());
	  }
}
