package db.tables;


import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import db.parking.beans.Admin;
import db.parking.ConnectionManage;


public class AdminManager {
	
	private static Connection conn = ConnectionManage.getInstance().getConnection();

	public static void displayAllRows() throws SQLException {

		String sql = "SELECT *  FROM users";
		try (
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				){

			System.out.println("Admin Table:");
			while (rs.next()) {
				StringBuffer bf = new StringBuffer();
				bf.append(rs.getInt("id") + ": ");
				bf.append(rs.getString("username") +", ");
				bf.append(rs.getString("age"));
				System.out.println(bf.toString());
			}
		}
	}

	public static Admin getRow(int adminId) throws SQLException {

		String sql = "SELECT * FROM users WHERE id = ?";
		ResultSet rs = null;

		try (
				PreparedStatement stmt = conn.prepareStatement(sql);
				){
			stmt.setInt(1, adminId);
			rs = stmt.executeQuery();

			if (rs.next()) {
				Admin bean = new Admin();
				bean.setAdminId(adminId);
				bean.setUserName(rs.getString("username"));
				bean.setAge(Integer.parseInt(rs.getString("age")));
				return bean;
			} else {
				return null;
			}

		} catch (SQLException e) {
			System.err.println(e);
			return null;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}

	}

	public static boolean insert(Admin bean) throws Exception {

		String sql = "INSERT into users (username, age) " +
				"VALUES (?, ?)";
		String name = "asd";
		int age=15;
		ResultSet keys = null;
		try (
				PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				) {
			
			stmt.setString(1, name);
			stmt.setInt(2, age);
			int affected = stmt.executeUpdate();
			
			if(affected == 1){
				keys = stmt.getGeneratedKeys();
				keys.next();
				int newKey = keys.getInt(1);
				bean.setAdminId(newKey);
			}
			else{
				System.err.println("No rows affected");
				return false;
			}
			
		} catch (SQLException e) {
			System.err.println(e);
			return false;
		} finally{
			if(keys != null)
				keys.close();
		}
		return true;
		
	}
	
	public static boolean update(Admin bean) throws Exception {

		String sql =
				"UPDATE users SET " +
				"age = ? " +
				"WHERE id = ?";
		
		String sql2 = "INSERT into users (username, age) " +
				"VALUES (?, ?)";
		
		try (
				PreparedStatement stmt = conn.prepareStatement(sql);
				PreparedStatement stmt2 = conn.prepareStatement(sql2);
				){
			
			stmt.setInt(1,bean.getAge());
			stmt.setInt(2,bean.getAdminId());
			
			int affected = stmt.executeUpdate();
			
			stmt2.setString(1, "gogo");
			stmt2.setInt(2,7);
			
			int affected2 = stmt2.executeUpdate();
			
			if(affected == 1 && affected2==1){
				return true;
			}else{
				return false;
			}
			
		}
		catch(SQLException e) {
			System.err.println(e);
			return false;
		}

	}
	
	public static boolean delete(int adminId) throws Exception {

		String sql = "DELETE FROM admin WHERE adminId = ?";
		try (
				PreparedStatement stmt = conn.prepareStatement(sql);
				){
			
			stmt.setInt(1,adminId);
			int affected = stmt.executeUpdate();
			if(affected == 1){
					return true;
			} else {
				return false;
			}
		}
		catch(SQLException e) {
			System.err.println(e);
			return false;
		}

	}
	
}
