import java.util.*;
import java.io.*;
class DepthFirstSearch {

	/* Returns true if the graph is a tree. By definition, a tree is a graph that
	* doesn't contain a cycle and is minimally connected, that is, if we remove any 
	* edge from the graph, it ceases to be connected. In addition, the number of
	* vertices in a tree is equal to the number of edges + 1. Depth-first search
	* traverses the graph by visiting the root node's neighbors, and going deepest
	* into the graph. This continues until the stack is empty, in which case all of the
	* nodes in the graph will have been visited, or there are nodes that haven't been
	* visited yet, proving that the given graph is not a tree.
	*/
	public static boolean DFS(ArrayList<ArrayList<Integer>> graph, int vertices) {
		// visited keeps track of which nodes we have visited already
		boolean[] visited = new boolean[vertices+1];
		// stack will keep track of which nodes we are visiting next
		Stack<Integer> stack = new Stack<Integer>();
		// add the root node to the stack
		stack.push(1);
		// mark the root node as visited
		visited[1] = true;

		// while the stack is not empty, continue to search the tree
		while (!stack.isEmpty()) {
			// current keeps track of the node we are currently on
			int current = stack.pop();
			// neighbors gives us a list of the neighbors of the current node
			List<Integer> neighbors = graph.get(current);

			// iterate through each neighbor of the current node and see if we
			// have visited the neighboring node; if not, add it to the stack.
			for (int i = 0; i < neighbors.size(); i++) {
				// neighbor keeps track of which neighbor of the current node
				// we are currently looking at
				int neighbor = neighbors.get(i);

				// if we haven't visited the current neighbor, add it to the stack
				if (!visited[neighbor]) {
					// mark the neighbor as visited
					visited[neighbor] = true;
					// add the neighbor to the stack
					stack.push(neighbor);
				}
			}
		}

		// iterate through the nodes and if there is any node we have yet to visit,
		// then the graph is not a tree and we return false
		for (int j = 1; j <= vertices; j++) {
			if (!visited[j]) {
				return false;
			}
		}

		// the graph is a tree and we return true
		return true;
	}

	public static void main(String[] args) throws Exception {
		Scanner in = new Scanner(System.in);
		int vertices = scan(in);
		int edges = scan(in);

		if (edges + 1 != vertices) {
			System.out.print("NO");
		} else {
			ArrayList<ArrayList<Integer>> graph = new ArrayList<ArrayList<Integer>>();

			for (int i = 0; i <= vertices; i++) {
				graph.add(new ArrayList<Integer>());
			}

			for (int j = 0; j < edges; j++) {
				int vertex1 = scan(in);
				int vertex2 = scan(in);
				graph.get(vertex1).add(vertex2);
				graph.get(vertex2).add(vertex1);
			}

			if (DFS(graph, vertices)) {
				System.out.print("YES");
			} else {
				System.out.print("NO");
			}
		}
	}

	public static int scan(Scanner in) throws Exception {
		return Integer.parseInt(in.next());
	}
}
