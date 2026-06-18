package org.example.powt2;

import client.ServerThread;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import server.Server;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;

public class Controller {
    @FXML
    Slider radiusSlider;
    @FXML
    ColorPicker colorPicker;
    @FXML
    Canvas canvas;
    @FXML
    TextField portField;
    @FXML
    TextField addressField;
    private ServerThread serverThread;
    private Server server;
    public Controller(Server server, ServerThread serverThread) {
        this.server = server;
        this.serverThread = serverThread;
        if (serverThread != null) {
            serverThread.setDot(dot -> {
                Platform.runLater(() -> {
                    drawCircle(
                            dot.x(),
                            dot.y(),
                            dot.radius(),
                            dot.color()
                    );
                });
            });
        }
    }
    public void onStartServerClicked(ActionEvent actionEvent) throws IOException, InterruptedException, SQLException {
        int port = Integer.parseInt(portField.getText());

        server = new Server(port);

        new Thread(() -> {
            try {
                if (server != null) {
                    server.listen();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();


        new Thread(() -> {

            try {

                Socket socket =
                        new Socket(addressField.getText(), port);

                serverThread = new ServerThread(socket, this);

                serverThread.setDot(dot -> {
                    Platform.runLater(() -> {
                        drawCircle(
                                dot.x(),
                                dot.y(),
                                dot.radius(),
                                dot.color()
                        );
                    });
                });

                new Thread(serverThread).start();


            } catch(IOException e) {
                e.printStackTrace();
            }

        }).start();

    }

    public void onConnectClicked(ActionEvent actionEvent) throws IOException {
        String address = addressField.getText().trim();
        int port = Integer.parseInt(portField.getText());

        Socket socket = new Socket(address, port);

        serverThread = new ServerThread(socket, this);

        serverThread.setDot(dot -> {
            Platform.runLater(() -> {
                drawCircle(
                        dot.x(),
                        dot.y(),
                        dot.radius(),
                        dot.color()
                );
            });
        });

        new Thread(serverThread).start();
//        serverThread = new ServerThread(socket, this);
//        new Thread(serverThread).start();
    }

    public void onMouseClicked(MouseEvent mouseEvent) throws IOException {
        // create a circle
        double radius = radiusSlider.getValue();
        double x = mouseEvent.getX();
        double y = mouseEvent.getY();
        Color c = colorPicker.getValue();
        if(serverThread != null){
            serverThread.send(x,y,radius,c);
        }
    }

    public void drawCircle(double x, double y, double radius, Color color) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(color);
        gc.fillOval(x-radius, y-radius, radius*2, radius*2);
        gc.save();
    }
}
