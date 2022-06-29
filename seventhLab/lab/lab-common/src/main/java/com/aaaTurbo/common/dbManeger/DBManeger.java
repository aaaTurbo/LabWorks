package com.aaaTurbo.common.dbManeger;

import com.aaaTurbo.common.util.classes.Coordinates;
import com.aaaTurbo.common.util.classes.Location;
import com.aaaTurbo.common.util.classes.LocationOne;
import com.aaaTurbo.common.util.classes.Route;
import com.aaaTurbo.common.util.classes.RouteGenerator;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class DBManeger {
    private static final int NAMEPOS = 1;
    private static final int COORDXPOS = 2;
    private static final int COORDYPOS = 3;
    private static final int DATEPOS = 4;
    private static final int LOCFROMXPOS = 5;
    private static final int LOCFROMYPOS = 6;
    private static final int LOCFROMNAMEPOS = 7;
    private static final int LOCTOXPOS = 8;
    private static final int LOCTOYPOS = 9;
    private static final int LOCTONAMEPOS = 10;
    private static final int DISTANCEPOS = 11;
    private static final int USERNAMEPOS = 12;
    private final String dbUrl = "jdbc:postgresql://127.0.0.1:5432/studs";
    private final String user = "s338924";
    private final String pass = "sux281";
    private RouteGenerator routeGenerator = new RouteGenerator();
    private Connection connection = DriverManager.getConnection(dbUrl, user, pass);

    public DBManeger() throws SQLException {
    }

    public HashSet<Route> loadCollection() throws Exception {
        String selectCollectionQuery = "SELECT * FROM s338924routes";
        Statement statement = connection.createStatement();
        ResultSet collectionSet = statement.executeQuery(selectCollectionQuery);
        HashSet<Route> resultSet = new HashSet<>();
        while (collectionSet.next()) {
            resultSet.add(routeGenerator.generateRouteWithIdAndTime(new String[]{
                    collectionSet.getString("id"),
                    collectionSet.getString("name"),
                    collectionSet.getString("coordX"),
                    collectionSet.getString("coordY"),
                    collectionSet.getString("creationDate"),
                    collectionSet.getString("locationFromX"),
                    collectionSet.getString("locationFromY"),
                    collectionSet.getString("locationFromName"),
                    collectionSet.getString("locationToX"),
                    collectionSet.getString("locationToY"),
                    collectionSet.getString("locationToName"),
                    collectionSet.getString("distance"),
                    collectionSet.getString("owner_id")}));
        }
        return resultSet;
    }

    public long addElement(Route route, String username) throws SQLException {
        String addQuery = "INSERT INTO s338924routes "
                + "(name, coordX, coordY, creationDate, locationFromX, "
                + "locationFromY, locationFromName, locationToX, locationToY, locationToName, distance, owner_id) "
                + "SELECT ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, id "
                + "FROM s338924users "
                + "WHERE s338924users.login = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(addQuery, Statement.RETURN_GENERATED_KEYS);
        Coordinates coordinates = route.getCoordinates();
        Location location = route.getFrom();
        LocationOne locationTo = route.getTo();
        preparedStatement.setString(NAMEPOS, route.getName());
        preparedStatement.setLong(COORDXPOS, coordinates.getX());
        preparedStatement.setInt(COORDYPOS, coordinates.getY());
        preparedStatement.setDate(DATEPOS, Date.valueOf(route.getCreationDate()));
        preparedStatement.setLong(LOCFROMXPOS, location.getX());
        preparedStatement.setInt(LOCFROMYPOS, location.getY());
        preparedStatement.setString(LOCFROMNAMEPOS, location.getName());
        preparedStatement.setDouble(LOCTOXPOS, locationTo.getX());
        preparedStatement.setFloat(LOCTOYPOS, locationTo.getY());
        preparedStatement.setString(LOCTONAMEPOS, locationTo.getName());
        preparedStatement.setLong(DISTANCEPOS, route.getDistance());
        preparedStatement.setString(USERNAMEPOS, username);
        preparedStatement.executeUpdate();
        ResultSet result = preparedStatement.getGeneratedKeys();
        result.next();
        return result.getLong(1);
    }

    public boolean removeById(long id, String username) throws SQLException {
        String removeQuery = "DELETE FROM s338924routes "
                + "USING s338924users "
                + "WHERE s338924routes.id = ? "
                + "AND s338924routes.owner_id = s338924users.id AND s338924users.login = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(removeQuery);
        preparedStatement.setLong(1, id);
        preparedStatement.setString(2, username);

        int deletedBands = preparedStatement.executeUpdate();
        return deletedBands > 0;
    }

    public boolean updateById(Route route, String username) throws SQLException {
        connection.createStatement().execute("BEGIN TRANSACTION;");
        String updateQuery = "UPDATE s338924routes "
                + "SET name = ?, "
                + "coordX = ?, "
                + "coordY = ?, "
                + "creationDate = ?, "
                + "locationFromX = ?, "
                + "locationFromY = ?, "
                + "locationFromName = ?,"
                + "locationToX = ?,"
                + "locationToY = ?,"
                + "locationToName = ?,"
                + "distance = ? "
                + "FROM s338924users "
                + "WHERE s338924routes.id = ? "
                + "AND s338924routes.owner_id = s338924users.id AND s338924users.login = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
        Coordinates coordinates = route.getCoordinates();
        Location location = route.getFrom();
        LocationOne locationTo = route.getTo();
        preparedStatement.setString(NAMEPOS, route.getName());
        preparedStatement.setLong(COORDXPOS, coordinates.getX());
        preparedStatement.setInt(COORDYPOS, coordinates.getY());
        preparedStatement.setDate(DATEPOS, Date.valueOf(route.getCreationDate()));
        preparedStatement.setLong(LOCFROMXPOS, location.getX());
        preparedStatement.setInt(LOCFROMYPOS, location.getY());
        preparedStatement.setString(LOCFROMNAMEPOS, location.getName());
        preparedStatement.setDouble(LOCTOXPOS, locationTo.getX());
        preparedStatement.setFloat(LOCTOYPOS, locationTo.getY());
        preparedStatement.setString(LOCTONAMEPOS, locationTo.getName());
        preparedStatement.setLong(DISTANCEPOS, route.getDistance());
        preparedStatement.setLong(USERNAMEPOS, route.getId());
        preparedStatement.setString(USERNAMEPOS + 1, username);
        int updatedRows = preparedStatement.executeUpdate();
        connection.createStatement().execute("COMMIT;");
        return updatedRows > 0;
    }

    public List<Long> clear(String username) throws SQLException {
        String clearQuery = "DELETE FROM s338924routes "
                + "USING s338924users "
                + "WHERE s338924routes.owner_id = s338924users.id AND s338924users.login = ? "
                + "RETURNING s338924routes.id;";
        PreparedStatement preparedStatement = connection.prepareStatement(clearQuery);
        preparedStatement.setString(1, username);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Long> resultingList = new ArrayList<>();
        while (resultSet.next()) {
            resultingList.add(resultSet.getLong("id"));
        }
        return resultingList;
    }

    public void addUser(String username, String password) throws SQLException {
        String addUserQuery = "INSERT INTO s338924users (login, password) "
                + "VALUES (?, ?);";
        PreparedStatement preparedStatement = connection.prepareStatement(addUserQuery,
                Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, username);
        String passwd = DigestUtils.md5Hex(password);
        preparedStatement.setString(2, passwd);

        preparedStatement.executeUpdate();
    }

    public String getPassword(String username) throws SQLException {
        String getPasswordQuery = "SELECT (password) "
                + "FROM s338924users "
                + "WHERE s338924users.login = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(getPasswordQuery);
        preparedStatement.setString(1, username);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getString("password");
        }
        return null;
    }

    public boolean validateUser(String username, String password) throws SQLException {
        String passwd = DigestUtils.md5Hex(password);
        return passwd.equals(getPassword(username));
    }

}
