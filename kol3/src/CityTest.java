import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class CityTest {

    static City inlandCity;
    static City portCity;

    static {

        Land land = new Land(List.of(
                new Point(0,0),
                new Point(10,0),
                new Point(10,10),
                new Point(0,10)
        ));

        inlandCity = new City(new Point(5,5), "A", 2);
        portCity = new City(new Point(1,1), "B", 4);

        land.addCity(inlandCity);
        land.addCity(portCity);
    }

    static Stream<org.junit.jupiter.params.provider.Arguments> data() {

        return Stream.of(

                org.junit.jupiter.params.provider.Arguments.of(
                        inlandCity,
                        new Resource(new Point(5,5), Resource.Type.Coal),
                        true
                ),

                org.junit.jupiter.params.provider.Arguments.of(
                        inlandCity,
                        new Resource(new Point(50,50), Resource.Type.Wood),
                        false
                ),

                org.junit.jupiter.params.provider.Arguments.of(
                        portCity,
                        new Resource(new Point(1,1), Resource.Type.Fish),
                        true
                ),

                org.junit.jupiter.params.provider.Arguments.of(
                        inlandCity,
                        new Resource(new Point(5,5), Resource.Type.Fish),
                        false
                )
        );
    }

    @ParameterizedTest
    @MethodSource("data")
    void testResources(City city, Resource resource, boolean expected) {

        city.resources.clear();

        city.addResourcesInRange(List.of(resource), 10);

        assertEquals(expected,
                city.resources.contains(resource.type));
    }
}