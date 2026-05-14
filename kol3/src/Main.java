public class Main {

    public static void main(String[] args) {

        MapParser parser = new MapParser();

        parser.parse("map.svg");

        for (Land land : parser.getLands()) {
            System.out.println(land);
        }
    }
}