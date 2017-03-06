import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.Stack;

/**
 * 
 * @author Ryan Ceresani & Tyler Sefcik
 * @version 3/6/2017
 */
public class SearchDriver {

	static final int NUM_OF_PROBLEMS = 10;
	/**
	 * Prints (to System.out) the path from the start to the goal implied by the backpointers.
	 * @param goal The end of the path that must be printed.
	 */
	public static void printSolutionPath(SearchNode goal) {

		/*
		 * 
		 * Implement this method to print the path from the start to 
		 * the goal (in that order).
		 * 
		 * HINTS: The State class has a toString method which you can use to
		 * get a String representing the State object.
		 * Each SearchNode should contain the relevant State objects.
		 * 
		 * You'll need to follow the backpointers from goal to the start
		 * to get the relevant State objects (but you'll get them 
		 * in the opposite order from what you want).
		 * You'll then want to print the State objects from start to goal.
		 * 
		 * SearchNode has a getBackpointer() method which returns the 
		 * previous SearchNode and a getState() method which returns the 
		 * State that is contained in that SearchNode.
		 * 
		 * You can identify the start state in that it will be contained
		 * in a SearchNode with a null backpointer.
		 * 
		 */

		System.out.println("\nSOLUTION PATH: ");
		Stack<SearchNode> path = new Stack<SearchNode>();
		SearchNode curr = new SearchNode(goal.getState(), goal.getBackpointer());
		while(curr.getBackpointer() != null){
			path.push(curr);
			curr = curr.getBackpointer();
		}
		path.push(curr);
		int counter = 1;
		while(!path.isEmpty()){
			System.out.println("Step " + counter + " -> " + path.pop().getState().toString());
			counter++;
		}
	}




	public static void main(String[] args) {

		// Example of how to construct a graph search problem.
		// First parameter is number of nodes.
		// Second parameter is the edge density (must be between 0.0 and 1.0).  Note: it ignores
		// the edge density if especially low (will always have at least the number of edges to
		// have a connected graph).
		// Third parameter should be set to true if you want all transition costs equal to 1, and false
		// otherwise.
		SimpleGraphSearchProblem[] problemSet = new SimpleGraphSearchProblem[NUM_OF_PROBLEMS];
		for (int i = 0; i < problemSet.length; i++) {
			problemSet[i] = new SimpleGraphSearchProblem(100, 0.1, false);
		}

		// If you want to time your code, you need this object.
		ThreadMXBean bean = ManagementFactory.getThreadMXBean();

		// Call this method before running any search algorithm to reset the
		// code that is tracking the number of State expansions.
		State.resetStats();

		long start;
		long totalTime;
		SearchNode solution;
		int expandedStates = 0, pathLength = 0, cost = 0;
		
		System.out.println("BFS");
		for (int i = 0; i < problemSet.length; i++) {
			System.out.println();
			System.out.println("BFS Problem #" + (i+1));
			// example of how to time a method call (for cpu time)
			start = bean.getCurrentThreadCpuTime();
			solution = problemSet[i].bfs();
			totalTime = bean.getCurrentThreadCpuTime() - start;
			System.out.println("Time: " + (totalTime/1000000000.0));
			// time is in nanoseconds, so divide by 1000000000.0 to get seconds

			// example of accessing the number of expanded states
			System.out.println("Expanded: " + State.getNumExpandedStates());
			expandedStates += State.getNumExpandedStates();
			// example of getting the path, its cost, etc.
			if (solution != null) {
				System.out.println("Path length: " + solution.getPathLengthToNode());
				pathLength += solution.getPathLengthToNode();
				System.out.println("Path cost: " + solution.getG());
				cost += solution.getG();
				printSolutionPath(solution);
			} else {
				System.out.println("No solution found");
			}
		}		
		System.out.println("\nAverage Expanded States: " + expandedStates/NUM_OF_PROBLEMS);
		System.out.println("Average Path Length: " + pathLength/NUM_OF_PROBLEMS);
		System.out.println("Average Cost: " + cost/NUM_OF_PROBLEMS);
		
		expandedStates = 0; 
		pathLength = 0; 
		cost = 0;
		
		State.resetStats();
		System.out.println("\nUNIFORM COST SEARCH");
		for (int i = 0; i < problemSet.length; i++) {
			System.out.println();
			System.out.println("UCS Problem #" + (i+1));
			start = bean.getCurrentThreadCpuTime();
			solution = problemSet[i].uniformCostSearch();
			totalTime = bean.getCurrentThreadCpuTime() - start;
			System.out.println("Time: " + (totalTime/1000000000.0));
			System.out.println("Expanded: " + State.getNumExpandedStates());
			expandedStates += State.getNumExpandedStates();

			if (solution != null) {
				System.out.println("Path length: " + solution.getPathLengthToNode());
				pathLength += solution.getPathLengthToNode();
				System.out.println("Path cost: " + solution.getG());
				cost += solution.getG();
				printSolutionPath(solution);
			} else {
				System.out.println("No solution found");
			}
		}
		System.out.println("\nAverage Expanded States: " + expandedStates/NUM_OF_PROBLEMS);
		System.out.println("Average Path Length: " + pathLength/NUM_OF_PROBLEMS);
		System.out.println("Average Cost: " + cost/NUM_OF_PROBLEMS);
		
		
		State.resetStats();
		System.out.println("\nDEPTH FIRST");
		for (int i = 0; i < problemSet.length; i++) {
			System.out.println();
			System.out.println("DFS Problem #" + (i+1));
			start = bean.getCurrentThreadCpuTime();
			solution = problemSet[i].dfs();
			totalTime = bean.getCurrentThreadCpuTime() - start;
			System.out.println("Time: " + (totalTime/1000000000.0));
			System.out.println("Expanded: " + State.getNumExpandedStates());
			expandedStates += State.getNumExpandedStates();

			if (solution != null) {
				System.out.println("Path length: " + solution.getPathLengthToNode());
				pathLength += solution.getPathLengthToNode();
				System.out.println("Path cost: " + solution.getG());
				cost += solution.getG();
				printSolutionPath(solution);
			} else {
				System.out.println("No solution found");
			}
		}
		System.out.println("\nAverage Expanded States: " + expandedStates/NUM_OF_PROBLEMS);
		System.out.println("Average Path Length: " + pathLength/NUM_OF_PROBLEMS);
		System.out.println("Average Cost: " + cost/NUM_OF_PROBLEMS);
		
		expandedStates = 0; 
		pathLength = 0; 
		cost = 0;
		
		State.resetStats();
		System.out.println("\nITERATIVE DEEPENING");
		for (int i = 0; i < problemSet.length; i++) {
			System.out.println();
			System.out.println("ID Problem #" + (i+1));
			start = bean.getCurrentThreadCpuTime();
			solution = problemSet[i].iterativeDeepeningSearch();
			totalTime = bean.getCurrentThreadCpuTime() - start;
			System.out.println("Time: " + (totalTime/1000000000.0));
			System.out.println("Expanded: " + State.getNumExpandedStates());
			expandedStates += State.getNumExpandedStates();

			if (solution != null) {
				System.out.println("Path length: " + solution.getPathLengthToNode());
				pathLength += solution.getPathLengthToNode();
				System.out.println("Path cost: " + solution.getG());
				cost += solution.getG();
				printSolutionPath(solution);
			} else {
				System.out.println("No solution found");
			}
		}
		System.out.println("\nAverage Expanded States: " + expandedStates/NUM_OF_PROBLEMS);
		System.out.println("Average Path Length: " + pathLength/NUM_OF_PROBLEMS);
		System.out.println("Average Cost: " + cost/NUM_OF_PROBLEMS);
		
		expandedStates = 0; 
		pathLength = 0; 
		cost = 0;
	}

}
