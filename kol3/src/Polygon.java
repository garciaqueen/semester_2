import java.util.*;public class Polygon
{

    private List<Point> points;

    public Polygon(List<Point> points) {
        this.points = points;
    }

    public List<Point> getPoints() {
        return points;
    }

    public boolean inside(Point point) {
        int counter = 0;
        for (int i = 0; i < points.size(); i++) {

            Point pa = points.get(i);
            Point pb = points.get((i + 1) % points.size());

            double y1 = pa.y;
            double y2 = pb.y;
            double x1 = pa.x;
            double x2 = pb.x;

            // Werte tauschen über lokale Variablen
            if (y1 > y2) {
                double tempY = y1;
                y1 = y2;
                y2 = tempY;

                double tempX = x1;
                x1 = x2;
                x2 = tempX;
            }

            if (y1 < point.y && point.y < y2) {

                double d = x2 - x1;
                double x;

                if (d == 0) {
                    x = x1;
                } else {
                    double a = (y2 - y1) / d;
                    double b = y1 - a * x1;
                    x = (point.y - b) / a;
                }

                if (x < point.x) {
                    counter++;
                }
            }
        }

        return counter % 2 != 0;
    }


}
