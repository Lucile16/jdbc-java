package fr.epsi.jdbcTP6;

import epsi.javajdbcTP5.bo.Fournisseur;
import epsi.javajdbcTP5.dal.FournisseurDaoJdbc;
import fr.epsi.jdbcTP6.dao.ArticleDao;
import fr.epsi.jdbcTP6.dao.ArticleDaoJdbc;
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
            ArticleDaoJdbc ADJ = new ArticleDaoJdbc();

            Article article1 = new Article("REFA", "Peinture blanche 1L", 12.50, fournisseur);
            Article article2 = new Article("REFB", "Peinture rouge mate 1L", 15.50, fournisseur);
            Article article3 = new Article("REFC", "Peinture noire laquée 1L", 17.80, fournisseur);
            Article article4 = new Article("REFD", "Peinture bleue mate 1L", 15.50, fournisseur);
            //ADJ.update("mate");
            //ADJ.extraire();
            ADJ.moyenne();
            //ADJ.delete("Peinture");
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