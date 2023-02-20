package epsi.javajdbcTP5;

import epsi.javajdbcTP5.dal.FournisseurDaoJdbc;

import java.sql.*;
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
        try {
            FournisseurDaoJdbc FDJ = new FournisseurDaoJdbc();
            FDJ.extraire();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}