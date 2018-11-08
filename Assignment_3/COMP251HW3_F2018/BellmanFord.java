import java.util.*;

public class BellmanFord{


	/**
	 * Utility class. Don't use.
	 */
	public class BellmanFordException extends Exception{
		private static final long serialVersionUID = -4302041380938489291L;
		public BellmanFordException() {super();}
		public BellmanFordException(String message) {
			super(message);
		}
	}

	/**
	 * Custom exception class for BellmanFord algorithm
	 *
	 * Use this to specify a negative cycle has been found
	 */
	public class NegativeWeightException extends BellmanFordException{
		private static final long serialVersionUID = -7144618211100573822L;
		public NegativeWeightException() {super();}
		public NegativeWeightException(String message) {
			super(message);
		}
	}

	/**
	 * Custom exception class for BellmanFord algorithm
	 *
	 * Use this to specify that a path does not exist
	 */
	public class PathDoesNotExistException extends BellmanFordException{
		private static final long serialVersionUID = 547323414762935276L;
		public PathDoesNotExistException() { super();}
		public PathDoesNotExistException(String message) {
			super(message);
		}
	}

    private int[] distances = null;
    private int[] predecessors = null;
    private int source;

    BellmanFord(WGraph g, int source) throws BellmanFordException{
        /* Constructor, input a graph and a source
         * Computes the Bellman Ford algorithm to populate the
         * attributes
         *  distances - at position "n" the distance of node "n" to the source is kept
         *  predecessors - at position "n" the predecessor of node "n" on the path
         *                 to the source is kept
         *  source - the source node
         *
         *  If the node is not reachable from the source, the
         *  distance value must be Integer.MAX_VALUE
         *
         *  When throwing an exception, choose an appropriate one from the ones given above
         */

        /* YOUR CODE GOES HERE */

        /**
         * initialization of the distances and
         * predecessors arrays.
         *
         * all distances from source
         * are set to the final largeValue.
         * distance of source from source is zero.
         * all predecessors are set to -1.
         */
        final int largeValue = Integer.MAX_VALUE;
        int graphNumNodes = g.getNbNodes();
        this.distances = new int[graphNumNodes];
        this.predecessors = new int[graphNumNodes];
        this.source = source;

        for(int i = 0; i < graphNumNodes; i++) {
            this.distances[i] = largeValue;
            this.predecessors[i] = -1;
        }
        this.distances[this.source] = 0;

        /**
         * relax the edges for (graphNumNodes-1)
         * number of iterations
         */
        for(int i = 1; i < graphNumNodes; i++) {
            for(Edge e: g.getEdges()) {
                this.relaxEdge(e);
            }
        }

        /**
         * after relaxing the edges over
         * (graphNumNodes-1) iterations,
         * check if it is still possible
         * to relax an edge. if we can still
         * relax an edge, that means that
         * there is a negative-weight cycle
         * present in the graph. so, we throw
         * an exception for it.
         */
        for(Edge e: g.getEdges()) {
            int startVertex = e.nodes[0];
            int endVertex = e.nodes[1];
            if(this.distances[endVertex] > this.distances[startVertex] + e.weight) {
                throw new NegativeWeightException("A negative-weight cycle has been detected!");
            }
        }


    }

    /**
     * private method to relax an edge e
     */
    private void relaxEdge(Edge e) {
        int startVertex = e.nodes[0];
        int endVertex = e.nodes[1];
        if(this.distances[endVertex] > this.distances[startVertex] + e.weight) {
            this.distances[endVertex] = this.distances[startVertex] + e.weight;
            this.predecessors[endVertex] = startVertex;
        }
    }

    public int[] shortestPath(int destination) throws BellmanFordException{
        /*Returns the list of nodes along the shortest path from
         * the object source to the input destination
         * If not path exists an Exception is thrown
         * Choose appropriate Exception from the ones given
         */

        /* YOUR CODE GOES HERE (update the return statement as well!) */

        Stack<Integer> path = new Stack<Integer>();
        int nodeToVisit = destination;
        path.push(destination);
        boolean stop = false;
        while(!stop) {
            nodeToVisit = predecessors[nodeToVisit];
            if(predecessors[nodeToVisit] == -1) {
                stop = True;
            }
            path.push(nodeToVisit);
        }

        int[] pathToReturn = new int[path.size()];
        if(path.peek() == this.source) {
            for(int i = 0; i < path.size(); i++) {
                pathToReturn[i] = path.pop();
            }
        }

        else {
            throw new PathDoesNotExistException("There is no path between the source and destination!");
        }

        return pathToReturn;
    }

    public void printPath(int destination){
        /*Print the path in the format s->n1->n2->destination
         *if the path exists, else catch the Error and
         *prints it
         */
        try {
            int[] path = this.shortestPath(destination);
            for (int i = 0; i < path.length; i++){
                int next = path[i];
                if (next == destination){
                    System.out.println(destination);
                }
                else {
                    System.out.print(next + "-->");
                }
            }
        }
        catch (BellmanFordException e){
            System.out.println(e);
        }
    }

    public static void main(String[] args){

        String file = args[0];
        WGraph g = new WGraph(file);
        try{
            BellmanFord bf = new BellmanFord(g, g.getSource());
            bf.printPath(g.getDestination());
        }
        catch (BellmanFordException e){
            System.out.println(e);
        }

   }
}
