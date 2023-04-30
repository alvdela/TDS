package com.uva.tds._2022_grupo1;
/**
 * @author Dario Gago Perez
 * @author Alvaro de la Rosa Zarzuelo
 *
 * Clase que representa las dimensiones de un producto
 */


public class Dimension3D {
	private double ancho;
	private double largo;
	private double alto;
	
	/**
	 * Constructor de Dimension3D
	 * @param ancho Ancho del producto
	 * @param largo Largo del producto
	 * @param alto Alto del producto
	 * @throws IllegalArgumentException ancho menor o igual que 0
	 * @throws IllegalArgumentException largo menor o igual que 0
	 * @throws IllegalArgumentException alto menor o igual que 0
	 */
	public Dimension3D(double ancho, double largo, double alto) {
		setAncho(ancho);setLargo(largo);setAlto(alto);
	}
	
	private void setAlto(double alto) {
		if(alto > 0)
			this.alto = alto;
		else
			throw new IllegalArgumentException("Alto menor o igual que cero");
	}

	private void setLargo(double largo) {
		if(largo > 0)
			this.largo = largo;
		else
			throw new IllegalArgumentException("Largo menor o igual que cero");
	}

	private void setAncho(double ancho) {
		if(ancho > 0)
			this.ancho = ancho;
		else
			throw new IllegalArgumentException("Ancho menor o igual que cero");
	}

	/**
	 * Devuelve el ancho del producto
	 * @return ancho del producto
	 */
	public double getAncho() {
		return this.ancho;
	}
	
	/**
	 * Devuelve el largo del producto
	 * @return largo del producto
	 */
	public double getLargo() {
		return this.largo;
	}
	
	/**
	 * Devuelve el alto del producto
	 * @return alto del producto
	 */
	public double getAlto() {
		return this.alto;
	}

}
