import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MapParser {

    static public final class Svg {

        @JacksonXmlElementWrapper(useWrapping = false)
        @JsonProperty("rect")
        private List<Map<String, String>> rects;

        @JacksonXmlElementWrapper(useWrapping = false)
        @JsonProperty("polygon")
        private List<Map<String, String>> polygons;

        @JacksonXmlElementWrapper(useWrapping = false)
        @JsonProperty("text")
        private List<Map<String, String>> texts;

        @JacksonXmlElementWrapper(useWrapping = false)
        @JsonProperty("circle")
        private List<Map<String, String>> circles;
    }

    private record Label(Point point, String text) {}

    private final List<Label> labels = new ArrayList<>();
    private final List<Land> lands = new ArrayList<>();
    private final List<City> cities = new ArrayList<>();

    public List<Land> getLands() {
        return lands;
    }

    public List<City> getCities() {
        return cities;
    }

    private void parseText(Map<String, String> params) {

        String text = params.get("$");

        if (text == null) {
            text = params.get("");
        }

        addLabel(
                text,
                new Point(
                        Double.parseDouble(params.get("x")),
                        Double.parseDouble(params.get("y"))
                )
        );
    }

    private void addLabel(String text, Point bottomLeft) {
        labels.add(new Label(bottomLeft, text));
    }

    private void parsePolygon(Map<String, String> params) {

        String raw = params.get("points").trim();

        String[] coords = raw.split("\\s+");

        List<Point> points = new ArrayList<>();

        for (String c : coords) {

            String[] xy = c.split(",");

            if (xy.length < 2) {
                continue;
            }

            points.add(new Point(
                    Double.parseDouble(xy[0]),
                    Double.parseDouble(xy[1])
            ));
        }

        lands.add(new Land(points));
    }

    private void parseRect(Map<String, String> params) {

        double x = Double.parseDouble(params.get("x"));
        double y = Double.parseDouble(params.get("y"));

        double width = Double.parseDouble(params.get("width"));

        Point center = new Point(
                x + width / 2,
                y + width / 2
        );

        cities.add(new City(center, null, width));
    }

    void matchLabelsToTowns() {

        for (City city : cities) {

            Label nearest = null;
            double bestDistance = Double.MAX_VALUE;

            for (Label label : labels) {

                double dx = city.center.x - label.point().x;
                double dy = city.center.y - label.point().y;

                double distance = Math.sqrt(dx * dx + dy * dy);

                if (distance < bestDistance) {
                    bestDistance = distance;
                    nearest = label;
                }
            }

            if (nearest != null) {
                city.setName(nearest.text());
            }
        }
    }

    void addCitiesToLands() {

        for (Land land : lands) {

            for (City city : cities) {

                if (land.inside(city.center)) {
                    land.addCity(city);
                }
            }
        }
    }

    void parse(String path) {

        XmlMapper xmlMapper = new XmlMapper();

        File file = new File(path);

        try {

            Svg svg = xmlMapper.readValue(file, Svg.class);

            for (var item : svg.texts)
                parseText(item);

            for (var item : svg.polygons)
                parsePolygon(item);

            for (var item : svg.rects)
                parseRect(item);

            matchLabelsToTowns();

            addCitiesToLands();

        } catch (IOException e) {

            throw new RuntimeException(e);
        }
    }
}