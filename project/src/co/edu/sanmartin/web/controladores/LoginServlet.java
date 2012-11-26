package co.edu.sanmartin.web.controladores;

import java.io.*;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class LoginServlet extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7379585428398443212L;
	// datos desde el formulario de acceso
	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		
		String usuario = request.getParameter("usuario");
		String clave = request.getParameter("clave");
		// manejo de excepeciones
		if (usuario == null){
			request.setAttribute("rejectReason", "Usuario no especificado");
			rejectLogin(request, response);
			return;
		}
		// Get del contexto "aplicaction"
		ServletContext context = getServletContext();
		// verificar si el pool de conexiones se a realizado
		IConnectionPool pool = (IConnectionPool) context.getAttribute("connectionPool");
		
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
				pool = new SimpleConnectionPool("jdbc:mysql//localhost/proveedor", "root", "3193115");
				context.setAttribute("connectionPool", pool);
			}
		}
		Connection conn = null;
		
		try{
			conn = pool.getConnection();
			// creando Conexión
			Usuario devQuery = new Usuario();
			/*
			 * Busca el objeto revelador que coincida con el nombre de usuario 
			 * introducido en el formulario
			 */
			Vector v = devQuery.getAll(conn,"username='"+usuario.toLowerCase()+"'");
			// Si el nombre de usuario no existe, muestra un error
			if (v.size() != 1){
				request.setAttribute("rejectReason", "Invalid Username");
				
				rejectLogin(request, response);
				
				return;
			}
			
		// Asegúrearse de que las contraseñas coinciden	
				Usuario d = (Usuario) v.elementAt(0);
				
				if(!d.clave.equals(clave)){
					request.setAttribute("rejectReason", "Invalid Password");
					
					rejectLogin(request, response);
					
					return;
				}
				
				HttpSession session = request.getSession();
				
				session.setAttribute("username", usuario);
				
				session.setAttribute("Usuario", d.getUsuario());
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