import java.util.ArrayList;
import java.util.List;

public class Cart {

    private List<Product> products = new ArrayList<>();

    public List<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product, int amount) {
        for (int i = 0; i < amount; i++) {
            this.products.add(product);
        }

    }

    public double getPrice(int year, int month) {
        //
        double sum = 0;
        for (Product p : products) {
            sum += p.getPrice(year, month);
        }
        return sum;
        // for each product
    }

    public double getInflation(int year1, int month1, int year2, int month2) {

        double price1 = getPrice(year1, month1);
        double price2 = getPrice(year2, month2);

        int months = (year2 - year1) * 12 + (month2 - month1);

        if (months <= 0) {
            throw new IllegalArgumentException("Invalid date range");
        }

        return (price2 - price1) / price1 * 100 / months * 12;
    }
}
