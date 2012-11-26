package co.edu.sanmartin.web.controladores;

import java.io.IOException;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * Esta clase se dedica a describir la forma en que se debe hacer CRUD
 * en la tabla factura_Servicio_estrato de la base de datos proveedor.
 * Ademas esta clase hereda los atributos de la clase DatabaseObject
 * quien realmente hace la interacción con la API JDBC
 * 
 */

public class FacturaServicioEstrato extends DatabaseObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7229075608489192063L;
	
	/*
	 * Con base a los campos de la base de datos se crean los siguientes objetos:
	 *`codigo_factura` VARCHAR(45) NOT NULL ,
	 *`codigo_servicio` VARCHAR(45) NOT NULL ,
	 *`consumo` INT(11) NOT NULL ,
	 */
	String codigoFactura;
	String codigoServicio;
	int    consumo;
	
	public String getCodigoFactura() {
		return codigoFactura;
	}

	public void setCodigoFactura(String codigoFactura) {
		this.codigoFactura = codigoFactura;
	}

	public String getCodigoServicio() {
		return codigoServicio;
	}

	public void setCodigoServicio(String codigoServicio) {
		this.codigoServicio = codigoServicio;
	}

	public int getConsumo() {
		return consumo;
	}

	public void setConsumo(int consumo) {
		this.consumo = consumo;
	}

	public FacturaServicioEstrato() {
		// TODO Auto-generated constructor stub
	}
	
	public FacturaServicioEstrato(ResultSet results) throws SQLException {
		// TODO Auto-generated constructor stub
		codigoFactura 	= results.getString("codigo_factura");
		codigoServicio 	= results.getString("codigo_servicio");
		consumo			= results.getInt("consumo");
	}
	
	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return "factura_has_servicio_estrato";
	}

	@Override
	public DatabaseObject createInstance(ResultSet results)
			throws SQLException, IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		return new FacturaServicioEstrato(results);
	}

	@Override
	public String getFieldList() {
		// TODO Auto-generated method stub
		return "codigoFactura,codigoServicio,consumo";	
	}

	@Override
	public String getInsertStatement() {
		// TODO Auto-generated method stub
		return "INSERT INTO factura_has_servicio_estrato(codigo_factura,codigo_servicio,consumo)"+"values(?,?,?)";
	}

	@Override
	public void prepareInsertStatement(PreparedStatement s) throws SQLException {
		// TODO Auto-generated method stub
		s.setString(1, codigoFactura);
		s.setString(2, codigoServicio);
		s.setInt(3, consumo);
	}

	@Override
	public String getUpdateStatement() {
		// TODO Auto-generated method stub
		return "UPDATE factura_has_servicio_estrato SET consumo=?" + " WHERE codigo_factura=? AND codigo_servicio=?";
	}

	@Override
	public void prepareUpdateStatement(PreparedStatement s) throws SQLException {
		// TODO Auto-generated method stub
		s.setString(1, codigoFactura);
		s.setString(2, codigoServicio);
		s.setInt(3, consumo);	
	}

	@Override
	public String getDeleteStatement() {
		// TODO Auto-generated method stub
		return "DELETE FROM factura_has_servicio_estrato WHERE codigo_factura = ? AND codigo_servicio=?";
	}

	@Override
	public void prepareDeleteStatement(PreparedStatement s) throws SQLException {
		// TODO Auto-generated method stub
		s.setString(1, codigoFactura);
	}
}