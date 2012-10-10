/*
 * Trabajo inicialmente complementado por:
 * Alejandro Romero
 * Jonatan Loaiza
 * Vanessa Chacon
 */
package sockets;
/******************************************************/
/******************************************************/
/******************************************************/
//Configurar un servidor que reciba una conexi—n de un cliente, envíe
//una cadena al cliente y cierre la conexi—n.
import java.io.*;
import java.net.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/******************************************************/
/******************************************************/
/******************************************************/
/*
 ******************************************************
 ******************************************************
 ******************************************************
 * FUNDACIÓN UNIVERSITARIA SAN MARTÍN
 * FACULTAD DE EDUCACIÓN ABIERTA Y A DISTANCIA
 * PROGRAMA DE INGENIERÍA DE SISTEMAS
 * SISTEMAS DISTRIBUIDOS
 ******************************************************
 ******************************************************
 ******************************************************
 * Clase Java para implementar una aplicaci—n servidora
 * que implementa un servidor de Sockets (ServerSocket)
 * y permite la conexi—n de un cliente.
 * 
 * Es de aclarar, que dada la estructura de implementaci—n
 * de esta clase, el servidor permite procesar una conexi—n
 * a la vez, por lo que el siguiente cliente solo podrá
 * conectarse al servidor de sockets, una vez se
 * ha liberado la conexi—n del cliente actual que está
 * interactuando con el servidor.
 ******************************************************
 ******************************************************
 ******************************************************
 * */
public class Servidor extends JFrame 
{
	/******************************************************/
	/******************************************************/
	/******************************************************/
	//Atributos de la clase
	private static final long serialVersionUID = 427070726708070735L;
	private JTextField campoIntroducir;
	private JTextArea areaPantalla;
	private ObjectOutputStream salida;
	private ObjectInputStream entrada;
	private ServerSocket servidor;
	private Socket conexion;
	private int contador = 1;
	// Array del tipo String para los valores
	private String[] lista;
	/******************************************************/
	/******************************************************/
	/******************************************************/
	// configurar GUI
	public Servidor()
	{
		super( "Servidor" );

		Container contenedor = getContentPane();

		// crear campoIntroducir y registrar componente de escucha
		campoIntroducir = new JTextField();
		campoIntroducir.setEditable( false );
		campoIntroducir.addActionListener(new ActionListener() {

					// enviar mensaje al cliente
					public void actionPerformed( ActionEvent evento )
					{
						enviarDatos( evento.getActionCommand() );
						campoIntroducir.setText( "" );
					}
				}  
		); 

		contenedor.add( campoIntroducir, BorderLayout.NORTH );

		// crear areaPantalla
		areaPantalla = new JTextArea();
		contenedor.add( new JScrollPane( areaPantalla ), 
				BorderLayout.CENTER );
        // Aumentando ancho de la ventana para visualizar mejor
		setSize( 500, 300 );
		setVisible( true );
	} // fin del constructor de Servidor
	/******************************************************/
	/******************************************************/
	/******************************************************/
	// configurar y ejecutar el servidor 
	public void ejecutarServidor()
	{
		// configurar servidor para que reciba conexiones; procesar las conexiones
		try {

			// Paso 1: crear un objeto ServerSocket.
			servidor = new ServerSocket(  12345, 100 );

			while ( true ) {

				try {
					esperarConexion(); // Paso 2: esperar una conexi—n.
					obtenerFlujos();        // Paso 3: obtener flujos de entrada y salida.
					procesarConexion(); // Paso 4: procesar la conexi—n.
				}

				// procesar excepci—n EOFException cuando el cliente cierre la conexi—n 
				catch ( EOFException excepcionEOF ) {
					System.err.println( "El servidor termin— la conexi—n" );
				}

				finally {
					cerrarConexion();   // Paso 5: cerrar la conexi—n.
					++contador;
				}

			} // fin de instrucci—n while

		} // fin del bloque try

		// procesar problemas con E/S
		catch ( IOException excepcionES ) {
			excepcionES.printStackTrace();
		}

	} // fin del método ejecutarServidor
	/******************************************************/
	/******************************************************/
	/******************************************************/
	// esperar que la conexi—n llegue, después mostrar informaci—n de la conexi—n
	private void esperarConexion() throws IOException
	{
		mostrarMensaje( "Esperando una conexi—n\n" );
		conexion = servidor.accept(); // permitir al servidor aceptar la conexi—n            
		mostrarMensaje( "Conexi—n " + contador + " recibida de: " +
				conexion.getInetAddress().getHostName() );
	}
	/******************************************************/
	/******************************************************/
	/******************************************************/
	// obtener flujos para enviar y recibir datos
	private void obtenerFlujos() throws IOException
	{
		// establecer flujo de salida para los objetos
		salida = new ObjectOutputStream( conexion.getOutputStream() );
		salida.flush(); // vaciar búfer de salida para enviar informaci—n de encabezado

		// establecer flujo de entrada para los objetos
		entrada = new ObjectInputStream( conexion.getInputStream() );

		mostrarMensaje( "\nSe recibieron los flujos de E/S\n" );
	}
	/******************************************************/
	/******************************************************/
	/******************************************************/
	// procesar la conexi—n con el cliente
	private void procesarConexion() throws IOException
	{
		// enviar mensaje de conexi—n exitosa al cliente
		String mensaje = "Conexi—n exitosa";
		enviarDatos( mensaje );

		// habilitar campoIntroducir para que el usuario del servidor pueda enviar mensajes
		establecerCampoTextoEditable( true );

		do { // procesar los mensajes enviados por el cliente

			// leer el mensaje y mostrarlo en pantalla
			try {
				mensaje = ( String ) entrada.readObject();
				
				// OPERACION SUMAR
				/* Revisar cadena valor para realizar operaciones
				 * Con el metodo indexOf verifico si en el String existen 
				 */
				if(mensaje.indexOf("+")!=-1 && mensaje.indexOf("CLIENTE>>>")!=-1 && mensaje.matches("-/*") == false){
					// verificando que no existan mas caracteres solo el de la primera operacion
					if(mensaje.indexOf("-")==-1 && mensaje.indexOf("*")==-1 && mensaje.indexOf("/")==-1){
						/* 
						 * con split separo los valores diferentes al + los de la izquierda del
						 * signo en lista[0] y los de la derecha lista[1]
						 * con replace quito la parte del String que se refiere a "CLIENTE>>>"
						 * con trim() quito espacios en blanco que puden generar errores
						 * al procesar la cadena para la operaci—n
						*/
						lista = mensaje.replace("CLIENTE>>>", "").trim().split("\\+");
						/* instanciando objeto "operacion" de la clase Operacion 
						 * Conviertiendo los valores de cadena a enteros
						 * y quitando espacios en blanco innecesarios
						 * */
						Operacion operacion = new Operacion(Integer.parseInt(lista[0].trim()), Integer.parseInt(lista[1].trim()));
						mensaje = String.valueOf(operacion.sumarValores());
						mostrarMensaje( "\n SERVIDOR>>> Respuesta de operaci—n solicitada del CLIENTE: " + mensaje );
						enviarDatos("\n SERVIDOR>>> El resultado de la suma entre: "+lista[0].trim() +" y "+ lista[1].trim() + " es " + mensaje);
					}else{
						mostrarMensaje( "\n SERVIDOR>>> Caracter no correcto solicitado por el CLIENTE: ");
						enviarDatos("\n SERVIDOR>>> Caracter no correcto solicitado por el CLIENTE: ");
					}	
				}
				// OPERACION RESTAR
				else if(mensaje.indexOf("-")!=-1 && mensaje.indexOf("CLIENTE>>>")!=-1){
					if(mensaje.indexOf("+")==-1 && mensaje.indexOf("*")==-1 && mensaje.indexOf("/")==-1){
						/* 
						 * con split separo los valores diferentes al + los de la izquierda del
						 * signo en lista[0] y los de la derecha lista[1]
						 * con replace quito la parte del String que se refiere a "CLIENTE>>>"
						 * con trim() quito espacios en blanco que puden generar errores
						 * al procesar la cadena para la operaci—n
						*/
						lista = mensaje.replace("CLIENTE>>>", "").trim().split("\\-");
						/* instanciando objeto "operacion" de la clase Operacion 
						 * Conviertiendo los valores de cadena a enteros
						 * y quitando espacios en blanco innecesarios
						 * */
						Operacion operacion = new Operacion(Integer.parseInt(lista[0].trim()), Integer.parseInt(lista[1].trim()));
						mensaje = String.valueOf(operacion.restarValores());
						mostrarMensaje( "\n SERVIDOR>>> Respuesta de operaci—n solicitada del CLIENTE: " + mensaje );
						enviarDatos("\n SERVIDOR>>> El resultado de la resta entre: "+lista[0].trim() +" y "+ lista[1].trim() + " es " + mensaje);
					}else{
						mostrarMensaje( "\n SERVIDOR>>> Caracter no correcto solicitado por el CLIENTE: ");
						enviarDatos("\n SERVIDOR>>> Caracter no correcto solicitado por el CLIENTE: ");
					}
				}
				// OPERACION MULTIPLICAR
				else if(mensaje.indexOf("*")!=-1 && mensaje.indexOf("CLIENTE>>>")!=-1){
					if(mensaje.indexOf("-")==-1 && mensaje.indexOf("+")==-1 && mensaje.indexOf("/")==-1){
						/* 
						 * con split separo los valores diferentes al + los de la izquierda del
						 * signo en lista[0] y los de la derecha lista[1]
						 * con replace quito la parte del String que se refiere a "CLIENTE>>>"
						 * con trim() quito espacios en blanco que puden generar errores
						 * al procesar la cadena para la operaci—n
						*/
						lista = mensaje.replace("CLIENTE>>>", "").trim().split("\\*");
						/* instanciando objeto "operacion" de la clase Operacion 
						 * Conviertiendo los valores de cadena a enteros
						 * y quitando espacios en blanco innecesarios
						 * */
						Operacion operacion = new Operacion(Integer.parseInt(lista[0].trim()), Integer.parseInt(lista[1].trim()));
						mensaje = String.valueOf(operacion.multiplicaValores());
						mostrarMensaje( "\n SERVIDOR>>> Respuesta de operaci—n solicitada del CLIENTE: " + mensaje );
						enviarDatos("\n SERVIDOR>>> El resultado de la multiplicacion entre: "+lista[0].trim() +" y "+ lista[1].trim() + " es " + mensaje);
					}else{
						mostrarMensaje( "\n SERVIDOR>>> Caracter no correcto solicitado por el CLIENTE: ");
						enviarDatos("\n SERVIDOR>>> Caracter no correcto solicitado por el CLIENTE: ");
					}				}
				// OPERACION DIVIDIR
				else if(mensaje.indexOf("/")!=-1 && mensaje.indexOf("CLIENTE>>>")!=-1){
					if(mensaje.indexOf("-")==-1 && mensaje.indexOf("*")==-1 && mensaje.indexOf("+")==-1){
						/* 
						 * con split separo los valores diferentes al + los de la izquierda del
						 * signo en lista[0] y los de la derecha lista[1]
						 * con replace quito la parte del String que se refiere a "CLIENTE>>>"
						 * con trim() quito espacios en blanco que puden generar errores
						 * al procesar la cadena para la operaci—n
						*/
						lista = mensaje.replace("CLIENTE>>>", "").trim().split("\\/");
						/* instanciando objeto "operacion" de la clase Operacion 
						 * Conviertiendo los valores de cadena a enteros
						 * y quitando espacios en blanco innecesarios
						 * */
						// Verificando denominador
						if(Integer.parseInt(lista[1].trim()) == 0){
							mostrarMensaje( "\n SERVIDOR>>> El valor enviado por el CLIENTE en el denominador es 0" );
							enviarDatos("\n SERVIDOR>>>El valor enviado por el CLIENTE en el denominador es 0");

						}else{
							/* instanciando objeto "operacion" de la clase Operacion 
							 * Conviertiendo los valores de cadena a enteros
							 * y quitando espacios en blanco innecesarios
							 * */
							Operacion operacion = new Operacion(Integer.parseInt(lista[0].trim()), Integer.parseInt(lista[1].trim()));
							mensaje = String.valueOf(operacion.divideValores());
							mostrarMensaje( "\n SERVIDOR>>> Respuesta de operaci—n solicitada del CLIENTE: " + mensaje );
							enviarDatos("\n SERVIDOR>>> El resultado de la division entre: "+lista[0].trim() +" y "+ lista[1].trim() + " es " + mensaje);
						}					
						}else{
						mostrarMensaje( "\n SERVIDOR>>> Caracter no correcto solicitado por el CLIENTE: ");
						enviarDatos("\n SERVIDOR>>> Caracter no correcto solicitado por el CLIENTE: ");
					}
				}else {
					mostrarMensaje( "\n  SERVIDOR>>> El CLIENTE No ingreso una operaci—n correcta" );
					enviarDatos("\n SERVIDOR>>> No ingreso datos correctos o no es una operaci—n");
				}
			}

			// atrapar problemas que pueden ocurrir al tratar de leer del cliente
			catch ( ClassNotFoundException excepcionClaseNoEncontrada ) {
				mostrarMensaje( "\nSe recibi— un tipo de objeto desconocido" );
			}
		} while ( !mensaje.equals( "CLIENTE>>> TERMINAR" ) );

	} // fin del método procesarConexion
	
	private void operaciones(String mensaje){
		/* 
		 * con split separo los valores diferentes al + los de la izquierda del
		 * signo en lista[0] y los de la derecha lista[1]
		 * con replace quito la parte del String que se refiere a "CLIENTE>>>"
		 * con trim() quito espacios en blanco
		*/
		Pattern operador = Pattern.compile("[+-*/]");
		Matcher buscador = operador.matcher("+");
		lista = mensaje.replace("CLIENTE>>>", "").trim().split("\\"+buscador);
		/* instanciando objeto "operacion" de la clase Operacion 
		 * Conviertiendo los valores de cadena a enteros
		 * y quitando espacios en blanco innecesarios
		 * */
		Operacion operacion = new Operacion(Integer.parseInt(lista[0].trim()), Integer.parseInt(lista[1].trim()));
		mensaje = String.valueOf(operacion.sumarValores());
		enviarDatos("\nSERVIDOR>>> El resultado de la suma entre: "+lista[0] +" y "+ lista[1] + " es " + mensaje);
		mostrarMensaje( "\nRespuesta: " + mensaje );
	}
	/******************************************************/
	/******************************************************/
	/******************************************************/
	// cerrar flujos y socket
	private void cerrarConexion() 
	{
		mostrarMensaje( "\nFinalizando la conexi—n\n" );
		establecerCampoTextoEditable( false ); // deshabilitar campoIntroducir

		try {
			salida.close();
			entrada.close();
			conexion.close();
		}
		catch( IOException excepcionES ) {
			excepcionES.printStackTrace();
		}
	}
	/******************************************************/
	/******************************************************/
	/******************************************************/
	// enviar mensaje al cliente
	private void enviarDatos( String mensaje )
	{
		// enviar objeto al cliente
		try {
			salida.writeObject( "SERVIDOR>>> " + mensaje );
			salida.flush();
			mostrarMensaje( "\nSERVIDOR>>> " + mensaje );
		}

		// procesar problemas que pueden ocurrir al enviar el objeto
		catch ( IOException excepcionES ) {
			areaPantalla.append( "\nError al escribir objeto" );
		}
	}
	/******************************************************/
	/******************************************************/
	/******************************************************/
	// método utilitario que es llamado desde otros subprocesos para manipular a
	// areaPantalla en el subproceso despachador de eventos
	private void mostrarMensaje( final String mensajeAMostrar )
	{
		// mostrar mensaje del subproceso de ejecuci—n despachador de eventos
		SwingUtilities.invokeLater(
				new Runnable() {  // clase interna para asegurar que la GUI se actualice apropiadamente

					public void run() // actualiza areaPantalla
					{
						areaPantalla.append( mensajeAMostrar );
						areaPantalla.setCaretPosition(areaPantalla.getText().length());
					}

				}  // fin de la clase interna

		); // fin de la llamada a SwingUtilities.invokeLater
	}
	/******************************************************/
	/******************************************************/
	/******************************************************/
	// método utilitario que es llamado desde otros subprocesos para manipular a 
	// campoIntroducir en el subproceso despachador de eventos
	private void establecerCampoTextoEditable( final boolean editable )
	{
		// mostrar mensaje del subproceso de ejecuci—n despachador de eventos
		SwingUtilities.invokeLater(
				new Runnable() {  // clase interna para asegurar que la GUI se actualice apropiadamente

					public void run()  // establece la capacidad de modificar a campoIntroducir
					{
						campoIntroducir.setEditable( editable );
					}

				}  // fin de la clase interna

		); // fin de la llamada a SwingUtilities.invokeLater
	}
	/******************************************************/
	/******************************************************/
	/******************************************************/
	public static void main( String args[] )
	{
		Servidor aplicacion = new Servidor();
		aplicacion.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		aplicacion.ejecutarServidor();
	}
	/******************************************************/
	/******************************************************/
	/******************************************************/
}  // fin de la clase Servidor