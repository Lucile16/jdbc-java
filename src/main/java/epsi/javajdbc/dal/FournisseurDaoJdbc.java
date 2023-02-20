package epsi.javajdbc.dal;

import epsi.javajdbc.bo.Fournisseur;

import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class FournisseurDaoJdbc implements FournisseurDao {

    private static final String url;
    private static final String user;
    private static final String pwd;

    static {
        ResourceBundle bundle = ResourceBundle.getBundle("db");
        url = bundle.getString("db.url");
        user = bundle.getString("db.login");
        pwd = bundle.getString("db.password");
    }

    @Override
    public ArrayList<Fournisseur> extraire() throws SQLException {
        try (Connection cnx = DriverManager.getConnection(url, user, pwd);
             Statement stmt = cnx.createStatement()) {
            cnx.setAutoCommit(false);
            ArrayList<Fournisseur> lstFournisseurs = new ArrayList<>();
            try (ResultSet rs = stmt.executeQuery("SELECT * FROM FOURNISSEUR")) {
                while (rs.next()) {
                    Fournisseur f1 = new Fournisseur(rs.getInt("ID"), rs.getString("NOM"));
                    lstFournisseurs.add(f1);
                    //%n correspond saut de ligne comme "\n"
                    //%s pour une chaîne de caractères
                    //%d pour un entier
                    System.out.printf("id = %d - nom = %s %n",
                            rs.getInt("ID"),
                            rs.getString("NOM")
                    );
                }
                System.out.printf("Nom du ou des fournisseur(s) : %n");
                for (Fournisseur fournisseur : lstFournisseurs) {
                    System.out.println(fournisseur);
                }
                cnx.commit();
            } catch (SQLException e) {
                cnx.rollback();
                throw new RuntimeException(e);
            }
            return lstFournisseurs;
        }
    }

    @Override
    public void insert(Fournisseur fournisseur) throws SQLException {
        try (Connection cnx = DriverManager.getConnection(url, user, pwd);
             Statement stmt = cnx.createStatement()) {
            cnx.setAutoCommit(false);
            int res = stmt.executeUpdate("INSERT INTO fournisseur (NOM) VALUES ('" + fournisseur.getNom() + "')");
            if (res == 1) {
                System.out.println("Insertion réussie !");
            }
            cnx.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int update(String ancienNom, String nouveauNom) throws SQLException {
        try (Connection cnx = DriverManager.getConnection(url, user, pwd);
             Statement stmt = cnx.createStatement()) {
            int res = stmt.executeUpdate("UPDATE fournisseur SET NOM = '" + nouveauNom + "' WHERE NOM = '" + ancienNom + "'");
            if (res == 1) {
                System.out.println("Modification réussie !");
            }
            cnx.setAutoCommit(false);
            try (ResultSet rs = stmt.executeQuery("SELECT * FROM FOURNISSEUR")) {
                while (rs.next()) {
                    //%n correspond saut de ligne comme "\n"
                    //%s pour une chaîne de caractères
                    //%d pour un entier
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
            return res;
        }
    }

    @Override
    public boolean delete(Fournisseur fournisseur) throws SQLException {
        try (Connection cnx = DriverManager.getConnection(url, user, pwd);
             Statement stmt = cnx.createStatement()) {
            int res = stmt.executeUpdate("DELETE FROM fournisseur WHERE ID = 6");
            boolean bool = false;
            if (res == 1) {
                System.out.println("Suppression réussie !");
            }
            cnx.setAutoCommit(false);
            try (ResultSet rs = stmt.executeQuery("SELECT * FROM FOURNISSEUR")) {
                while (rs.next()) {
                    //%n correspond saut de ligne comme "\n"
                    //%s pour une chaîne de caractères
                    //%d pour un entier
                    System.out.printf("id = %d - nom = %s %n",
                            rs.getInt("ID"),
                            rs.getString("NOM")
                    );
                }
                cnx.commit();
                bool = true;
            } catch (SQLException e) {
                cnx.rollback();
                throw new RuntimeException(e);
            }
            return bool;
        }
    }
}
