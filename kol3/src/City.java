import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class City extends Polygon {

    public final Point center;

    private String name;


    private boolean port;

    Set<Resource.Type> resources = new HashSet<>();

    public String getName() {
        return name;
    }

    public boolean isPort() {
        return port;
    }

    void setPort(boolean port) {
        this.port = port;
    }

    public void setName(String name) {
        this.name = name;
    }

    public City(Point center, String name, double wallLength) {

        super(Arrays.asList(
                new Point(center.x - wallLength / 2, center.y - wallLength / 2),
                new Point(center.x + wallLength / 2, center.y - wallLength / 2),
                new Point(center.x + wallLength / 2, center.y + wallLength / 2),
                new Point(center.x - wallLength / 2, center.y + wallLength / 2)
        ));

        this.center = center;
        this.name = name;

    }

    public void addResourcesInRange(List<Resource> resources, double range) {

        for (Resource r : resources) {

            double dx = r.location.x - center.x;
            double dy = r.location.y - center.y;

            double distance = Math.sqrt(dx * dx + dy * dy);

            if (distance <= range) {

                if (r.type == Resource.Type.Fish && !port) {
                    continue;
                }

                this.resources.add(r.type);
            }
        }
    }

    @Override
    public String toString() {

        if (port) {
            return name + " ⚓";
        }

        return name;
    }
}

