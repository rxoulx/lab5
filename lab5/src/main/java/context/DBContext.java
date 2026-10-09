package context;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBContext {

    // =========================================================================
    // CHẾ ĐỘ KẾT NỐI:
    // Đặt true để dùng MySQL (theo file hướng dẫn Codespace & my_sql_northwind.sql)
    // Đặt false để dùng SQL Server (theo file hướng dẫn Word của thầy)
    // =========================================================================
    public static final boolean USE_MYSQL = true;

    // --- CẤU HÌNH MYSQL (Docker Codespace) ---
    private final String MYSQL_SERVER = "127.0.0.1";
    private final String MYSQL_PORT = "3306";
    private final String MYSQL_DATABASE = "northwind";
    private final String MYSQL_USER = "root";
    private final String MYSQL_PASSWORD = "Vietnam@123";

    // --- CẤU HÌNH SQL SERVER (Docker Codespace theo file Word của thầy) ---
    private final String MSSQL_SERVER = "127.0.0.1";
    private final String MSSQL_PORT = "1433";
    private final String MSSQL_DATABASE = "Northwind";
    private final String MSSQL_USER = "sa";
    private final String MSSQL_PASSWORD = "Vietnam@123";

    public Connection getConnection() {
        Connection conn = null;
        try {
            if (USE_MYSQL) {
                // 1. Kết nối MySQL
                Class.forName("com.mysql.cj.jdbc.Driver");
                String url = "jdbc:mysql://" + MYSQL_SERVER + ":" + MYSQL_PORT + "/" + MYSQL_DATABASE
                        + "?useSSL=false"
                        + "&serverTimezone=Asia/Ho_Chi_Minh"
                        + "&allowPublicKeyRetrieval=true";
                conn = DriverManager.getConnection(url, MYSQL_USER, MYSQL_PASSWORD);
            } else {
                // 2. Kết nối SQL Server
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                String url = "jdbc:sqlserver://" + MSSQL_SERVER + ":" + MSSQL_PORT
                        + ";databaseName=" + MSSQL_DATABASE
                        + ";encrypt=true"
                        + ";trustServerCertificate=true";
                conn = DriverManager.getConnection(url, MSSQL_USER, MSSQL_PASSWORD);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void main(String[] args) {
        DBContext db = new DBContext();
        Connection conn = db.getConnection();
        if (conn != null) {
            System.out.println("Kết nối CSDL thành công!");
        } else {
            System.out.println("Kết nối CSDL thất bại!");
        }
    }
}