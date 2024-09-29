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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.Bill;
import model.BillDetail;
import model.Cart;
import model.Item;
import model.Product;
import model.User;

/**
 * Data Access Object for handling billing-related operations.
 */
public class BillDAO {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    /**
     * Add a new order to the database.
     *
     * @param u       The user placing the order.
     * @param cart    The shopping cart containing items to be ordered.
     * @param payment The payment method used.
     * @param address The delivery address.
     * @param phone   The phone number for contact.
     */
    public void addOrder(User u, Cart cart, String payment, String address, int phone) {
        LocalDate curDate = LocalDate.now();
        String date = curDate.toString();

        try {
            // Insert the order information into the 'bill' table
            String insertBillSql = "INSERT INTO [bill] VALUES(?,?,?,?,?,?)";
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(insertBillSql);
            ps.setInt(1, u.getUser_id());
            ps.setDouble(2, cart.getTotalMoney());
            ps.setString(3, payment);
            ps.setString(4, address);
            ps.setString(5, date);
            ps.setInt(6, phone);
            ps.executeUpdate();

            // Retrieve the newly created bill ID
            String selectMaxBillIdSql = "SELECT TOP 1 bill_id FROM [bill] ORDER BY bill_id DESC";
            ps = conn.prepareStatement(selectMaxBillIdSql);
            rs = ps.executeQuery();

            if (rs.next()) {
                int bill_id = rs.getInt(1);
                for (Item i : cart.getItems()) {
                    // Insert order details into the 'bill_detail' table
                    String insertBillDetailSql = "INSERT INTO [bill_detail] VALUES(?,?,?,?,?,?)";
                    double total = i.getQuantity() * i.getProduct().getProduct_price();
                    ps = conn.prepareStatement(insertBillDetailSql);
                    ps.setInt(1, bill_id);
                    ps.setString(2, i.getProduct().getProduct_id());
                    ps.setInt(3, i.getQuantity());
                    ps.setString(4, i.getSize());
                    ps.setString(5, i.getColor());
                    ps.setDouble(6, total);
                    ps.executeUpdate();
                }
            }

            // Update product quantities in the 'product' table
            String updateProductQuantitySql = "UPDATE product SET quantity = quantity - ? WHERE product_id = ?";
            ps = conn.prepareStatement(updateProductQuantitySql);
            for (Item i : cart.getItems()) {
                ps.setInt(1, i.getQuantity());
                ps.setString(2, i.getProduct().getProduct_id());
                ps.executeUpdate();
            }

        } catch (SQLException e) {
            // Handle the exception or log it
        }
    }

    /**
     * Retrieve billing information for all orders.
     *
     * @return A list of Bill objects containing billing information.
     */
    public List<Bill> getBillInfo() {
        List<Bill> list = new ArrayList<>();
        String sql = "SELECT b.bill_id, u.user_name, b.phone, b.address, b.date, b.total, b.payment FROM bill b " +
                     "INNER JOIN users u ON b.user_id = u.user_id";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                User u = new User(rs.getString(2));
                list.add(new Bill(rs.getInt(1), u, rs.getFloat(6), rs.getString(7), rs.getString(4), rs.getDate(5), rs.getInt(3)));
            }
        } catch (SQLException e) {
            // Handle the exception or log it
        }
        return list;
    }

    /**
     * Retrieve the most recent bill.
     *
     * @return A Bill object representing the most recent bill.
     */
    public Bill getBill() {
        String sql = "SELECT TOP 1 bill_id, total, date FROM [bill] ORDER BY bill_id DESC";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                return new Bill(rs.getInt(1), rs.getFloat(2), rs.getDate(3));
            }
        } catch (SQLException e) {
            // Handle the exception or log it
        }
        return null;
    }

    /**
     * Retrieve billing details for a specific bill.
     *
     * @param bill_id The ID of the bill for which details are requested.
     * @return A list of BillDetail objects containing billing details.
     */
    public List<BillDetail> getDetail(int bill_id) {
        List<BillDetail> list = new ArrayList<>();
        String sql = "SELECT d.detail_id, p.product_id, p.product_name, p.img, d.quantity, d.size, d.color, d.price FROM bill_detail d " +
                     "INNER JOIN product p ON d.product_id = p.product_id WHERE d.bill_id = ?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, bill_id);
            rs = ps.executeQuery();
            while (rs.next()) {
                Product p = new Product(rs.getString(2), rs.getString(3), rs.getString(4));
                list.add(new BillDetail(rs.getInt(1), p, rs.getInt(5), rs.getString(6), rs.getString(7), rs.getFloat(8)));
            }
        } catch (SQLException e) {
            // Handle the exception or log it
        }
        return list;
    }

    /**
     * Update the payment method for a specific bill.
     *
     * @param payment The new payment method to be updated.
     * @param bill_id The ID of the bill to be updated.
     */
    public void updatePayment(String payment, int bill_id) {
        String sql = "UPDATE bill SET payment = ? WHERE bill_id = ?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, payment);
            ps.setInt(2, bill_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            // Handle the exception or log it
        }
    }

    /**
     * Retrieve billing information for a specific user.
     *
     * @param user_id The ID of the user for which billing information is requested.
     * @return A list of Bill objects containing billing information for the user.
     */
    public List<Bill> getBillByID(int user_id) {
        List<Bill> list = new ArrayList<>();
        String sql = "SELECT b.bill_id, b.date, b.total, b.payment, b.address, b.phone FROM bill b WHERE user_id = ?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, user_id);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Bill(rs.getInt(1), rs.getFloat(3), rs.getString(4), rs.getString(5), rs.getDate(2), rs.getInt(6)));
            }
        } catch (SQLException e) {
            // Handle the exception or log it
        }
        return list;
    }

    /**
     * Retrieve billing information for orders placed on the current day.
     *
     * @return A list of Bill objects containing billing information for today's orders.
     */
    public List<Bill> getBillByDay() {
        List<Bill> list = new ArrayList<>();
        String sql = "SELECT b.bill_id, u.user_name, b.phone, b.address, b.date, b.total, b.payment FROM bill b " +
                     "INNER JOIN users u ON b.user_id = u.user_id WHERE date = CAST(GETDATE() AS DATE)";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                User u = new User(rs.getString(2));
                list.add(new Bill(rs.getInt(1), u, rs.getFloat(6), rs.getString(7), rs.getString(4), rs.getDate(5), rs.getInt(3)));
            }
        } catch (SQLException e) {
            // Handle the exception or log it
        }
        return list;
    }
}
