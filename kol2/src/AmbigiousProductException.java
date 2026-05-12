import java.util.List;

public class AmbigiousProductException extends RuntimeException {
    private final List<String> products;

    public AmbigiousProductException(List<String> products) {
        super("Ambiguous products: " + products);
        this.products = products;
    }

    public List<String> getProducts() {
        return products;
    }
}
