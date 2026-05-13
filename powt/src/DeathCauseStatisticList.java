import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DeathCauseStatisticList {

    private List<DeathCauseStatistic> statistics = new ArrayList<>();

    public void repopulate(String path) throws IOException {
        BufferedReader bf = new BufferedReader(new FileReader(path));
        if (!statistics.isEmpty()) {
            statistics.clear();
        }
        String line = bf.readLine();
        line = bf.readLine();
        while ((line = bf.readLine()) != null) {
            this.statistics.add(DeathCauseStatistic.fromCsvLine(line));
        }

    }

    public List<DeathCauseStatistic> mostDeadlyDiseases(int age, int n) {
        // 1. Sort the list based on the deathCount for the given age
        // We use reversed() because the task asks for descending order (highest first)
        List<DeathCauseStatistic> deths = statistics;
        deths.sort(Comparator.comparingInt((DeathCauseStatistic d) -> d.getBracketAge(age).deathCount)
                .reversed());

        // 2. Return the first n elements
        return deths.subList(0, n);
    }
}
