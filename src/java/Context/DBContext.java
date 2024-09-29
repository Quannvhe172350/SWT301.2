/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Context;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author TGDD
 */
public class DBContext {
    private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=Shop";
    private static final String DB_USERNAME = "sa";
    private static final String DB_PASSWORD = "sa";

    private Connection connection;

    public DBContext() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        } catch (ClassNotFoundException | SQLException ex) {
            // Xử lý ngoại lệ ở đây, ví dụ: ghi vào log, ném lại ngoại lệ hoặc thông báo người dùng
            ex.printStackTrace();
        }
    }

    // Thêm các phương thức khác để thao tác với cơ sở dữ liệu

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
                // Xử lý ngoại lệ khi đóng kết nối
                ex.printStackTrace();
            }
        }
    }
}

