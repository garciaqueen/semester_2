import java.io.*;
import java.net.Socket;

public class Client implements Runnable {
    private final Socket socket;
    private final BufferedReader reader;
    private final PrintWriter writer;
    private String name;
    public Client(String address, int port, String name) throws IOException {
        this.name = name;
        socket = new Socket(address, port);
        InputStream input = socket.getInputStream();
        OutputStream output = socket.getOutputStream();
        reader = new BufferedReader(new InputStreamReader(input));
        writer = new PrintWriter(output, true);
    }
    @Override
    public void run() {
        try {
            String message;
            while ((message = reader.readLine()) != null)
                System.out.println(message);
        } catch (IOException e) { e.printStackTrace(); }
    }


    public void send(String message) {
        writer.println(message);
    }

    public static void main(String[] args) throws Exception {

        BufferedReader console =
                new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Enter login: ");
        String login = console.readLine();

        Client client = new Client("localhost", 3001, login);
        new Thread(client).start();

        client.send("JOINED" + login);

        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            String message = reader.readLine();
            client.send(message);
        }
    }

}
