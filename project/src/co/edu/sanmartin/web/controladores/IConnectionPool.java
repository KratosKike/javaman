package co.edu.sanmartin.web.controladores;

import java.sql.*;

/*
 * Interfaz que define los métodos comunes que un Pool 
 * de Conexión debe tener
 */

public interface IConnectionPool {
	public Connection getConnection() throws SQLException;
	public void releaseConnection(Connection conn) throws SQLException;
}