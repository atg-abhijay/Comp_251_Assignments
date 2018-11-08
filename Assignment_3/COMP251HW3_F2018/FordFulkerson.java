import java.io.*;
import java.util.*;




public class FordFulkerson {


	public static ArrayList<Integer> pathDFS(Integer source, Integer destination, WGraph graph){
		ArrayList<Integer> Stack = new ArrayList<Integer>();
		/* YOUR CODE GOES HERE */

		if(source == destination) {
			Stack.add(source);
			return Stack;
		}

		for(Edge e: graph.getEdges()) {
			if(e.nodes[0] == source && e.weight < 0) {
				continue;
			}

			else if(e.nodes[0] == source && e.weight > 0) {
				for(Edge h: graph.getEdges()) {
					if(h.nodes[1] == e.nodes[1]) {
						graph.setEdge(h.nodes[0], e.nodes[1], -1);
					}
				}

				ArrayList<Integer> pathAhead = pathDFS(e.nodes[1], destination, graph);
				if(!pathAhead.isEmpty()) {
					Stack.add(source);
					Stack.addAll(pathAhead);
					break;
				}
			}
		}
		return Stack;
	}



	public static void fordfulkerson(Integer source, Integer destination, WGraph graph, String filePath){
		String answer="";
		String myMcGillID = "260708548"; //Please initialize this variable with your McGill ID
		int maxFlow = 0;

				/* YOUR CODE GOES HERE */
		/**
		 * the residual graph will be the same
		 * as the original graph at the beginning
		 * and it will have backward edges with
		 * weights zero for all its original corresponding edges
		 */
		WGraph residualGraph = new WGraph(graph);

		for(Edge e: graph.getEdges()) {
			Edge backwardEdge = new Edge(e.nodes[1], e.nodes[0], 0);
			residualGraph.addEdge(backwardEdge);
		}

		ArrayList<Integer> stPath = pathDFS(source, destination, new WGraph(residualGraph));
		int count = 1;
		while(!stPath.isEmpty()) {
			System.out.println("Count: " + count);
			count += 1;
			stPath.forEach(node->System.out.println(node + " "));
			System.out.println();
			/**
			 * find the bottleneck
			 * along the s-t path
			 */
			Integer bottleneck = Integer.MAX_VALUE;
			for(int i = 0; i < stPath.size()-1; i++) {
				int startNode = stPath.get(i);
				int endNode = stPath.get(i+1);
				Edge capacityEdge = graph.getEdge(startNode, endNode);
				Edge flowEdge = residualGraph.getEdge(endNode, startNode);
				int forwardEdgeFlow = capacityEdge.weight - flowEdge.weight;
				if(forwardEdgeFlow < bottleneck) {
					bottleneck = forwardEdgeFlow;
				}
			}

			System.out.println("Bottleneck: " + bottleneck);

			/**
			 * update the maxFlow value
			 * by the bottleneck
			 */
			maxFlow += bottleneck;

			/**
			 * augment the s-t path and update
			 * the residual graph based on the new flow
			 */
			for(int i = 0; i < stPath.size()-1; i++) {
				int startNode = stPath.get(i);
				int endNode = stPath.get(i+1);
				Edge pathEdge = graph.getEdge(startNode, endNode);
				/**
				 * forward edge
				 */
				if(pathEdge != null) {
					residualGraph.setEdge(startNode, endNode,
						residualGraph.getEdge(startNode, endNode).weight - bottleneck);
				}
				/**
				 * backward edge
				 */
				else {
					residualGraph.setEdge(endNode, startNode,
						residualGraph.getEdge(endNode, startNode).weight + bottleneck);
				}
			}

			stPath = pathDFS(source, destination, new WGraph(residualGraph));
		}

		/**
		 * update the original graph
		 * with the final flow values
		 */
		for(Edge originalEdge: graph.getEdges()) {
			int startNode = originalEdge.nodes[0];
			int endNode = originalEdge.nodes[1];
			int residualEdgeWeight = residualGraph.getEdge(startNode, endNode).weight;
			graph.setEdge(startNode, endNode, residualEdgeWeight);
		}

		answer += maxFlow + "\n" + graph.toString();
		writeAnswer(filePath+myMcGillID+".txt",answer);
		System.out.println(answer);
	}


	public static void writeAnswer(String path, String line){
		BufferedReader br = null;
		File file = new File(path);
		// if file doesnt exists, then create it

		try {
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(line+"\n");
		bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	 public static void main(String[] args){
		 String file = args[0];
		 File f = new File(file);
		 WGraph g = new WGraph(file);
		 fordfulkerson(g.getSource(),g.getDestination(),g,f.getAbsolutePath().replace(".txt",""));
	 }
}
