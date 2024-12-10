package DAO;

import model.Discount;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DiscountDAO extends BaseDAO {

    //CREATE
    public void create(Discount discount) {
        String query = "INSERT INTO db_discount (Description, Amount) VALUES (?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, discount.getDescription());
            ps.setFloat(2, discount.getAmount());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //READ
    public Discount read(int id) {
        String query = "SELECT * FROM db_discount WHERE ID = ?";
        Discount discount = null;

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                discount = new Discount(rs.getInt("ID"), rs.getString("Description"), rs.getFloat("Amount"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return discount;
    }

    //READ ALL
    public List<Discount> readAll() {
        String query = "SELECT * FROM db_discount";
        List<Discount> discounts = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                discounts.add(new Discount(rs.getInt("ID"), rs.getString("Description"), rs.getFloat("Amount")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return discounts;
    }

    //UPDATE
    public void update(Discount discount) {
        String query = "UPDATE db_discount SET Description = ?, Amount = ? WHERE ID = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, discount.getDescription());
            ps.setFloat(2, discount.getAmount());
            ps.setInt(3, discount.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //DELETE
    public void delete(int id) {
        String query = "DELETE FROM db_discount WHERE ID = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
