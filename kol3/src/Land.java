import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Land extends Polygon {

    private List<City> cities = new ArrayList<>();

    public Land(List<Point> points) {
        super(points);
    }

    public boolean isLand(Point p) {
        return inside(p);
    }

    private void updatePortStatus(City city) {

        for (Point p : city.getPoints()) {

            if (!inside(p)) {
                city.setPort(true);
                return;
            }
        }

        city.setPort(false);
    }

    public void addCity(City city) {

        if (!inside(city.center)) {
            throw new RuntimeException(city.getName());
        }

        updatePortStatus(city);
        cities.add(city);
    }

    @Override
    public String toString() {

        return cities.stream()
                .map(City::toString)
                .collect(Collectors.joining(", "));
    }
}

