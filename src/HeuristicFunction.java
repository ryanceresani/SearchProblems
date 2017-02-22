/**
 * Interface for heuristic functions (e.g., used for A* search, Greedy Best First Search,
 * Iterative Deepening A* Search, among others).
 * 
 * @author Vincent Cicirello
 * @version CSIS 4463
 */
public interface HeuristicFunction {

	/**
	 * Implementations must provide a heuristic estimate of the cost of the 
	 * least costly path from State s to the goal state.  Ideally, the heuristic
	 * should be admissible.
	 * 
	 * @param s The state for which we need an estimated cost to goal.
	 * @return Estimate of the cost of the least cost path from s to the goal.
	 */
	int h(State s);
}
