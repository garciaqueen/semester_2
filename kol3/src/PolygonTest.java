import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class PolygonTest {

    @Test
    void pointInsidePolygon() {

        Polygon polygon = new Polygon(Arrays.asList(
                new Point(0, 0),
                new Point(4, 0),
                new Point(4, 4),
                new Point(0, 4)
        ));

        Point point = new Point(2, 2);

        assertTrue(polygon.inside(point));
    }

    @Test
    void pointBelowPolygon() {

        Polygon polygon = new Polygon(Arrays.asList(
                new Point(0, 0),
                new Point(4, 0),
                new Point(4, 4),
                new Point(0, 4)
        ));

        Point point = new Point(2, -2);

        assertFalse(polygon.inside(point));
    }

    @Test
    void pointRightOfPolygon() {

        Polygon polygon = new Polygon(Arrays.asList(
                new Point(0, 0),
                new Point(4, 0),
                new Point(4, 4),
                new Point(0, 4)
        ));

        Point point = new Point(6, 2);

        assertFalse(polygon.inside(point));
    }
}