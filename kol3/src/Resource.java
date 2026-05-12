public class Resource {

    public enum Type {Coal, Wood, Fish};

    public final Point location;

    public final Type type;

    public Resource(Point location, Type type) {
        this.location = location;
        this.type = type;
    }

}
