package DAO;

import Models.Transaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO extends BaseDAO {

    //CREATE
    public Transaction create(Transaction transaction) {
        String query = "INSERT INTO db_transaction (UserID, Date) VALUES (?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, transaction.getUserId());
            ps.setTimestamp(2, transaction.getDate());
            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int transactionId = generatedKeys.getInt(1);
                    transaction.setId(transactionId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transaction;
    }

    //READ
    public Transaction read(int id) {
        String query = "SELECT t.ID, t.UserID, t.Date, u.Firstname, u.Lastname FROM db_transaction t JOIN db_user u ON t.UserID = u.ID WHERE t.ID = ?";
        Transaction transaction = null;

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                //Creating the Transaction object and including User's First and Last Name
                transaction = new Transaction(
                        rs.getInt("ID"),
                        rs.getInt("UserID"),
                        rs.getString("Firstname"),
                        rs.getString("Lastname"),
                        rs.getTimestamp("Date")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transaction;
    }

    //READ ALL
    public List<Transaction> readAll() {
        String query = "SELECT t.ID, t.UserID, t.Date, u.Firstname, u.Lastname FROM db_transaction t JOIN db_user u ON t.UserID = u.ID ORDER BY t.ID ASC";
        List<Transaction> transactions = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                //Creating the Transaction object and including User's First and Last Name
                transactions.add(new Transaction(
                        rs.getInt("ID"),
                        rs.getInt("UserID"),
                        rs.getString("Firstname"),
                        rs.getString("Lastname"),
                        rs.getTimestamp("Date")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactions;
    }

    //UPDATE
    public void update(Transaction transaction) {
        String query = "UPDATE db_transaction SET UserID = ?, Date = ? WHERE ID = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, transaction.getUserId());
            ps.setTimestamp(2, transaction.getDate());
            ps.setInt(3, transaction.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //DELETE
    public void delete(int id) {
        String query = "DELETE FROM db_transaction WHERE ID = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}