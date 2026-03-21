import java.io.FileWriter;
import java.io.IOException;

public class SvgScene {
    private final Shape[] shapes;
    private int index;

    public SvgScene() {
        shapes = new Shape[3];
        index = 0;
    }

    public void addShape(Shape shape) {
        shapes[index] = shape;
        index++;

        if (index == shapes.length) {
            index = 0;
        }
    }

    public String toSvg() {
        StringBuilder st = new StringBuilder();

        for (Shape s : this.shapes) {
            if (s != null) {
                st.append(s.toSvg());
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
            for (Shape s : shapes) {
                if (s != null) {
                    fw.write(s.toSvg());
                }
            }

            // CLOSE SVG
            fw.write("</svg>");

        } catch (IOException e) {
            System.err.println("Error while saving SVG: " + e.getMessage());
        }
    }
}