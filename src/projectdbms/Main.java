package projectdbms;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;


public class Main {
	private static final String URL = "jdbc:postgresql://localhost:5432/cs623";
    private static final String USER = "postgres";
    private static final String PASSWORD = "root";

    public static void main(String[] args) {
    	
    	try {
    		Class.forName("org.postgresql.Driver");
    		Connection conn= DriverManager.getConnection(URL, USER, PASSWORD);
    		
//        Auto Commit OFF for Atomicity so we manually commit
    		conn.setAutoCommit(false);
    		
//        For Isolation
    		conn.setTransactionIsolation(conn.TRANSACTION_SERIALIZABLE);
    		
//        Prepared Statement avoids SQL Injection
    		PreparedStatement updateProduct = null;
    		PreparedStatement updateDepot = null;
//    		PreparedStatement updateStock = null;
    		
    		try {
    			updateProduct = conn.prepareStatement("UPDATE Product SET prod_id = ? WHERE prod_id = ?");
    			updateProduct.setString(1,"pp4");
    			updateProduct.setString(2, "p4");
    			updateProduct.executeUpdate();
    			
//    			updateStock = conn.prepareStatement("UPDATE Stock SET prod_id = ? WHERE prod_id = ?");
//    			updateStock.setString(1, "pp4");
//    			updateStock.setString(2, "p4");
//    			updateStock.executeUpdate();
    			
    			
    		} catch (Exception e) {
    			e.printStackTrace();
//            For Atomicity (Entire transaction should happen or nothing)
    			conn.rollback();    
    		}
    		conn.commit();
    		updateProduct.close();
    		
    		try {
    			updateDepot = conn.prepareStatement("UPDATE Depot SET dep_id = ? WHERE dep_id = ?");
    			updateDepot.setString(1, "dd5");
    			updateDepot.setString(2, "d5");
    			updateDepot.executeUpdate();
    			
//    			updateStock = conn.prepareStatement("UPDATE Stock SET dep_id = ? WHERE dep_id = ?");
//    			updateStock.setString(1, "dd5");
//    			updateStock.setString(2, "d5");
//    			updateStock.executeUpdate();
    			
    		} catch (Exception e) {
    			e.printStackTrace();
//        	For Atomicity 
    			conn.rollback();
    		}
    		
    		conn.commit();
    		updateDepot.close();
//    		updateStock.close();
    		conn.close();
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
	
}