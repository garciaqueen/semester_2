import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class FoodProduct extends Product {

    private String name;

    private String[] provinces;
    private Double[][] prices;

    private FoodProduct(String name, String[] provinces, Double[][] prices) {
        this.name = name;
        this.provinces = provinces;
        this.prices = prices;
    }

    public String getName() {
        return name;
    }

    public String[] getProvinces() {
        return provinces;
    }

    public static FoodProduct fromCsv(Path path) {
        String name;

        int size;
        try {
            size = (int) Files.lines(path).count() - 2;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String[] provinces;
        Double[][] prices;
        prices = new Double[size][];
        provinces = new String[size];

        try {
            Scanner scanner = new Scanner(path);
            name = scanner.nextLine(); // odczytuję pierwszą linię i zapisuję ją jako nazwa
            scanner.nextLine();  // pomijam drugą linię z nagłówkiem tabeli

            int i = 0;
            int j = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                // cut a string into different parts
                int index = line.indexOf(';');

                String left = line.substring(0, index);
                String right = line.substring(index + 1);
                provinces[i] = left;

                prices[i] = Arrays.stream(right.split(";")) // odczytuję kolejną linię i dzielę ją na tablicę
                        .map(value -> value.replace(",",".")) // zamieniam polski znak ułamka dziesiętnego - przecinek na kropkę
                        .map(Double::valueOf) // konwertuję string na double
                        .toArray(Double[]::new);
                i++;
            }
            scanner.close();

            return new FoodProduct(name, provinces, prices);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public double getPrice(int year, int month) {

        int index = (year - 2010) * 12 + (month - 1);

        if (index < 0 || index >= prices[0].length) {
            throw new IndexOutOfBoundsException("Date outside dataset range: " + year + "-" + month);
        }

        double sum = 0;

        for (int i = 0; i < provinces.length; i++) {
            sum += prices[i][index];
        }

        return sum;
    }

    public double getPrice(int year, int month, String province) throws IndexOutOfBoundsException {
        List<String> list = Arrays.asList(provinces);

        int index = list.indexOf(province);
        if ((year < 2010 || year > 2022) || (month < 1 || month > 12) || ((year == 2022) && (month > 3))) {
            throw new IndexOutOfBoundsException("The index is out of bonds");
        }
        return prices[index][(year-2010)*12+(month-1)];
    }
}
