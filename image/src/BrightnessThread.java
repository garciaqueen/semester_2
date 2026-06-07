import static java.lang.Math.max;
import static java.lang.Math.min;

public class BrightnessThread implements Runnable {

    private int y_Start;
    private int y_End;
    private int increment;

    public BrightnessThread(int y_start, int y_end, int Increment) {
        this.y_Start = y_start;
        this.y_End = y_end;
        this.increment = Increment;
    }

    @Override
    public void run() {
        System.out.println(y_Start);
        for (int y = y_Start; y < y_End; y++) {
            for (int x = 0; x < ImageProcessor.imageForBrightness.getWidth(); x++) {
                int pixel = ImageProcessor.imageForBrightness.getRGB(x, y);

                int r = pixel & 0xFF;
                int g = pixel >> 8 & 0xFF;
                int b = pixel >> 16 & 0xFF;

                r = max(min(r + increment, 255), 0);
                g = max(min(g + increment, 255), 0);
                b = max(min(b + increment, 255), 0);
                ImageProcessor.imageForBrightness.setRGB(x, y, r | (g << 8) | (b << 16) | 0xFF000000);
            }
        }
    }
}
