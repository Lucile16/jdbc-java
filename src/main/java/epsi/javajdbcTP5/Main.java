package epsi.javajdbcTP5;

import java.sql.*;
import java.util.ResourceBundle;

public class Main {
    private static final String url;
    private static final String user;
    private static final String pwd;

    static {
        ResourceBundle bundle = ResourceBundle.getBundle("db");
        url = bundle.getString("db.url");
        user = bundle.getString("db.login");
        pwd = bundle.getString("db.password");
    }

    public static void main(String[] args) throws SQLException {
        try (Connection cnx = DriverManager.getConnection(url, user, pwd);
             PreparedStatement stmt = cnx.prepareStatement("SELECT * FROM FOURNISSEUR")) {
            try(ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    System.out.printf("id = %d - nom = %s %n",
                            rs.getInt("ID"),
                            rs.getString("NOM")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}