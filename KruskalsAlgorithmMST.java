import java.util.*;
import java.io.*;

class KruskalsAlgorithmMST {
	
	/*
	* SPOJ Problems:
	* - CSTREET
	* - MST
	*
	* UCF Problems:
	* - CPU - 2007
	* - ANT - 2014
	*/

	/*
	* Kruskal's Algorithm works by choosing the smallest edge that connects two
	* vertices that were previously unreachable from each other and add it to that
	* minimum spanning tree we're building. Repeat this step until every vertex is
	* reachable from any other.
	*/

	public static int KruskalsAlgorithm(PriorityQueue<Edge> priorityQueue, int vertices) {
		DisjointSets sets = new DisjointSets(vertices);

		int components = vertices;
		int cost = 0;

		// repeat until every node can reach every other node or we have run out of
		// edges to search through
		while (!priorityQueue.isEmpty()) {
			if (components <= 1) {
				break;
			}

			Edge edge = priorityQueue.remove();

			// if the smallest remaining edge connects two vertices that can
			// already get to each  other, just ignore that edge and move on
			if (sets.find(edge.start) == sets.find(edge.end)) {
				continue;
			}

			// combine the components containing the endpoints of the edge
			// into a single component
			sets.union(edge.start, edge.end);
			components--;
			cost += edge.cost;
		}

		// if we weren't able to connect every vertex, return -1
		if (components > 1) {
			return -1;
		} else {
			return cost;
		}
	}

	public static void main(String[] args) throws Exception {
		Scanner in = new Scanner(System.in);
		int t = in.nextInt();

		for (int i = 1; i <= t; i++) {

			int vertices = in.nextInt();
			int edges = in.nextInt();

			PriorityQueue<Edge> priorityQueue = new PriorityQueue<Edge>();

			for (int k = 0; k < edges; k++) {
				int start = in.nextInt();
				int finish = in.nextInt();
				int cost = in.nextInt();
				priorityQueue.add(new Edge(start, finish, cost));
			}

			int cost = KruskalsAlgorithm(priorityQueue, vertices);

			if (cost == -1) {
				System.out.printf("Campus #%d: I'm a programmer, not a miracle worker!\n", i);
			} else {
			 	System.out.printf("Campus #%d: %d\n", i, cost);	
			 }
		}
	}

	static class Edge implements Comparable<Edge> {
		int start, end, cost;

		public Edge(int start, int end, int cost) {
			this.start = Math.min(start, end);
			this.end = Math.max(start, end);
			this.cost = cost;
		}

		public int compareTo(Edge e) {
			return this.cost - e.cost;
		}
	}

	/*
	* The purpose of the union-find algorithm:
	*
	* Union-find operates on disjoint sets and consists of two operations,
	* union and find. The union operation combines two disjoint sets into one.
	* The find operation gives a "canonical element" of a set. What this means
	* is that if two vertices belong to the same set, find is guaranteed to
	* return the same thing when called on each of them.
	*/

	static class DisjointSets {

		/*
		* For each node, store a link to another node which may or may not be
		* the canonical element of the set. Every node that points to itself
		* is a canonical element. All other nodes can find their canonical
		* element by following the chain of links.
		*/

		private int[] parent;

		public DisjointSets(int size) {
			parent = new int[size];
			for (int i = 0; i < size; i++) {
				parent[i] = i;
			}
		}

		/*
		* The find algorithm, in addition to return the canonical element
		* of the set containing node x, also makes all nodes along x's path
		* to the canonical element point at the canonical element. That way,
		* the next time find is called on any of those nodes, it will be fast.
		*/

		public int find(int x) {
			if (parent[x] == x) {
				return x;
			}

			parent[x] = find(parent[x]);
			return parent[x];
		}

		/*
		* Union combines two sets by making the canonical element of one point
		* at the canonical element of the other.
		*/

		public void union(int x, int y) {
			parent[find(x)] = find(y);
		}
	}
}
