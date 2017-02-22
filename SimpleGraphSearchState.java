import java.util.Collection;
import java.util.LinkedList;

/**
 * A simple graph search problem.
 * 
 * 
 * @author Vincent Cicirello
 * @version CSIS4463
 *
 */
public class SimpleGraphSearchState extends State {
	
	private SimpleGraph theGraph;
	private int start;
	private int goal;
	
	private int nodeID;

	/**
	 * Constructs a graph search state.
	 * 
	 * @param theGraph The graph.
	 * @param start The id of the node that is the start state.  Must be a valid index: 0 <= start < theGraph.getNumNodes() 
	 * @param goal  The id of the node that is the goal state.  Must be a valid index: 0 <= goal < theGraph.getNumNodes() 
	 * @param nodeID  The id of the graph node that is THIS state.  Must be a valid index: 0 <= nodeID < theGraph.getNumNodes() 
	 */
	public SimpleGraphSearchState(SimpleGraph theGraph, int start, int goal, int nodeID) {
		this.theGraph = theGraph;
		this.start = start;
		this.goal = goal;
		this.nodeID = nodeID;
	}

	/**
	 * Gets the list of neighbor states.
	 * 
	 * @return a collection of the neighboring states in the graph.
	 */
	@Override
	public Collection<State> getSuccessors() {
		super.getSuccessors();
		LinkedList<State> nextStates = new LinkedList<State>();
		int N = theGraph.getNumNodes();
		for (int i = 0; i < N; i++) {
			if (theGraph.isEdge(nodeID, i)) {
				nextStates.add(new SimpleGraphSearchState(theGraph, start, goal, i));
			}
		}
		return nextStates;
	}
	
	/**
	 * Gets the cost of transitioning between states.
	 * 
	 * @param next The state we are transitioning to.
	 * @return the cost from this to next
	 */
	@Override
	public int getTransitionCost(State next) {
		SimpleGraphSearchState other = (SimpleGraphSearchState)next;
		return theGraph.cost(nodeID, other.nodeID);
	}
	
	/**
	 * Compares states for equality.
	 */
	@Override
	public boolean equals(Object o) {
		if (o == null) return false;
		if (this.getClass()!= o.getClass()) return false;
		SimpleGraphSearchState other = (SimpleGraphSearchState)o;
		return theGraph == other.theGraph && start == other.start && goal == other.goal && nodeID == other.nodeID;
	}
	
	/**
	 * Checks if the state is the goal
	 */
	public boolean isGoalState() {
		return nodeID == goal;
	}
	
	/**
	 * Checks if the state is the start.
	 */
	public boolean isStartState() {
		return nodeID == start;
	}
	
	public String toString() {
		return "State: " + nodeID;
	}

	

	

}
