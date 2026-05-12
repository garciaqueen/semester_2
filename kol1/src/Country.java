import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public abstract class Country {

    private final String name;

    private static String deathsCsv;

    private static String confCasesCsv;


    protected Country(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private static class CountryColumns {
        public final int firstColumnIndex;
        public final int columnCount;

        public CountryColumns(int firstColumnIndex, int columnCount) {
            this.firstColumnIndex = firstColumnIndex;
            this.columnCount = columnCount;
        }
    }


    public static void setFiles(String death, String cases) throws FileNotFoundException {
        // check if the files exist

        deathsCsv = death;

        confCasesCsv = cases;

        File f1 = new File(deathsCsv);

        if (!f1.exists() || !f1.canRead()) {
            throw new FileNotFoundException(death);
        }

        File f2 = new File(confCasesCsv);

        if (!f2.exists() || !f2.canRead()) {
            throw new FileNotFoundException(cases);
        }


    }

    public static Country fromCsv(String country)
            throws IOException, CountryNotFoundException {

        BufferedReader casesBr =
                new BufferedReader(new FileReader(confCasesCsv));

        BufferedReader deathsBr =
                new BufferedReader(new FileReader(deathsCsv));

        // headers
        String casesHeader = casesBr.readLine();
        String deathsHeader = deathsBr.readLine();

        String casesProvinces = casesBr.readLine();
        String deathsProvinces = deathsBr.readLine();

        // find country columns
        CountryColumns cols =
                getCountryColumns(casesHeader, country);

        int start = cols.firstColumnIndex;
        int count = cols.columnCount;

        List<String> provList =
                List.of(casesProvinces.split(";"));

        Country result;

        // build structure
        if (provList.get(start).equals("nan")) {

            result = new CountryWithoutProvinces(country);

        } else {

            Country[] provinces = new Country[count];

            for (int i = 0; i < count; i++) {
                provinces[i] =
                        new CountryWithoutProvinces(
                                provList.get(start + i)
                        );
            }

            result = new CountryWithProvinces(provinces, country);
        }

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("M/d/yy");

        String lineCases;
        String lineDeaths;

        while ((lineCases = casesBr.readLine()) != null &&
                (lineDeaths = deathsBr.readLine()) != null) {

            String[] c = lineCases.split(";");
            String[] d = lineDeaths.split(";");

            LocalDate date = LocalDate.parse(c[0], fmt);

            if (result instanceof CountryWithoutProvinces cwp) {

                int cases = Integer.parseInt(c[start]);
                int deaths = Integer.parseInt(d[start]);

                cwp.addDailyStatistic(date, cases, deaths);

            } else {

                CountryWithProvinces cp = (CountryWithProvinces) result;

                Country[] provinces = cp.getProvinces();

                for (int i = 0; i < count; i++) {

                    int cases = Integer.parseInt(c[start + i]);
                    int deaths = Integer.parseInt(d[start + i]);

                    ((CountryWithoutProvinces) provinces[i])
                            .addDailyStatistic(date, cases, deaths);
                }
            }
        }

        return result;
    }
    /*
    * krok 6
    * Napisz statyczną metodę przeciążającą fromCsv, która zamiast pojedynczej nazwy kraju przyjmuje
        tablicę takich nazw. Metoda powinna zwrócić tablicę obiektów Country. Jeżeli metoda fromCsv(String)
        (poprzednia) rzuca wyjątek CountryNotFoundException, należy wyświetlić na standardowym wyjściu
        wartość zwracaną przez metodę getMessage() wyjątku i pominąć to państwo w wynikowej liście
     */
    public static Country[] fromCsv(String[] names)
            throws IOException {

        List<Country> result = new ArrayList<>();

        for (String name : names) {

            try {

                result.add(fromCsv(name));

            } catch (CountryNotFoundException e) {

                System.out.println(e.getMessage());
            }
        }

        return result.toArray(new Country[0]);
    }

    public abstract int getConfirmedCases(LocalDate date);

    public abstract int getDeaths(LocalDate date);

//    public static Country fromCsv(String country)
//            throws FileNotFoundException {
//
//        File deaths = new File(deathsCsv);
//
//        try (BufferedReader br =
//                     new BufferedReader(new FileReader(deaths))) {
//
//            // pierwsze dwa wiersze
//            String countryH = br.readLine();
//            String provincesH = br.readLine();
//
//            // rozdzielenie po średnikach
//            List<String> countries =
//                    List.of(countryH.split(";"));
//
//            List<String> provinces =
//                    List.of(provincesH.split(";"));
//
//            // znajdź pierwszy indeks kraju
//            int ind = countries.indexOf(country);
//
//            if (ind == -1) {
//                return null;
//            }
//
//            // kraj bez prowincji
//            if (provinces.get(ind).equals("nan")) {
//                return new CountryWithoutProvinces(country);
//            }
//
//            // policz prowincje
//            int count = 0;
//            int temp = ind;
//
//            while (temp < countries.size()
//                    && countries.get(temp).equals(country)) {
//
//                count++;
//                temp++;
//            }
//
//            Country[] provinceTable = new Country[count];
//
//            // utwórz prowincje
//            for (int i = 0; i < count; i++) {
//
//                String provinceName =
//                        provinces.get(ind + i);
//
//                provinceTable[i] =
//                        new CountryWithoutProvinces(provinceName);
//            }
//
//            return new CountryWithProvinces(provinceTable, country);
//
//        } catch (IOException e) {
//
//            throw new RuntimeException(e);
//        }
//    }


    private static CountryColumns getCountryColumns(String firstRow, String country) throws FileNotFoundException, CountryNotFoundException {


        List<String> countries = List.of(firstRow.split(";"));

        int ind = countries.indexOf(country);

        if (ind == -1) {
            throw new CountryNotFoundException(country);
        }

        int count = 0;
        int temp = ind;

        while (temp < countries.size()
                && countries.get(temp).equals(country)) {

            count++;
            temp++;
        }

        return new CountryColumns(ind, count);
    }

    public int getDeathsFromTo(LocalDate start, LocalDate end) {

        int sum = 0;

        LocalDate current = start;

        while (!current.isAfter(end)) {

            sum += getDeaths(current);

            current = current.plusDays(1);
        }

        return sum;
    }

//    W klasie Country napisz publiczną, statyczną metodę sortByDeaths, która przyjmie listę obiektów
//    Country oraz dwie daty: początkową i końcową. Metoda powinna posortować tablicę malejąco według
//    liczby śmierci w okresie między datą początkową, a końcową włącznie z nimi. Zakładamy poprawność
//    podanych dat oraz, że początkowa jest wcześniejsza niż końcowa.

    public static void sortByDeaths(List<Country> countries, LocalDate dateStart, LocalDate dateLast) {
        // we have countries
        // simply sort
        //getDeaths for each country
        // sum of the deaths between the dates

        countries.sort((a, b) ->
                Integer.compare(
                        b.getDeathsFromTo(dateStart, dateLast),
                        a.getDeathsFromTo(dateStart, dateLast)
                )
        );
    }

    public void saveToDataFile(String path) {

        try {

            FileWriter writer = new FileWriter(path);

            int lines =
                    (int) Files.lines(Path.of(deathsCsv)).count();

            DateTimeFormatter inputFmt =
                    DateTimeFormatter.ofPattern("M/d/yy");

            DateTimeFormatter outputFmt =
                    DateTimeFormatter.ofPattern("d/MM/yy");

            LocalDate date =
                    LocalDate.parse("1/23/20", inputFmt);

            for (int i = 0; i < lines - 2; i++) {

                writer.write(
                        date.format(outputFmt)
                                + "\t"
                                + getConfirmedCases(date)
                                + "\t"
                                + getDeaths(date)
                                + "\n"
                );

                date = date.plusDays(1);
            }

            writer.close();

        } catch (IOException e) {

            System.out.println("An error occurred.");
        }
    }



}
