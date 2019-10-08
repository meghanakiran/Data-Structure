/*@author-Meghana Kiran 
Question 2 -TOPOLOGICAL SORT */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.lang.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class TopologicalSort {

	static String[] node;
	static int[][] vertexValue;
	static int[][] nodeValue;
	static String[][] vertex;
	static String[] oneline;
	static ArrayList<ArrayList<Integer>> adjList;
	static ArrayList<ArrayList<Integer>> adjLists;

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

		return adjList;
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter filename: ");
		String filename = scanner.nextLine();
		vertexValue = readfile(filename/* "input" */);
		nodeValue = new int[node.length][node.length];
		nodeValue = VertexChange(vertexValue);
		adjLists = new ArrayList<ArrayList<Integer>>();
		adjLists = createList(nodeValue);
		GraphTopo dfsGraph = new GraphTopo(adjLists);
		dfsGraph.DFS();
		dfsGraph.printRes();

		/*
		 * for (int i = 0; i < adjLists.size(); i++) System.out.println(i + "->"
		 * + adjLists.get(i) + " ");
		 */

		
	}

}



class GraphTopo {
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

		public Vertex(int v) {
			vertex = v;
			parent = null;
			color = Color.WHITE;
			discoverTime = Integer.MAX_VALUE;
			finishTime = Integer.MAX_VALUE;

		}
	}

	/* Number of vertex */
	private ArrayList<ArrayList<Integer>> graph;
	private int n;
	private Vertex[] vertices;
	private int time;
	private ArrayList<Vertex> stack = new ArrayList<Vertex>();
	static LinkedList<Integer> nodesDecresing = new LinkedList<Integer>();
	private ArrayList<Vertex> visitComplete;

	public enum Color {
		WHITE, GRAY, BLACK
	}

	public GraphTopo(ArrayList<ArrayList<Integer>> graph) {
		this.n = graph.size();
		/* System.out.println("graph size" + graph.size()); */
		this.graph = graph;
		this.vertices = new Vertex[n];
		this.visitComplete = new ArrayList<Vertex>();
		this.time = 0;
	}

	public void DFS() {
		int i = 0;
		for (i = 0; i < n; i++) {
			vertices[i] = new Vertex(i);
		}
		for (i = 0; i < n; i++) {
			Vertex u = vertices[i];
			if (u.color == Color.WHITE) {
				visitDFS(u);

			}
		}
	}

	public ArrayList<Vertex> getRes() {
		return visitComplete;
	}

	public void printRes() {

		System.out.println("Question 2");
		Iterator<Integer> itr = nodesDecresing.iterator();
		System.out
				.println("Output of TOPOLOGICAL -SORT : Sorted list of vertices with finishing time in decreasing order ");
		while (itr.hasNext()) {
			String node = TopologicalSort.node[itr.next()];
			System.out.print(node + " ");
			/* System.out.print(itr.next()+ " "); */
		}

	}

	private void visitDFS(Vertex u) {
		visitComplete.add(u);
		u.color = Color.GRAY;
		time++;
		u.discoverTime = time;
		ArrayList<Integer> adjList = graph.get(u.vertex);
		/* System.out.println("adjlist size in dfsGraph " + adjList.size()); */
		/* System.out.println(adjList); */
		/* System.out.println(vertices); */
		for (int i = 0; i < adjList.size(); i++) {
			Vertex v = vertices[adjList.get(i)];

			if (v.color == Color.WHITE) {
				v.parent = u;
				visitDFS(v);
			}
		}
		u.color = Color.BLACK;
		time++;
		u.finishTime = time;
		/* System.out.println( "u.vertex "+u.vertex); */
		nodesDecresing.addFirst(u.vertex);

	}

}
