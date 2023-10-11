package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckExistence {
    private static final String query = "SELECT COUNT(*) AS username FROM card WHERE username = ? LIMIT 1";

    public static boolean existenceCheck(String username) {
        try {
            Connection connection = JDBCConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt("username");
                if (count != 0) {
                    return true;
                }
                return false;
            }
            JDBCConnection.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

