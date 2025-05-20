public class Edge {

    private String name;
    private double weight;
    private Vertex sourceVertex;
    private Vertex targetVertex;

    /*
     * Complete the constructor
     */
    public Edge(Vertex sourceVertex, Vertex targetVertex, String name, double weight) {
        this.sourceVertex = sourceVertex;
        this.targetVertex = targetVertex;
        this.name = name;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    public Vertex getSourceVertex() {
        return sourceVertex;
    }

    public Vertex getTargetVertex() {
        return targetVertex;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        Edge other = (Edge) obj;
        return this.name.equals(other.name) && this.weight == other.weight &&
            this.sourceVertex.getName().equals(other.sourceVertex.getName()) && 
            this.targetVertex.getName().equals(other.targetVertex.getName());
    }
    
    @Override
    public String toString() {
        return String.format("%s -> %s: %s, %f",
                this.sourceVertex.getId(), this.targetVertex.getId(), this.name, this.weight);
    }
}
