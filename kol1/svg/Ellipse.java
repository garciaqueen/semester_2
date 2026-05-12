public class Ellipse extends Shape {
    private final Point p;
    private final double rx;
    private final double ry;

    public Ellipse(Point center, double rx, double ry, Style style) {
        super(style);

        this.p = new Point(center);
        this.rx = rx;
        this.ry = ry;
    }


    @Override
    public String toSvg() {
        StringBuilder st = new StringBuilder();

        st.append("<ellipse cx=\"")
        .append(p.getX())
        .append("\" cy=\"")
        .append(p.getY())
        .append("\" rx=\"")
        .append(this.rx)
        .append("\" ry=\"")
        .append(this.ry)
        .append("\" ")
        .append(style.toSvg())
        .append("/>");

        return st.toString();
    }
}
