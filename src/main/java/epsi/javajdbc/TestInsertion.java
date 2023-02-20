package epsi.javajdbc;

import java.sql.*;
import java.util.ResourceBundle;

public class TestInsertion {
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
             PreparedStatement stmt = cnx.prepareStatement("INSERT INTO fournisseur (NOM) VALUES (?)")) {
            cnx.setAutoCommit(false);
            stmt.setString(1, "La maison de la peinture");
            int res = stmt.executeUpdate();
            if (res == 1) {
                System.out.println("Insertion réussie !");
            }
            cnx.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}