package co.edu.sanmartin.web.controladores;

import java.io.*;
import java.sql.*;
/*
 * Esta clase se dedica a describir la forma en que se debe hacer CRUD
 * en la tabla servicios_estrato de la base de datos proveedor.
 * Ademas esta clase hereda los atributos de la clase DatabaseObject
 * quien realmente hace la interacción con la API JDBC
 * 
 */

public class ServicioEstrato extends DatabaseObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4096715643665399692L;
	
	/*
	 * Con base a los campos de la base de datos se crean los siguientes objetos:
	 * `codigo_servicio_estrato` VARCHAR(45) NOT NULL ,
	 * `estrato` INT(11) NOT NULL 
	 * `valor` FLOAT NOT NULL COMMENT 'campo de precisión simple' ,
	 * `codigo_servicio` VARCHAR(45) NOT NULL
	 */
	
	String 	codigoServicioEstrato;
	int		estrato;
	float	valor;
	String codigoServicio;
	
	public String getCodigoServicioEstrato() {
		return codigoServicioEstrato;
	}

	public void setCodigoServicioEstrato(String codigoServicioEstrato) {
		this.codigoServicioEstrato = codigoServicioEstrato;
	}

	public int getEstrato() {
		return estrato;
	}

	public void setEstrato(int estrato) {
		this.estrato = estrato;
	}

	public float getValor() {
		return valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}
	
	public String getCodigoServicio() {
		return codigoServicio;
	}

	public void setCodigoServicio(String codigoServicio) {
		this.codigoServicio = codigoServicio;
	}

	public ServicioEstrato(ResultSet results) throws SQLException {
		// TODO Auto-generated constructor stub
		codigoServicioEstrato = results.getString("codigo_servicio_estrato");
		estrato				  = results.getInt("estrato");
		valor				  = results.getFloat("valor");
		codigoServicio		  = results.getString("codigo_servicio");
	}
	
	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return "servicio_estrato";
	}

	@Override
	public DatabaseObject createInstance(ResultSet results)
			throws SQLException, IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		return new ServicioEstrato(results);
	}


	@Override
	public String getFieldList() {
		// TODO Auto-generated method stub
		return "codigoServicioEstrato,estrato,valor,codigoServicio";
	}

	@Override
	public String getInsertStatement() {
		// TODO Auto-generated method stub
		return "INSERT INTO servicio_estrato(codigo_servicio_estrato,estrato,valor,codigo_servicio)"+"values(?,?,?,?)";
	}

	@Override
	public void prepareInsertStatement(PreparedStatement s) throws SQLException {
		// TODO Auto-generated method stub
		s.setString(1, codigoServicioEstrato);
		s.setInt(2, estrato);
		s.setFloat(3, valor);
		s.setString(4, codigoServicio);
	}

	@Override
	public String getUpdateStatement() {
		// TODO Auto-generated method stub
		return "UPDATE servicio SET estrato=?,valor=?,codigo_servicio=?" + " WHERE codigo_servicio_estrato=?";
	}

	@Override
	public void prepareUpdateStatement(PreparedStatement s) throws SQLException {
		// TODO Auto-generated method stub
		s.setString(1, codigoServicioEstrato);
		s.setInt(2, estrato);
		s.setFloat(3, valor);
		s.setString(4, codigoServicio);
	}

	@Override
	public String getDeleteStatement() {
		// TODO Auto-generated method stub
		return "DELETE FROM servicio_estrato WHERE codigo_servicio_estrato=?";
	}

	@Override
	public void prepareDeleteStatement(PreparedStatement s) throws SQLException {
		// TODO Auto-generated method stub
		s.setString(1, codigoServicioEstrato);
	}
}
