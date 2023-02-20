package epsi.javajdbc;

import epsi.javajdbc.bo.Fournisseur;

import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TestSelect {
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
            cnx.setAutoCommit(false);
            try (ResultSet rs = stmt.executeQuery()) {
                ArrayList<Fournisseur> lstFournisseurs = new ArrayList<>();
                while (rs.next()) {
                    Fournisseur f1 = new Fournisseur(rs.getInt("ID"), rs.getString("NOM"));
                    lstFournisseurs.add(f1);
                    System.out.printf("id = %d - nom = %s %n",
                            rs.getInt("ID"),
                            rs.getString("NOM")
                    );
                }
                cnx.commit();
            } catch (SQLException e) {
                cnx.rollback();
                throw new RuntimeException(e);
            }
        }
    }
}