package fr.epsi.jdbcTP6.dao;

import epsi.javajdbcTP5.bo.Fournisseur;
import fr.epsi.jdbcTP6.entites.Article;

import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ArticleDaoJdbc implements ArticleDao{

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
    public ArrayList<Article> extraire() throws SQLException {
        try (Connection cnx = DriverManager.getConnection(url, user, pwd);
             Statement stmt = cnx.createStatement()) {
            cnx.setAutoCommit(false);
            ArrayList<Article> lstArticles = new ArrayList<>();
            try (ResultSet rs = stmt.executeQuery("SELECT * FROM ARTICLE")) {
                while (rs.next()) {
                    Fournisseur f1 = new Fournisseur(rs.getInt("ID_FOU"));
                    Article a1 = new Article(rs.getInt("ID"), rs.getString("REF"), rs.getString("DESIGNATION"), rs.getDouble("PRIX"), f1);
                    lstArticles.add(a1);
                    //%n correspond saut de ligne comme "\n"
                    //%s pour une chaîne de caractères
                    //%d pour un entier
                    System.out.printf("id = %d - ref = %s - designation = %s - prix = %.2f - id du fournisseur = %d %n",
                            rs.getInt("ID"),
                            rs.getString("REF"),
                            rs.getString("DESIGNATION"),
                            rs.getDouble("PRIX"),
                            rs.getInt("ID_FOU")
                    );
                }
                cnx.commit();
            } catch (SQLException e) {
                cnx.rollback();
                throw new RuntimeException(e);
            }
            return lstArticles;
        }
    }

    @Override
    public void insert(Article article) throws SQLException {
        try (Connection cnx = DriverManager.getConnection(url, user, pwd);
             Statement stmt = cnx.createStatement()) {
            cnx.setAutoCommit(false);
            int res = stmt.executeUpdate("INSERT INTO article (REF, DESIGNATION, PRIX, ID_FOU) VALUES ('" + article.getRef() + "', '" + article.getDesignation() + "', '" + article.getPrix() + "', '" + article.getFournisseur().getId() + "')");
            if (res == 1) {
                System.out.println("Insertion réussie !");
            }
            cnx.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int update(String designation) throws SQLException {
        try (Connection cnx = DriverManager.getConnection(url, user, pwd);
             Statement stmt = cnx.createStatement()) {
            cnx.setAutoCommit(false);
            int res = stmt.executeUpdate("UPDATE article SET PRIX = PRIX * 0.75 WHERE DESIGNATION LIKE '%" + designation + "%'");
            if (res != 0) {
                System.out.println("Modification(s) réussie(s) !");
            }
            cnx.commit();
            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(String designation) throws SQLException {
        try (Connection cnx = DriverManager.getConnection(url, user, pwd);
             Statement stmt = cnx.createStatement()) {
            cnx.setAutoCommit(false);
            int res = stmt.executeUpdate("DELETE FROM article WHERE DESIGNATION LIKE '%" + designation + "%'");
            boolean bool = false;
            if (res != 0) {
                System.out.println("Suppression réussie !");
                bool = true;
            }
            cnx.commit();
            return bool;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public double moyenne() throws SQLException {
        double moyenne = 0;
        try (Connection cnx = DriverManager.getConnection(url, user, pwd);
             Statement stmt = cnx.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT AVG(PRIX) as moyenne FROM ARTICLE")) {
            while (rs.next()) {
                System.out.println("Moyenne réussie !");
                System.out.println(rs.getDouble("moyenne"));
                moyenne = rs.getDouble("moyenne");
            }
            return moyenne;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
