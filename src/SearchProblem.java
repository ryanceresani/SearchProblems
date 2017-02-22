import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Queue;
import java.util.Stack;



/**
 * A Search Problem representation.
 * 
 * Multiple of our assignments will involve implementing search algorithms in 
 * this Java class.
 * 
 * @author Ryan Ceresani & Tyler Sefcik
 * @version 2/22/2017
 */
public class SearchProblem {

	final private State start;
	final private State goal;

	/**
	 * Use this constructor for search problems where
	 * there is a single start state and for which
	 * either there are multiple potential goal states
	 * or for which it is more convenient to not explicitly
	 * specify the goal state.
	 * 
	 * @param start The start state.
	 */
	public SearchProblem(State start) {
		this.start = start;
		goal = null;
	}

	/**
	 * Constructs a search problem with specified start and
	 * goal states.
	 * 
	 * @param start  The start state
	 * @param goal  The goal state
	 */
	public SearchProblem(State start, State goal) {
		this.start = start;
		this.goal = goal;
	}


	/*
	 * Helper method for checking if a State is the goal.
	 */
	private boolean goalCheck(State s) {
		return goal != null && s.equals(goal) || goal == null && s.isGoalState();
	}


	/**
	 * Breadth First Search
	 * 
	 * @return A SearchNode containing the Goal state and such that following the
	 * backpointers will enable recovering the path from the Start to the Goal.
	 * Obviously, the backpointers will give us that path backwards.
	 * Returns null if the Goal was not found.
	 */
	public SearchNode bfs() {

		/*
		 * Implement breadth first search (BFS).
		 * 
		 * Pseudocode:
		 * 
		 * 1. Check if the start state is the goal.  If the start is the goal, then return a SearchNode containing the start.
		 * 2. Initialize an empty Queue of SearchNodes for our frontier.
		 * 3. Initialize an empty set of States (this is our visited set).
		 * 4. Add the start State to the visited set.
		 * 5. Add a SearchNode containing the start to the frontier.
		 * 6. Repeat as long as the frontier is not empty.
		 *   6.a. Poll the frontier and call that SearchNode s.
		 *   6.b. For each successor e of s
		 *      6.b.i. If e is the goal, return a SearchNode containing e with backpointer to s.
		 *      6.b.ii. Otherwise, if e is not in the visited set, add e to the visited set and add a SearchNode
		 *                containing e with backpointer to s to the frontier.
		 * 7. Return null (if we get to this point, there is no solution).
		 * 
		 * Hints:
		 * 
		 * -- There are several classes in the Java API that implement the Queue interface.  I'd recommend the LinkedList class or
		 * the ArrayDeque class for this.
		 * 
		 * -- I recommend Java's HashSet class for the visited set.  It implements the discrete math concept of a set using a
		 * hash table.
		 */
		if (goalCheck(start)) {
			return new SearchNode(start);
		}
		Queue<SearchNode> frontier = new ArrayDeque<SearchNode>();
		HashSet<State> visited = new HashSet<State>();

		visited.add(start);
		frontier.add(new SearchNode(start));

		while (!frontier.isEmpty()) {
			SearchNode s = frontier.poll();
			Collection<State> succs = s.getState().getSuccessors();
			for (State e : succs) {
				if (goalCheck(e)) {
					return new SearchNode(e, s);
				} else {
					visited.add(e);
					frontier.add(new SearchNode(e, s));
				}
			}
		}
		return null;
	}


	/**
	 * Uniform Cost Search
	 * 
	 * @return A SearchNode containing the Goal state and such that following the
	 * backpointers will enable recovering the path from the Start to the Goal.
	 * Obviously, the backpointers will give us that path backwards.
	 * Returns null if the Goal was not found.
	 */
	public SearchNode uniformCostSearch() {

		/*
		 * Implement Uniform Cost Search.
		 * 
		 * Pseudocode:
		 * 
		 * 1. Check if the start state is the goal.  If the start is the goal, then return a SearchNode containing the start.
		 * 2. Initialize an empty PriorityQueue of SearchNodes for our frontier.
		 * 3. Initialize an empty set of (State,Value) pairs (this is our visited set which will also be used to keep track
		 *     of prior g values).
		 * 4. Add the start State to the visited set with a g value of 0.
		 * 5. Add a SearchNode containing the start to the frontier.
		 * 6. Repeat as long as the frontier is not empty.
		 *   6.a. Poll the frontier and call that SearchNode s.
		 *   6.b. If s contains the goal State, return s.
		 *   6.c. For each successor e of s
		 *      6.c.i. If the visited set doesn't contain e, then add a SearchNode containing e with backpointer to s to the frontier,
		 *              and add e to the visited set mapped to its g value.
		 *      6.c.ii. Otherwise, if the visited set does contain e, check if we found a less costly path (compare the new g value to the
		 *               g value e is mapped to in visited).  If so, update its g value in visited and add/promote in the frontier.
		 * 7. Return null (if we get to this point, there is no solution).
		 * 
		 * Hints:
		 * 
		 * -- Use the provided PQ class for the Priority Queue.
		 * 
		 * -- You will need to implement a Comparator that compares the g value of SearchNodes.
		 * 
		 * -- I recommend Java's HashMap class for the visited set (map State objects to Integer objects).
		 * 
		 * -- My implementation of A* Search may be useful as examples of how to do some of what you need for uniform cost search.
		 * 
		 * IMPORTANT: Don't just call my A* Search implementation with a heuristic function that always returns 0.  Technically,
		 * that would in fact be equivalent to Uniform Cost Search, but I want to see that you can implement Uniform Cost Search.
		 */
		if (goalCheck(start)) 
			return new SearchNode(start);

		// Comparator which compares the f values.  Needed for the priority queue.
		class UCSComparator implements Comparator<SearchNode> {
			@Override
			public int compare(SearchNode o1, SearchNode o2) {
				return o1.getG()-o2.getG();
			}
		}

		PQ<SearchNode> frontier = new PQ<SearchNode>(new UCSComparator());
		frontier.offer(new SearchNode(start));
		HashMap<State,Integer> visited = new HashMap<State,Integer>();
		visited.put(start, 0);

		while(!frontier.isEmpty()){
			SearchNode curr = frontier.poll();
			if(goalCheck(curr.getState())){
				return curr;
			}
			Collection<State> succs = curr.getState().getSuccessors();
			for(State e : succs){
				if(!visited.containsKey(e)){
					SearchNode eNode = new SearchNode(e, curr);
					visited.put(e, eNode.getG());
					frontier.offer(eNode);
				} else {
					SearchNode eNode = new SearchNode(e, curr);
					if(eNode.getG() < visited.get(e)){
						visited.put(e, eNode.getG());
						frontier.offer(eNode);
					}
				}
			}
		}
		return null;
	}


	/**
	 * Depth First Search (path checking DFS)
	 * 
	 * @return A SearchNode containing the Goal state and such that following the
	 * backpointers will enable recovering the path from the Start to the Goal.
	 * Obviously, the backpointers will give us that path backwards.
	 * Returns null if the Goal was not found.
	 */
	public SearchNode dfs() {

		/*
		 * Implement depth first search (DFS).
		 * 
		 * Pseudocode:
		 * 
		 * 1. Check if the start state is the goal.  If the start is the goal, then return a SearchNode containing the start.
		 * 2. Initialize an empty Stack of SearchNodes for our frontier.
		 * 3. Add a SearchNode containing the start to the frontier.
		 * 4. Repeat as long as the frontier is not empty.
		 *   4.a. Pop the frontier and call that SearchNode s.
		 *   4.b. For each successor e of s
		 *      4.b.i. If e is the goal, return a SearchNode containing e with backpointer to s.
		 *      4.b.ii. Otherwise, check if e is already on our current path, and if it is not on our current parh, push a SearchNode
		 *                containing e with backpointer to s to the frontier.
		 * 7. Return null (if we get to this point, there is no solution).
		 * 
		 * Hints:
		 * 
		 * -- I recommend Java's Stack class for the Stack.
		 *
		 * -- I gave you an unimplemented helper method for checking the path (see isOnPath below).  Implement that first as you will need it for 4.b.ii above. 
		 */

		return null;
	}


	/*
	 * Helper method for path-checking DFS.  Checks if a State is already in the current path.
	 */
	private boolean isOnPath(SearchNode pathEnd, State s) {

		// check if s is in node pathEnd, and if so return true.
		// otherwise, follow the backpointers until you either find a node that contains s (in which case return true)
		// or until you find a null backpointer (in which case return false)

		return false;
	}


	// HINT: for when you implement depthLimitedDFS and iterativeDeepening (will help give iterativeDeepening info on whether or not you should 
	// increase limit further
	private boolean didLimit; 


	/**
	 * Depth Limited DFS
	 * 
	 * @param limit Limit on how deep into the search space the search goes
	 *  
	 * @return A SearchNode containing the Goal state and such that following the
	 * backpointers will enable recovering the path from the Start to the Goal.
	 * Obviously, the backpointers will give us that path backwards.
	 * Returns null if the Goal was not found.
	 */
	public SearchNode depthLimitedDFS(int limit) {

		// Implement depth limited DFS.  This is just like DFS (path checking), except ignore any states that are more than limit steps away from the start.
		// See the comment for the didLimit field.  Technically you can ignore that and get depthLimitedDFS correct.  However, to help with
		// iterativeDeepening below, do the following: (a) initialize didLimit to false at the start of this method, and set it to true if you discover a state
		// beyond the limit (i.e., if depthLimitedSearch actually ignores something because it is more than limit steos from the start).
		return null;
	}

	/**
	 * Iterative Deepening Search
	 * 
	 * @return A SearchNode containing the Goal state and such that following the
	 * backpointers will enable recovering the path from the Start to the Goal.
	 * Obviously, the backpointers will give us that path backwards.
	 * Returns null if the Goal was not found.
	 */
	public SearchNode iterativeDeepeningSearch() {

		/* Implement Iterative Deepening search
		 * 
		 * PSEUDOCODE
		 *
		 * 1. Check if the start state is the goal.  If the start is the goal, then return a SearchNode containing the start.
		 * 2. Initialize limit to 1.
		 * 3. Perform a depthLimitedDFS, and if it solves problem, return its solution.
		 * 4. If solution not found, increase limit by 1, and go back to step 3 (repeat until either solution found or depthLimitedDFS doesn't actually limit anything).
		 * 5. If no solution found, return null.
		 */

		return null;
	}	


	/**
	 * A* Search.
	 * 
	 * @param h A heuristic function. 
	 * @return A SearchNode containing the Goal state and such that following the
	 * backpointers will enable recovering the path from the Start to the Goal.
	 * Obviously, the backpointers will give us that path backwards.
	 * Returns null if the Goal was not found.
	 */
	public SearchNode AStarSearch(HeuristicFunction h) {

		if (goalCheck(start)) 
			return new SearchNode(start);

		// A* needs f values for the priority queue.
		// This subclass of SearchNode provides that.  Inner class since only needed here.
		class AStarNode extends SearchNode {
			private int f;
			public AStarNode(State state) {
				super(state);
				f = getG()+h.h(state);
			}
			public AStarNode(State state, AStarNode back) {
				super(state,back);
				f = getG()+h.h(state);
			}
			public int getF() { return f; }
		}

		// Comparator which compares the f values.  Needed for the priority queue.
		class MyComparator implements Comparator<AStarNode> {
			@Override
			public int compare(AStarNode o1, AStarNode o2) {
				return o1.getF()-o2.getF();
			}
		}

		PQ<AStarNode> frontier = new PQ<AStarNode>(new MyComparator());
		frontier.offer(new AStarNode(start));
		HashMap<State,Integer> generated = new HashMap<State,Integer>();
		generated.put(start,h.h(start));

		while (!frontier.isEmpty()) {
			AStarNode s = frontier.poll();
			if (goalCheck(s.getState())) return s;
			Collection<State> succs = s.getState().getSuccessors();
			for (State e : succs) {
				if (!generated.containsKey(e)) {
					AStarNode eS = new AStarNode(e,s);
					generated.put(e, eS.getF());
					frontier.offer(eS);
				} else {
					AStarNode eS = new AStarNode(e,s);
					if (eS.getF() < generated.get(e)) {
						generated.put(e, eS.getF());
						frontier.offer(eS);
					}
				}
			}
		}

		return null;
	}
}
