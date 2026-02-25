public class Main {
  public static Segment max_seg(Segment tab[], int n) {
        int num = 0;
        // Point x_1 = tab[0].p1;
        // Point y_2 = tab[0].p2;
        double seg_st = 0;
    for (int i = 0; i < n; i++) {
        double seg_len = tab[n].length();

        if (seg_st < seg_len) {
            num = n;
            seg_st = seg_len;
        }
    }
    return tab[num];
  }
  public static void main(String[] args) {
    // Point p1 = new Point();

    // p1.x = 12;
    // p1.y = 22;
    // Point p2 = p1.tranlate(p1.x, p1.y);
    // System.out.println(p2.x + " " + p2.y);
    // System.out.println(p1.x);
    // System.out.println(p1.y);

    // System.out.println(p1.toString());
    // System.out.println(p1.toSvg());

    // Segment seg = new Segment();
    // seg.ob1 = new Point();
    // seg.ob2 = new Point();
    // seg.ob1.x = 2;
    // seg.ob1.x = -2;
    // seg.ob2.x = 1;
    // seg.ob2.x = -1;

    // System.out.println(seg.length(seg.ob1, seg.ob2));
    Segment[] tab = new Segment[3];

    tab[0] = new Segment();
    tab[0].p1 = new Point();
    tab[0].p2 = new Point();
    tab[1] = new Segment();
    tab[1].p1 = new Point();
    tab[1].p2 = new Point();
    tab[2] = new Segment();
    tab[2].p1 = new Point();
    tab[2].p2 = new Point();

    tab[0].p1.x = 2.0;
    tab[0].p1.y = 4.0;
    tab[0].p2.x = 2.0;
    tab[0].p2.y = 4.0;
    // 0
    tab[1].p1.x = 1.0;
    tab[1].p1.y = 2.0;
    tab[1].p2.x = 3.0;
    tab[1].p2.y = 4.0;
    // sqrt(1+4) - 5
    tab[2].p1.x = 1.0;
    tab[2].p1.y = 5.0;
    tab[2].p2.x = 3.3;
    tab[2].p2.y = 7.0;
    // sqrt(26) - sqrt(9+49)
    Segment segm = max_seg(tab, 2);
    System.out.println(segm.p1.x + " " + segm.p1.y + " " + segm.p2.x + " " + segm.p2.y);
  }
}

