import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Scanner;

public class NonFoodProduct extends Product{
    String name;
    Double[] prices;

    private NonFoodProduct(String name, Double[] prices) {
        this.name = name;
        this.prices = prices;
    }

    public String getName() {
        return name;
    }

    public Double[] getPrices() {
        return prices;
    }

    public static NonFoodProduct fromCsv(Path path) {
        String name;
        Double[] prices;

        try {
            Scanner scanner = new Scanner(path);
            name = scanner.nextLine(); // odczytuję pierwszą linię i zapisuję ją jako nazwa
            scanner.nextLine();  // pomijam drugą linię z nagłówkiem tabeli
            prices = Arrays.stream(scanner.nextLine().split(";")) // odczytuję kolejną linię i dzielę ją na tablicę
                    .map(value -> value.replace(",",".")) // zamieniam polski znak ułamka dziesiętnego - przecinek na kropkę
                    .map(Double::valueOf) // konwertuję string na double
                    .toArray(Double[]::new); // dodaję je do nowo utworzonej tablicy

            scanner.close();

            return new NonFoodProduct(name, prices);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public double getPrice(int year, int month) throws IndexOutOfBoundsException {

        // name ofthefood.csv
        //first line
        // we have array [0] - price [1] - price
        //2010 I
        // 2022 III
        // 13 4
        //2010-2013 = (3)*12 +4 -1
        if ((year < 2010 || year > 2022) || (month < 1 || month > 12) || ((year == 2022) && (month > 3))) {
            throw new IndexOutOfBoundsException("The index is out of bonds");
        }
        return prices[(year-2010)*12+(month-1)];
    }
}
