package org.example.auth;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.example.database.DatabaseConnection;

import javax.naming.AuthenticationException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Account {
    protected final int id;
    protected final String username;

    public Account(int id, String username) {
        this.id = id;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }

    public static class Persistence {
        public static void init() {
            try {
                String createSQLTable = "CREATE TABLE IF NOT EXISTS account( " +
                        "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                        "username TEXT NOT NULL UNIQUE," + // Added UNIQUE rule to ensure database sanity
                        "password TEXT NOT NULL)";
                PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(createSQLTable);
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        public static int register(String username, String password) throws SQLException {
            String hashedPassword = BCrypt.withDefaults().hashToString(12, password.toCharArray());
            String insertSQL = "INSERT INTO account(username, password) VALUES (?, ?)";

            // CRITICAL FIX: Pass Statement.RETURN_GENERATED_KEYS to access auto-incrementing row keys
            PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, username);
            statement.setString(2, hashedPassword);
            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            if(resultSet.next())
                return resultSet.getInt(1);
            else throw new SQLException("Failed to retrieve auto-generated ID sequence");
        }

        public static Account authenticate(String username, String password) throws AuthenticationException {
            try {
                String sql = "SELECT id, username, password FROM account WHERE username = ?";
                PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql);
                statement.setString(1, username);

                ResultSet result = statement.executeQuery();
                if (!result.next()) {
                    throw new AuthenticationException("No such user");
                }

                String hashedPassword = result.getString(3);
                boolean okay = BCrypt.verifyer().verify(password.toCharArray(), hashedPassword.toCharArray()).verified;

                if (!okay) {
                    throw new AuthenticationException("Wrong password");
                }

                return new Account(result.getInt(1), result.getString(2));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}