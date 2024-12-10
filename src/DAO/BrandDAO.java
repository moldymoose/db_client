package DAO;

import Models.Brand;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BrandDAO extends BaseDAO {

    //CREATE
    public Brand create(Brand brand) {
        //Brand object comes name but no ID.  Prepared statement inserts new row with specified name and returns generated key.
        String query = "INSERT INTO db_brand (Name) VALUES (?)";
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, brand.getName());
            ps.executeUpdate();

            //Generated key is placed in result set and assigned to the ID of the brand object
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int brandId = generatedKeys.getInt("ID");
                    brand.setId(brandId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //Method returns brand object complete with ID and name
        return brand;
    }

    //READ
    public Brand read(int id) {
        String query = "SELECT * FROM db_brand WHERE ID = ?";
        //Object initializes as null (in case ID does not exist)
        Brand brand = null;

        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            //If query returns with value the object is updated with the name and ID
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