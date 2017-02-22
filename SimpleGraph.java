import java.util.Random;

/**
 * A simple representation of a directed graph.
 * 
 * 
 * @author Vincent Cicirello
 * @version CSIS4463
 *
 */

public class SimpleGraph {
	
	private int[][] graph;
	
	/**
	 * Constructs a 10 node graph with edge density 0.5 and all edge costs equal to 1.
	 */
	public SimpleGraph() {
		this(10, 0.5, true);
	}
	
	/**
	 * Constructs a graph with edge density 0.5 and all edge costs equal to 1.
	 * 
	 * @param numNodes The number of nodes.
	 */
	public SimpleGraph(int numNodes) {
		this(numNodes, 0.5, true);
	}
	
	/**
	 * Constructs a graph with all edge costs equal to 1.
	 * 
	 * @param numNodes The number of nodes.
	 * @param edgeDensity the edge density, should be between 0 and 1.
	 */
	public SimpleGraph(int numNodes, double edgeDensity)  {
		this(numNodes, edgeDensity, true);
	}
	
	/**
	 * Constructs a graph.
	 * 
	 * @param numNodes The number of nodes.
	 * @param edgeDensity the edge density, should be between 0 and 1.
	 * @param costsAllSame if true, all edge costs are 1 otherwise, selected randomly from [1,20]
	 */
	public SimpleGraph(int numNodes, double edgeDensity, boolean costsAllSame)  {
		if (numNodes < 1) throw new IllegalArgumentException("Must have at least 1 node!");
		if (edgeDensity < 0 || edgeDensity > 1) throw new IllegalArgumentException("Edge density out of range.");
		
		Random gen = new Random();
		graph = new int[numNodes][numNodes];
		
		int[] nodes = new int[numNodes];
		for (int i = 0; i < numNodes; i++) {
			nodes[i] = i;
		}
		for (int i = numNodes-1; i > 0; i--) {
			int which = gen.nextInt(i+1);
			if (which != i) {
				nodes[i] ^= nodes[which];
				nodes[which] ^= nodes[i];
				nodes[i] ^= nodes[which];
			}
		}
		
		graph[nodes[0]][nodes[1]] = (costsAllSame) ? 1 : gen.nextInt(20) + 1;
		for (int i = 2; i < numNodes; i++) {
			int which = gen.nextInt(i);
			graph[nodes[which]][nodes[i]] = (costsAllSame) ? 1 : gen.nextInt(20) + 1;
		}
		
		int complete = (numNodes * (numNodes - 1));
		int targetEdges = (int)Math.round(edgeDensity * complete);
		
		if (targetEdges > (numNodes-1)) {
			double P = (targetEdges - numNodes + 1.0) / complete;
			for (int i = 0; i < numNodes; i++) {
				for (int j = 0; j < numNodes; j++) {
					if (i==j) continue;
					if (graph[i][j] == 0) {
						if (gen.nextDouble() < P) {
							graph[i][j] = (costsAllSame) ? 1 : gen.nextInt(20) + 1;
						}
					}
				}
			}
		}
	}

	/**
	 * Generates a string representing the graph.  For each node, lists the adjacent nodes.
	 */
	public String toString() {
		String result = "";
		for (int i = 0; i < graph.length; i++) {
			result += i + ":\t";
			for (int j = 0; j < graph.length; j++) {
				if (graph[i][j] != 0) result += "\t" + j;
			}
			result += "\n";
		}
		return result;
	}
	
	/**
	 * Checks if an edge exists.
	 * 
	 * @param i node id
	 * @param j node id
	 * @return true if an edge exists from i to j
	 */
	public boolean isEdge(int i, int j) {
		return graph[i][j] != 0;
	}

	/**
	 * Gets number of nodes.
	 */
	public int getNumNodes() {
		return graph.length;
	}

	/**
	 * Gets transition cost.
	 * @param nodeID First node
	 * @param nodeID2 Second node
	 * @return Cost of transitioning from nodeID to nodeID2
	 */
	public int cost(int nodeID, int nodeID2) {
		if (graph[nodeID][nodeID2] == 0) throw new IllegalArgumentException("No edge exists for given nodes.");
		return graph[nodeID][nodeID2];
	}
}
