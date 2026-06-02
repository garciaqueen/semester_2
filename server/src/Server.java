import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private final ServerSocket serverSocket;
    private final ArrayList<ClientHandler> handlers = new ArrayList<>();

    public Server() throws IOException {
        serverSocket = new ServerSocket(3001);
    }

    public ArrayList<ClientHandler> getHandlers() {
        return handlers;
    }
    public synchronized void broadcast(String message) {
        for (ClientHandler handler : handlers) {
            handler.send(message);
        }
    }

    public synchronized void removeHandler(ClientHandler handler) {
        handlers.remove(handler);
    }

    public void listen() throws IOException {
        System.out.println("Server started");

        while (true) {
            Socket socket = serverSocket.accept();

            ClientHandler handler = new ClientHandler(socket, this);

            handlers.add(handler);

            new Thread(handler).start();
        }
    }
}