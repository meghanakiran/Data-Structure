/*@author-MEGHANA KIRAN*/
/*Strongly connected component  -Question 3 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class SCC {

	static String[] node;
	static int[][] vertexValue;
	static int[][] nodeValue;
	static String[][] vertex;
	static String[] oneline;
	static ArrayList<ArrayList<Integer>> adjList;
	static ArrayList<ArrayList<Integer>> adjLists;
	private static Scanner scanner;

	public static int[][] readfile(String readFile) {
		ArrayList<String> arrlist = new ArrayList<String>();
		try {
			/*
			 * System.out.println("entered"); System.out.println(readFile);
			 */
			File file = new File(readFile /* "input" */);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				arrlist.add(line);
				/* System.out.println(line); */
			}
			fileReader.close();
			node = arrlist.get(0).trim().split(" ");
			/* System.out.println("node length = " + node.length); */
			vertex = new String[node.length][node.length + 1];
			vertexValue = new int[node.length][node.length];
			for (int i = 1; i < arrlist.size(); i++) {
				oneline = arrlist.get(i).split(" ");
				/* System.out.println("oneline"+ oneline.length); */

				for (int k = 0; k < oneline.length; k++) {
					vertex[i - 1][k] = oneline[k];

				}

			}
			/*
			 * System.out.println("vertex.length = " + vertex.length);
			 * System.out.println("vertex [0] length = " + vertex[0].length);
			 */
			for (int i = 0; i < vertex.length; i++) {
				for (int j = 1; j < vertex[0].length; j++) {
					vertexValue[i][j - 1] = Integer.parseInt(vertex[i][j]);
				}
			}

			/*
			 * for (int i = 0; i < node.length; i++) {
			 * System.out.println("node loop"); System.out.println(node[i]); }
			 */

			/*
			 * System.out.println("printing vertex "); for (int i = 0; i <
			 * vertex.length; i++) { for (int j = 0; j < vertex[0].length; j++)
			 * { System.out.print(vertex[i][j] + " "); } System.out.println(); }
			 * System.out.println();
			 * System.out.println("printing vertexValue "); for (int i = 0; i <
			 * vertexValue.length; i++) { for (int j = 0; j <
			 * vertexValue.length; j++) { System.out.print(vertexValue[i][j] +
			 * " "); } System.out.println(); } System.out.println();
			 */

		}

		catch (IOException e) {
			e.printStackTrace();
		}
		return vertexValue;
	}

	public static int[][] VertexChange(int[][] vertexValue) {

		for (int i = 0; i < vertexValue.length; i++) {
			for (int j = 0; j < vertexValue.length; j++) {
				if (vertexValue[i][j] == 1) {

					vertexValue[i][j] = j + 1;

				}
			}

		}
		/*
		 * System.out.println("Vertex changed "); for (int i = 0; i <
		 * vertexValue.length; i++) { for (int j = 0; j < vertexValue.length;
		 * j++) { System.out.print(vertexValue[i][j] + " "); }
		 * System.out.println(); }
		 */
		return vertexValue;

	}

	public static ArrayList<ArrayList<Integer>> createList(int[][] nodeValue) {
		adjList = new ArrayList<ArrayList<Integer>>();
		for (int v = 0; v < nodeValue.length; v++) {
			adjList.add(new ArrayList<Integer>());
		}
		for (int i = 0; i < vertexValue.length; i++) {
			for (int j = 0; j < vertexValue[0].length; j++) {
				if (vertexValue[i][j] != 0) {
					adjList.get(i).add(vertexValue[i][j] - 1);

				}
			}

		}
		/*
		 * for(int i = 0; i < adjList.size(); i++) System.out.println(i + "->"+
		 * adjList.get(i)+" ");
		 */

		return adjList;
	}

	public static void main(String[] args) {
		scanner = new Scanner(System.in);
		System.out.print("Enter filename: ");
		String filename = scanner.nextLine();
		vertexValue = readfile(filename/* "input" */);
		nodeValue = new int[node.length][node.length];
		nodeValue = VertexChange(vertexValue);
		adjLists = new ArrayList<ArrayList<Integer>>();
		adjLists = createList(nodeValue);
		/*System.out.println(adjLists);*/
		GraphSCC g = new GraphSCC(node.length);

		for (int i = 0; i < adjLists.size(); i++) {

			ArrayList<Integer> tempArrList = new ArrayList<Integer>();

			tempArrList = adjLists.get(i);
			/* System.out.println(tempArrList); */

			for (int j = 0; j < tempArrList.size(); j++) {

				g.addEdge(i, tempArrList.get(j));
			}
		}
		g.printSCCs();

	}

}

class GraphSCC {
	/* No. of vertices */
	private int V;
	/* Adjacency List */
	private LinkedList<Integer> adj[];
	ArrayList<Integer> subgraphs = new ArrayList<Integer>();
	ArrayList<String> subgraphs2 = new ArrayList<String>();
	/* create two dimensional array for final graph */
	static int[][] graphPlot;
	ArrayList<Integer> subIteration = new ArrayList<Integer>();

	GraphSCC(int v) {
		V = v;
		adj = new LinkedList[v];
		for (int i = 0; i < v; ++i)
			adj[i] = new LinkedList();
	}

	/* recursive function */
	ArrayList<Integer> dfsIter(int v, boolean visited[], int j) {
		if (j == 1) {
			graphPlot = new int[SCC.node.length][SCC.node.length];
			for (int i = 0; i < SCC.node.length; i++) {
				for (int k = 0; k < SCC.node.length; k++) {
					graphPlot[i][k] = 0;
				}
			}

		}
		j = 0;

		visited[v] = true;
		subIteration.add(v);
		/*System.out.print(v + " ");
		System.out.println();*/
		/* subIteration.add(v); */

		int n;

		/* visit all the vertices adjacent to vertex v */
		Iterator<Integer> i = adj[v].iterator();
		while (i.hasNext()) {
			n = i.next();
			if (!visited[n]) {
				graphPlot[v][n] = 1;
				dfsIter(n, visited, 0);
			}
		}

		/*
		 * for (int m = 0; m < SCC.node.length; m++) { for (int k = 0; k <
		 * SCC.node.length; k++) { System.out.print(graphPlot[m][k] + " "); }
		 * System.out.println(); }
		 */

		return subIteration;
	}

	/* to reverse the graph */
	GraphSCC getTranspose() {
		GraphSCC g = new GraphSCC(V);
		for (int v = 0; v < V; v++) {
			// Recur for all the vertices adjacent to this vertex
			Iterator<Integer> i = adj[v].listIterator();
			while (i.hasNext())
				g.adj[i.next()].add(v);
		}
		return g;
	}

	void fillOrder(int v, boolean visited[], Stack<Integer> stack) {

		visited[v] = true;

		Iterator<Integer> i = adj[v].iterator();
		while (i.hasNext()) {
			int n = i.next();
			if (!visited[n])
				fillOrder(n, visited, stack);
		}

		stack.push(new Integer(v));
	}

	/* add edge */
	void addEdge(int v, int w) {
		adj[v].add(w);
	}

	/* function that finds and prints all strongly connected components */
	void printSCCs() {
		Stack<Integer> stack = new Stack();
		ArrayList<ArrayList<Integer>> subgraphPrint = new ArrayList<ArrayList<Integer>>();

		boolean visited[] = new boolean[V];
		for (int i = 0; i < V; i++)
			visited[i] = false;

		for (int i = 0; i < V; i++)
			if (visited[i] == false)
				fillOrder(i, visited, stack);

		GraphSCC gr = getTranspose();
		System.out.println("OUTPUT -Question 3 -SCC ");
		for (int i = 0; i < V; i++)
			visited[i] = false;

		while (stack.empty() == false) {

			int v = stack.pop();

			int i = 0;
			if (visited[v] == false) {

				subgraphs = gr.dfsIter(v, visited, 1);
				System.out.println();
				System.out.println("traversal: ");
				for (int j = 0; j < subgraphs.size(); j++) {
					int o = subgraphs.get(j);
					/*String node = SCC.node[o];*/
					System.out.print(SCC.node[o] + " ");
					
				}
				/*System.out.println(subgraphs);*/
				/* subgraphPrint.add(subgraphs); */
				
				/*subgraphs.clear();*/

				/* static */
				System.out.println();
				System.out.println("subadjacency matrix is:");
				System.out.print("  ");
				for (int h = 0; h < SCC.node.length; h++) {
					String node1 = SCC.node[h];

					System.out.print(node1 + " ");
					
				}
				System.out.println();
				for (int m = 0; m < SCC.node.length; m++) {
					String node2 = SCC.node[m];
					System.out.print(node2 + " ");
					for (int k = 0; k < SCC.node.length; k++) {
						System.out.print(graphPlot[m][k] + " ");
					}
					System.out.println();
				}
				subgraphs.clear();
			}
			i++;
		}

	}

}
