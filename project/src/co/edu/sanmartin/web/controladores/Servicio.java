package co.edu.sanmartin.web.controladores;

import java.io.IOException;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * Esta clase se dedica a describir la forma en que se debe hacer CRUD
 * en la tabla servicio de la base de datos proveedor.
 * Ademas esta clase hereda los atributos de la clase DatabaseObject
 * quien realmente hace la interacción con la API JDBC
 * 
 */

public class Servicio extends DatabaseObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2086916526193662219L;
	
	/*
	 * Con base a los campos de la base de datos se crean los siguientes objetos:
	 * codigo_servicio` VARCHAR(45) NOT NULL ,
	 * `nombre_servicio` VARCHAR(45) NOT NULL ,
	 * `unidad` FLOAT(11) NOT NULL
	 */
	String codigoServicio;
	String nombreServicio;
	int    unidad;
	
	public String getCodigoServicio() {
		return codigoServicio;
	}

	public void setCodigoServicio(String codigoServicio) {
		this.codigoServicio = codigoServicio;
	}

	public String getNombreServicio() {
		return nombreServicio;
	}

	public void setNombreServicio(String nombreServicio) {
		this.nombreServicio = nombreServicio;
	}

	public int getUnidad() {
		return unidad;
	}

	public void setUnidad(int unidad) {
		this.unidad = unidad;
	}
	/*
	 * El metodo constructor se asocia con cada campo de la
	 * tabla creada en la base de datos proveedor llamada 
	 * servicio
	 */
	public Servicio(ResultSet results) throws SQLException{
		// TODO Auto-generated constructor stub
		codigoServicio = results.getString("codigo_servicio");
		nombreServicio = results.getString("nombre_servicio");
		unidad		   = results.getInt("unidad");
	}
	
	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return "servicio";
	}
	
	@Override
	public DatabaseObject createInstance(ResultSet results)
			throws SQLException, IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		return new Servicio(results);
	}
	
	@Override
	public String getFieldList() {
		// TODO Auto-generated method stub
		return "codigoServicio,nombreServicio,unidad";
	}

	@Override
	public String getInsertStatement() {
		// TODO Auto-generated method stub
		return "INSERT INTO servicio(codigoServicio,nombreServicio,unidad)"+"values(?,?,?)";
	}

	@Override
	public void prepareInsertStatement(PreparedStatement s) throws SQLException {
		// TODO Auto-generated method stub
		/*
		 * Cada setXXX lo que hace es preparar el dato para que el 
		 * driver lo convierta en el SQL de los campos 
		 * VARCHAR, INT, DATE etc...
		 */
		s.setString(1, codigoServicio);
		s.setString(2, nombreServicio);
		s.setInt(3, unidad);
		
	}

	@Override
	public String getUpdateStatement() {
		// TODO Auto-generated method stub
		return "UPDATE servicio SET codigo_servicio=?,nombre_servicio=?,unidad=?" + " WHERE codigoServicio=?";
	}

	@Override
	public void prepareUpdateStatement(PreparedStatement s) throws SQLException {
		// TODO Auto-generated method stub
		s.setString(1, codigoServicio);
		s.setString(2, nombreServicio);
		s.setInt(3, unidad);
		
	}

	@Override
	public String getDeleteStatement() {
		// TODO Auto-generated method stub
		return "DELETE FROM servicio WHERE codigo_servicio = ?";
	}

	@Override
	public void prepareDeleteStatement(PreparedStatement s) throws SQLException {
		// TODO Auto-generated method stub
		s.setString(1, codigoServicio);
		
	}
}