public class Polygon {

    private Point[] points;

    public Polygon(Point[] points) {
        this.points = new Point[points.length];

        for (int i = 0; i < points.length; i++) {
            this.points[i] = new Point(points[i]);
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public Polygon(Polygon other) {
        this.points = new Point[other.points.length];
        for (int i = 0; i < other.points.length; i++) {
            this.points[i] = new Point(other.points[i]);
        }
    }

    @Override
    public String toString() {
        StringBuilder st = new StringBuilder("Points array: ");
        for (Point p : points) {
            st.append(p.toString()).append("\n");
        }
        return st.toString();
    }

    public String toSvg() {
        StringBuilder st = new StringBuilder();

        st.append("""
                <svg width="300" height="300">
                <polygon points="
                """);

        for (Point p : points) {
            st.append(p.getX()).append(",").append(p.getY()).append(" ");
        }

        st.append("""
                " style="fill:yellow;stroke:red;stroke-width:2"/>
                </svg>
                """);

        return st.toString();
    }
}