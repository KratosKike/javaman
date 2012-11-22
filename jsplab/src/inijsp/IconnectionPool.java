package inijsp;
import java.sql.*;

/*
 * Interfaz que define los métodos comunes que un Pool 
 * de Conexión debe tener
 */

public interface IconnectionPool {
	public Connection getConnection() throws SQLException;
	public void releaseConnection(Connection conn) throws SQLException;
}