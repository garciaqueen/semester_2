public class Main {

  public static Segment max_seg(Segment tab[], int n) {
        int num = 0;
        double seg_st = 0;

        for (int i = 0; i < n; i++) {
            double seg_len = tab[i].length();

            if (seg_st < seg_len) {
                num = i;
                seg_st = seg_len;
            }
        }
        return tab[num];
  }


  public static void main(String[] args) {

    // Point[] pts1 = new Point[3];
    Point pts1 = new Point(200, 200);
    // Point pts2 = new Point(80, 60);
    // pts1[2] = new Point(50, 80);

    Style style = new Style("red", "black", 1);

    // create scene
    SvgScene scene = new SvgScene();
    // Segment seg1 = new Segment();
    // seg1.setP1(pts1);
    // seg1.setP2(pts2);
    // Polygon poly1 = Polygon.square(seg1, style);
    // scene.addPolygon(poly1);
    Ellipse ellipse1 = new Ellipse(pts1, 80.0, 40.0, style);
    scene.addShape(ellipse1);

    scene.save("scene.svg");

    System.out.println("SVG file saved!");

  }


}