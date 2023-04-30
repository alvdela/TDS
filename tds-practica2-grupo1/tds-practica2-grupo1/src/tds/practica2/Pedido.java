package tds.practica2;

import java.util.Calendar;
import java.util.Map;

/**
 * 
 * @author Dario Gago Perez
 * @author Alvaro de la Rosa
 * 
 * Implementacion de la clase Pedido, parte de los servicios de un gestor de pedidos.
 */

public class Pedido {

	
	/**
	 * Constructor de un pedido, inicialmente el pedido estara en estado de Recibido.
	 * @param id Identificador unico del pedido
	 * @param cantidades Lista de las cantidades de cada producto
	 * @param fechaCreacion Fecha de creacion del pedido
	 * @throws IllegalArgumentException id no posee entre 1 y 12 caracteres
	 * @throws IllegalArgumentException productos vacia o nula
	 * @throws IllegalArgumentException cantidades vacia o nula
	 * @throws IllegalArgumentException fechaCreacion nula
	 */
	public Pedido(String id, Map<Producto, Integer> cantidades, Calendar fechaCreacion) {

	}
	
	/**
	 * Devuelve el identificador del pedido
	 * @return Identificador del pedido
	 */
	public String getId() {
		return null;
	}
	
	/**
	 * Devuelve la lista de productos que componen el pedido junto con sus cantidades
	 * @return Lista de tuplas <Producto,cantidad>
	 */	
	public Map<Producto,Integer> getProductos() {
		return null;
	}
	
	/**
	 * Devuelve la fecha de creacion del pedido
	 * @return Fecha de creacion
	 */	
	public static Calendar getFechaCreacion() {
		return null;
	}
	
	/**
	 * Devuelve la fecha de la ultima modificacion del pedido
	 * @return Fecha de modificacion
	 */	
	public static Calendar getFechaModificacion() {
		return null;
	}
	
	/**
	 * Devuelve el estado actual del pedido
	 * @return Estado del pedido, puede ser Recibido, Cancelado, Pagado, Enviado o Entregado
	 */	
	public EstadoPedido getEstado() {
		return null;
	}
	
	/**
	 * Devuelve la direccion de envio del pedido
	 * @return Direccion de envio, sera si el pedido no ha sido enviado todavia
	 */	
	public String getDireccion() {
		return null;
	}
	
	/**
	 * A�ade un nuevo producto o una cantidad del producto existente a la lista de productos
	 * @param producto Producto a annadir
	 * @throws IllegalStateException Pedido no esta en estado Recibido
	 * @throws IllegalArgumentException producto nulo
	 */
	public void annadirProducto(Producto producto) {
		
	}
	
	/**
	 * Elimina una cantidad de un producto de la lista de productos, si la nueva cantidad es cero se elimina el producto
	 * @param producto Producto a eliminar
	 * @throws IllegalStateException Estado distinto de Recibido
	 * @throws IllegalArgumentException producto nulo
	 */
	public void eliminarProducto(Producto producto) {
		
	}
	
	/**
	 * Cancelacion del pedido, tras cancelarse el estado sera Cancelar
	 * @param fechaModificacion Fecha en la que se realiza la cancelacion
	 * @throws IllegalStateException Estado distinto de Recibido
	 * @throws IllegalArgumentException fechaModificacion nula o anterior a la fecha de creacion
	 */
	public void cancelar(Calendar fechaModificacion) {
		
	}
	
	/**
	 * Pago de un pedido, tras pagarse el estado sera Pagado
	 * @param fechaModificacion Fecha en la que se realiza el pago
	 * @throws IllegalStateException Estado distinto de Recibido
	 * @throws IllegalArgumentException fechaModificacion nula o anterior a la fecha de creacion
	 */
	public void pagar(Calendar fechaModificacion) {
		
	}
	
	/**
	 * Envio de un pedido, tras enviarse el estado sera Enviado
	 * @param fechaModificacion Fecha en la que se realiza el pago
	 * @param direccion Direccion de envio
	 * @throws IllegalStateException Estado distinto de Pagado
	 * @throws IllegalArgumentException direccion vacia o nula, o fechaModificacion nula o anterior a la fecha de pago
	 */
	public void enviar(Calendar fechaModificacion, String direccion) {

	}
	
	/**
	 * Entrega del pedido, tras entregarse el estado sera Entregado
	 * @param fechaModificacion Fecha en la que se realiza la entrega
	 * @throws IllegalStateException Estado distinto de Enviado
	 * @throws IllegalArgumentException fechaModificacion nula o anterior a la fecha de envio
	 */
	public void entregar(Calendar fechaModificacion) {
		
	}
	
	/**
	 * Compara dos pedidos por su fecha de creacion
	 * @param pedido Pedido a comparar
	 * @return valor 0 si ambos pedidos tienen la misma fecha de creacion, valor -1 si this Pedido es mas reciente, o valor 1 si this Pedido es menos reciente
	 * @throws IllegalArgumentException pedido nulo
	 */
	public int comparar(Pedido pedido) {
		return -2;
	}
	
	/**
	 * Compara dos pedidos por su identificacion unica
	 * @param Objeto Pedido
	 * @return True si ambos pedidos poseen el mismo id, en caso contrario devolvera false
	 * @throws IllegalArgumentException objeto no es instancia de Pedido o es nulo
	 */
	@Override
	public boolean equals(Object o) {
		return false;
	}

}
