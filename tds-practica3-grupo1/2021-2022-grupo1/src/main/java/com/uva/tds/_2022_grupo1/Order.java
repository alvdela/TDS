package com.uva.tds._2022_grupo1;

import java.time.LocalDate;
import java.util.Map;

/**
 * 
 * @author Dario Gago Perez
 * @author Alvaro de la Rosa
 * 
 * Implementacion de la clase Pedido, parte de los servicios de un gestor de pedidos.
 */

public class Order {
	
	private String mensajeError1 = "Estado distinto de Recibido";
	private String mensajeError2 = "Fecha nula";
	
	private String id;
	private Map<Producto, Integer> productos;
	private LocalDate fechaCreacion;
	private LocalDate fechaModificacion;
	private OrderState estado;
	private String direccion;
	
	/**
	 * Constructor de un pedido, inicialmente el pedido estara en estado de Recibido.
	 * @param id Identificador unico del pedido
	 * @param productos Lista de las cantidades de cada producto
	 * @param fechaCreacion Fecha de creacion del pedido
	 * @throws IllegalArgumentException id no posee entre 1 y 12 caracteres o nulo
	 * @throws IllegalArgumentException productos vacia o nula
	 * @throws IllegalArgumentException fecha de creacion nula
	 */
	public Order(String id, Map<Producto, Integer> productos, LocalDate fechaCreacion) {
		setId(id);
		setProductos(productos);
		setFechaCreacion(fechaCreacion);
		setEstado(OrderState.Recibido);
		this.direccion=null;
	}
	
	private void setId(String id) {
		if(id == null || id.isEmpty() || id.length() > 12)
			throw new IllegalArgumentException("id no posee entre 1 y 12 caracteres o nulo");
		else
			this.id = id;
	}

	private void setProductos(Map<Producto, Integer> productos) {
		if(productos == null || productos.isEmpty())
			throw new IllegalArgumentException("productos vacia o nula");
		else
			this.productos = productos;
	}

	private void setFechaCreacion(LocalDate fechaCreacion) {
		if(fechaCreacion == null)
			throw new IllegalArgumentException("fecha de creacion nula");
		else {
			this.fechaCreacion = fechaCreacion;
			setFechaModificacion(fechaCreacion);
		}
	}

	private void setFechaModificacion(LocalDate fechaModificacion) {
		if(fechaModificacion.compareTo(fechaCreacion) < 0)
			throw new IllegalArgumentException("fecha de modificacion nula o anterior a la fecha de creacion");
		else
			this.fechaModificacion = fechaModificacion;
	}
	
	private void setEstado(OrderState estado) {
		this.estado = estado;
	}
	private void setDireccion(String direccion) {
		if(direccion == null || direccion.isEmpty())
			throw new IllegalArgumentException("direccion nula o vacia");
		this.direccion = direccion;
	}

	/**
	 * Devuelve el identificador del pedido
	 * @return Identificador del pedido
	 */
	public String getId() {
		return this.id;
	}
	
	/**
	 * Devuelve la lista de productos que componen el pedido junto con sus cantidades
	 * @return Lista de tuplas Producto,cantidad
	 */	
	public Map<Producto,Integer> getProductos() {
		return this.productos;
	}
	
	/**
	 * Devuelve la fecha de creacion del pedido
	 * @return Fecha de creacion
	 */	
	public LocalDate getFechaCreacion() {
		return this.fechaCreacion;
	}
	
	/**
	 * Devuelve la fecha de la ultima modificacion del pedido
	 * @return Fecha de modificacion
	 */	
	public LocalDate getFechaModificacion() {
		return this.fechaModificacion;
	}
	
	/**
	 * Devuelve el estado actual del pedido
	 * @return Estado del pedido, puede ser Recibido, Cancelado, Pagado, Enviado o Entregado
	 */	
	public OrderState getEstado() {
		return this.estado;
	}
	
	/**
	 * Devuelve la direccion de envio del pedido
	 * @return Direccion de envio, sera si el pedido no ha sido enviado todavia
	 */	
	public String getDireccion() {
		return this.direccion;
	}
	
	/**
	 * A�ade un nuevo producto o una cantidad del producto existente a la lista de productos
	 * @param producto Producto a annadir
	 * @throws IllegalStateException Pedido no esta en estado Recibido
	 * @throws IllegalArgumentException producto nulo
	 */
	public void annadirProducto(Producto producto) {
		if(this.estado != OrderState.Recibido)
			throw new IllegalStateException(mensajeError1);
		if(producto == null)
			throw new IllegalArgumentException("Producto nulo");
		if(this.productos.containsKey(producto)) {
			productos.put(producto, productos.get(producto)+1);
		}
		else
			productos.put(producto, 1);
	}
	
	/**
	 * Elimina una cantidad de un producto de la lista de productos, si la nueva cantidad es cero se elimina el producto
	 * @param producto Producto a eliminar
	 * @throws IllegalStateException Estado distinto de Recibido
	 * @throws IllegalArgumentException producto nulo
	 */
	public void eliminarProducto(Producto producto) {
		if(this.estado != OrderState.Recibido)
			throw new IllegalStateException(mensajeError1);
		if(producto == null)
			throw new IllegalArgumentException("Producto nulo");
		if(this.productos.containsKey(producto)) {
			if(productos.get(producto) > 1)
				productos.put(producto, productos.get(producto)-1);
			else
				productos.remove(producto);
		}
	}
	
	/**
	 * Cancelacion del pedido, tras cancelarse el estado sera Cancelar
	 * @param fechaModificacion Fecha en la que se realiza la cancelacion
	 * @throws IllegalStateException Estado distinto de Recibido
	 * @throws IllegalArgumentException fechaModificacion nula o anterior a la fecha de creacion
	 */
	public void cancelar(LocalDate fechaModificacion) {
		if(this.estado != OrderState.Recibido)
			throw new IllegalStateException(mensajeError1);
		if(fechaModificacion == null)
			throw new IllegalArgumentException(mensajeError2);
		setEstado(OrderState.Cancelado);
		setFechaModificacion(fechaModificacion);
	}
	
	/**
	 * Pago de un pedido, tras pagarse el estado sera Pagado
	 * @param fechaModificacion Fecha en la que se realiza el pago
	 * @throws IllegalStateException Estado distinto de Recibido
	 * @throws IllegalArgumentException fechaModificacion nula o anterior a la fecha de creacion
	 */
	public void pagar(LocalDate fechaModificacion) {
		if(this.estado != OrderState.Recibido)
			throw new IllegalStateException(mensajeError1);
		if(fechaModificacion == null)
			throw new IllegalArgumentException(mensajeError2);
		setEstado(OrderState.Pagado);
		setFechaModificacion(fechaModificacion);
	}
	
	/**
	 * Envio de un pedido, tras enviarse el estado sera Enviado
	 * @param fechaModificacion Fecha en la que se realiza el pago
	 * @param direccion Direccion de envio
	 * @throws IllegalStateException Estado distinto de Pagado
	 * @throws IllegalArgumentException direccion vacia o nula, o fechaModificacion nula o anterior a la fecha de pago
	 */
	public void enviar(LocalDate fechaModificacion, String direccion) {
		if(this.estado != OrderState.Pagado)
			throw new IllegalStateException("Estado distinto de Pagado");
		if(fechaModificacion == null)
			throw new IllegalArgumentException(mensajeError2);
		setEstado(OrderState.Enviado);
		setFechaModificacion(fechaModificacion);
		setDireccion(direccion);
	}
	
	/**
	 * Entrega del pedido, tras entregarse el estado sera Entregado
	 * @param fechaModificacion Fecha en la que se realiza la entrega
	 * @throws IllegalStateException Estado distinto de Enviado
	 * @throws IllegalArgumentException fechaModificacion nula o anterior a la fecha de envio
	 */
	public void entregar(LocalDate fechaModificacion) {
		if(this.estado != OrderState.Enviado)
			throw new IllegalStateException("Estado distinto de Enviado");
		if(fechaModificacion == null)
			throw new IllegalArgumentException(mensajeError2);
		setEstado(OrderState.Entregado);
		setFechaModificacion(fechaModificacion);
	}
	
	/**
	 * Compara dos pedidos por su fecha de creacion
	 * @param pedido Pedido a comparar
	 * @return valor 0 si ambos pedidos tienen la misma fecha de creacion, valor -1 si this Pedido es mas reciente, o valor 1 si this Pedido es menos reciente
	 * @throws IllegalArgumentException pedido nulo
	 */
	public int comparar(Order pedido) {
		if(pedido == null)
			throw new IllegalArgumentException("pedido nulo");
		if(this.fechaCreacion.compareTo(pedido.getFechaCreacion()) == 0)
			return 0;
		else if(this.fechaCreacion.compareTo(pedido.getFechaCreacion()) < 0)
			return -1;
		else 
			return 1;
	}
	
	/**
	 * Compara dos pedidos por su identificacion unica
	 * @param o Pedido a comparar
	 * @return True si ambos pedidos poseen el mismo id, en caso contrario devolvera false
	 * @throws IllegalArgumentException objeto no es instancia de Pedido o es nulo
	 */
	@Override
	public boolean equals(Object o) {
		if (o == this) 
			return true;
		if (!(o instanceof Order)) 
			return false;
		Order pedido = (Order) o;
		return this.id.equals(pedido.getId());
	}

}
