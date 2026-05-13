import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException {


        DeathCauseStatistic d = DeathCauseStatistic.fromCsvLine("C02.8          ,114,-,-,-,-,-,-,2,3,2,8,8,22,24,16,10,7,7,2,2,1");

        System.out.println(d.getIcd10());

        DeathCauseStatistic.AgeBracketDeaths a = d.getBracketAge(43);

        System.out.println(a.young);
        System.out.println(a.old);
        System.out.println(a.deathCount);

        DeathCauseStatisticList sa = new DeathCauseStatisticList();
        sa.repopulate("zgony.csv"); // This will now compile!

        List<DeathCauseStatistic> da = sa.mostDeadlyDiseases(76, 5);

        // To see the results clearly, loop through them
        for(DeathCauseStatistic stat : da) {
            System.out.println(stat.getIcd10() + " deaths: " + stat.getBracketAge(76).deathCount);
        }

        String pathTxt = "icd10.txt";
        ICDCodeTabular timeOpt = new ICDCodeTabularOptimizedForTime(pathTxt);

        try {
            System.out.println("A00: " + timeOpt.getDescription("A00"));
            System.out.println("A01.0: " + timeOpt.getDescription("A01.0"));
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }

        ICDCodeTabular memOpt = new ICDCodeTabularOptimizedForMemory(pathTxt);

        try {
            System.out.println("A02.1: " + memOpt.getDescription("A02.1"));
            // Test błędu (kod, którego nie ma)
            //System.out.println("XYZ: " + memOpt.getDescription("XYZ"));
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Złapano oczekiwany błąd: " + e.getMessage());
        }
    }
}
