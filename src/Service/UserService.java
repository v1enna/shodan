package Service;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import Database.DBConnectionPool;

import java.sql.PreparedStatement;

import Model.User;
import Utils.PasswordHasher;

public class UserService implements Serializable {

	private static final long serialVersionUID = -7885019384296693341L;
	
	private Connection db;
	private PreparedStatement statement;
	
	public UserService() {
		try {
			db = DBConnectionPool.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int getIdByUsername(String username) {
		int id = -1;
		
		try {
			String query = "SELECT user_id FROM users WHERE user_name = '" + username + "'";
			
			statement = db.prepareStatement(query);
			ResultSet result = statement.executeQuery();
			
			System.out.println("# UserService > Query > " + query);
			
			result.next();
			id = result.getInt("user_id");
				
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return id;
	}
	
	public User getUser(int id) {
		User user = null;
		
		try {
			String query = "SELECT * FROM users WHERE user_id = " + id;
			
			statement = db.prepareStatement(query);
			ResultSet result = statement.executeQuery();
			
			System.out.println("# UserService > Query > " + query);
			
			if(result.next())
				user = new User(
					result.getInt("user_id"),
					result.getString("user_name"),
					result.getString("user_password"),
					result.getString("user_email"),
					result.getInt("user_money"),
					result.getBoolean("user_admin")
				);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return user;
	}
	
	public boolean updateUser(User user) {
		try {
			String query = 
				  "UPDATE users SET"
				+ "  user_id = " + user.getId()
				+ ", user_name = '" + user.getName() + "'"
				+ ", user_password = '" + user.getPassword() + "'"
				+ ", user_email = '" + user.getEmail() + "'"
				+ ", user_money = " + user.getMoney()
				+ ", user_admin = " + user.isAdmin()
				+ " WHERE user_id = " + user.getId();
		
			System.out.println("# UserService > Query > " + query);
			
			statement = db.prepareStatement(query);
			statement.executeUpdate();
			
			System.out.println("# UserService > Aggiorno l'utente ID " + user.getId());
			
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean insertUser(String username,String password,String email) {
		try {
			String query = "INSERT INTO users(user_name, user_password, user_email) VALUES('" + username + "','" + password + "','" + email + "')";
			
			
			System.out.println("# UserService > Query > " + query);
			
			statement = db.prepareStatement(query);
			statement.executeUpdate();
			
			System.out.println("# UserService > Inserisco l'utente " + username);
			
			return true;
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean deleteUser(int userId) {
		try {
			String query = "DELETE FROM users WHERE user_id =" + userId ;
			
			System.out.println("# UserService > Query > " + query);
			
			statement = db.prepareStatement(query);
			statement.executeUpdate();
			
			System.out.println("# UserService > Eliminazione dell'utente " + userId);
			
			return true;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
}
