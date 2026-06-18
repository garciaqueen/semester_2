package org.example.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class Paddle extends GraphicsItem {

    public Paddle() {
        this.width = 0.15;
        this.height = 0.03;
        this.x = (1-this.width) / 2;
        this.y = 1-this.height - 0.05;
    }
    @Override
    public void draw(GraphicsContext gc) {
        double pixelX = x * canvasWidth;
        double pixelY = y * canvasHeight;
        double pixelWidth = width * canvasWidth;
        double pixelHeight = height * canvasHeight;
        gc.setFill(Color.PLUM);
        gc.fillRect(pixelX, pixelY, pixelWidth, pixelHeight);
        gc.save();
    }

    public void getXMouse(double xMouse) {
        double relativeMouse = xMouse/canvasWidth;
        this.x = relativeMouse - (this.width / 2.0);

        if (this.x < 0.0) {
            this.x = 0.0;
        }
        if (this.x > 1 - this.width) {
            this.x = 1 - this.width;
        }
    }
}
