package com.aaaTurbo.server.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBLocalConnector {
    private final String dbUrl = "jdbc:postgresql://127.0.0.1:5432/studs";
    private final String user = "s338924";
    private final String pass = "sux281";

    public DBLocalConnector() {
        try {
            Class.forName("org.postgresql.Driver");
            initializeDB();
        } catch (ClassNotFoundException e) {
            System.out.println("No DB driver!");
            System.exit(1);
        } catch (SQLException e) {
            System.out.println("Error occurred during initializing tables!" + e.getMessage());
            System.exit(1);
        }
    }

    private void initializeDB() throws SQLException {
        Connection connection = DriverManager.getConnection(dbUrl, user, pass);

        Statement statement = connection.createStatement();

        statement.execute("CREATE SEQUENCE IF NOT EXISTS s338924routes_id_seq INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1");

        statement.execute("CREATE SEQUENCE IF NOT EXISTS s338924users_id_seq INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1");

        statement.execute("CREATE TABLE IF NOT EXISTS s338924users "
                + "("
                + "login varchar(255) NOT NULL UNIQUE CHECK(login<>''),"
                + "password varchar(255) NOT NULL CHECK(password<>''),"
                + "id bigint NOT NULL PRIMARY KEY DEFAULT nextval('s338924users_id_seq')"
                + ");");

        statement.execute("CREATE TABLE IF NOT EXISTS s338924routes"
                + "("
                + "id integer NOT NULL PRIMARY KEY DEFAULT nextval('s338924routes_id_seq'),"
                + "name varchar(100) NOT NULL CHECK(name<>''),"
                + "coordX bigint NOT NULL,"
                + "coordY integer ,"
                + "creationDate date NOT NULL,"
                + "locationFromX bigint,"
                + "locationFromY integer ,"
                + "locationFromName varchar(255) NOT NULL,"
                + "locationToX double precision ,"
                + "locationToY real NOT NULL,"
                + "locationToName varchar(382) NOT NULL,"
                + "distance bigint NOT NULL,"
                + "owner_id integer NOT NULL REFERENCES s338924users (id)"
                + ");");

        connection.close();
    }
}
