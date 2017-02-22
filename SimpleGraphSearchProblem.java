/**
 * A simple search problem.  Search in a simple graph with a
 * single start state, a single goal state.
 * 
 * @author Vincent Cicirello
 * @version CSIS4463
 */
public class SimpleGraphSearchProblem extends SearchProblem {

	/**
	 * Constructs a simple graph search problem.
	 * 
	 * @param numStates Number of states.
	 * @param edgeDensity Edge density (i.e., the probability that an edge exists between
	 * 						any pair of states).  Must be between 0 and 1
	 * @param allTransitionCostsSame if true, all transition costs will be 1, otherwise, will be selected
	 * 						randomly between 1 and 20.
	 */
	public SimpleGraphSearchProblem(int numStates, double edgeDensity, boolean allTransitionCostsSame) {
		super(new SimpleGraphSearchState(new SimpleGraph(numStates, edgeDensity, allTransitionCostsSame),0,numStates-1,0));
	}
	
	
}
