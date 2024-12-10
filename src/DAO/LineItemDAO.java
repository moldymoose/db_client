package DAO;

import model.LineItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LineItemDAO extends BaseDAO {

    //CREATE
    public LineItem create(LineItem lineItem) {
        String query = "INSERT INTO db_lineitem (ProductID, TransactionID, DiscountID) VALUES (?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, lineItem.getProductId());
            ps.setInt(2, lineItem.getTransactionId());

            //accounts for possible null discount
            if (lineItem.getDiscountId() == null) {
                ps.setNull(3, Types.INTEGER);  // Set NULL if DiscountID is null
            } else {
                ps.setInt(3, lineItem.getDiscountId());  // Otherwise set the DiscountID
            }

            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int lineItemId = generatedKeys.getInt(1);
                    lineItem.setId(lineItemId);  // Set the generated ID in the transaction object
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lineItem;
    }

    //READ
    public LineItem read(int id) {
        String query = "SELECT * FROM db_lineitem WHERE ID = ?";
        LineItem lineItem = null;

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                lineItem = new LineItem(rs.getInt("ProductID"), rs.getInt("TransactionID"), rs.getInt("DiscountID"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lineItem;
    }

    //READ ALL
    public List<LineItem> readAll() {
        String query = "SELECT * FROM db_lineitem";
        List<LineItem> lineItems = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lineItems.add(new LineItem(rs.getInt("ProductID"), rs.getInt("TransactionID"), rs.getInt("DiscountID")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lineItems;
    }

    //UPDATE
    public void update(LineItem lineItem) {
        String query = "UPDATE db_lineitem SET ProductID = ?, TransactionID = ?, DiscountID = ? WHERE ID = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, lineItem.getProductId());
            ps.setInt(2, lineItem.getTransactionId());
            ps.setInt(3, lineItem.getDiscountId());
            ps.setInt(4, lineItem.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //DELETE
    public void delete(int id) {
        String query = "DELETE FROM db_lineitem WHERE ID = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}