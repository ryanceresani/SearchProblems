import java.util.Collection;

/**
 * Abstract base class for search problem states.
 * 
 * @author Vincent Cicirello
 * @version CSIS4463
 */
public abstract class State {
	
	
	private static long numExpandedStates = 0;
	/**
	 * Resets the tracking of the number of expanded states.
	 */
	final public static void resetStats() {
		numExpandedStates = 0;
	}
	/**
	 * Gets the number of expanded states.
	 * @return Number of expanded states.
	 */
	final public static long getNumExpandedStates() {
		return numExpandedStates;
	}

		
	/**
	 * Implementations of this method must construct and
	 * return all successors of the given state.
	 * 
	 * @return A collection of the successors of the given state. 
	 */
	public Collection<State> getSuccessors() {
		numExpandedStates++;
		return null;
	}
	
	/**
	 * Implementations of this method must return the cost
	 * of transitioning to a given state.
	 * 
	 * @param next the other state
	 * @return cost(this, next)
	 */
	abstract public int getTransitionCost(State next);
	
	/**
	 * Checks if the state is the goal state.
	 * 
	 * @return true if the state is a goal state
	 */
	abstract public boolean isGoalState();

	
	/**
	 * Checks if the state is the start state.
	 * Override if it is easy to check if a given state is
	 * a start state.
	 * 
	 * @return true of the state is a start state
	 */
	public boolean isStartState() {
		throw new UnsupportedOperationException("Start state check unsupported by this class.");
	}
	
	/**
	 * Subclasses of State must override equals of the object class.
	 * Many of the search algorithms we'll look at are easiest implemented
	 * using data structures that rely on its existence.
	 */
	abstract public boolean equals(Object o);
	
	/**
	 * Subclasses of State must override the toString method of the Object class.
	 */
	abstract public String toString();
	
	/**
	 * Simple implementation of hashCode which simply relies on the default hashCode method of
	 * the String class applied to the String representation of the state.
	 */
	public int hashCode() {
		return toString().hashCode();
	}
	
}
