public class Point {
    public double x;
    public double y;


    @Override
    public String toString() {
        return "Point with x = " + this.x + " y = " + this.y; 
    }

    public String toSvg() {
        return """
               <svg height="100" width="100" xmlns="http://www.w3.org/2000/svg">\r
                 <circle r="45" cx=""" //
         + this.x + " cy=" + this.y + " fill=\"red\" />\r\n" + //
                        "</svg>";
    }

    public void tranlate(double dx, double dy) {
        dx++;
        dy++;
    }

    public void translated(double dx, double dy) {
        Point p2 = new Point();
        p2.x = 1.0;
        p2.y = 2.0;
        p2.x += dx;
        p2.y += dy;
    }

}
