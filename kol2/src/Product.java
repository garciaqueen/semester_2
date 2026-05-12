import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public abstract class Product {

    private String name;

    private static List<Product> products = new ArrayList<>();

    @FunctionalInterface
    interface ProductLoader<T> {
        T load(Path path);
    }

    public String getName() {
        return name;
    }

    public static List<Product> getProducts() {
        return products;
    }

    public abstract double getPrice(int year, int month) throws IndexOutOfBoundsException;

    public static void clearProducts() {
        products.clear();
    }

    public static <T extends Product> void addProducts(ProductLoader<T> loader, Path path) {
        T product = loader.load(path);
        products.add(product);
    }

    public static Product getProducts(String prefix)
            throws AmbigiousProductException, IndexOutOfBoundsException {

        List<Product> matches = products.stream()
                .filter(p -> p.getName().startsWith(prefix))
                .toList();

        if (matches.isEmpty()) {
            throw new IndexOutOfBoundsException(
                    "Prefix nie wskazuje na żaden produkt: " + prefix);

        } else if (matches.size() > 1) {

            List<String> names = matches.stream()
                    .map(Product::getName)
                    .toList();

            throw new AmbigiousProductException(names);

        } else {
            return matches.get(0);
        }
    }
}
