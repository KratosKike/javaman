package inijsp;

import java.io.IOException;
import java.sql.*;
import java.util.*;

/*
 * Case Base para los objetos que se asignan a las tablas de bases de datos
 */

public abstract class DatabaseObject {

	/*
	 * Obtiene todos los objetos en una tabla
	 */
	public Vector<?> getAll(Connection conn) throws SQLException, IOException, ClassNotFoundException{
		return getAll(conn, null, null);
	}
	/*
	 * Obtiene todos los objetos de una tabla especificando los objetos
	 */
	public Vector<?> getAll(Connection conn, String whereClause) throws SQLException, IOException, ClassNotFoundException{
		return getAll(conn, whereClause, null);
	}
	/*
	 * Obtiene todos los objetos de una tabla junto con una clausua
	 * where determinada y permite especificar tablas adicionales
	 * que se utilizan en la clausula where
	 */
	public Vector<DatabaseObject> getAll(Connection conn, String whereClause, String additionalTables) throws SQLException, IOException, ClassNotFoundException{
		Statement s = null;
		try{
			s = conn.createStatement();
			Vector<DatabaseObject> v = new Vector<DatabaseObject>();
			
			//Construyendo Query, el query básico "SELECT <campos> FROM tabla"
			String query = "SELECT "+getFieldList()+" FROM "+getTableName();
			
			// agrgando tablas adicionales si es necesario
			if(additionalTables !=null){
				query = query + "," + additionalTables;
			}
			// Agregando clausula Where
			if(whereClause != null){
				query = query + "WHERE" + whereClause;
			}
			ResultSet results = s.executeQuery(query);
			
			// Creando un vector de los resultados
			while (results.next()){
				v.addElement(createInstance(results));
			}
			return v;
		}
		finally{
			if (s!=null){
				try {s.close();} catch (Exception ignore){}
			}
		}
	}
	/*
	 * Ejecuta un query arbitrario y debe devolver todos los campos de 
	 * la tabla al igual que las otras consultas
	 */
	public Vector<DatabaseObject> executeQuery(Connection conn, String query) throws SQLException, IOException, ClassNotFoundException{
		Statement s = null;
		try{
			s = conn.createStatement();
			Vector<DatabaseObject> v = new Vector<DatabaseObject>();
			
			// Ejecutando Query
			ResultSet results = s.executeQuery(query);		
			// Creando un vector con los resultados
			while(results.next()){
				v.addElement(createInstance(results));
			}
			return v;
		}
		finally{
			if (s!=null){
				try {s.close();} catch (Exception ignore){}
			}
		}
	}
	/*
	 * Insertando un objeto en la base de datos
	 */
	public int insert(Connection conn)throws SQLException{
		PreparedStatement s = null;
		try{
			// Objeto para crear su propia instrucción de inserción
			s = conn.prepareStatement(getInsertStatement());
			
			// Llenando la instrucción de inserción con los valores de los datos
			prepareInsertStatement(s);
			return s.executeUpdate();
		}
		finally{
			if (s!=null){
				try {s.close();} catch (Exception ignore){}
			}
		}
	}
	/*
	 * El siguiente metodo devuelve una lista de los campos en la tabla 
	 * (este se utiliza para seleccionar campos)
	 */
	public abstract String getFieldList();
	
		/*
		 * Devuelve una lista de campos de la tabla mientras se
		 * especifica los nombres de los campos, con alias por ejemplo
		 * tabla.campo  
		 */
	public String getFieldList(String tableName){
		// Creando tokenizer para analizar en la lista original
		StringTokenizer fieldList = new StringTokenizer(getFieldList());
		// Creando String buffer con los resultados de la lista
		StringBuffer newList = new StringBuffer();
		boolean first = true;
		
		while (fieldList.hasMoreTokens()){
			String field = fieldList.nextToken();
			if(!first) newList.append(',');
			first = false;
			newList.append(tableName);
			newList.append('.');
			newList.append(field);
			newList.append(" as ");
			newList.append(tableName);
		}
		return newList.toString();
	}
	
	/*
	 * Creando instrucción para la inserción en PrepareStatement
	 */
	public abstract String getInsertStatement();
	
	/*
	 * En este metodo almacena los datos del objeto en el PreparedStatement
	 * y devuelve PrepareInsertStatement.
	 */
	public abstract void prepareInsertStatement(PreparedStatement s) throws SQLException;
	
	/*
	 * Actualizando la base datos especificando la fila que contiene
	 * el objeto
	 */
	
	public int update(Connection conn) throws SQLException{
		PreparedStatement s = null;
	try{
		s = conn.prepareStatement(getUpdateStatement());
		prepareUpdateStatement(s);
		return s.executeUpdate();
	}finally{ 
		if (s!=null){
			try {s.close();} catch (Exception ignore){}
		}
	}
	}
	public abstract String getUpdateStatement();
	public abstract void prepareUpdateStatement(PreparedStatement s) throws SQLException;
	
	/*
	 * Devuelve el número de secuencias utilidas para insertar el objeto
	 * Este metodo es muy dependientede la base de datos.
	 */
	
	public int getSequenceNumber(Connection conn) throws SQLException{
		Statement s = null;
		try{
			s = conn.prepareStatement(getInsertStatement());
			ResultSet results = s.executeQuery("SELECT last_insert_id()");
			if(results.next()){
				return results.getInt(1);
			}
			else{
				throw new SQLException("Unable to generate sequence number");
			}
		}
		finally{
			if (s!=null){
				try {s.close();} catch (Exception ignore){}
			}
		}
	}
	
	public abstract String getDeleteStatement();
	public abstract void prepareDeleteStatement(PreparedStatement s) throws SQLException;
	
	public abstract String getTableName();
	public abstract DatabaseObject createInstance(ResultSet results) throws SQLException,IOException, ClassNotFoundException;
	public String getSequenceGenerator(){return null;}
}