package tds.practica2;
/**
 * @author Dario Gago Perez
 * @author Alvaro de la Rosa Zarzuelo
 *
 * Clase que modela los productos contenidos en los pedidos
 */

public class Producto {

	
	/**
	 * Contructor de producto
	 * @param id Identificador unico del producto
	 * @param descripcion Descricion del producto 
	 * @param dimensiones Dimensiones del producto
	 * @throws IllegalArgumentException id no tiene entre 1 y 10 caracteres o es vacio
	 * @throws IllegalArgumentException descripcion es vacia
	 * @throws IllegalArgumentException dimensiones es nulo
	 */
	public Producto(String id, String descripcion, Dimension3D dimensiones) {
	}
	
	/**
	 * Devuelve el id del producto
	 * @return id del producto
	 */
	public String getId() {
		return null;
	}
	
	 /**
	  * Devuelve la descripcion del producto
	  * @return descripcion del producto
	  */
	public String getDescripcion() {
		return null;
	}
	
	/**
	 * Devuelve las dimensiones del producto
	 * @return dimensiones del producto
	 */
	public Dimension3D getDimensiones() {
		return null;
	}

}
