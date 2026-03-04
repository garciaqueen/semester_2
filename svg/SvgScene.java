public class SvgScene {

    private Polygon[] polygons;

    private int index;
    public SvgScene() {
        polygons = new Polygon[3];
        index = 0;
    }
    public void addPolygon(Polygon polygon) {
        this.polygons[index] = polygon;
        index++;
        if (index == polygons.length) {
            index = 0;
        }
    }
}
