package co.edu.sanmartin.web.controladores;

import java.io.IOException;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

/*
 * Esta clase se dedica a describir la forma en que se debe hacer CRUD
 * en la tabla factura de la base de datos proveedor.
 * Ademas esta clase hereda los atributos de la clase DatabaseObject
 * quien realmente hace la interacción con la API JDBC
 * 
 */

public class Factura extends DatabaseObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 730199030723020552L;
	
	/*
	 * Con base a los campos de la base de datos se crean los siguientes objetos:
	 * `codigo_factura` VARCHAR(45) NOT NULL ,
	 * `fecha_creacion_factura` TIMESTAMP NOT NULL ,
	 * `abonado_numero_documento` VARCHAR(15) NOT NULL 
	 */
	String codigoFactura;
	Date   fechaCreacionFactura;
	// objeto que interactua con la tabla abonado
	String abonadoNumeroDocumeto;
	
	
	public String getCodigoFactura() {
		return codigoFactura;
	}

	public void setCodigoFactura(String codigoFactura) {
		this.codigoFactura = codigoFactura;
	}

	public Date getFechaCreacionFactura() {
		return fechaCreacionFactura;
	}

	public void setFechaCreacionFactura(Date fechaCreacionFactura) {
		this.fechaCreacionFactura = fechaCreacionFactura;
	}

	public String getAbonadoNumeroDocumeto() {
		return abonadoNumeroDocumeto;
	}

	public void setAbonadoNumeroDocumeto(String abonadoNumeroDocumeto) {
		this.abonadoNumeroDocumeto = abonadoNumeroDocumeto;
	}

	public Factura() {
		// TODO Auto-generated constructor stub
	}
	
	public Factura(ResultSet results) throws SQLException {
		// TODO Auto-generated constructor stub
		codigoFactura = results.getString("codigo_factura");
		fechaCreacionFactura = results.getTimestamp("fecha_creacion_factura");
		abonadoNumeroDocumeto = results.getString("abonado_numero_documento");
	}
	
	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return "factura";
	}

	@Override
	public DatabaseObject createInstance(ResultSet results)
			throws SQLException, IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		return new Factura(results);
	}
	@Override
	public String getFieldList() {
		// TODO Auto-generated method stub
		return "codigoFactura,fechaCreacionFactura,	abonadoNumeroDocumeto";
	}

	@Override
	public String getInsertStatement() {
		// TODO Auto-generated method stub
		return "INSERT INTO factura(codigo_factura,fecha_creacion_factura,abonado_numero_documeto)"+"values(?,?,?)";
	}

	@Override
	public void prepareInsertStatement(PreparedStatement s) throws SQLException {
		// TODO Auto-generated method stub
		s.setString(1, codigoFactura);
		s.setTimestamp(2, (Timestamp) fechaCreacionFactura);
		s.setString(3, abonadoNumeroDocumeto);
	}

	@Override
	public String getUpdateStatement() {
		// TODO Auto-generated method stub
		return "UPDATE factura SET fecha_creacion_factura=?,abonado_numero_documeto=?" + " WHERE codigoFactura=?";
	}

	@Override
	public void prepareUpdateStatement(PreparedStatement s) throws SQLException {
		// TODO Auto-generated method stub
		s.setString(1, codigoFactura);
		s.setTimestamp(2, (Timestamp) fechaCreacionFactura);
		s.setString(3, abonadoNumeroDocumeto);
	}

	@Override
	public String getDeleteStatement() {
		// TODO Auto-generated method stub
		return "DELETE FROM factura WHERE codigo_factura = ?";
	}

	@Override
	public void prepareDeleteStatement(PreparedStatement s) throws SQLException {
		// TODO Auto-generated method stub
		s.setString(1, codigoFactura);
	}

}
