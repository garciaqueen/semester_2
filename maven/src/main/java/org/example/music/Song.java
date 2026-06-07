package org.example.music;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public record Song(String artist, String title, int seconds) {

    public static class Persistance {
        //connect
        private static Connection connection;
        public void connect() throws Exception {
            URL url = Song.class.getClassLoader().getResource("songs.db");

            String dbPath = Paths.get(url.toURI()).toString();

            this.connection =
                    DriverManager.getConnection("jdbc:sqlite:" + dbPath);

        }
        //disconnect
        public static void disconnect() throws SQLException {
            if (connection != null) {
                connection.close();
                connection = null;
            }
        }
        public static Optional<Song> read(int index) throws Exception {
            String sql = "SELECT * FROM song ORDER BY id LIMIT 1 OFFSET ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, index);

            ResultSet rs = stmt.executeQuery();

            Optional<Song> result;

            if (rs.next()) {
                Song song = new Song(
                        rs.getString("artist"),
                        rs.getString("title"),
                        rs.getInt("length")
                );
                result = Optional.of(song);
            } else {
                result = Optional.empty();
            }

            disconnect();
            return result;
        }
    }
}
