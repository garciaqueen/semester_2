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

    Point[] pts1 = new Point[3];
    pts1[0] = new Point(10, 10);
    pts1[1] = new Point(100, 10);
    pts1[2] = new Point(50, 80);

    Style style = new Style("blue", "black", 1);

    Polygon poly1 = new Polygon(pts1, style);



    // create second polygon
    Point[] pts2 = new Point[4];
    pts2[0] = new Point(120, 50);
    pts2[1] = new Point(200, 50);
    pts2[2] = new Point(200, 120);
    pts2[3] = new Point(120, 120);

    Polygon poly2 = new Polygon(pts2, style);

    // create scene
    SvgScene scene = new SvgScene();

    scene.addPolygon(poly1);
    scene.addPolygon(poly2);

    // save SVG file
    scene.save("scene.svg");

    System.out.println("SVG file saved!");

  }


}