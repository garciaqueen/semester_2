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
    public void setP1(Point p1) {
        this.p1 = p1;
    }

    public void setP2(Point p2) {
        this.p2 = p2;
    }

    public Point getP1() {
        return this.p1;
    }

    public Point getP2() {
        return this.p2;
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

    public Segment perpendicular() {
        Point m = new Point((this.p1.getX()+this.p2.getX())/2, (this.p1.getY()+this.p2.getY())/2);
        double dx = this.p2.getX() - this.p1.getX();
        double dy = this.p2.getY() - this.p1.getY();

        Point p1_1 = new Point(m.getX()-dy / 2.0, m.getY()+dx / 2.0);
        Point p2_1 = new Point(m.getX()+dy / 2.0, m.getY()-dx / 2.0);
        Segment seg2 = new Segment();
        seg2.setP1(p1_1);
        seg2.setP2(p2_1);
        return seg2;

    }

    
}