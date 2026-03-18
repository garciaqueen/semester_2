import java.io.FileWriter;
import java.io.IOException;

public class SvgScene {

    private final Polygon[] polygons;
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

        for (Polygon p : polygons) {
            if (p != null) {
                st.append(p.toSvg());
                st.append("\n");
            }
        }

        return st.toString();
    }

    public void save(String filePath) {

        try (FileWriter fw = new FileWriter(filePath)) {

            // OPEN SVG
            fw.write("<svg width=\"300\" height=\"300\" xmlns=\"http://www.w3.org/2000/svg\">\n");

            // WRITE POLYGONS
            for (Polygon p : polygons) {
                if (p != null) {
                    fw.write(p.toSvg());
                }
            }

            // CLOSE SVG
            fw.write("</svg>");

        } catch (IOException e) {
            System.err.println("Error while saving SVG: " + e.getMessage());
        }
    }
}