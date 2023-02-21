package fr.epsi.jdbcTP6;

import epsi.javajdbcTP5.bo.Fournisseur;
import epsi.javajdbcTP5.dal.FournisseurDaoJdbc;
import fr.epsi.jdbcTP6.dao.ArticleDao;
import fr.epsi.jdbcTP6.entites.Article;

import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TestJdbcArticles {
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
        try {
            Fournisseur fournisseur = new Fournisseur(8, "La Maison de la Peinture");
            FournisseurDaoJdbc FDJ = new FournisseurDaoJdbc();
            //FDJ.insert(fournisseur);
            ArticleDao AD = new ArticleDao() {
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
                        if (res == 1) {
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
                    try (Connection cnx = DriverManager.getConnection(url, user, pwd);
                         Statement stmt = cnx.createStatement();
                         ResultSet rs = stmt.executeQuery("SELECT AVG(PRIX) as moyenne FROM ARTICLE")) {
                        if (rs.next()) {
                            System.out.println("Moyenne réussie !");
                            double moyenne = rs.getDouble("moyenne");
                            //System.out.println(moyenne);
                            return moyenne;
                        } else {
                            throw new RuntimeException("Impossible de calculer la moyenne des prix des articles.");
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            };

            Article article1 = new Article("REFA", "Peinture blanche 1L", 12.50, fournisseur);
            Article article2 = new Article("REFB", "Peinture rouge mate 1L", 15.50, fournisseur);
            Article article3 = new Article("REFC", "Peinture noire laquée 1L", 17.80, fournisseur);
            Article article4 = new Article("REFD", "Peinture bleue mate 1L", 15.50, fournisseur);
            //AD.update("mate");
            AD.extraire();
            AD.moyenne();
            //AD.delete("Peinture");
            //FDJ.delete(fournisseur);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

//Utilisez la DAO FournisseurDao pour insérer un fournisseur appelé « La Maison de la Peinture »
//Utilisez la DAO ArticleDao pour insérer 4 articles dans la table des articles en les associant au fournisseur
//« La Maison de la Peinture » :
//o Un article dont la désignation est « Peinture blanche 1L » ă 12.5€
//o Un article dont la désignation est « Peinture rouge mate 1L » ă 15.5€
//o Un article dont la désignation est « Peinture noire laquée 1L » ă 17.8€
//o Un article dont la désignation est « Peinture bleue mate 1L » de 15.5€
// Utilisez la DAO pour diminuer le prix de toutes les peintures mates de 25%
// Utilisez la DAO pour afficher la liste de tous les articles.
// Utilisez la DAO pour exécuter une requête qui extrait la moyenne des prix des articles
//et affiche cette moyenne. Attention la moyenne est effectuée par la base et non en Java !!!
// Utilisez les DAO pour :
//o supprimer tous les articles dont le nom contient « Peinture » de la base de
//données.
//o supprimer le fournisseur « La Maison de la Peinture »