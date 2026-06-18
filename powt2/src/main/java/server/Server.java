package server;


import javafx.scene.paint.Color;
import org.example.powt2.Dot;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private ServerSocket serverSocket;
    private List<ClientThread> clients;
    private Connection connection;

    public Server(int port) throws IOException, SQLException {
        serverSocket = new ServerSocket(port);
        clients = new ArrayList<>();
        // create db
        connection = DriverManager.getConnection("jdbc:sqlite:dots.db");
        Statement st = connection.createStatement();

        st.execute("""
            CREATE TABLE IF NOT EXISTS dot(
            id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
            x INTEGER NOT NULL,
            y INTEGER NOT NULL,
            radius INTEGER NOT NULL,
            color TEXT NOT NULL
            )""");
    }

    public void saveDot(Dot dot) throws SQLException {
        String sql = "INSERT INTO dot(x, y, radius, color) VALUES(?,?,?,?)";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setDouble(1, dot.x());
        ps.setDouble(2, dot.y());
        ps.setDouble(3, dot.radius());
        ps.setString(4, dot.color().toString());


        ps.executeUpdate();

    }

    public List<Dot> getSavedDots() throws SQLException {

        List<Dot> dots = new ArrayList<>();

        Statement st = connection.createStatement();

        ResultSet rs = st.executeQuery(
                "SELECT x,y,color,radius FROM dot"
        );

        while(rs.next()) {

            Dot dot = new Dot(
                    rs.getDouble("x"),
                    rs.getDouble("y"),
                    rs.getDouble("radius"),
                    Color.valueOf(rs.getString("color"))
            );

            dots.add(dot);
        }

        return dots;
    }

    public void broadcast(Dot dot) throws SQLException {
        saveDot(dot);

        for (ClientThread client : clients) {
            client.send(Dot.toMessage(dot.x(), dot.y(), dot.radius(), dot.color()));
        }
    }

    public void listen() throws IOException {
        System.out.println("Server is listening...");
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                ClientThread client = new ClientThread(socket, this);
                clients.add(client);
                new Thread(client).start();
                for(Dot dot : getSavedDots()) {
                    client.send(Dot.toMessage(dot.x(), dot.y(), dot.radius(), dot.color()));
                }

            } catch (IOException | SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
