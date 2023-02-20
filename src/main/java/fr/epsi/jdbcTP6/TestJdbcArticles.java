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
            Fournisseur f8 = new Fournisseur(9, "La Maison de la Peinture");
            FournisseurDaoJdbc FDJ = new FournisseurDaoJdbc();
            //FDJ.insert(f8);
            ArticleDao AD = new ArticleDao() {
                @Override
                public ArrayList<Article> extraire() throws SQLException {
                    try (Connection cnx = DriverManager.getConnection(url, user, pwd);
                         Statement stmt = cnx.createStatement()) {
                        cnx.setAutoCommit(false);
                        ArrayList<Article> lstArticles = new ArrayList<>();
                        try (ResultSet rs = stmt.executeQuery("SELECT * FROM ARTICLE")) {
                            while (rs.next()) {
                                Article a1 = new Article(rs.getInt("ID"), rs.getString("REF"), rs.getString("DESIGNATION"), rs.getDouble("PRIX"), (Fournisseur) rs.getObject("ID_FOU"));
                                lstArticles.add(a1);
                                //%n correspond saut de ligne comme "\n"
                                //%s pour une chaîne de caractères
                                //%d pour un entier
                                System.out.printf("id = %d - ref = %s - designation = %s %n",
                                        rs.getInt("ID"),
                                        rs.getString("REF"),
                                        rs.getString("DESIGNATION")
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

                }

                @Override
                public int update(String ancienneDesignation, String nouvelleDesignation) throws SQLException {
                    return 0;
                }

                @Override
                public boolean delete(Article article) throws SQLException {
                    return false;
                }
            };
            AD.extraire();
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