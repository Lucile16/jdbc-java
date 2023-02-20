package epsi.javajdbcTP5.dal;

import epsi.javajdbcTP5.bo.Fournisseur;

import java.sql.SQLException;
import java.util.ArrayList;

public interface FournisseurDao {

        ArrayList<Fournisseur> extraire() throws SQLException;
        void insert(Fournisseur fournisseur) throws SQLException;
        int update(String ancienNom, String nouveauNom) throws SQLException;
        boolean delete(Fournisseur fournisseur) throws SQLException;
}
