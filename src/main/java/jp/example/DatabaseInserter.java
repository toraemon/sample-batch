package jp.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DatabaseInserter {

	private static final String URL = "jdbc:mysql://localhost:3306/testdb";
	private static final String USER = "user";
	private static final String PASSWORD = "password";
	
	public void insertData(List<String[]> data) {
		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
			String sql = "INSERT INTO sample_data (name, age, address) VALUES (?, ?, ?)";
			try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
				for (String[] row: data) {
					pstmt.setString(1, row[0]);
					pstmt.setInt(2, Integer.parseInt(row[1]));
					pstmt.setString(3, row[2]);
					pstmt.executeUpdate();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
