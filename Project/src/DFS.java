/*@author-MEGHANA KIRAN*/
/*Depth First Search -Question 1 */

import java.io.BufferedReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class DFS {

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
			/* System.out.println("entered"); */
			/* System.out.println(readFile); */
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
		DFSGraph dfsGraph = new DFSGraph(adjLists);
		dfsGraph.DFS();
		dfsGraph.printarrList();
		dfsGraph.plot();

	}

}

/* DFSGraph class */

class DFSGraph {
	public static class Vertex {
		public int vertex;
		/* color of vertex */
		public Color color;
		/* to find the parent */
		public Vertex parent;
		/* visit time */
		public int discoverTime;
		/* finish time */
		public int finishTime;
		/* to know the path */
		public int join;

		public Vertex(int v) {
			vertex = v;
			parent = null;
			join = 0;
			color = Color.WHITE;
			discoverTime = Integer.MAX_VALUE;
			finishTime = Integer.MAX_VALUE;

		}
	}

	public enum Color {
		WHITE, GRAY, BLACK
	}

	/* Number of vertex */
	private int n;
	private ArrayList<ArrayList<Integer>> graph;
	private Vertex[] vertices;
	private ArrayList<Vertex> res;
	private int timer;
	private ArrayList<Vertex> stack = new ArrayList<Vertex>();
	private LinkedList<Integer> adj[];
	private int V;
	static int[][] multi;
	static int[][] multi3;

	public DFSGraph(ArrayList<ArrayList<Integer>> graph) {
		this.n = graph.size();
		/* System.out.println("graph size" + graph.size()); */
		this.graph = graph;
		this.vertices = new Vertex[n];
		MatrixCreation(graph.size());
		this.res = new ArrayList<Vertex>();
		this.timer = 0;
	}

	public void DFS() {
		int i = 0;
		for (i = 0; i < n; i++) {
			vertices[i] = new Vertex(i);
			/* System.out.println("vertices" + vertices[i]); */
		}
		for (i = 0; i < n; i++) {
			Vertex u = vertices[i];
			if (u.color == Color.WHITE) {
				visitDFS(u);
				/* visit DFS Iteratively */
			}
		}
	}

	/* insert traversal into arraylist */
	public ArrayList<Vertex> getRes() {
		return res;
	}

	private void visitDFS(Vertex u) {
		res.add(u);
		u.color = Color.GRAY;
		timer++;
		u.discoverTime = timer;
		ArrayList<Integer> adjList = graph.get(u.vertex);
		/*
		 * System.out.println("adjlist size in dfsGraph " + adjList.size());
		 * System.out.println(adjList);
		 */
		/* System.out.println(vertices); */
		for (int i = 0; i < adjList.size(); i++) {
			Vertex v = vertices[adjList.get(i)];

			if (v.color == Color.WHITE) {
				v.parent = u;
				v.join = 1;
				addEdge(v.vertex, u.vertex);
				visitDFS(v);
			}
		}
		u.color = Color.BLACK;
		timer++;
		u.finishTime = timer;

	}

	private void addEdge(int v, int u) {
		adj[v].add(u);

	}

	private void MatrixCreation(int v) {
		V = v;
		adj = new LinkedList[v];
		for (int i = 0; i < v; ++i)
			adj[i] = new LinkedList();

	}

	public void printarrList() {
		System.out.println("Question 1: DFS Algorithm - OUTPUT ");
		System.out.println("Traversal path : ");
		for (int i = 0; i < n; i++) {
			Vertex v = res.get(i);
			String node = DFS.node[v.vertex];
			System.out.print(node + " ");
		}
		System.out.println();
		System.out.println("Vertex:Discover time/Finish time");
		for (int i = 0; i < n; i++) {
			Vertex v = res.get(i);
			String node = DFS.node[v.vertex];
			System.out.println(node + " : " + v.discoverTime + " / "
					+ v.finishTime);

		}

	}

	public void plot() {
		System.out.println();
		System.out.println("Final Output Matrix:");
		multi = new int[n][n];
		System.out.print("  ");
		for (int i = 0; i < n; i++) {
			String node1 = DFS.node[i];

			System.out.print(node1 + " ");
			// System.out.printf("%6s",node1);
		}
		System.out.println();
		/*
		 * for (int k = 0; k < n; k++) { for (int j = 0; j < n; j++) {
		 * 
		 * System.out.print(multi[k][j] + " "); } System.out.println(); }
		 */

		for (int i = 0; i < n; i++) {
			Vertex u = res.get(i);

			plotDFS(u);
			/* visit plotDFS(u) iteratively */
		}

		for (int k = 0; k < n; k++) {
			String node2 = DFS.node[k];
			System.out.print(node2 + " ");
			for (int j = 0; j < n; j++) {

				System.out.print(multi[k][j] + " ");
			}
			System.out.println();
		}

		changematrix(multi);

	}

	private void plotDFS(Vertex u) {
		ArrayList<Integer> adjList = graph.get(u.vertex);
		/*
		 * System.out.println("adjlist size in dfsGraph " + adjList.size());
		 * System.out.println(adjList);
		 */
		/* System.out.println(vertices); */
		for (int i = 0; i < adjList.size(); i++) {
			Vertex v = vertices[adjList.get(i)];

			if (v.join == 1 && v.parent == u) {
				/* add 1 to that place */
				multi[u.vertex][v.vertex] = 1;

				plotDFS(v);
			}
		}

	}

	private void changematrix(int[][] multi2) {
		System.out.println();
		System.out.println("Final Output Matrix with Discover time/Finish time on the diagonal :");

		System.out.print(" ");
		for (int i = 0; i < n; i++) {
			String node1 = DFS.node[i];

			System.out.print("  " + node1 + "  ");
			/* System.out.printf("%6s",node1); */
		}
		System.out.println();
		/*
		 * multi3= new int[n][n]; multi3 = changeDiagonal(multi2);
		 */

		for (int k = 0; k < n; k++) {
			int findApproNode = 0;
			Vertex u = res.get(k);/* getvertex(k); */
			/* System.out.println("before "+u.vertex); */
			while (u.vertex != k && findApproNode < n - 1) {
				findApproNode++;
				u = res.get(findApproNode);
			}
			/* System.out.println("after "+u.vertex); */
			String node2 = DFS.node[u.vertex];

			System.out.print(node2 + "  ");

			/* for(int l =0;l<n;l++){ */
			for (int j = 0; j < n; j++) {
				if (/* u.vertex == j && u.vertex == k */k == j) {
					/* v= res.get(k);Vertex */
					System.out.print(u.discoverTime + "/" + u.finishTime + " ");
					/*
					 * System.out.printf("%1s%s%2s ",u.discoverTime+"/",u.finishTime
					 * ," ");
					 */
				} else {
					System.out.print(multi2[k][j] + "    ");
					/* System.out.printf("%2s%5s", multi2[k][j]," "); */
				}
			}
			System.out.println();
		}
		/* System.out.println(); */

	}

	private Vertex getvertex(int k) {
		System.out.println(k);
		/* for(int l =0;l<n;l++){ */
		Vertex u = res.get(k);
		for (int j = 0; j < n; j++) {
			/* System.out.println(u.vertex); */
			if (u.vertex == j && u.vertex == k) {
				return u;
			}

		}

		return null;
	}

}
