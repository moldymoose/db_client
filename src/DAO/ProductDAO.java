package DAO;

import Models.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO extends BaseDAO {

    //CREATE
    public Product create(Product product) {
        String query = "INSERT INTO db_product (BrandID, ProductName, Description, Price, Active) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, product.getBrandId());
            ps.setString(2, product.getProductName());
            if (product.getDescription() == null) {
                ps.setNull(3,Types.VARCHAR);
            } else {
                ps.setString(3, product.getDescription());
            }
            ps.setFloat(4, product.getPrice());
            ps.setBoolean(5, product.isActive());
            ps.executeUpdate();

            try(ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if(generatedKeys.next()) {
                    int productId = generatedKeys.getInt(1);
                    product.setId(productId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return product;
    }

    //READ
    public Product read(int id) {
        String query = "SELECT * FROM db_product WHERE ID = ?";
        Product product = null;

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                product = new Product(rs.getInt("ID"), rs.getInt("BrandID"), rs.getString("ProductName"),
                        rs.getString("Description"), rs.getFloat("Price"), rs.getBoolean("Active"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return product;
    }

    //READ ALL
    public List<Product> readAll() {
        String query = "SELECT * FROM db_product";
        List<Product> products = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                products.add(new Product(rs.getInt("ID"), rs.getInt("BrandID"), rs.getString("ProductName"),
                        rs.getString("Description"), rs.getFloat("Price"), rs.getBoolean("Active")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    //UPDATE
    public void update(Product product) {
        String query = "UPDATE db_product SET BrandID = ?, ProductName = ?, Description = ?, Price = ?, Active = ? WHERE ID = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, product.getBrandId());
            ps.setString(2, product.getProductName());
            ps.setString(3, product.getDescription());
            ps.setFloat(4, product.getPrice());
            ps.setBoolean(5, product.isActive());
            ps.setInt(6, product.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //SOFT DELETE
    public void delete(int id) {
        String query = "UPDATE db_product SET Active = false WHERE ID = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}