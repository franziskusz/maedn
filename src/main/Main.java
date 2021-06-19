package main;

import main.graph.Graph;

public class Main {

	public static void main(String[] args) {

		GUI gui = new GUI();
	
		
		Graph board = new Graph();
		board.initGraph();
		board.testPrint();
		
	}
}
