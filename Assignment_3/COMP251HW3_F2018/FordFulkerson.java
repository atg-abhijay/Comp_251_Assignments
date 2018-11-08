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
			/**
			 * if an edge has been used to reach
			 * a node previously, its weight was
			 * turned to -1 and so we are not
			 * allowed to traverse it again.
			 */
			if(e.nodes[0] == source && e.weight < 0) {
				continue;
			}

			/**
			 * edges with positive weights have not been
			 * tried out yet and are valid to traverse over.
			 * since we cannot have a boolean[] visited
			 * implementation for the vertices here (since the parameters
			 * of pathDFS() are the source, destination and the graph, we
			 * cannot pass a boolean array that would help us
			 * keep track of the nodes that have been visited), instead
			 * we change the weights of all incoming edges
			 * into a node x to -1 so that we may not traverse
			 * over them again. also, since we have a residual
			 * graph with backward edges, we ONLY change the
			 * weight of the edge u--->x (used to arrive at
			 * x just now) to -1 so that we may not traverse it again.
			 */
			else if(e.nodes[0] == source && e.weight > 0) {
				for(Edge h: graph.getEdges()) {
					if(h.nodes[1] == e.nodes[1]) {
						graph.setEdge(h.nodes[0], e.nodes[1], -1);
					}
				}
				graph.setEdge(e.nodes[1], e.nodes[0], -1);

				/**
				 * if the returned path is empty, then that
				 * means that using the path that lies ahead,
				 * we cannot reach the destination node. so,
				 * we disregard the obtained path that lies
				 * ahead and search for another path.
				 */
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
		try{
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

			/**
			 * we pass a copy of the residual graph for finding
			 * the DFS path from the source to the destination
			 * since pathDFS() modifies the weights of the edges
			 * and we don't want it to affect the residual graph
			 * we are using here. so, we call pathDFS() with a
			 * copy of the residual graph.
			 */
			ArrayList<Integer> stPath = pathDFS(source, destination, new WGraph(residualGraph));
			while(!stPath.isEmpty()) {
				/**
				 * find the bottleneck
				 * along the s-t path
				 */
				Integer bottleneck = Integer.MAX_VALUE;
				for(int i = 0; i < stPath.size()-1; i++) {
					int startNode = stPath.get(i);
					int endNode = stPath.get(i+1);
					int pathEdgeCap = residualGraph.getEdge(startNode, endNode).weight;
					if(pathEdgeCap < bottleneck) {
						bottleneck = pathEdgeCap;
					}
				}

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
					residualGraph.setEdge(startNode, endNode,
						residualGraph.getEdge(startNode, endNode).weight - bottleneck);
					residualGraph.setEdge(endNode, startNode,
						residualGraph.getEdge(endNode, startNode).weight + bottleneck);
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
				/**
				 * the backward edges store the flow
				 * through the network. so we assign
				 * their values to the original edges
				 * from the graph.
				 */
				int residualEdgeWeight = residualGraph.getEdge(endNode, startNode).weight;
				graph.setEdge(startNode, endNode, residualEdgeWeight);
			}
		}

		catch(Exception e) {
			maxFlow = -1;
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
