package A2;
import java.util.*;

public class Kruskal{

    public static WGraph kruskal(WGraph g){

        /* Fill this method (The statement return null is here only to compile) */
        WGraph mst = new WGraph();
        /**
         * start with each vertex
         * in its own component
         */
        DisjointSets p = new DisjointSets(g.getNbNodes());
        ArrayList<Edge> sortedEdges = g.listOfEdgesSorted();
        for(Edge e: sortedEdges) {
            Boolean safeToAdd = IsSafe(p, e);
            if(safeToAdd) {
                mst.addEdge(e);
            }
        }
        return mst;
    }

    public static Boolean IsSafe(DisjointSets p, Edge e){
        int u = e.nodes[0];
        int v = e.nodes[1];

        if(p.find(u) != p.find(v)) {
            p.union(u, v);
            return true;
        }
        else {
            return false;
        }
        /* Fill this method (The statement return 0 is here only to compile) */
    }

    public static void main(String[] args){

        String file = args[0];
        WGraph g = new WGraph(file);
        WGraph t = kruskal(g);
        System.out.println(t);

   }
}
