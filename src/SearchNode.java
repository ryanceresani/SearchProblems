

/**
 * Representation of a Search Node (i.e., a node in the search space).
 * This is a distinct concept from a problem state.
 * A Search Node contains a state, as well as a backpointer which indicates
 * its parent in the search (i.e., how we immediately got there) and
 * some additional book keeping stuff (e.g., number of steps to the node from 
 * the start and the total cost of the path from the start to the node).
 * 
 * @author Vincent Cicirello
 * @version CSIS4463
 */
public class SearchNode {

	private State state;
	private SearchNode backpointer;
	private int g;
	private int pathLengthToNode;
	
	/**
	 * Constructs a Search Node.  This constructor should mainly be used
	 * to construct the root of the search space (i.e., a search node for the start state).
	 * 
	 * @param state The state the search node contains.
	 */
	public SearchNode(State state) {
		this.state = state;
		backpointer = null;
		g = 0;
	}
	
	/**
	 * Constructs a search node for a given state and given predecessor.
	 * Sets the path length from start and the value of g(state) appropriately.
	 * 
	 * @param state The state the search node contains.
	 * @param back The predecessor in the search space (i.e., the backpointer)
	 */
	public SearchNode(State state, SearchNode back) {
		this.state = state;
		backpointer = back;
		g = back.g + back.state.getTransitionCost(state);
		pathLengthToNode = back.pathLengthToNode + 1;
	}
	
	/**
	 * Gets the State object contained by this Search Node
	 * 
	 * @return The State object
	 */
	public State getState() {
		return state;
	}
	
	/**
	 * Sets a backpointer.  Also, sets the path length from start and the value of g(state) appropriately based on that
	 * backpointer.  Note: Be careful in using this (better to set it at time of construction).  If other nodes have
	 * this one as their predecessor, their path lengths and g are not updated by this method.
	 * 
	 * @param back  The predecessor in the search space (i.e., the backpointer)
	 */
	public void setBackpointer(SearchNode back) {
		backpointer = back;
		g = back.g + back.state.getTransitionCost(state);
		pathLengthToNode = back.pathLengthToNode + 1;
	}
	
	/**
	 * Gets the backpointer.
	 * 
	 * @return the backpointer
	 */
	public SearchNode getBackpointer() {
		return backpointer;
	}
	
	/**
	 * Gets the value of G for the search node (i.e., the total cost of the path from the start node to
	 * this node that is implied by the backpointers).
	 * 
	 * @return g of the state
	 */
	public int getG() {
		return g;
	}
	
	/**
	 * Gets the length of the path to this node from the start that is implied by the backpointers.
	 * 
	 * @return the length of the path from the start to this state
	 */
	public int getPathLengthToNode() {
		return pathLengthToNode;
	}
		
}
