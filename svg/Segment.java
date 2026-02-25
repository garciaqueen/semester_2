

public class Segment {
    public Point p1 = new Point();
    public Point p2 = new Point();

    public double length() {
        double len = Math.sqrt(this.p1.x*this.p1.x + this.p1.y*this.p1.y) - Math.sqrt(this.p2.x*this.p2.x + this.p2.y*this.p2.y);
        if (len < 0) {
            return len*(-1);
        } else {
            return len;
        }
    }

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
}
