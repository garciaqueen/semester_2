package client;

import javafx.application.Platform;
import javafx.scene.paint.Color;
import org.example.powt2.Controller;
import org.example.powt2.Dot;

import java.io.*;
import java.net.Socket;
import java.util.function.Consumer;

public class ServerThread implements Runnable {
    private Socket socket;
    private PrintWriter pw;
    private BufferedReader br;
    private Controller controller;
    private Consumer<Dot> dotConsumer;

    public ServerThread(Socket socket, Controller controller) throws IOException {
        this.socket = socket;
        this.controller = controller;
        pw = new PrintWriter(socket.getOutputStream(), true);
        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void connect(String host, int port) throws IOException {
        this.socket = new Socket(host, port);
        this.br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.pw = new PrintWriter(socket.getOutputStream(), true);
    }

    public void setDot(Consumer<Dot> dotConsumer) {
        this.dotConsumer = dotConsumer;
    }

    public void send(double x, double y, double radius, Color color) {
        String msg = "Parameters:"+ x+ ":" + y +":"+radius+":"+color.toString();
        pw.println(msg);
    }

    @Override
    public void run() {
        try {
            String msgFromServer;

            while ((msgFromServer = br.readLine()) != null) {
                Dot dot = Dot.fromMessage(msgFromServer);

                if (dotConsumer != null) {
                    Platform.runLater(() -> {
                        dotConsumer.accept(dot);
                    });
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
