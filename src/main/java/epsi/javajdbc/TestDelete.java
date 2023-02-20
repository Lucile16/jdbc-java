package epsi.javajdbc;

import java.sql.*;
import java.util.ResourceBundle;

public class TestDelete {
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
             PreparedStatement stmt = cnx.prepareStatement("DELETE FROM fournisseur WHERE ID = ?")) {
            cnx.setAutoCommit(false);
            stmt.setInt(1, 3);
            int ex = stmt.executeUpdate();
            if (ex == 1) {
                System.out.println("Suppression réussie !");
            }
            cnx.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}