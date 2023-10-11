package dao.Implement;

import dao.JDBCConnection;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAOImpl implements dao.UserDAO<User> {
    private final String tableName = "user";
    private final String username = "username";
    private final String fullName = "fullName";
    private final String age = "age";
    private final String sex = "sex";
    private final String address = "address";
    private static final String insertUser = "INSERT INTO %s(%s, %s, %s, %s, %s)VALUES (?, ?, ?, ?, ?)";
    private static final String updateUser = "UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ?";
    private static final String deleteUser = "DELETE FROM %s WHERE %s = ?";
    private static final String selectAll = "SELECT * FROM %s";
    private static final String selectById = "SELECT * FROM %s WHERE %s = ?";

    public static UserDAOImpl getInstance() {
        return new UserDAOImpl();
    }

    @Override
    public void insert(User user) {
        Connection connection = null;
        try {
            connection = JDBCConnection.getConnection();
            String sql = insertUser.formatted(tableName, username, fullName, age, sex, address);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getFullName());
            preparedStatement.setInt(3, user.getAge());
            preparedStatement.setString(4, user.getSex());
            preparedStatement.setString(5, user.getAddress());
            int result = preparedStatement.executeUpdate();
            if (result != 0) {
                System.out.println("added successfully!");
            }
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("Error closing");
                }
            }
        }
    }

    @Override
    public void update(User user) {
        Connection connection = null;
        try {
            connection = JDBCConnection.getConnection();
            String sql = updateUser.formatted(tableName, fullName, age, sex, address, username);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getFullName());
            preparedStatement.setInt(2, user.getAge());
            preparedStatement.setString(3, user.getSex());
            preparedStatement.setString(4, user.getAddress());
            preparedStatement.setString(5, user.getUsername());
            int result = preparedStatement.executeUpdate();
            if (result != 0) {
                System.out.println("update successfully!");
            }
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("Error closing");
                }
            }
        }
    }

    @Override
    public void delete(String id) {
        Connection connection = null;
        try {
            connection = JDBCConnection.getConnection();
            String sql = deleteUser.formatted(tableName, username);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            int result = preparedStatement.executeUpdate();
            if (result != 0) {
                System.out.println("delete successfully!");
            }
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("Error closing");
                }
            }
        }
    }

    @Override
    public ArrayList<User> selectAll() {
        Connection connection = null;
        ArrayList<User> userArrayList = new ArrayList<User>();
        try {
            connection = JDBCConnection.getConnection();
            String sql = selectAll.formatted(tableName);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String username = resultSet.getString(this.username);
                String fullName = resultSet.getString(this.fullName);
                int age = resultSet.getInt(this.age);
                String sex = resultSet.getString(this.sex);
                String address = resultSet.getString(this.address);
                User user = new User(username, fullName, age, sex, address);
                userArrayList.add(user);
            }
            if (userArrayList != null) {
                System.out.println("select successfully!");
            }
            JDBCConnection.closeConnection(connection);
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("Error closing");
                }
            }
        }
        return userArrayList;
    }

    @Override
    public User selectById(String id) {
        Connection connection = null;
        User user = null;
        try {
            connection = JDBCConnection.getConnection();
            String sql = selectById.formatted(tableName, username);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String username = resultSet.getString(this.username);
                String fullName = resultSet.getString(this.fullName);
                int age = resultSet.getInt(this.age);
                String sex = resultSet.getString(this.sex);
                String address = resultSet.getString(this.address);
                user = new User(username, fullName, age, sex, address);

            }
            if (user != null) {
                System.out.println("select by id successfully!");
            }
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("Error closing");
                }
            }
        }
        return user;
    }
}