import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class ImageProcessor {
    private BufferedImage image;
    public static BufferedImage imageForBrightness;
    public void processImage(String path) throws IOException {
        image = ImageIO.read(new File(path));
   }

   public void saveImage(String path) throws IOException {
        // what is it why
        String format = path.substring(path.lastIndexOf(".") + 1);
        ImageIO.write(image, format, new File(path));
   }

    private int clamp(int value) {
        return Math.min(Math.max(0, value), 255);
    }

   public void brightenImage(int value) {
//       RescaleOp rescaleOp = new RescaleOp(1, power, null);
//       rescaleOp.filter(image, image);
       for (int y = 0; y < image.getHeight(); y++) {
           for (int x = 0; x < image.getWidth(); x++) {
               int pixel = image.getRGB(x, y);

               int r = pixel & 0xFF;
               int g = pixel >> 8 & 0xFF;
               int b = pixel >> 16 & 0xFF;

               r = clamp(r + value);
               g = clamp(g + value);
               b = clamp(b + value);
               image.setRGB(x, y, r | (g << 8) | (b << 16) | 0xFF000000);
           }
       }
   }

   public void brightenImageThreads(int value) {
        imageForBrightness = image;

        int cores = Runtime.getRuntime().availableProcessors();
        int perCore = image.getHeight()/cores;

        for (int i = 0; i < cores; i++) {
            Runnable brightnessThread = new BrightnessThread(i*perCore, (i+1)*perCore, value);
            Thread thread = new Thread(brightnessThread);
            thread.start();

            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }

        try {
            ImageIO.write(imageForBrightness, "jpg", new File("brightness.jpg"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
   }

   public void brighten2(int value) {
       ExecutorService es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
       imageForBrightness = image;

       for (int i = 0; i < image.getHeight(); i++) {
           es.submit(new BrightnessThread(i, i+1, value));
       }
       es.shutdown();
       try {
           es.awaitTermination(999, TimeUnit.DAYS);
       } catch (InterruptedException e) {
           throw new RuntimeException(e);
       }

       try {
           ImageIO.write(imageForBrightness, "jpg", new File("brightness.jpg"));
       } catch (IOException e) {
           throw new RuntimeException(e);
       }
   }

   public int[] computeHistogram() {
        int[] histogram = new int[256];

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int rgb = image.getRGB(x, y);

                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = rgb & 0xFF;

                int gray = (int)(0.299 * r + 0.587 * g + 0.114 * b);

                histogram[gray]++;
            }
        }
        return histogram;
   }

   public void saveHistogram(String path) throws IOException {
        int[] histogram = computeHistogram();

        BufferedImage img = new BufferedImage(256, 200, BufferedImage.TYPE_INT_RGB);
        Graphics g = img.getGraphics();

        g.setColor(Color.WHITE);

        g.fillRect(0, 0, 256, 200);

        g.setColor(Color.BLACK);

        int max = 1;
        for (int v : histogram) {
            if (v > max) max = v;
        }

        for (int x = 0; x < 256; x++) {
            int h = histogram[x] * 200 / max;
            g.drawLine(x, 200, x, 200 - h);
        }

        g.dispose();

        ImageIO.write(img, "jpg", new File(path));
   }

}
