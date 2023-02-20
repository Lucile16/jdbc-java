package epsi.javajdbc;

import java.sql.*;
import java.util.ResourceBundle;

public class TestUpdate {
    private static final String url;
    private static final String user;
    private static final String pwd;

    static {
        ResourceBundle bundle = ResourceBundle.getBundle("db");
        url = bundle.getString("db.url");
        user = bundle.getString("db.login");
        pwd = bundle.getString("db.password");
    }

    public static void main(String[] args) {
        try (Connection cnx = DriverManager.getConnection(url, user, pwd);
             PreparedStatement stmt = cnx.prepareStatement("UPDATE fournisseur SET NOM = ? WHERE ID = ?")) {
            stmt.setString(1, "L'espace création");
            stmt.setInt(2, 3);
            // OU "UPDATE fournisseur SET NOM = 'L''espace création' WHERE ID = 3"
            cnx.setAutoCommit(false);
            int ex = stmt.executeUpdate();
            if(ex == 1) {
                System.out.println("Modification réussie !");
            }
            cnx.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}