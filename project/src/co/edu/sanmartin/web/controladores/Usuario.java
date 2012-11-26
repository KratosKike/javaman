package co.edu.sanmartin.web.controladores;

import java.io.IOException;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Usuario extends DatabaseObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3761794741077375693L;
	
	String usuario;
	String clave;
	
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}
	
	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return "usuario";
	}
	
	public Usuario(){
		super();
	}
	
	public Usuario(ResultSet results) throws SQLException{
		usuario = results.getString("usuario");
		clave	= results.getString("clave");
	}
	
	@Override
	public DatabaseObject createInstance(ResultSet results)
			throws SQLException, IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		return new Usuario(results);
	}
	
	@Override
	public String getFieldList() {
		// TODO Auto-generated method stub
		return "usuario,clave";
	}

	@Override
	public String getInsertStatement() {
		// TODO Auto-generated method stub
		return "INSERT INTO usuario(usuario,clave)"+"values(?,?)";
	}

	@Override
	public void prepareInsertStatement(PreparedStatement s) throws SQLException {
		// TODO Auto-generated method stub
		s.setString(1, usuario);
		s.setString(2, clave);
	}

	@Override
	public String getUpdateStatement() {
		// TODO Auto-generated method stub
		return "UPDATE servicio SET clave=?" + " WHERE usuario=?";
	}

	@Override
	public void prepareUpdateStatement(PreparedStatement s) throws SQLException {
		// TODO Auto-generated method stub
		s.setString(1, usuario);
		s.setString(2, clave);
	}

	@Override
	public String getDeleteStatement() {
		// TODO Auto-generated method stub
		return "DELETE FROM usuario WHERE usuario = ?";
	}

	@Override
	public void prepareDeleteStatement(PreparedStatement s) throws SQLException {
		// TODO Auto-generated method stub
		s.setString(1, usuario);
	}

}
