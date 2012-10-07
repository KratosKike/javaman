package sockets;

import java.io.*;
import java.net.*;

public class Servidor {
	static final int PUERTO = 5000;
	public Servidor(){
		try{
			ServerSocket skServidor = new ServerSocket(PUERTO); 
			System.out.println("Escucho por el puerto: "+PUERTO);
			for (int numClientes = 0; numClientes<3; numClientes++){
				Socket skCliente = skServidor.accept();
				System.out.println("Sirvio al cliente: "+numClientes);
				OutputStream auxiliar = skCliente.getOutputStream();
				DataOutputStream flujo = new DataOutputStream(auxiliar);
				flujo.writeUTF("Hola Cliente: "+numClientes);
				skCliente.close();
			}
		} catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Servidor();
	}
}
