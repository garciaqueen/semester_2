public class Point {
    public double x;
    public double y;

    @Override
    public String toString() {
        return ("(" + x + "," + y + ")"); 
    }
    public static void init_print(double x, double y) {
        x = 3.1;
        y = 5.2;
        System.out.println(x + " " + y);
    }

}
