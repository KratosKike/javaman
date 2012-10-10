package sockets;

import java.io.Serializable;

public class Operacion implements Serializable{
	/**
	 * @param serialVersionUID Valor de la implementaci—n del tipo seriarizable
	 */
	private static final long serialVersionUID = -8985596826499934533L;
	private int valor1;
	private int valor2;
	// Constructor de la clase con parametros por referencia
	public Operacion(int valor1, int valor2){
		// TODO Auto-generated constructor stub
		setValor1(valor1);
		setValor2(valor2);
	}
	// SET y GET de las variables para la operaciones
	public int getValor1() {
		return valor1;
	}

	public void setValor1(int valor1) {
		this.valor1 = valor1;
	}

	public int getValor2() {
		return valor2;
	}

	public void setValor2(int valor2) {
		this.valor2 = valor2;
	}
	// Operaciones realizadas sobre valores
	public int sumarValores(){
		return getValor1() + getValor2();
	}
	
	public int restarValores(){
		return getValor1() - getValor2();
	}
	public int multiplicaValores(){
		return getValor1()*getValor2();
	}
	public int divideValores(){
		return getValor1()/getValor2();
	}
}
