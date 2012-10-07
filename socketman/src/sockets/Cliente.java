package sockets;
import java.io.*;
import java.net.*;

public class Cliente {
	static final String HOST = "localhost";
	static final int PUERTO = 5000;
	public Cliente(){
		try{
			Socket skCliente = new Socket(HOST, PUERTO);
			InputStream auxiliar = skCliente.getInputStream();
			DataInputStream flujo = new DataInputStream(auxiliar);
			System.out.println(flujo.readUTF());
			skCliente.close();
		} catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Cliente();
	}
}
