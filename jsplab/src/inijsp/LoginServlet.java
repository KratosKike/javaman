package inijsp; 
/*
 * Hay que configurar y buscar la ruta para cargar javax.servlet.*
 * /usr/share/tomcat6/lib/servlet-api.jar
 * y el servidor
 */
import java.io.*;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

/*
 * LoginServlet realiza el inicio de sesión. 
 * este el punto de entrada en el sistema,  donde se asigna el pool de conexiones de base de datos 
 * ocurre la primera vez que el servlet se ejecuta. 
 */
public class LoginServlet extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7379585428398443212L;
	// datos desde el formulario de acceso
	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		
		String username = request.getParameter("username");
		String password = request.getParameter("username");
		
		if (username == null){
			request.setAttribute("rejectReason", "No username specified");
			rejectLogin(request, response);
			return;
		}
		if (password == null){
			request.setAttribute("rejectReason", "No password specified");
			rejectLogin(request, response);
			return;
		}
		// Get del contexto "aplicaction"
		ServletContext context = getServletContext();
		// verificar si el pool de conexiones se a realizado
		IconnectionPool pool = (IconnectionPool) context.getAttribute("connectionPool");
		
		// si la conexión no esta creada
		if (pool == null){
			/*
			 * Sincroniza el objet de la aplicación y comprueba una vez más
			 * que el pool se crea
			 */
			synchronized(context){
				try{
					Class.forName("org.gjt.mm.mysql.Driver").newInstance();
				}catch (Exception exc){
					getServletContext().log("Error loading JDBC driver", exc);
				}
				/*
				 * Crear conexión y almacenarlo en el objeto de la aplicación
				 */
				pool = new SimpleConnectionPool("jdbc:mysql//localhost/inijsp", "root", "3193115");
				context.setAttribute("connectionPool", pool);
			}
		}
		Connection conn = null;
		
		try{
			conn = pool.getConnection();
			// creando Conexión
			Developer devQuery = new Developer();
			/*
			 * Busca el objeto revelador que coincida con el nombre de usuario 
			 * introducido en el formulario
			 */
			Vector v = devQuery.getAll(conn,"username='"+username.toLowerCase()+"'");
			// Si el nombre de usuario no existe, muestra un error
			if (v.size() != 1){
				request.setAttribute("rejectReason", "Invalid Username");
				
				rejectLogin(request, response);
				
				return;
			}
			
		// Asegúrearse de que las contraseñas coinciden	
				Developer d = (Developer) v.elementAt(0);
				
				if(!d.password.equals(password)){
					request.setAttribute("rejectReason", "Invalid Password");
					
					rejectLogin(request, response);
					
					return;
				}
				
				HttpSession session = request.getSession();
				
				session.setAttribute("username", username);
				
				session.setAttribute("developer", d.getDeveloper());
				// Enviar al usuario a la pantalla principal o la que se genera index
				// Ruta de donde se encuentra la clase
				RequestDispatcher dispatch = context.getRequestDispatcher("ProblemReporting.jsp");
				
				dispatch.forward(request, response);
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			if(conn == null){
				try {
					pool.releaseConnection(conn);				
				}catch (Exception ignore){
				}
			}
		}
	}
	/*
	 * Muestra un error si el inicio de sesión falla por cualquier razón
	 */
	public void rejectLogin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		// Ruta de donde se encuentra la clase
		RequestDispatcher dispatch = getServletContext().getRequestDispatcher("RejectLogin.jsp");
		
		dispatch.forward(request, response)
;	}
}
