package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MySQLConnection {

	private Connection conn;

	public MySQLConnection() {
		try { //连接数据库
			Class.forName("com.mysql.cj.jdbc.Driver").getConstructor().newInstance();
			conn = DriverManager.getConnection(MySQLDBUtil.URL);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void close() {
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public String getFullname(String userId) {
		if (conn == null) {
			System.err.println("DB connection failed");
			return "";
		}
		String name = "";
		String sql = "SELECT first_name, last_name FROM users WHERE user_id = ? ";
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, userId);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				name = rs.getString("first_name") + " " + rs.getString("last_name");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return name;
	}

	public boolean verifyLogin(String userId, String password) {
		if (conn == null) {
			System.err.println("DB connection failed");
			return false;
		}
		String sql = "SELECT user_id FROM users WHERE user_id = ? AND password = ?";
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, userId);
			statement.setString(2, password);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	public boolean addUser(String userId, String password, String firstname, String lastname,String emailAddress, String phoneNumber) {
		if (conn == null) {
			System.err.println("DB connection failed");
			return false;
		}

		String sql = "INSERT IGNORE INTO users VALUES (?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, userId);
			statement.setString(2, password);
			statement.setString(3, firstname);
			statement.setString(4, lastname);
			statement.setString(5, emailAddress);
			statement.setString(6, phoneNumber);

			return statement.executeUpdate() == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<String> getUserProfiles(String userId){
		if (conn == null) {
			System.err.println("DB connection failed");
			return new ArrayList<>();
		}
		List<String> user = new ArrayList<>();
		try {
			String sql = "SELECT email_address,phone_number FROM dispatch.users WHERE user_id = ?;";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1,userId);
			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				String emailAddress = rs.getString("email_address");
				user.add(emailAddress);
				String phoneNumber = rs.getString("phone_number");
				user.add(phoneNumber);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	public List<String> getTimes(String trackingId){
		if (conn == null) {
			System.err.println("DB connection failed");
			return new ArrayList<>();
		}
		List<String> times = new ArrayList<>();
		try {
			String sql = "SELECT created_at,delivered_at FROM tracking WHERE tracking_id = ?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1,trackingId);
			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				String createdTime = rs.getString("created_at");
				times.add(createdTime);
				String deliveredTime = rs.getString("delivered_at");
				times.add(deliveredTime);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return times;
	}



	public boolean addTrackingInfo(String trackingId, String created_at, String delievered_at) {
		if (conn == null) {
			System.err.println("DB connection failed");
			return false;
		}

		String sql = "INSERT IGNORE INTO dispatch.tracking(tracking_id,created_at,delivered_at)values(?,?,?)";
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, trackingId);
			statement.setString(2, created_at);
			statement.setString(3, delievered_at);
			return statement.executeUpdate() == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}