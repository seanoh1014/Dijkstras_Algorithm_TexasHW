import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        String verticesFile = "TX-dfw50.vertices.txt";
        String edgesFile = "TX-dfw50.edges.txt";
        Map<Integer, Vertex> graph = getGraph(readFile(verticesFile), readFile(edgesFile)); // complete the getGraph method below
        
        Vertex start = graph.get(740); //Coppell (TX121@DenTapRd)
        Vertex end = graph.get(1328);  //Midlothian (US67/US287)

        // call computePath in DijkstrasAlgorithm class and pass it the starting Vertex.
        DijkstrasAlgorithm da = new DijkstrasAlgorithm();

        da.computePath(start);

        // call getPath in DijkstrasAlgorithm class
        List<Vertex> path = da.getPath(end);
        
        System.out.printf("\nPath from %s to %s = %s\n", start, end, path);
        System.out.printf("\nDistance from %s to %s = %f miles\n", start, end, end.getDistance());
        System.out.printf("Path size = %d\n", path.size());
        System.out.printf("Poll count = %,d\n", DijkstrasAlgorithm.pollCount);
    }

    /*
     * The getGraph method should return a Map where the Key is the Vertex name
     * and the value is the Vertex. You will need to create all the vertices then
     * add the edges to each Vertex.
     */
    public static Map<Integer, Vertex> getGraph(List<String> verticesList, List<String> edgesList) {
        Map<Integer, Vertex> graph = new HashMap<>();

        for (int i = 0; i < verticesList.size(); i++) {
            String[] arr = verticesList.get(i).split(" ");
            String name = arr[0];
            double latitude = Double.parseDouble(arr[1]);
            double longitude = Double.parseDouble(arr[2]);

            graph.put(i, new Vertex(i, name, latitude, longitude));
        }
        
        for (int i = 0; i < edgesList.size(); i++) {
            String[] arr = edgesList.get(i).split(" ");
            Vertex source = graph.get(Integer.parseInt(arr[0]));
            Vertex target = graph.get(Integer.parseInt(arr[1]));
            String name = arr[2];
            double lat1 = source.getLatitude();
            double lon1 = source.getLongitude();
            double lat2 = target.getLatitude();
            double lon2 = target.getLongitude();
            double weight = 0.0;

            // if an edge has additional stops
            if (arr.length > 3) {
                // calculate source --> first pair
                weight += calculateDistance(lat1, lon1, Double.parseDouble(arr[3]), Double.parseDouble(arr[4]));

                // i.e) if arr contains three additional pairs, two calculations are needed for pairs
                int numPairs = (arr.length - 3) / 2;
                
                if (numPairs > 1) {
                    for (int j = 3; j < (numPairs - 1) * 2 + 3; j+=2) {
                        double firstLat = Double.parseDouble(arr[j]);
                        double firstLon = Double.parseDouble(arr[j+1]);
                        double secondLat = Double.parseDouble(arr[j+2]);
                        double secondLon = Double.parseDouble(arr[j+3]);

                        weight += calculateDistance(firstLat, firstLon, secondLat, secondLon);
                    }
                }   

                // calculate pair --> target
                double lastLat = Double.parseDouble(arr[arr.length-2]);
                double lastLon = Double.parseDouble(arr[arr.length-1]);

                weight += calculateDistance(lastLat, lastLon, lat2, lon2);

            } else {
                weight = calculateDistance(lat1, lon1, lat2, lon2);
            }
            
            source.addEdge(new Edge(source, target, name, weight));
            target.addEdge(new Edge(target, source, name, weight));
        }

        return graph;
    }

    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {

        final double R = 3958.8; // Earth's radius in miles
        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);

        double latDiff = lat2 - lat1;
        double lonDiff = lon2 - lon1;
        double a = Math.sin(latDiff / 2) * Math.sin(latDiff / 2) +
                Math.cos(lat1) * Math.cos(lat2) * 
                Math.sin(lonDiff / 2) * Math.sin(lonDiff / 2);

        return 2 * R * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    }

    public static void resetGraph(Map<Integer, Vertex> graph) {
        DijkstrasAlgorithm.pollCount = 0;
        for (Vertex v : graph.values()) {
            v.setPrevious(null);
            v.setDistance(Integer.MAX_VALUE);
        }
    }

    public static List<String> readFile(String filename) {
        List<String> list = new ArrayList<>();
        try {
            Scanner input = new Scanner(new File(filename));
            while (input.hasNextLine()) {
                list.add(input.nextLine());
            }
        } catch (FileNotFoundException ex) {
            System.err.println("Find not found");
        }
        return list;
    }
}
