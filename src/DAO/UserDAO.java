package DAO;

import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends BaseDAO {

    //CREATE
    public void create(User user) {
        String query = "INSERT INTO db_user (Firstname, Lastname, PhoneNum, Address, City, State, Zip, Active) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getPhoneNum());
            ps.setString(4, user.getAddress());
            ps.setString(5, user.getCity());
            ps.setString(6, user.getState());
            ps.setInt(7, user.getZip());
            ps.setBoolean(8, user.isActive());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //READ
    public User read(int id) {
        String query = "SELECT * FROM db_user WHERE ID = ?";
        User user = null;

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user = new User(rs.getInt("ID"), rs.getString("Firstname"), rs.getString("Lastname"),
                        rs.getString("PhoneNum"), rs.getString("Address"), rs.getString("City"),
                        rs.getString("State"), rs.getInt("Zip"), rs.getBoolean("Active"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    //READ ALL
    public List<User> readAll() {
        String query = "SELECT * FROM db_user";
        List<User> users = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                users.add(new User(rs.getInt("ID"), rs.getString("Firstname"), rs.getString("Lastname"),
                        rs.getString("PhoneNum"), rs.getString("Address"), rs.getString("City"),
                        rs.getString("State"), rs.getInt("Zip"), rs.getBoolean("Active")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    //READ ALL ACTIVE
    public List<User> readActiveUsers() {
        String query = "SELECT * FROM db_user WHERE Active = true";
        List<User> users = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                users.add(new User(rs.getInt("ID"), rs.getString("Firstname"), rs.getString("Lastname"),
                        rs.getString("PhoneNum"), rs.getString("Address"), rs.getString("City"),
                        rs.getString("State"), rs.getInt("Zip"), rs.getBoolean("Active")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    //UPDATE
    public void update(User user) {
        String query = "UPDATE db_user SET Firstname = ?, Lastname = ?, PhoneNum = ?, Address = ?, City = ?, State = ?, Zip = ?, Active = ? WHERE ID = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getPhoneNum());
            ps.setString(4, user.getAddress());
            ps.setString(5, user.getCity());
            ps.setString(6, user.getState());
            ps.setInt(7, user.getZip());
            ps.setBoolean(8, user.isActive());
            ps.setInt(9, user.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //SOFT DELETE
    public void delete(int id) {
        String query = "UPDATE db_user SET Active = false WHERE ID = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
