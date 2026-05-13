public class DeathCauseStatistic {

    private String icd10;

    private int[] cases;

    public DeathCauseStatistic(String icd10, int[] cases) {
        this.icd10 = icd10;
        this.cases = cases;
    }

    public String getIcd10() {
        return icd10;
    }

    public class AgeBracketDeaths {
        public final int young;
        public final int old;
        public final int deathCount;


        public AgeBracketDeaths(int young, int old, int deathCount) {
            this.young = young;
            this.old = old;
            this.deathCount = deathCount;
        }
    }

    public static DeathCauseStatistic fromCsvLine(String line) {
        //A02.1          ,5,-,-,-,-,-,-,-,-,-,-,-,-,1,2,-,1,1,-,-,-
        String[] parts = line.split(";");
        String ticd10 = parts[0].trim();
        int num = parts.length - 1;
        int[] cass = new int[num];

        for (int i = 1; i < parts.length-1; i++) {
            if (parts[i] == "-") {
                cass[i] = 0;
            } else {
                cass[i] = Integer.parseInt(parts[i].trim());
            }

        }

        return new DeathCauseStatistic(ticd10, cass);
    }

    public AgeBracketDeaths getBracketAge(int age) {
        int index = age / 5;
        int low = index* 5;
        int high = low + 4;
        int count = cases[index];

        return new AgeBracketDeaths(low, high, count);
    }
}
