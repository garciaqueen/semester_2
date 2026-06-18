package org.example.game;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Brick extends GraphicsItem {
    private static int gridRows;
    private static int gridCols;
    private Color color;
    public enum CrushType {NoCrush, HorizontalCrush, VerticalCrush}

    public Brick(int col, int row, Color color){
        this.width = 1.0 /gridCols;
        this.height = 1.0 / gridRows;
        this.x = col * this.width;
        this.y = row * this.height;
        this.color = color;
    }

    public static void setGridSize(int col, int row) {
        gridCols = col;
        gridRows = row;
    }
    public CrushType checkCrush(Point2D top, Point2D bottom, Point2D left, Point2D right) {
        double brickLeft = x;
        double brickRight = x + width;
        double brickTop = y;
        double brickBottom = y + height;

        if (top.getX() >= brickLeft && top.getX() <= brickRight && top.getY() >= brickTop && top.getY() <= brickBottom) {
            return CrushType.VerticalCrush;
        }
        if (bottom.getX() >= brickLeft && bottom.getX() <= brickRight && bottom.getY() >= brickTop && bottom.getY() <= brickBottom) {
            return CrushType.VerticalCrush;
        }
        if (left.getX() >= brickLeft && left.getX() <= brickRight && left.getY() >= brickTop && left.getY() <= brickBottom) {
            return CrushType.HorizontalCrush;
        }
        if (right.getX() >= brickLeft && right.getX() <= brickRight && right.getY() >= brickTop && right.getY() <= brickBottom) {
            return CrushType.HorizontalCrush;
        }

        return CrushType.NoCrush;
    }

    @Override
    public void draw(GraphicsContext gc) {
        double pixelX = x * canvasWidth;
        double pixelY = y * canvasHeight;
        double pixelWidth = width * canvasWidth;
        double pixelHeight = height * canvasHeight;
        gc.setFill(color);
        gc.fillRect(pixelX, pixelY, pixelWidth, pixelHeight);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1.5);
        gc.strokeRect(pixelX, pixelY, pixelWidth, pixelHeight);
    }

}
