public class Point {
    private double x;
    private double y;

    public Point() {
        this.x = 0;
        this.y = 0;
    }

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public void setX(double x) {
        this.x = x;
    }


    public void setY(double y) {
        this.y = y;
    }


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
        this.x = x+dx;
        this.y = y+dy;
    }

    public Point translated(double dx, double dy) {
        Point p2 = new Point();
        p2.tranlate(dx, dy);
        return p2;

    }

}

