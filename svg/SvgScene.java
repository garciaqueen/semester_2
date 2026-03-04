import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
    public String toSvg() {
        StringBuilder st = new StringBuilder();
        for (Polygon p: polygons) {
            if (p != null) {
                st.append(p.toSvg());
                st.append("\n");

            }
        }
        return st.toString();
    }

    public void save(String filePath) {
        double maxX = 0;
        double maxY = 0;

        for (Polygon p : polygons) {
            if (p != null) {
                BoundingBox box = p.boundingBox();
                if (box.x() + box.width() > maxX) maxX = box.x() + box.width();
                if (box.y() + box.height() > maxY) maxY = box.y() + box.height();
            }
        }



        try (FileWriter fw = new FileWriter(filePath)) {
            for (Polygon p: polygons) {
                fw.write();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
