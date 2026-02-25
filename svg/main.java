public class Main {
  public static void main(String[] args) {
    Point p1 = new Point();

    p1.x = 12;
    p1.y = 22;

    // System.out.println(p1.x);
    // System.out.println(p1.y);

    // System.out.println(p1.toString());
    System.out.println(p1.toSvg());
  }
  
}

