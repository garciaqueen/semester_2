import java.time.LocalDate;

public class CountryWithProvinces extends Country {

    private Country[] provinces;

    public CountryWithProvinces(Country[] provinces, String name) {
        super(name);
        this.provinces = provinces;
    }

    public Country[] getProvinces() {
        return provinces;
    }


    @Override
    public int getConfirmedCases(LocalDate date) {
        int sum = 0;
        for (int i = 0; i < provinces.length; i++) {
            sum += provinces[i].getConfirmedCases(date);
        }
        return sum;
    }

    @Override
    public int getDeaths(LocalDate date) {
        int sum = 0;
        for (int i = 0; i < provinces.length; i++) {
            sum += provinces[i].getDeaths(date);
        }
        return sum;
    }
}
