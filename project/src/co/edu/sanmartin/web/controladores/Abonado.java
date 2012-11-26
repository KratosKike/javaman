package co.edu.sanmartin.web.controladores;

import java.io.IOException;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * Esta clase se dedica a describir la forma en que se debe hacer CRUD
 * en la tabla abonado de la base de datos proveedor.
 * Ademas esta clase hereda los atributos de la clase DatabaseObject
 * quien realmente hace la interacción con la API JDBC
 * 
 */

public class Abonado extends DatabaseObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9189599326105916249L;
	
	/*
	 * Con base a los campos de la base de datos se crean los siguientes objetos:
	 * numero_documento` VARCHAR(15) NOT NULL ,
	 * `nombre` VARCHAR(120) NOT NULL ,
	 * `tipo_documento` VARCHAR(25) NOT NULL COMMENT 'cedula\\ncedula extrajeria\\nnit' 
	 * `edad` INT(11) NOT NULL ,
	 * `genero` VARCHAR(15) NOT NULL COMMENT '0 masculino\\n1 femenino' ,
	 * `numero_telefono` VARCHAR(25) NOT NULL ,
	 * `direccion` VARCHAR(45) NOT NULL ,
	 * `estrato` INT(11)
	 */
	
	String numeroDocumento;
	String nombre;
	String tipoDocumento;
	int    edad;
	String genero;
	String numeroTelefono;
	String direccion;
	int    estrato;
	
	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getNumeroTelefono() {
		return numeroTelefono;
	}

	public void setNumeroTelefono(String numeroTelefono) {
		this.numeroTelefono = numeroTelefono;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public int getEstrato() {
		return estrato;
	}

	public void setEstrato(int estrato) {
		this.estrato = estrato;
	}
	/*
	 * El metodo constructor se asocia con cada campo de la
	 * tabla creada en la base de datos proveedor llamada 
	 * abonado
	 */
	public Abonado(ResultSet results) throws SQLException {
		// TODO Auto-generated constructor stub
		numeroDocumento 	= results.getString("numero_documento");
		nombre 				= results.getString("nombre");
		tipoDocumento 		= results.getString("tipo_documento");
		edad				= results.getInt("edad");
		genero				= results.getString("genero");
		numeroTelefono		= results.getString("numero_telefono");
		direccion			= results.getString("direccion");
		estrato				= results.getInt("estrato");
	}
	
	/*
	 * referenciando el nombre de la tabla abonado
	 * de la base de datos proveedor 
	 * (non-Javadoc)
	 * @see co.edu.sanmartin.web.controladores.DatabaseObject#getTableName()
	 */
	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return "abonado";
	}
	/*
	 * Creando instancia del metodo DatabaseObject de la base de datos
	 * proveedor tabla abonado
	 * (non-Javadoc)
	 * @see co.edu.sanmartin.web.controladores.DatabaseObject#createInstance(java.sql.ResultSet)
	 */
	@Override
	public DatabaseObject createInstance(ResultSet results)
			throws SQLException, IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		return new Abonado(results);
	}
	
	@Override
	public String getFieldList() {
		// TODO Auto-generated method stub
		return "numeroDocumento,nombre,tipoDocumento,edad,genero,numeroTelefono,direccion,estrato";
	}
	
	// parametros de una inserción en la base de datos
	@Override
	public String getInsertStatement() {
		// TODO Auto-generated method stub
		return "INSERT INTO developer(numeroDocumento,nombre,tipoDocumento,edad,genero,numeroTelefono,direccion,estrato)"+"values(?,?,?,?,?,?,?,?)";
	}
	
	// preparando para realizar la inserción en la base de datos
	@Override
	public void prepareInsertStatement(PreparedStatement s) throws SQLException {
		// TODO Auto-generated method stub
		/*
		 * Cada setXXX lo que hace es preparar el dato para que el 
		 * driver lo convierta en el SQL de los campos 
		 * VARCHAR, INT, DATE etc...
		 */
		s.setString(1, numeroDocumento);
		s.setString(2, nombre);
		s.setString(3, tipoDocumento);
		s.setInt(4, edad);
		s.setString(5, genero);
		s.setString(6, numeroTelefono);
		s.setString(7, direccion);
		s.setInt(8, estrato);
	}

	/*
	 * Metodo para preparar query para realizar update sobre la base de datos
	 * (non-Javadoc)
	 * @see co.edu.sanmartin.web.controladores.DatabaseObject#getUpdateStatement()
	 */
	@Override
	public String getUpdateStatement() {
		// TODO Auto-generated method stub
		return "UPDATE abonado SET nombre=?,tipoDocumento=?,edad=?,genero=?,numeroTelefono=?,direccion=?,estrato=?" + " WHERE numeroDocumento=?";
	}

	@Override
	public void prepareUpdateStatement(PreparedStatement s) throws SQLException {
		// TODO Auto-generated method stub
		s.setString(1, numeroDocumento);
		s.setString(2, nombre);
		s.setString(3, tipoDocumento);
		s.setInt(4, edad);
		s.setString(5, genero);
		s.setString(6, numeroTelefono);
		s.setString(7, direccion);
		s.setInt(8, estrato);
	}

	@Override
	public String getDeleteStatement() {
		// TODO Auto-generated method stub
		return "DELETE FROM abonado WHERE numeroDocumento = ?";
	}
	// asignando valor a borrar de la base datos abonado con respecto a la tabla abonado
	@Override
	public void prepareDeleteStatement(PreparedStatement s) throws SQLException {
		// TODO Auto-generated method stub
		s.setString(1, numeroDocumento);
		
	}
}