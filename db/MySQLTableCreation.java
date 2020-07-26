package db;

import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.Connection;

public class MySQLTableCreation {
	// Run this as Java application to reset the database.
	public static void main(String[] args) {
		try {
			// Step 1 Connect to MySQL.
			System.out.println("Connecting to " + MySQLDBUtil.URL);
			Class.forName("com.mysql.cj.jdbc.Driver").getConstructor().newInstance();
			Connection conn = DriverManager.getConnection(MySQLDBUtil.URL);
			
			if (conn == null) {
				return;
			}
			
			// Step 2 Drop tables in case they exist.
			Statement statement = conn.createStatement();
			
			// disable foreign key check to drop the table
			statement.execute("SET FOREIGN_KEY_CHECKS=0");
			
			String sql = "DROP TABLE IF EXISTS users";
			statement.executeUpdate(sql);
			
			sql = "DROP TABLE IF EXISTS station";
			statement.executeUpdate(sql);
			
			sql = "DROP TABLE IF EXISTS tracking";
			statement.executeUpdate(sql);
			
			sql = "DROP TABLE IF EXISTS contact";
			statement.executeUpdate(sql);

			sql = "DROP TABLE IF EXISTS machine";
			statement.executeUpdate(sql);
			
			sql = "DROP TABLE IF EXISTS users";
			statement.executeUpdate(sql);
			
			sql = "DROP TABLE IF EXISTS orders";
			statement.executeUpdate(sql);
		
			
			sql = "CREATE TABLE users ("
					+ "user_id VARCHAR(255) NOT NULL,"
					+ "password VARCHAR(255) NOT NULL,"
					+ "first_name VARCHAR(255) NOT NULL,"
					+ "last_name VARCHAR(255) NOT NULL,"
					+ "email_address VARCHAR(255) NOT NULL,"
					+ "phone_number VARCHAR(13) NOT NULL,"
					+ "PRIMARY KEY (user_id)"
					+ ")";
			statement.executeUpdate(sql);
			
			
			sql = "CREATE TABLE orders ("
					+ "order_id INT NOT NULL,"
					+ "user_id VARCHAR(255) NOT NULL,"
					+ "tracking_id VARCHAR(255) NOT NULL,"
					+ "station_id INT NOT NULL,"
					+ "machine_id INT NOT NULL,"
					+ "active BOOLEAN NOT NULL,"
					+ "sender_id INT NOT NULL,"
					+ "recipient_id INT NOT NULL,"
					+ "package_weight FLOAT NOT NULL,"
					+ "package_height FLOAT NOT NULL,"
					+ "package_fragile BOOLEAN NOT NULL,"
					+ "package_length FLOAT NOT NULL,"
					+ "package_width FLOAT NOT NULL"
					+ "total_cost FLOAT NOT NULL,"
					+ "appointment_time VARCHAR(45) NOT NULL,"
					+ "PRIMARY KEY (order_id),"
					+ "FOREIGN KEY (user_id) REFERENCES users(user_id),"
					+ "FOREIGN KEY (tracking_id) REFERENCES tracking(tracking_id),"
					+ "FOREIGN KEY (station_id) REFERENCES station(station_id),"
					+ "FOREIGN KEY (machine_id) REFERENCES machine(machine_id),"
					+ "FOREIGN KEY (sender_id) REFERENCES contact(contact_id),"
					+ "FOREIGN KEY (recipient_id) REFERENCES contact(contact_id),"
					+ "FOREIGN KEY (recipient_id) REFERENCES contact(contact_id)"
					+ ")";
			statement.executeUpdate(sql);
			
			sql = "CREATE TABLE tracking ("
					+ "tracking_id VARCHAR(255) NOT NULL,"
					+ "status VARCHAR(255) NOT NULL,"
					+ "created_at DATETIME NOT NULL,"
					+ "estimated_delivered_at VARCHAR(255) NOT NULL,"
					+ "delay BOOLEAN NOT NULL,"
					+ "previous_destination VARCHAR(255) NOT NULL,"
					+ "previous_destination_start_time VARCHAR(255) NOT NULL,"
					+ "PRIMARY KEY (tracking_id)"
					+ ")";
			statement.executeUpdate(sql);
			
			sql = "CREATE TABLE station ("
					+ "station_id INT NOT NULL,"
					+ "drone_num INT NOT NULL,"
					+ "robot_num INT NOT NULL,"
					+ "address VARCHAR(1025) NOT NULL,"
					+ "lon DOUBLE NOT NULL,"
					+ "lat DOUBLE NOT NULL,"
					+ "PRIMARY KEY (station_id)"
					+ ")";
			statement.executeUpdate(sql);
			
			sql = "CREATE TABLE machine ("
					+ "machine_id INT NOT NULL,"
					+ "station_id INT NOT NULL,"
					+ "machine_type VARCHAR(255) NOT NULL,"
					+ "available BOOLEAN NOT NULL,"
					+ "height_limit FLOAT NOT NULL,"
					+ "weight_limit FLOAT NOT NULL,"
					+ "unit_price_per_mile FLOAT NOT NULL,"
					+ "unit_price_per_kg FLOAT NOT NULL,"
					+ "PRIMARY KEY (machine_id),"
					+ "FOREIGN KEY (station_id) REFERENCES station(station_id)"
					+ ")";
			statement.executeUpdate(sql);
			
			sql = "CREATE TABLE contact ("
					+ "contact_id INT NOT NULL,"
					+ "first_name VARCHAR(255) NOT NULL,"
					+ "last_name VARCHAR(255) NOT NULL,"
					+ "phone_number VARCHAR(20),"
					+ "email_address VARCHAR(255) NOT NULL,"
					+ "address VARCHAR(1025),"
					+ "PRIMARY KEY (contact_id)"
					+ ")";
			statement.executeUpdate(sql);
			
			conn.close();
			System.out.println("Import done successfully");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}