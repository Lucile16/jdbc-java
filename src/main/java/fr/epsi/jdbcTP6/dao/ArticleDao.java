package fr.epsi.jdbcTP6.dao;

import epsi.javajdbcTP5.bo.Fournisseur;
import fr.epsi.jdbcTP6.entites.Article;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface ArticleDao {
    ArrayList<Article> extraire() throws SQLException;
    void insert(Article article) throws SQLException;
    int update(String designation) throws SQLException;
    boolean delete(String designation) throws SQLException;
    double moyenne() throws SQLException;
}
