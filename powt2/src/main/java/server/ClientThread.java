package server;

import org.example.powt2.Dot;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;

public class ClientThread implements Runnable {
    private final Socket socket;
    private BufferedReader br;
    private PrintWriter pw;
    private Server server;

    public ClientThread(Socket socket, Server server) throws IOException {
        this.socket = socket;
        this.server = server;
        pw = new PrintWriter(socket.getOutputStream(), true);
        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void send(String msg) {
        pw.println(msg);
    }


    @Override
    public void run() {
        while (true) {
            String msg;
            try {
                if ((msg = br.readLine()) != null) {
                    server.broadcast(Dot.fromMessage(msg));
                }
            } catch (IOException | SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


}
