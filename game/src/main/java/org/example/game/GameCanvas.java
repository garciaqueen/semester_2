package org.example.game;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.Iterator;

public class GameCanvas extends Canvas {
    private GraphicsContext gc;
    private Paddle paddle = new Paddle();
    private final Ball ball = new Ball();
    private boolean isGameStarted = false;
    private ArrayList<Brick> bricks;

    public GameCanvas(double width, double height) {
        super(width, height);
        this.gc = this.getGraphicsContext2D();

        GraphicsItem.setCanvasSize(width, height);
        bricks = new ArrayList<>();
        loadLevel();
        this.setOnMouseMoved((MouseEvent event) -> {
            paddle.getXMouse(event.getX());
        });
        this.setOnMouseClicked((MouseEvent e) -> {
            isGameStarted = true;
        });
        AnimationTimer timer = new AnimationTimer() {
            private long lastTime = 0;
            @Override
            public void handle(long now) {
                if (lastTime == 0) {
                    lastTime = now;
                    return;
                }
                long nanoDiff = now - lastTime;
                double dt = nanoDiff/1_000_000_000.0;
                lastTime = now;
                if (isGameStarted) {
                    ball.updatePosition(dt);
                    if (shouldBallBounceHorizontally()) {
                        ball.bounceHorizontally();
//                        Point2D currVector = ball.getMoveVector();
//                        ball.setMoveVector(new Point2D(-currVector.getX(), currVector.getY()));
                    }
                    if (shouldBallBounceVertically()) {
                        ball.bounceVertically();
//                        Point2D currVector = ball.getMoveVector();
//                        ball.setMoveVector(new Point2D(currVector.getX(), -currVector.getY()));
                    }
                    if (shouldBallBounceFromPaddle()) {
                        double ballCenter = ball.getX() + ball.getWidth() / 2;
                        double paddleCenter = paddle.getX() + paddle.getWidth() / 2;

                        double hitPosition =
                                (ballCenter - paddleCenter) / (paddle.getWidth() / 2);

                        ball.bounceFromPaddle(hitPosition);

                        ball.setY(paddle.getY() - ball.getHeight());
                    }
                    checkBrickCollision();
                } else {
                    double paddleCenterX = (paddle.getX() + paddle.getWidth() / 2) * getWidth();
                    double paddleTopY = paddle.getY() * getHeight() - (ball.getHeight() / 2.0) * getHeight();
                    ball.setPosition(new Point2D(paddleCenterX, paddleTopY));
                }
                draw();
            }
        };
        timer.start();
    }

    public void draw() {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, getWidth(), getHeight());

        paddle.draw(gc);
        ball.draw(gc);

        for(Brick brick : bricks) {
            brick.draw(gc);
        }
    }


    private boolean shouldBallBounceHorizontally() {
        return ball.getX() <= 0.0 || (ball.getX() + ball.getWidth()) >= 1.0;

    }

    private boolean shouldBallBounceVertically() {
        return ball.getY() <= 0;
    }

    private boolean shouldBallBounceFromPaddle() {
        return (ball.getX() + ball.getWidth() >= paddle.getX()) &&
                (ball.getX() <= paddle.getX() + paddle.getWidth()) &&
                (ball.getY() + ball.getHeight() >= paddle.getY()) &&
                (ball.getY() <= paddle.getY() + paddle.getHeight());
    }

    private void checkBrickCollision() {

        Iterator<Brick> iterator = bricks.iterator();

        while(iterator.hasNext()) {

            Brick brick = iterator.next();

            Brick.CrushType crush = brick.checkCrush(
                    ball.getTopPoint(),
                    ball.getBottomPoint(),
                    ball.getLeftPoint(),
                    ball.getRightPoint()
            );

            if (crush == Brick.CrushType.HorizontalCrush) {
                ball.bounceHorizontally();
                iterator.remove();
                break;
            }

            if (crush == Brick.CrushType.VerticalCrush) {
                ball.bounceVertically();
                iterator.remove();
                break;
            }
        }
    }

    private void loadLevel() {
        Brick.setGridSize(10, 20);

        Color[] colors = {
                Color.web("#E0AAFF"),
                Color.web("#C77DFF"),
                Color.web("#9D4EDD"),
                Color.web("#7B2CBF"),
                Color.web("#5A189A"),
                Color.web("#3C096C")
        };

        for (int row = 2; row <= 7; row++) {
            for (int col = 0; col< 10; col++) {
                bricks.add(new Brick(col, row, colors[row-2]));
            }
        }

    }


}
