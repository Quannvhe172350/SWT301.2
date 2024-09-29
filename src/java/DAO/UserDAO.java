/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Context.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.User;

public class UserDAO {

    private DBContext dbContext;

    public UserDAO() {
        // Initialize the DBContext in the constructor
        dbContext = new DBContext();
    }
//check user

    public User checkUser(String user_email, String user_pass) {
        String query = "SELECT * FROM users WHERE user_email = ? AND user_pass = ?";
        try ( Connection conn = dbContext.getConnection();  PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, user_email);
            ps.setString(2, user_pass);
            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    return new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
                }
            }
        } catch (SQLException e) {
            // Handle the exception or log it
            System.out.println(e);
        }
        return null;
    }
//update user

    public void updateUser(int user_id, String user_name, String user_pass) {
        String sql = "UPDATE users SET user_name = ?, user_pass = ? WHERE user_id = ?";
        try ( Connection conn = dbContext.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user_name);
            ps.setString(2, user_pass);
            ps.setInt(3, user_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            // Handle the exception or log it
            System.out.println(e);
        }
    }
//check acc

    public User checkAcc(String user_email) {
        String query = "SELECT * FROM users WHERE user_email = ?";
        try ( Connection conn = dbContext.getConnection();  PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, user_email);
            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    return new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
                }
            }
        } catch (SQLException e) {
            // Handle the exception or log it
            System.out.println(e);
        }
        return null;
    }
//dang ki acc

    public void signup(String user_email, String user_pass) {
        String query = "INSERT INTO users VALUES(?, ?, ?, ?)";
        String[] parts = user_email.split("@");
        String username;
        if (parts.length > 0) {
            username = parts[0].replaceAll("[0-9]", "");
            if (!username.isEmpty()) {
                username = username.substring(0, 1).toUpperCase() + username.substring(1);
            } else {
                username = "";
            }
        } else {
            username = "";
        }

        try ( Connection conn = dbContext.getConnection();  PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, username);
            ps.setString(2, user_email);
            ps.setString(3, user_pass);
            ps.setString(4, "False");
            ps.executeUpdate();
        } catch (SQLException e) {
            // Handle the exception or log it
            System.out.println(e);
        }
    }
//lay tat ca user

    public List<User> getUsers() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try ( Connection conn = dbContext.getConnection();  PreparedStatement ps = conn.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
            }
        } catch (SQLException e) {
            // Handle the exception or log it
            System.out.println(e);
        }
        return list;
    }
//update admin

    public void setAdmin(int user_id, String isAdmin) {
        String sql = "UPDATE users SET isAdmin = ? WHERE user_id = ?";
        try ( Connection conn = dbContext.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, isAdmin.toUpperCase());
            ps.setInt(2, user_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            // Handle the exception or log it
            System.out.println(e);
        }
    }
}
