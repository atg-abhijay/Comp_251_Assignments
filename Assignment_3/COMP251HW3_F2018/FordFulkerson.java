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
				// Stack.add(-1);
				continue;
			}

			else if(e.nodes[0] == source && e.weight > 0) {
				for(Edge h: graph.getEdges()) {
					if(h.nodes[1] == e.nodes[1]) {
						graph.setEdge(h.nodes[0], e.nodes[1], -1);
					}
				}
				// graph.setEdge(source, e.nodes[1], -1);
				ArrayList<Integer> pathAhead = pathDFS(e.nodes[1], destination, graph);
				if(!pathAhead.isEmpty()) {
					Stack.add(source);
					Stack.addAll(pathAhead);
					break;
				}
				// ArrayList<Integer> pathAhead = pathDFS(e.nodes[1], destination, graph);
				// if(!pathAhead.contains(-1)) {
				// 	Stack.add(source);
				// 	Stack.addAll(pathAhead);
				// 	break;
				// }
			}
		}
		return Stack;
	}



	public static void fordfulkerson(Integer source, Integer destination, WGraph graph, String filePath){
		String answer="";
		String myMcGillID = "26000000"; //Please initialize this variable with your McGill ID
		int maxFlow = 0;

				/* YOUR CODE GOES HERE
		//
		//
		//
		//
		//
		//
		//
		*/


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
