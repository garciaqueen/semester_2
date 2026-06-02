import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final BufferedReader reader;
    private final PrintWriter writer;
    private final Server server;
    private String login;

    public ClientHandler(Socket socket, Server server) throws IOException {
        this.socket = socket;
        this.server = server;

        InputStream input = socket.getInputStream();
        OutputStream output = socket.getOutputStream();

        reader = new BufferedReader(new InputStreamReader(input));
        writer = new PrintWriter(output, true);
    }

    public void send(String message) {
        writer.println(message);
    }

    private void close() throws IOException {
        socket.close();
        server.removeHandler(this);
    }

    @Override
    public void run() {
        System.out.println("Client connected");
        // somewhere here
        try {
            String message;

            while ((message = reader.readLine()) != null) {
                if (message.startsWith("JOINED")) {
                    login = message.substring(6);

                    server.broadcast("[SERVER] " + login + " joined the chat");
                    continue;
                }
                if (message.startsWith("/online")) {
                    // get all online users, all handlers
                    for (ClientHandler client : server.getHandlers()) {
                        send("Online: " + client.login);
                    }
                    continue;
                }
                if (message.startsWith("/w ")) {
                    String[] parts = message.split(" ", 3);
                    if (parts.length < 3) {
                        send("Usage: /w recipient message");
                        return;
                    }
                    String recipient = parts[1];
                    String msg = parts[2];
                    boolean found = false;
                    for (ClientHandler client : server.getHandlers()) {
                        if (client.login != null && client.login.equalsIgnoreCase(recipient)) {
                            client.send(login + " (private message): " + msg);
                            send("Privately sent to " + client.login + ": " + msg);
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        send(recipient + " is not online.");
                    }
                    continue;
                }
                server.broadcast(login + ": " + message);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            server.broadcast("[SERVER] " + login + " left the chat");
            try {
                socket.close();
                close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Client disconnected");
    }
}