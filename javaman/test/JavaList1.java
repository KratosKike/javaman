package test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;

public class JavaList1 {

	public JavaList1() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<String> lista = new ArrayList<String>();
		lista.add("yopo");
		lista.add("yepo");
		lista.add("repe");
		lista.add("ropo");
		Iterator<String> iterador = lista.iterator();
		for (int i=0; iterador.hasNext(); i++){
			String iterado = (String)iterador.next();
			if(iterado.equals("repe")){
				lista.remove(i);
				JOptionPane.showMessageDialog(null,iterado);
			}
		}
		iterador = lista.iterator();
		while(iterador.hasNext()){
			JOptionPane.showMessageDialog(null,iterador.next());
		}
	}
}
