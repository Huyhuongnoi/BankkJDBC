package dao.Implement;

import dao.CardDAO;
import dao.JDBCConnection;
import model.Card;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CardDAOImpl implements CardDAO<Card> {
    private static final String tableName = "card";
    private static final String username = "username";
    private static final String password = "passWord";
    private static final String balance = "balance";
    private static final String insertCard = "INSERT INTO %s(%s, %s, %s)VALUES (?, ?, ?)";

    private static final String updateCard = "UPDATE %s SET %s = ?, %s = ? WHERE %s = ?";

    private static final String deleteCard = "DELETE FROM %s WHERE %s = ?";
    private static final String selectCard = "SELECT * FROM %s";
    private static final String selectById = "SELECT * FROM %s WHERE %s = ?";

    public static CardDAOImpl getInstance() {
        return new CardDAOImpl();
    }

    @Override
    public void insert(Card card) {
        Connection connection = null;
        try {
            connection = JDBCConnection.getConnection();
            String sql = insertCard.formatted(tableName, username, password, balance);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, card.getUsername());
            preparedStatement.setString(2, card.getPassword());
            preparedStatement.setFloat(3, card.getBalance());
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
    public void update(Card card) {
        Connection connection = null;
        try {
            connection = JDBCConnection.getConnection();
            String sql = updateCard.formatted(tableName, password, balance, username);
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, card.getPassword());
            preparedStatement.setFloat(2, card.getBalance());
            preparedStatement.setString(3, card.getUsername());
            int result = preparedStatement.executeUpdate();
            connection.commit();
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
            String sql = deleteCard.formatted(tableName, username);
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
    public ArrayList<Card> selectAll() {
        Connection connection = null;
        ArrayList<Card> cardArrayList = new ArrayList<Card>();
        try {
            connection = JDBCConnection.getConnection();
            String sql = selectCard.formatted(tableName);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String username = resultSet.getString(CardDAOImpl.username);
                String password = resultSet.getString(CardDAOImpl.password);
                float balance = resultSet.getFloat(CardDAOImpl.balance);
                Card card = new Card(username, password, balance);
                cardArrayList.add(card);
            }
            if (cardArrayList != null) {
                System.out.println("select successfully!");
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
        return cardArrayList;
    }

    @Override
    public Card selectById(String id) {
        Connection connection = null;
        Card card = null;
        try {
            connection = JDBCConnection.getConnection();
            String sql = selectById.formatted(tableName, username);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String username = resultSet.getString(CardDAOImpl.username);
                String password = resultSet.getString(CardDAOImpl.password);
                float balance = resultSet.getFloat(CardDAOImpl.balance);
                card = new Card(username, password, balance);
            }
            if (card != null) {
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
        return card;
    }

}