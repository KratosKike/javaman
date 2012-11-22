package inijsp;
import java.io.*;
import java.sql.*;

/*
 * Esta clase se dedica a describir la forma en que se relacionan 
 * la tabla problem_report y developer, con respecto a la cardinalidad
 * mucho a muchos m:n.
 * Ademas esta clase hereda los atributos de la clase DatabaseObject
 * quien realmente hace la interacción con la API JDBC
 * 
 */

public class ProblemReportDeveloper extends DatabaseObject implements Serializable{

	private static final long serialVersionUID = 690734525639971138L;
	/*
	 * Con base a los campos de la base de datos se crean los siguientes campos:
	 * problem_id integer,
	 * developer varchar(50)
	 */
	public int problemId;
	public String developer;

	public ProblemReportDeveloper() {
		// TODO Auto-generated constructor stub
		super();
	}
	public ProblemReportDeveloper(ResultSet results) throws SQLException{
		// TODO Auto-generated constructor stub
		problemId = results.getInt("problem_id");
		developer = results.getString("developer");
		}

	public int getProblemId() {
		return problemId;
	}
	public void setProblemId(int problemId) {
		this.problemId = problemId;
	}
	public String getDeveloper() {
		return developer;
	}
	public void setDeveloper(String developer) {
		this.developer = developer;
	}
	
	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return "problem_report_developer";
	}
	@Override
	public String getFieldList() {
		// TODO Auto-generated method stub
		return "problem_id, developer";
	}

	@Override
	public String getInsertStatement() {
		// TODO Auto-generated method stub
		return "INSERT INTO problem_report_developer (problem_id, developer) values(?,?)";
	}

	@Override
	public void prepareInsertStatement(PreparedStatement s) throws SQLException {
		// TODO Auto-generated method stub
		s.setInt(1, problemId);
		s.setString(2, developer);
	}

	@Override
	public String getUpdateStatement() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void prepareUpdateStatement(PreparedStatement s) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getDeleteStatement() {
		// TODO Auto-generated method stub
		return "DELETE FROM problem_report_developer WHERE "+"problem_id=? and developer=?";
	}

	@Override
	public void prepareDeleteStatement(PreparedStatement s) throws SQLException {
		// TODO Auto-generated method stub
		s.setInt(1, problemId);
		s.setString(2, developer);
	}

	@Override
	public DatabaseObject createInstance(ResultSet results)
			throws SQLException, IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		return new ProblemReportDeveloper(results);
	}
}