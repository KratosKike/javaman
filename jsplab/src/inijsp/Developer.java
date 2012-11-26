package inijsp;

import java.io.*;
import java.sql.*;
/*
 * Esta clase se dedica a describir la forma en que se debe hacer CRUD
 * en la tabla developer,.
 * Ademas esta clase hereda los atributos de la clase DatabaseObject
 * quien realmente hace la interacción con la API JDBC
 * 
 */
public class Developer extends DatabaseObject implements Serializable{
	
	private static final long serialVersionUID = -7021589671631189183L;
	/*
	 * Con base a los campos de la base de datos se crean los siguientes campos:
	 * username varchar(10) primary key, 
	 * pword varchar(10), 
	 * developer varchar(50
	 */
	public String developer;
	public String userName;
	public String password;
	
	public Developer(){
		super();
	}
	
	public Developer(ResultSet results) throws SQLException{
		developer = results.getString("developer");
		userName = results.getString("userName");
		password = results.getString("pword");
	}
	
	
	public String getDeveloper() {
		return developer;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}
	
	public void setDeveloper(String aDeveloper) {
		this.developer = aDeveloper;
	}

	public void setUserName(String aUserName) {
		this.userName = aUserName;
	}

	public void setPassword(String aPassword) {
		this.password = aPassword;
	}
	
	// llamando nombre de la tabla
	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return "developer";
	}
	
	// Creando objeto DatabaseObject objeto de la base de datos
	@Override
	public DatabaseObject createInstance(ResultSet results) throws SQLException {
		// TODO Auto-generated method stub
		return new Developer(results);
	}
	// devolviendo campos de la base de datos
	@Override
	public String getFieldList() {
		// TODO Auto-generated method stub
		return "developer,username,pword";
	}

	@Override
	public String getInsertStatement() {
		// TODO Auto-generated method stub
		return "INSERT INTO developer(developer, username, pword)"+"values(?,?,?)";
	}

	@Override
	public void prepareInsertStatement(PreparedStatement s) throws SQLException {
		// TODO Auto-generated method stub
		s.setString(1, developer);
		s.setString(2, userName);
		s.setString(3, password);
	}

	@Override
	public String getUpdateStatement() {
		// TODO Auto-generated method stub
		return "UPDATE developer SET developer=?, pword=?" + " WHERE username=?";
	}

	@Override
	public void prepareUpdateStatement(PreparedStatement s) throws SQLException {
		// TODO Auto-generated method stub
		s.setString(1, developer);
		s.setString(2, userName);
		s.setString(3, password);	
	}

	@Override
	public String getDeleteStatement() {
		// TODO Auto-generated method stub
		return "DELETE FROM developer WHERE username = ?";
	}

	@Override
	public void prepareDeleteStatement(PreparedStatement s) throws SQLException {
		// TODO Auto-generated method stub
		s.setString(1, userName);
	}
}