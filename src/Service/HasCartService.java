package Service;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import Database.DBConnectionPool;
import Model.Game;
import Model.HasCart;
import Model.User;

public class HasCartService implements Serializable {

	private static final long serialVersionUID = -8268400551985297095L;
	
	private Connection db;
	private PreparedStatement statement;
	
	public HasCartService() {
		try {
			db = DBConnectionPool.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	
	
	public boolean addItem(HasCart cart) {
		String ownedGamesQuery = "SELECT * FROM has_game WHERE user_id = " + cart.getUserId() + " AND game_id = " + cart.getGameId();
		
		System.out.println("# HasCartService > Query > " + ownedGamesQuery);
		
		try {
			statement = db.prepareStatement(ownedGamesQuery);
			ResultSet result = statement.executeQuery();
			
			if(result.next())
				return false;
			
		} catch(SQLException e) {
			e.printStackTrace();
			
			return false;
		}
		
		String addGameQuery = "INSERT INTO has_cart VALUES (" + cart.getUserId() + ", " + cart.getGameId() + ")";
		
		System.out.println("# HasCartService > Query > " + addGameQuery);
		
		try {
			statement = db.prepareStatement(addGameQuery);
			statement.executeUpdate();
			
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean removeItem(HasCart cart) {
		String query = "DELETE FROM has_cart WHERE user_id = " + cart.getUserId() + "AND game_id = " + cart.getGameId();
		
		System.out.println("# HasCartService > Query > " + query);
		
		try {
			statement = db.prepareStatement(query);
			statement.executeUpdate();
			
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean dropCart(User user) {
		String query = "DELETE FROM has_cart WHERE user_id = " + user.getId();
		
		System.out.println("# HasCartService > Query > " + query);
		
		try {
			statement = db.prepareStatement(query);
			statement.executeUpdate();
			
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public ArrayList<Game> selectCart(User user) {
		ArrayList<Game> cart = new ArrayList<Game>();
		
		String query = "SELECT * FROM has_cart WHERE user_id = " + user.getId();
		
		System.out.println("# HasCartService > Query > " + query);
		
		try {
			statement = db.prepareStatement(query);
			ResultSet result = statement.executeQuery();
			
			while(result.next())
				cart.add(new GameService().getGame(result.getInt("game_id")));
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return cart;
	}
}