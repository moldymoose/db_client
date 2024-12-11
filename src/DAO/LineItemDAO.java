package DAO;

import Models.LineItem;
import Models.Transaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LineItemDAO extends BaseDAO {

    //CREATE
    public LineItem create(LineItem lineItem) {
        //LineItem object comes name but no ID.  Prepared statement inserts new row with details and returns generated key.
        String query = "INSERT INTO db_lineitem (ProductID, TransactionID, DiscountID) VALUES (?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, lineItem.getProductId());
            ps.setInt(2, lineItem.getTransactionId());

            //Accounts for possible null discount
            if (lineItem.getDiscountId() == null) {
                ps.setNull(3, Types.INTEGER); //Set db field null
            } else {
                ps.setInt(3, lineItem.getDiscountId()); //Sets discount ID if not null
            }

            ps.executeUpdate();

            //Generated key is placed in result set and assigned to the ID of the lineItem object
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int lineItemId = generatedKeys.getInt(1);
                    lineItem.setId(lineItemId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lineItem;
    }

    //READ
    public LineItem read(int id) {
        String query = "SELECT li.ID AS LineItemID, li.ProductID, p.ProductName, p.Price AS ProductPrice, li.TransactionID, li.DiscountID, IFNULL(d.Amount, 0.00) AS DiscountAmount FROM db_lineitem li JOIN db_product p ON li.ProductID = p.ID LEFT JOIN db_discount d ON li.DiscountID = d.ID WHERE li.ID = ?";
        LineItem lineItem = null;

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                lineItem = new LineItem(rs.getInt("LineItemID"), rs.getInt("ProductID"), rs.getString("ProductName"), rs.getFloat("ProductPrice"), rs.getInt("TransactionID"), rs.getInt("DiscountID"), rs.getFloat("DiscountAmount"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lineItem;
    }

    //READ ALL FROM SPECIFIED TRANSACTION
    public List<LineItem> readAll(Transaction transaction) {
        //Query selects all from table based on transaction ID from transaction object in parameters
        String query = "SELECT li.ID AS LineItemID, li.ProductID, p.ProductName, p.Price AS ProductPrice, li.TransactionID, li.DiscountID, IFNULL(d.Amount, 0.00) AS DiscountAmount FROM db_lineitem li JOIN db_product p ON li.ProductID = p.ID LEFT JOIN db_discount d ON li.DiscountID = d.ID WHERE li.TransactionID = ?";
        List<LineItem> lineItems = new ArrayList<>();

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, transaction.getId());

            //List of rows is stored in result set
            ResultSet rs = ps.executeQuery();

            //iterates through result set and creates new lineItem object to populate list
            while (rs.next()) {
                lineItems.add(new LineItem(rs.getInt("LineItemID"), rs.getInt("ProductID"), rs.getString("ProductName"), rs.getFloat("ProductPrice"), rs.getInt("TransactionID"), rs.getInt("DiscountID"), rs.getFloat("DiscountAmount")));
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