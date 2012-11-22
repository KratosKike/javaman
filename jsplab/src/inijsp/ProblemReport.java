package inijsp;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.Date;

/*
 * Esta clase se dedica a describir la forma en que se debe hacer CRUD
 * en la tabla problem_report.
 * Ademas esta clase hereda los atributos de la clase DatabaseObject
 * quien realmente hace la interacción con la API JDBC
 * 
 */
public class ProblemReport extends DatabaseObject implements Serializable{
	
	private static final long serialVersionUID = 1681576605017418385L;
	/*
	 * Con base a los campos de la base de datos se crean los siguientes campos:
	 * problem_id integer not null primary key auto_increment,
	 * entered timestamp, 
	 * system varchar(20), 
	 * subsystem varchar (20),  
	 * originator varchar(20), 
	 * description text, 
	 * priority integer, 
	 * status varchar(20), 
	 * ecd date, 
	 * notes blob
	 */	

	public int problemId;
	public Date entered;
	public String system;
	public String subsystem;
	public String originator;
	public String description;
	public int priority;
	public String status;
	public Date estimatedCompletionDate;
	public Vector<byte[]> notes;
	
	public ProblemReport() {
		// TODO Auto-generated constructor stub
		super();
	}
	
	@SuppressWarnings("unchecked")
	public ProblemReport(ResultSet results) throws SQLException, IOException, ClassNotFoundException{
		// se refiere a cada uno de los campos de la tabla en la base de datos
		problemId   = results.getInt("problem_id");
		entered     = results.getTimestamp("entered");
		system      = results.getString("system");
		subsystem   = results.getString("subsystem");
		originator  = results.getString("originator") ;
		description = results.getString("description");
		priority    = results.getInt("priority");
		status		= results.getString("status");
		estimatedCompletionDate	= results.getTimestamp("ecd");
		/*
		 * MySQL devuelve el objeto notas como una matriz de bytes
		 */
		Object tempNotes = results.getObject("notes");
		if (tempNotes == null){
			notes = new Vector<byte[]>(0);
		}
		else if(tempNotes instanceof Vector){
			notes = (Vector<byte[]>) results.getObject("notes");
		}
		else if (tempNotes instanceof byte[]){
			try{
				// convertir los bytes dentro de un objeto array
				ObjectInputStream obIn = new ObjectInputStream(new ByteArrayInputStream((byte[]) tempNotes));
				notes = (Vector<byte[]>) obIn.readObject();
				obIn.close();
			}catch (Exception exc){
			exc.printStackTrace();
			}
		}
		else{
			// Indicador real del error
			System.out.println("The note object is: "+tempNotes.getClass().getName());
		}
	}
	
	public int getProblemId() {
		return problemId;
	}

	public void setProblemId(int problemId) {
		this.problemId = problemId;
	}

	public Date getEntered() {
		return entered;
	}

	public void setEntered(Date entered) {
		this.entered = entered;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public String getSubsystem() {
		return subsystem;
	}

	public void setSubsystem(String subsystem) {
		this.subsystem = subsystem;
	}

	public String getOriginator() {
		return originator;
	}

	public void setOriginator(String originator) {
		this.originator = originator;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getEstimatedCompletionDate() {
		return estimatedCompletionDate;
	}

	public void setEstimatedCompletionDate(Date estimatedCompletionDate) {
		this.estimatedCompletionDate = estimatedCompletionDate;
	}

	public Vector<byte[]> getNotes() {
		return notes;
	}

	public void setNotes(Vector<byte[]> notes) {
		this.notes = notes;
	}
	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return "problem_report";
	}
	
	@Override
	public DatabaseObject createInstance(ResultSet results) throws SQLException, IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		return new ProblemReport(results);
	}
	
	public int insert(Connection conn) throws SQLException{
		int numRows = super.insert(conn);
		problemId = getSequenceNumber(conn);
		return numRows;
	}
	
	@Override
	public String getFieldList() {
		// TODO Auto-generated method stub
		return "problem_id, entered, system, subsystem, originator, description, priority, status, ecd, notes";
	}

	@Override
	public String getInsertStatement() {
		// TODO Auto-generated method stub
		return "INSERT INTO problem_report(problem_id, entered, system, subsystem, originator, description, priority, status, ecd, notes) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	}

	@Override
	public void prepareInsertStatement(PreparedStatement s) throws SQLException {
		// TODO Auto-generated method stub
		s.setInt(1, 0);
		s.setTimestamp(2, new Timestamp(entered.getTime()));
		s.setString(3, system);
		s.setString(4, subsystem);
		s.setString(5, originator);
		s.setString(6, description);
		s.setInt(7, priority);
		s.setString(8, status);
		s.setDate(9, new java.sql.Date(estimatedCompletionDate.getTime()));
		s.setObject(10, notes);
	}

	@Override
	public String getUpdateStatement() {
		// TODO Auto-generated method stub
		return "UPDATE problem_report SET entered=?, system=?, subsystem=?, originator=?, description=?, priority=?, status=?, ecd=?, notes=?";
	}

	@Override
	public void prepareUpdateStatement(PreparedStatement s) throws SQLException {
		// TODO Auto-generated method stub
		s.setInt(1, 0);
		s.setTimestamp(2, new Timestamp(entered.getTime()));
		s.setString(3, system);
		s.setString(4, subsystem);
		s.setString(5, originator);
		s.setString(6, description);
		s.setInt(7, priority);
		s.setString(8, status);
		s.setDate(9, new java.sql.Date(estimatedCompletionDate.getTime()));
		s.setObject(10, notes);
		
	}

	@Override
	public String getDeleteStatement() {
		// TODO Auto-generated method stub
		return "DELETE FROM problem_report WHERE problem_id=?";
	}

	@Override
	public void prepareDeleteStatement(PreparedStatement s) throws SQLException {
		// TODO Auto-generated method stub
		s.setInt(1, problemId);
	}
	
	public String getSequenceGenerator(){
		return "problem_id_gen";
	}
}
