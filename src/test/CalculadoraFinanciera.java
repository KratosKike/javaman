package test;

import javax.swing.JOptionPane;

public class CalculadoraFinanciera {

	RegistroFinanzas registro = new RegistroFinanzas();
	
	public CalculadoraFinanciera() {
		// TODO Auto-generated constructor stub
	}
	
	public double calcularTasaRepresentativa(double precio, double porcentaje){
			double tasa = 0.0;
			tasa = 2 * (precio - porcentaje) + Math.sqrt(3 * tasa) + Math.log(precio);
			return (tasa);
   }
	
	public double obtenerPrecioMercado (double precio, double porcentaje){
		
		double precioMercado = 0.0;
		
		if (precio >= 1000 && precio < 2000){
			System.out.println ("Se obtuvo el precio de mercado 1");
			precioMercado = 2 * precio + 3 * porcentaje;
		}
		else
			if (precio > 2000 && precio < 5000)
			{
				System.out.println ("Se obtuvo el precio de mercado 2");
				precioMercado = 3 * precio - Math.sqrt(porcentaje + 1000);
			}
		else
			if (precio >= 5000)
			{
				System.out.println ("Se obtuvo el precio de mercado 3");
				precioMercado = 4 * precio - 2 * Math.log(porcentaje+ 5000);
			}
		return (precioMercado);
	}
	
	public double leerValorReal(String mensaje){
		double valor = 0.0;
		valor = Double.parseDouble(JOptionPane.showInputDialog(null,mensaje));
		return (valor);
	}
	public String leerValorCadena(String mensaje){
		String valor = "";
		valor = JOptionPane.showInputDialog(null,mensaje);
		return (valor);
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RegistroFinanzas registroDatos = new RegistroFinanzas();
		CalculadoraFinanciera calculadora = new CalculadoraFinanciera();
		double tasa = 0.0;
		double precio = 0.0;
		registroDatos.setCodigo(calculadora.leerValorCadena("Ingrese el c—digo del registro"));
		registroDatos.setPorcentaje(calculadora.leerValorReal("Ingrese el valor del porcentaje de mercado: "));
		registroDatos.setPrecio(calculadora.leerValorReal("Ingrese el precio de mercado: "));
		tasa = calculadora.calcularTasaRepresentativa(registroDatos.getPrecio(),registroDatos.getPorcentaje());
		precio = calculadora.obtenerPrecioMercado(registroDatos.getPrecio(), registroDatos.getPorcentaje());
		registroDatos.setValorCalculado(precio);
		JOptionPane.showMessageDialog(null,"El valor de la tasa para el precio " + registroDatos.getPrecio() + " es " + tasa);
		JOptionPane.showMessageDialog(null,"El precio de mercado obtenido es: " + registroDatos.getValorCalculado());
	}
}