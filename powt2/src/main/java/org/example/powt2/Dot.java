package org.example.powt2;
import javafx.scene.paint.Color;

public record Dot(double x, double y, double radius, Color color) {
    public static String toMessage(double x, double y, double radius, Color color) {
        return "Parameters:"+ x+ ":" + y +":" + radius + ":" + color.toString();
    }

    public static Dot fromMessage(String msg) {
        String parts[] = msg.split(":");
        return new Dot(Double.parseDouble(parts[1]), Double.parseDouble(parts[2]), Double.parseDouble(parts[3]), Color.valueOf(parts[4]));
    }
}
