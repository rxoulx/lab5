package dao;

import context.DBContext1;
import model.Record;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RecordDAO {

    public RecordDAO() {
    }

    // ---------------- GET ALL ----------------
    public List<Record> getAllRecords() {

    List<Record> list = new ArrayList<>();

    String sql = """
            SELECT id,
                   stname,
                   course,
                   fee                   
            FROM records
            """;

    try (
            Connection conn = new DBContext1().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
    ) {

        while (rs.next()) {

            Record p = new Record(
                    rs.getInt("id"),
                    rs.getString("stname"),
                    rs.getString("course"),
                    rs.getInt("fee")
                   
            );

            list.add(p);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}

   public void insertRecord(String stname, String course, int fee) {
    String sql = "INSERT INTO records (stname, course, fee) VALUES (?, ?, ?)";
    try (java.sql.Connection conn = new context.DBContext1().getConnection();
         java.sql.PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, stname);
        ps.setString(2, course);
        ps.setInt(3, fee);
        ps.executeUpdate();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
}