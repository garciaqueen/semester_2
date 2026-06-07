import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException {
        ImageProcessor img = new ImageProcessor();
        img.processImage("output.jpg");
        img.brighten2(90);
        //img.saveImage("brighter1.jpg");
        img.saveHistogram("input.png");
    }


}