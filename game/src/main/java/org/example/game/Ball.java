package org.example.game;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Ball extends GraphicsItem {

    private Point2D moveVector;
    private double velocity;

    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public Point2D getMoveVector() {
        return moveVector;
    }

    public void setMoveVector(Point2D moveVector) {
        this.moveVector = moveVector;
    }

    public Ball() {
        this.width = 0.05;
        this.height = 0.05;
        this.x = 0.5 - (this.width/2);
        this.y = 0.5 - (this.height/2);
        this.velocity = 0.9;
        double angleRad = Math.toRadians(45);
        this.moveVector = new Point2D(Math.cos(angleRad), -Math.sin(angleRad));
    }

    @Override
    public void draw(GraphicsContext gc) {
        double pixelX = x * canvasWidth;
        double pixelY = y * canvasHeight;
        double pixelWidth = width * canvasWidth;
        double pixelHeight = height * canvasHeight;
        gc.setFill(Color.GHOSTWHITE);
        gc.fillOval(pixelX, pixelY, pixelWidth, pixelHeight);
    }

    public void setPosition(Point2D point) {
        double relativeCenterX = point.getX() / canvasWidth;
        double relativeCenterY = point.getY() / canvasHeight;
        this.x = relativeCenterX - (this.width/2);
        this.y = relativeCenterY - (this.height/2);
    }
    public void updatePosition(double dt) {
        this.x += this.moveVector.getX() * velocity * dt;
        this.y += this.moveVector.getY() * velocity * dt;
    }

    public void bounceHorizontally() {
        this.moveVector = new Point2D(-this.moveVector.getX(), this.moveVector.getY());
    }
    public void bounceVertically() {
        this.moveVector = new Point2D(this.moveVector.getX(), -this.moveVector.getY());
    }

    public Point2D getTopPoint() {
        return new Point2D(x + width / 2, y);
    }

    public Point2D getBottomPoint() {
        return new Point2D(x + width / 2, y + height);
    }

    public Point2D getLeftPoint() {
        return new Point2D(x, y + height / 2);
    }

    public Point2D getRightPoint() {
        return new Point2D(x + width, y + height / 2);
    }

    public void bounceFromPaddle(double hitPosition) {
        hitPosition = Math.max(-1, Math.min(1, hitPosition));
        double angle = hitPosition * Math.toRadians(60);

        double newX = Math.sin(angle);
        double newY = -Math.cos(angle);

        moveVector = new Point2D(newX, newY).normalize();
    }

}
