public class Segment {

    public Point p1;
    public Point p2;

    public Segment() {
        p1 = new Point(0,0);
        p2 = new Point(0,0);
    }

    public double length() {

        double dx = p1.getX() - p2.getX();
        double dy = p1.getY() - p2.getY();

        return Math.sqrt(dx * dx + dy * dy);
    }

    public static Segment max_seg(Segment tab[], int n) {

        int num = 0;
        double seg_st = 0;

        for (int i = 0; i < n; i++) {

            double seg_len = tab[i].length();

            if (seg_len > seg_st) {
                seg_st = seg_len;
                num = i;
            }
        }

        return tab[num];
    }
}