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

    // Segment[] tab = new Segment[3];

    // tab[0] = new Segment();
    // tab[0].p1 = new Point(0,0);
    // tab[0].p2 = new Point(0,0);

    // tab[1] = new Segment();
    // tab[1].p1 = new Point(0,0);
    // tab[1].p2 = new Point(0,0);

    // tab[2] = new Segment();
    // tab[2].p1 = new Point(0,0);
    // tab[2].p2 = new Point(0,0);

    // tab[0].p1.setX(2.0);
    // tab[0].p1.setY(4.0);
    // tab[0].p2.setX(2.0);
    // tab[0].p2.setY(4.0);

    // tab[1].p1.setX(1.0);
    // tab[1].p1.setY(2.0);
    // tab[1].p2.setX(3.0);
    // tab[1].p2.setY(4.0);

    // tab[2].p1.setX(1.0);
    // tab[2].p1.setY(5.0);
    // tab[2].p2.setX(3.3);
    // tab[2].p2.setY(7.0);

    // Segment segm = max_seg(tab, 3);

    // System.out.println(
    //     segm.p1.getX() + " " +
    //     segm.p1.getY() + " " +
    //     segm.p2.getX() + " " +
    //     segm.p2.getY()
    // );

            Point[] pts1 = new Point[3];
        pts1[0] = new Point(10, 10);
        pts1[1] = new Point(100, 10);
        pts1[2] = new Point(50, 80);

        Polygon poly1 = new Polygon(pts1);

        // create second polygon
        Point[] pts2 = new Point[4];
        pts2[0] = new Point(120, 50);
        pts2[1] = new Point(200, 50);
        pts2[2] = new Point(200, 120);
        pts2[3] = new Point(120, 120);

        Polygon poly2 = new Polygon(pts2);

        // create scene
        SvgScene scene = new SvgScene();

        scene.addPolygon(poly1);
        scene.addPolygon(poly2);

        // save SVG file
        scene.save("scene.svg");

        System.out.println("SVG file saved!");

  }
}