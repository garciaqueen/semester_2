// =======================================================
// OOP / JAVA COLLOQUIUM CHEAT SHEET (ALL IN ONE FILE)
// =======================================================

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

// =======================================================
// 1. RECORD (immutable data carrier)
// =======================================================
record Point(double x, double y) {
    // records are FINAL, immutable, auto-generate getters:
    // x(), y(), toString(), equals(), hashCode()
}

// =======================================================
// 2. ABSTRACT CLASS
// =======================================================
abstract class Shape {
    // shared logic field
    protected String name;

    public Shape(String name) {
        this.name = name;
    }

    // abstract method = MUST be implemented
    abstract double area();

    // normal method = shared logic
    public void info() {
        System.out.println("Shape: " + name);
    }
}

// =======================================================
// 3. INHERITANCE (Polygon)
// =======================================================
class Polygon extends Shape {

    protected List<Point> points;

    public Polygon(List<Point> points) {
        super("Polygon");

        // DEEP COPY (VERY IMPORTANT IN EXAMS)
        this.points = new ArrayList<>();
        for (Point p : points) {
            this.points.add(new Point(p.x(), p.y()));
        }
    }

    // POINT-IN-POLYGON (core exam algorithm)
    public boolean inside(Point p) {
        int count = 0;

        for (int i = 0; i < points.size(); i++) {
            Point a = points.get(i);
            Point b = points.get((i + 1) % points.size());

            if ((a.y() > p.y()) != (b.y() > p.y())) {
                double x = (b.x() - a.x()) *
                        (p.y() - a.y()) /
                        (b.y() - a.y()) + a.x();

                if (x > p.x()) count++;
            }
        }

        return count % 2 == 1;
    }

    @Override
    double area() {
        return 0; // simplified
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Point p : points) {
            sb.append(p).append(" ");
        }
        return sb.toString();
    }
}

// =======================================================
// 4. DEEP vs SHALLOW COPY DEMO
// =======================================================
class CopyDemo {

    // SHALLOW COPY (BAD)
    static List<Point> shallow(List<Point> input) {
        return input; // same reference ❌
    }

    // DEEP COPY (GOOD)
    static List<Point> deep(List<Point> input) {
        List<Point> copy = new ArrayList<>();
        for (Point p : input) {
            copy.add(new Point(p.x(), p.y()));
        }
        return copy;
    }
}

// =======================================================
// 5. LIST / ARRAYLIST
// =======================================================
class ListDemo {

    public static void run() {

        // interface type (GOOD PRACTICE)
        List<String> list = new ArrayList<>();

        list.add("A");
        list.add("B");

        System.out.println(list);
    }
}

// =======================================================
// 6. SET (unique values)
// =======================================================
class SetDemo {

    public static void run() {

        Set<String> set = new HashSet<>();

        set.add("coal");
        set.add("wood");
        set.add("coal"); // ignored

        System.out.println(set);
    }
}

// =======================================================
// 7. MAP / HASHMAP
// =======================================================
class MapDemo {

    public static void run() {

        Map<String, Integer> map = new HashMap<>();

        map.put("coal", 10);
        map.put("wood", 5);

        System.out.println(map.get("coal"));
    }
}

// =======================================================
// 8. STRINGBUILDER (VERY IMPORTANT)
// =======================================================
class StringBuilderDemo {

    public static void run() {

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 5; i++) {
            sb.append("Value: ").append(i).append("\n");
        }

        System.out.println(sb.toString());
    }
}

// =======================================================
// 9. FILES (READ / WRITE)
// =======================================================
class FileDemo {

    public static void run() throws IOException {

        // WRITE FILE
        List<String> lines = List.of("Hello", "World");
        Files.write(Path.of("file.txt"), lines);

        // READ FILE
        List<String> read = Files.readAllLines(Path.of("file.txt"));

        System.out.println(read);
    }
}

class FileWriteBuffered {
    public static void main(String[] args) throws Exception {

        BufferedWriter bw =
                new BufferedWriter(new FileWriter("a.txt"));

        bw.write("Line 1");
        bw.newLine();
        bw.write("Line 2");

        bw.close();
    }
}


import java.nio.file.*;
import java.util.stream.*;

class FileReadStream {
    public static void main(String[] args) throws Exception {

        try (Stream<String> lines =
                     Files.lines(Path.of("a.txt"))) {

            lines.forEach(System.out::println);
        }
    }
}



import java.io.*;

class FileReadBuffered {
    public static void main(String[] args) throws Exception {

        BufferedReader br =
                new BufferedReader(new FileReader("a.txt"));

        String line;

        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }

        br.close();
    }
}

import java.io.*;
import java.util.*;

class FileReadScanner {
    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(new File("a.txt"));

        while (sc.hasNextLine()) {
            System.out.println(sc.nextLine());
        }

        sc.close();
    }
}
// =======================================================
// 10. SVG GENERATION
// =======================================================
class SvgDemo {

    public static void run() {

        Point p = new Point(10, 10);

        String circle =
                "<circle cx=\"" + p.x() +
                "\" cy=\"" + p.y() +
                "\" r=\"5\" />";

        System.out.println(circle);
    }
}

// =======================================================
// 11. STREAMS (VERY IMPORTANT)
// =======================================================
class StreamDemo {

    public static void run() {

        List<Integer> nums = List.of(1, 2, 3, 4, 5);

        List<Integer> filtered =
                nums.stream()
                        .filter(n -> n % 2 == 0)
                        .toList();

        List<Integer> mapped =
                nums.stream()
                        .map(n -> n * 10)
                        .toList();

        int sum =
                nums.stream()
                        .reduce(0, Integer::sum);

        System.out.println(filtered);
        System.out.println(mapped);
        System.out.println(sum);
    }
}

// =======================================================
// 12. LAMBDA
// =======================================================
class LambdaDemo {

    public static void run() {

        Function<Integer, Integer> f = x -> x * 2;
        Predicate<Integer> p = x -> x > 5;

        System.out.println(f.apply(10));
        System.out.println(p.test(3));
    }
}

// =======================================================
// 13. EXCEPTIONS
// =======================================================
class ExceptionDemo {

    static void check(int x) {
        if (x < 0) {
            throw new RuntimeException("Negative value");
        }
    }
}


class InvalidCityException extends RuntimeException {

    public InvalidCityException(String cityName) {
        super("City invalid: " + cityName);
    }
}

class MyCheckedException extends Exception {
    public MyCheckedException(String msg) {
        super(msg);
    }
}

try {
    throw new MyCheckedException("error");
} catch (MyCheckedException e) {
    System.out.println(e.getMessage());
}
// =======================================================
// 14. JACKSON XML (USED IN YOUR LABS)
// =======================================================
class XmlDemo {

    static class Dummy {
        public int x;
        public int y;
    }

    public static void run() throws Exception {

        XmlMapper mapper = new XmlMapper();

        File file = new File("data.xml");

        // object <-> xml conversion
        // Dummy obj = mapper.readValue(file, Dummy.class);
    }
}


import java.util.*;

class SortNumbers {

    public static void main(String[] args) {

        List<Integer> list = Arrays.asList(5, 1, 9, 2);

        Collections.sort(list);

        System.out.println(list); // [1,2,5,9]
    }
}

Collections.sort(list, Collections.reverseOrder());

class Person {
    String name;
    int age;

    Person(String n, int a) {
        name = n;
        age = a;
    }
}

List<Person> list = new ArrayList<>();

list.add(new Person("A", 30));
list.add(new Person("B", 20));

list.sort((p1, p2) -> p1.age - p2.age);

//Clean way

list.sort(Comparator.comparing(p -> p.age));

list.sort(Comparator.comparing((Person p) -> p.age).reversed());



class Person implements Comparable<Person> {

    String name;
    int age;

    Person(String n, int a) {
        name = n;
        age = a;
    }

    @Override
    public int compareTo(Person other) {
        return this.age - other.age;
    }
}

//-----------------------------
Collections.sort(list); // uses compareTo


import java.util.Arrays;

class ArraySort {

    public static void main(String[] args) {

        int[] arr = {5, 2, 9, 1};

        Arrays.sort(arr);

        System.out.println(Arrays.toString(arr));
    }
}

Integer[] arr = {5, 2, 9, 1};

Arrays.sort(arr, Collections.reverseOrder());

Integer.compare(p1.age, p2.age)

  list.stream()
    .sorted(Comparator.comparing(p -> p.age))
    .toList();
// =======================================================
// 15. MAIN (RUN ALL DEMOS)
// =======================================================
public class Main {

    public static void main(String[] args) throws Exception {

        ListDemo.run();
        SetDemo.run();
        MapDemo.run();
        StringBuilderDemo.run();
        StreamDemo.run();
        LambdaDemo.run();

        SvgDemo.run();

        ExceptionDemo.check(10);

        FileDemo.run();

        System.out.println("CHEAT SHEET RUN COMPLETE");
    }
}
