import java.util.*;
import java.io.*;
class DijkstrasAlgorithm {

	/* Dijkstra's algorithm finds the shorest path on a weighted graph between
	* any two vertices. In order to do this, we will utilize a PriorityQueue, which
	* functions the same as a regular queue (push and pop elements), but
	* is different in that it sorts the elements in the queue as they are pushed
	* into it. The PriorityQueue will be sorting vertices based on the minimum
	* distance up to each vertex. Dijkstra's algorithm works by first setting the
	* minimum distance of the source vertex to 0, and setting the minimum distance
	* of every other vertex to infinity (Integer.MAX_VALUE in this case). Now we
	* examine each neighbor of the source vertex and see if the current distance
	* added with the distance between the vertices is smaller than the minimum 
	* distance of the neighboring vertex. If it is, then we remove that neighboring
	* vertex from the queue, update it's minimum distance, and push it back into
	* the queue so it can be sorted once again. After visiting all neighbors who
	* have yet to be visited, we set the current vertex to the vertex with the lowest
	* minimum distance who has yet to be visited, or, the next vertex out of the
	* queue. We repeat this process until there are no vertices left in the queue,
	* or we have visited our destination vertex. If the minimum distance of the
	* destination vertex is still at infinity, then we know it has not been visited,
	* otherwise, we return the minimum distance of the destination vertex. 
	*/

	public static int Dijkstras(Vertex[] graph, int source, int destination) {
		//current keeps track of the vertex we are currently looking at
		Vertex current = graph[source];
		//set the minimum distance of the source vertex to 0
		current.minDistance = 0;

		//create the PriorityQueue which will keep track of the vertices by minimum distance
		PriorityQueue<Vertex> priorityQueue = new PriorityQueue<Vertex>();
		//push the current vertex into the queue
		priorityQueue.add(current);

		//continue while the queue is not empty
		while (!priorityQueue.isEmpty()) {
			//current is the next vertex in the queue, or the vertex with the minimum distance
			current = priorityQueue.remove();

			//if we are currently looking at the destination vertex, break out of the loop
			if (current.id == destination) {
				break;
			}

			//examine each neighbor of the current vertex by looking at each edge that connects them
			for (Edge edge : current.neighbors) {
				//end keeps track of the vertex opposite of the current vertex on the edge
				Vertex end = graph[edge.end];
				//distance keeps track of the distance between the current vertex and the end vertex
				int distance = edge.distance;
				//newDistance keeps track of the distance up to the current vertex added with the distance
				//between the current vertex and the end vertex
				int newDistance = current.minDistance + distance;

				//if the newDistance is less than the minimum distance of the end vertex, replace it
				if (newDistance < end.minDistance) {
					priorityQueue.remove(end);
					end.minDistance = newDistance;
					priorityQueue.add(end);
				}
			}
		}

		//if we haven't visited the destination vertex, return -1, otherwise, return the distance
		if (graph[destination].minDistance == Integer.MAX_VALUE) {
			return -1;
		} else {
			return graph[destination].minDistance;
		}
	}	

	public static void main(String[] ags) throws Exception {
		Scanner in = new Scanner(System.in);
		int t = in.nextInt();

		for (int i = 0; i < t; i++) {
			int vertices = in.nextInt();
			int edges = in.nextInt();

			Vertex[] graph = new Vertex[vertices+1];

			for (int j = 1; j <= vertices; j++) {
				graph[j] = new Vertex(j);
			}

			for (int k = 0; k < edges; k++) {
				int a = in.nextInt();
				int b = in.nextInt();
				int distance = in.nextInt();
				graph[a].neighbors.add(new Edge(b, distance));
			}

			int source = in.nextInt();
			int destination = in.nextInt();

			int result = Dijkstras(graph, source, destination);

			if (result == -1) {
				System.out.println("NO");
			} else {
				System.out.println(result);
			}
		}
	}	

	static class Vertex implements Comparable<Vertex> {
		int id, minDistance;
		List<Edge> neighbors;

		public Vertex(int id) {
			this.id = id;
			this.minDistance = Integer.MAX_VALUE;
			this.neighbors = new ArrayList<Edge>();
		}
		
		public int compareTo(Vertex v) {
			return (this.minDistance > v.minDistance) ? 1 : -1;
		}
	}

	static class Edge {
		int end, distance;

		public Edge(int end, int distance) {
			this.end = end;
			this.distance = distance;
		}
	}
}
