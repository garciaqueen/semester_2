import java.util.Arrays;
import java.util.List;

public class Land extends Polygon {
    public Land(List<Point> points) {
        super(points);
    }

    private List<City> cities;

    public boolean isLand(Point p) {
        // przykładowa implementacja (do zastąpienia własną logiką)
        return true;
    }

    private void updatePortStatus(City city) {

        for (Point p : Arrays.asList(
                city.getPoints().get(0),
                city.getPoints().get(1),
                city.getPoints().get(2),
                city.getPoints().get(3)
        )) {
            if (!isLand(p)) {
                city.setPort(true);
                return;
            }
        }

        city.setPort(false);
    }

    public void addCity(City city) {

        if (!isLand(city.center)) {
            throw new RuntimeException(city.getName());
        }

        updatePortStatus(city);
        cities.add(city);
    }
}


}
