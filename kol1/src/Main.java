import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        try {

            // ustaw pliki CSV
            Country.setFiles(
                    "deaths.csv",
                    "confirmed_cases.csv"
            );

            // =========================
            // TEST 1 — pojedynczy kraj
            // =========================

            Country poland =
                    Country.fromCsv("Poland");

            System.out.println("=== SINGLE COUNTRY ===");
            System.out.println(poland.getName());

            LocalDate d =
                    LocalDate.of(2021, 3, 15);

            System.out.println(
                    "Cases: " +
                            poland.getConfirmedCases(d)
            );

            System.out.println(
                    "Deaths: " +
                            poland.getDeaths(d)
            );

            // =========================
            // TEST 2 — kraj z prowincjami
            // =========================

            Country australia =
                    Country.fromCsv("Australia");

            System.out.println("\n=== COUNTRY WITH PROVINCES ===");

            System.out.println(australia.getName());

            System.out.println(
                    "Cases: " +
                            australia.getConfirmedCases(d)
            );

            System.out.println(
                    "Deaths: " +
                            australia.getDeaths(d)
            );

            // =========================
            // TEST 3 — tablica krajów
            // =========================

            System.out.println("\n=== ARRAY VERSION ===");

            String[] names = {
                    "Poland",
                    "Germany",
                    "Narnia",
                    "Australia"
            };

            Country[] arr =
                    Country.fromCsv(names);

            for (Country c : arr) {
                System.out.println(c.getName());
            }

            // =========================
            // TEST 4 — sortowanie
            // =========================

            System.out.println("\n=== SORT BY DEATHS ===");

            List<Country> list =
                    new ArrayList<>();

            list.addAll(Arrays.asList(arr));

            LocalDate start =
                    LocalDate.of(2021, 1, 1);

            LocalDate end =
                    LocalDate.of(2021, 12, 31);

            Country.sortByDeaths(
                    list,
                    start,
                    end
            );

            for (Country c : list) {

                int totalDeaths = 0;

                LocalDate current = start;

                while (!current.isAfter(end)) {

                    totalDeaths +=
                            c.getDeaths(current);

                    current =
                            current.plusDays(1);
                }

                System.out.println(
                        c.getName()
                                + " -> "
                                + totalDeaths
                );
            }

            // =========================
            // TEST 5 — zapis do pliku
            // =========================

            System.out.println("\n=== SAVE FILE ===");

            poland.saveToDataFile(
                    "poland_output.txt"
            );

            System.out.println(
                    "Saved to poland_output.txt"
            );

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}