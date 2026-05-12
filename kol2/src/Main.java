import java.nio.file.Path;

public class Main {

    public static void main(String[] args) {

        // =========================
        // 1. LOAD PRODUCTS
        // =========================

        Product.clearProducts();

        Product.addProducts(FoodProduct::fromCsv, Path.of("buraki.csv"));

        Product.addProducts(NonFoodProduct::fromCsv, Path.of("mydlo.csv"));

        System.out.println("Loaded products:");
        for (Product p : Product.getProducts()) {
            System.out.println("- " + p.getName());
        }

        // =========================
        // 2. CART TEST
        // =========================

        Cart cart = new Cart();

        // add first product twice (example)
        cart.addProduct(Product.getProducts().get(0), 2);

        // add second product once (if exists)
        if (Product.getProducts().size() > 1) {
            cart.addProduct(Product.getProducts().get(1), 1);
        }

        // =========================
        // 3. PRICE TEST
        // =========================

        double price = cart.getPrice(2020, 5);
        System.out.println("\nCart price (2020-05): " + price);

        // =========================
        // 4. INFLATION TEST
        // =========================

        double inflation = cart.getInflation(
                2020, 5,
                2021, 5
        );

        System.out.println("Inflation: " + inflation + "%");

        // =========================
        // 5. PREFIX SEARCH TEST
        // =========================

        try {
            Product result = Product.getProducts("My");
            System.out.println("\nFound product: " + result.getName());

        } catch (AmbigiousProductException e) {
            System.out.println("\nAmbiguous products:");
            for (String name : e.getProducts()) {
                System.out.println("- " + name);
            }

        } catch (IndexOutOfBoundsException e) {
            System.out.println("\nNo product found");
        }
    }
}