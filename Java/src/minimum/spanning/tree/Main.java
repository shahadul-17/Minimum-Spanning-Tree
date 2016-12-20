package minimum.spanning.tree;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	
	private static int totalEdgeWeight;
	private static final int SIZE = 26;							// for pre-allocation of memory for 26 alphabets...
	private static int[][] matrix = new int[SIZE][SIZE];		// advantage: easy to implement, can check for an edge in constant time...
																// disadvantage: not space efficient, no quick way to determine the vertices adjacent from another vertex...
	
	private static Vertex[] vertices = new Vertex[SIZE];
	private static ArrayList<Vertex> arrayListVertices = new ArrayList<Vertex>();
	
	public static void main(String[] args) {
		Main main = new Main();
		
		try {
			main.loadGraph("input.txt");
		}
		catch (Exception exc) {
			exc.printStackTrace();
			
			return;
		}
		
		System.out.print("Please enter the root vertex = ");
		
		Scanner scanner = new Scanner(System.in);
		Vertex rootVertex = vertices[toIndex(scanner.nextLine().trim().charAt(0))];
		rootVertex.key = 0;
		
		scanner.close();
		System.out.println();
		
		while (!arrayListVertices.isEmpty()) {
			Vertex element = main.extractMinimum();
			element.visited = true;
			
			for (Vertex vertex : vertices) {
				if (vertex != null && !vertex.visited && matrix[element.index][vertex.index] != 0 && matrix[element.index][vertex.index] < vertex.key) {
					vertex.parent = element;
					vertex.key = matrix[element.index][vertex.index];
				}
			}
		}
		
		for (Vertex vertex : vertices) {
			if (vertex != null) {
				totalEdgeWeight += vertex.key;
				
				System.out.println(vertex);
			}
		}
		
		System.out.println("\nThe sum of edge-weights in the Minimum Spanning Tree = " + totalEdgeWeight);
	}
	
	private void loadGraph(String fileName) throws Exception {
		int[] indices = new int[2];
		
		String text;
		String[] substrings;
		BufferedReader bufferedReader = new BufferedReader(new FileReader("input.txt"));
		
		while ((text = bufferedReader.readLine()) != null) {
			substrings = text.split("\\s+");		// "\\s+" is the regular expression for space...
			
			for (int i = 0; i < substrings.length - 1; i++) {
				indices[i] = toIndex(substrings[i].charAt(0));
				
				if (vertices[indices[i]] == null) {
					vertices[indices[i]] = new Vertex();
					vertices[indices[i]].index = indices[i];
					
					arrayListVertices.add(vertices[indices[i]]);
				}
			}
			
			matrix[indices[0]][indices[1]] = matrix[indices[1]][indices[0]] = Integer.parseInt(substrings[2]);		// for undirected graph...
																													// matrix[indices[0]][indices[1]] = Integer.parseInt(substrings[2]);		// for directed graph...
		}
		
		bufferedReader.close();
	}
	
	private Vertex extractMinimum() {
		Vertex minimumVertex = arrayListVertices.get(0);
		
		for (int i = 1; i < arrayListVertices.size(); i++) {
			if (minimumVertex.key > arrayListVertices.get(i).key) {
				minimumVertex = arrayListVertices.get(i);
			}
		}
		
		arrayListVertices.remove(minimumVertex);
		
		return minimumVertex;
	}
	
	private static int toIndex(char character) {
		return Character.toLowerCase(character) - 97;
	}
	
	public static char toCharacter(int index) {
		return (char) (index + 97);
	}
	
}