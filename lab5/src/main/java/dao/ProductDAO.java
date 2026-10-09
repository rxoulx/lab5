package dao;

import context.DBContext;
import model.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public List<Product> getAllProducts() {
        List<Product> list = new ArrayList<>();
        // Trong MySQL (Northwind) bảng là Product, trong SQL Server là Products
        String sql =
                "SELECT ProductID, ProductName, "
                + "SupplierID, CategoryID, "
                + "QuantityPerUnit, UnitPrice, UnitsInStock "
                + "FROM Product";

        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Product p = new Product(
                        rs.getInt("ProductID"),
                        rs.getString("ProductName"),
                        rs.getInt("SupplierID"),
                        rs.getInt("CategoryID"),
                        rs.getString("QuantityPerUnit"),
                        rs.getDouble("UnitPrice"),
                        rs.getInt("UnitsInStock")
                );
                list.add(p);
            }
        } catch (Exception e) {
            // Dự phòng: nếu bảng Product không tìm thấy (ví dụ chạy trên SQL Server có bảng Products)
            try {
                if (conn != null && !conn.isClosed()) {
                    String sqlFallback =
                            "SELECT ProductID, ProductName, "
                            + "SupplierID, CategoryID, "
                            + "QuantityPerUnit, UnitPrice, UnitsInStock "
                            + "FROM Products";
                    ps = conn.prepareStatement(sqlFallback);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        Product p = new Product(
                                rs.getInt("ProductID"),
                                rs.getString("ProductName"),
                                rs.getInt("SupplierID"),
                                rs.getInt("CategoryID"),
                                rs.getString("QuantityPerUnit"),
                                rs.getDouble("UnitPrice"),
                                rs.getInt("UnitsInStock")
                        );
                        list.add(p);
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}