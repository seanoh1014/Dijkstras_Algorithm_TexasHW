import java.util.*;

public class DijkstrasAlgorithm {

    /*
     * Used to count the number of vertices polled from the 
     * priority queue. Please increment pollCount whenever 
     * you poll the priority queue.
     */
    public static int pollCount = 0;


    /*
     * Complete the computePath method. You may use your code from the
     * previous assignment except update pollCount when you poll the
     * priority queue.
     */
    public static void computePath(Vertex start) {
        PriorityQueue<Vertex> queue = new PriorityQueue();

        start.setDistance(0);
        queue.add(start);

        while(!queue.isEmpty()) {
            Vertex current = queue.poll();
            pollCount++;

            for (Edge edge : current.getEdgeList()) {
                Vertex target = edge.getTargetVertex();
                double distance = current.getDistance() + edge.getWeight();

                if (target.getDistance() > distance) {
                    target.setDistance(distance);
                    target.setPrevious(current);
                    queue.add(target);
                }
            }
        }

    }

    /**
     * Given a vertex, return a List of vertices that make up the shortest
     * path by getting each vertex's previous vertex. Make sure the list
     * returned is ordered from the starting vertex to the given vertex.
     */
    public static List<Vertex> getPath(Vertex current) {
       List<Vertex> list = new ArrayList<>();
        
        while (current != null) {
            list.add(current);

            current = current.getPrevious();
        }

        Collections.reverse(list);

        return list;
    }
}
