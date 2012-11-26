package co.edu.sanmartin.web.controladores;

import java.sql.*;
import java.util.*;

public class SimpleConnectionPool implements IConnectionPool{
	
	protected Stack<Connection> pool;
	protected String connectionURL;
	protected String userName;
	protected String password;
	
	/*
	 * Crea un grupo de parametro para la conexión utilizando la 
	 * URL, nombre de Usuario, contraseña
	 */
	public SimpleConnectionPool(String aConnectionURL, String aUserName, String aPassword ) {
		// TODO Auto-generated constructor stub
		connectionURL = aConnectionURL;
		userName      = aUserName;
		password      = aPassword;
	}
	/*
	 * Obtienen conexión desde pool o crea una si esta vacia
	 * (non-Javadoc)
	 * @see inijsp.IconnectionPool#getConnection()
	 */
	@Override
	public synchronized Connection getConnection() throws SQLException {
		// si la conexión existe devuelve el pool 
		if(!pool.empty()){
			return (Connection) pool.pop();
		}
		// si no existe crea la nueva conexión
		else
			return DriverManager.getConnection(connectionURL, userName, password);
	}

	@Override
	public synchronized void releaseConnection(Connection conn) throws SQLException {
		pool.push(conn);
	}
}