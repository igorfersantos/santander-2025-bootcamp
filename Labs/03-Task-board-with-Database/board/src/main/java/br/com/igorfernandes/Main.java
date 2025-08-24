package br.com.igorfernandes;

import br.com.igorfernandes.persistence.migration.MigrationStrategy;
import br.com.igorfernandes.ui.MainMenu;

import java.sql.SQLException;

import static br.com.igorfernandes.persistence.config.ConnectionConfig.getConnection;


public class Main {

    public static void main(String[] args) throws SQLException {
        try(var connection = getConnection()){
            new MigrationStrategy(connection).executeMigration();
        }
        new MainMenu().execute();
    }

}
