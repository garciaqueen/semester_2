import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CountryWithoutProvinces extends Country {


    private List<DailyStatistic> stats = new ArrayList<>();
    public CountryWithoutProvinces(String name) {
        super(name);
    }

    private static class DailyStatistic {
        LocalDate date;
        int cases;
        int deaths;

        public DailyStatistic(LocalDate date, int cases, int deaths) {
            this.date = date;
            this.cases = cases;
            this.deaths = deaths;
        }
    }

    public void addDailyStatistic(LocalDate date, int cases, int deaths) {
        stats.add(new DailyStatistic(date, cases, deaths));
    }


    @Override
    public int getConfirmedCases(LocalDate date) {
        for (DailyStatistic s : stats) {
            if (s.date.equals(date)) {
                return s.cases;
            }
        }
        return 0;
    }

    @Override
    public int getDeaths(LocalDate date) {
        for (DailyStatistic s : stats) {
            if (s.date.equals(date)) {
                return s.deaths;
            }
        }
        return 0;
    }
}
