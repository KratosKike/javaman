package test;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

public class JavaList {

	public JavaList() {
		// TODO Auto-generated constructor stub
	}
	static void muestraElemento(Object objeto){
		if (objeto instanceof Map)
			objeto = ((Map)objeto).entrySet();
		if (objeto instanceof Collection){
			Collection coleccion = (Collection)objeto;
			Iterator iterador = coleccion.iterator();
			while(iterador.hasNext()){
				JOptionPane.showMessageDialog(null,(iterador.next()));
			}
		}
		else{
			JOptionPane.showMessageDialog(null,"Coleccion no valida");
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JavaList lista = new JavaList();
		List<Object> matriz = new ArrayList<Object>();
		matriz.add(JOptionPane.showInputDialog(null,"Ingrese valor 1"));
		matriz.add(JOptionPane.showInputDialog(null,"Ingrese valor 2"));
		matriz.add(JOptionPane.showInputDialog(null,"Ingrese valor 3"));
		lista.muestraElemento(matriz);
	}
}