package DAO;

import model.Brand;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BrandDAO extends BaseDAO {

    //CREATE
    public void create(Brand brand) {
        String query = "INSERT INTO db_brand (Name) VALUES (?)";
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, brand.getName());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //READ
    public Brand read(int id) {
        String query = "SELECT * FROM db_brand WHERE ID = ?";
        Brand brand = null;

        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                brand = new Brand(rs.getInt("ID"), rs.getString("Name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return brand;
    }

    //READ ALL
    public List<Brand> readAll() {
        String query = "SELECT * FROM db_brand";
        List<Brand> brands = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                brands.add(new Brand(rs.getInt("ID"), rs.getString("Name")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return brands;
    }

    //UPDATE
    public void update(Brand brand) {
        String query = "UPDATE db_brand SET Name = ? WHERE ID = ?";

        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, brand.getName());
            ps.setInt(2, brand.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //DELETE
    public void delete(int id) {
        String query = "DELETE FROM db_brand WHERE ID = ?";

        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}