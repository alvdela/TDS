package com.uva.tds._2022_grupo1;

/**
 * @author Dario Gago Perez
 * @author Alvaro de la Rosa Zarzuelo
 *
 * Clase que modela los productos contenidos en los pedidos
 */

public class Producto {
	private String id;
	private String descripcion;
	private Dimension3D dimensiones;

	/**
	 * Contructor de producto
	 * @param id Identificador unico del producto
	 * @param descripcion Descricion del producto 
	 * @param dimensiones Dimensiones del producto
	 * @throws IllegalArgumentException id no tiene entre 1 y 10 caracteres o nulo
	 * @throws IllegalArgumentException descripcion es vacia o nula
	 * @throws IllegalArgumentException dimensiones es nulo
	 */
	public Producto(String id, String descripcion, Dimension3D dimensiones) {
		setId(id);
		setDescripcion(descripcion);
		setDimensiones(dimensiones);
	}
	
	private void setId(String id) {
		if(id == null || id.isEmpty() || id.length() > 10)
			throw new IllegalArgumentException("id nula, vacia o tiene mas de 10 caracteres");
		else 
			this.id = id;
			
	}

	private void setDescripcion(String descripcion) {
		if(descripcion == null || descripcion.isEmpty())
			throw new IllegalArgumentException("Descripcion vacia o nula");	
		else 
			this.descripcion = descripcion;	
	}

	private void setDimensiones(Dimension3D dimensiones) {
		if(dimensiones == null)
			throw new IllegalArgumentException("Dimensiones nula");
		else
			this.dimensiones = dimensiones;
	}

	/**
	 * Devuelve el id del producto
	 * @return id del producto
	 */
	public String getId() {
		return this.id;
	}
	
	 /**
	  * Devuelve la descripcion del producto
	  * @return descripcion del producto
	  */
	public String getDescripcion() {
		return this.descripcion;
	}
	
	/**
	 * Devuelve las dimensiones del producto
	 * @return dimensiones del producto
	 */
	public Dimension3D getDimensiones() {
		return this.dimensiones;
	}

}
