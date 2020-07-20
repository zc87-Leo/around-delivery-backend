package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import entity.Order;

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

	public boolean addContact(String firstName, String lastName,String emailAddress, String phoneNumber, String address) {
		if (conn == null) {
			System.err.println("DB connection failed");
			return false;
		}

		String sql = "INSERT IGNORE INTO dispatch.contact(first_name,last_name,phone_number,email_address,address) VALUES(?,?,?,?,?)";
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, firstName);
			statement.setString(2, lastName);
			statement.setString(3, phoneNumber);
			statement.setString(4, emailAddress);
			statement.setString(5, address);
			return statement.executeUpdate() == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	public int getContactId(String firstName, String lastName,String emailAddress, String phoneNumber, String address) {
		if (conn == null) {
			System.err.println("DB connection failed");
			return -1;
		}
		int id = -1;
		try {
			String sql = "SELECT contact_id FROM dispatch.contact WHERE first_name = ? and last_name = ? and phone_number = ? and email_address = ? and address = ?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, firstName);
			statement.setString(2, lastName);
			statement.setString(3, phoneNumber);
			statement.setString(4, emailAddress);
			statement.setString(5, address);
			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				id = rs.getInt("contact_id");
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return id;

	}

	public boolean createOrder(Order order, int senderId, int recipientId) {
		if (conn == null) {
			System.err.println("DB connection failed");
			return false;
		}


		try {
			String sql = "INSERT IGNORE INTO tracking(tracking_id,created_at,status) VALUES (?, ?, ?)";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, order.getTrackingId());
			statement.setString(2, order.getOrderCreateTime());
			String status = (order.getActive() == true) ? "active":"overdue";
			statement.setString(3, status);
			int b1 = statement.executeUpdate();

			String sql2 = "INSERT IGNORE INTO orders(order_id,user_id,tracking_id,active,sender_id,recipient_id,package_weight,package_height,package_fragile,total_cost,package_width,package_length,carrier,delivery_time) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			statement = conn.prepareStatement(sql2);
			statement.setString(1, order.getOrderId());
			statement.setString(2, order.getUserId());
			statement.setString(3, order.getTrackingId());
			statement.setBoolean(4, order.getActive());
			statement.setInt(5, senderId);
			statement.setInt(6, recipientId);
			statement.setFloat(7, order.getPackageWeight());
			statement.setFloat(8, order.getPackageHeight());
			statement.setBoolean(9, order.getIsFragile());
			statement.setFloat(10, order.getTotalCost());
			statement.setFloat(11, order.getPackageWidth());
			statement.setFloat(12, order.getPackageLength());
			statement.setString(13, order.getCarrier());
			statement.setString(14, order.getDeliveryTime());
//		statement.setString(11, order.getOrderCreateTime());
			int b2 = statement.executeUpdate();
			return b1 == 1 && b2 ==1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}