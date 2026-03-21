public class Polygon {

    private final Point[] points;
    private Style style;

    public Polygon(Point[] points, Style style) {
        this.points = new Point[points.length];
        this.style = style;
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
    // record BoundingBox(double x_l, double y_l, double width, double height) {}
    
    // public BoundingBox boundingBox() {
    //     double minX = points[0].getX();
    //     double minY = points[0].getY();
    //     double maxX = points[0].getX();
    //     double maxY = points[0].getY();
    // }
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
                <polygon points="
                """);

        for (Point p : points) {
            st.append(p.getX()).append(",").append(p.getY()).append(" ");
        }
        st.append("\"");
        st.append(this.style.toSvg());
        st.append("></polygon>");

        return st.toString();
    }

    public static Polygon square(Segment seg, Style style) {
        Segment seg2 = seg.perpendicular();
        Point[] points = {seg.p1, seg2.p1, seg.p2, seg2.p2};
        
        Polygon pol = new Polygon(points, style);
        return pol;
    }
}