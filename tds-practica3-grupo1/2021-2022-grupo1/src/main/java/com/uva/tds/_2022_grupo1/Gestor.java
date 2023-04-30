package com.uva.tds._2022_grupo1;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * @author Dario Gago Perez
 * @author Alvaro de la Rosa Zarzuelo
 *
 * Clase que modela un gestor de pedidos
 */

public class Gestor {
	private IDatabaseManager iDatabaseManager;	
	
	/**
	 * Constructor de un Gestor de Pedidos vacio
	 */
	public Gestor() {
		//Clase implementada mediante la interfaz IDatabaseManager
	}

	
	/**
	 * Almacena la conexión con una base de datos
	 * @param iDataBaseManager Conexión con la base de datos
	 */
	public void setIDatabaseManager(IDatabaseManager iDataBaseManager) {
		this.iDatabaseManager = iDataBaseManager;
	}

	/**
	 * Devuelve la lista de los pedidos contenidos en el gestor.
	 * @return Lista de los pedidos
	 */
	public ArrayList<Order> getPedidos() {
		return iDatabaseManager.getByStateAndDates(null, null, null);
	}

	/**
	 * Devuelve el número de pedidos que contiene el gestor
	 * @return Número de pedidos
	 */
	public int getNumPedidos() {
		return getPedidos().size();
	}

	/**
	 * Annade un nuevo pedido al gestor
	 * @param nuevoPedido Pedido a annadir
	 * @throws IllegalArgumentException pedido ya contenido en el gestor o nulo
	 */
	public void annadirPedido(Order nuevoPedido) {	
		if(nuevoPedido == null || (iDatabaseManager.get(nuevoPedido.getId())) != null)
			throw new IllegalArgumentException();
		else {
			iDatabaseManager.add(nuevoPedido);
		}
	}

	/**
	 * Elimina un pedido de la lista del gestor
	 * @param pedido Pedido que se desea eliminar de la lista
	 * @throws IllegalArgumentException pedido nulo
	 */
	public void eliminarProducto(Order pedido) {
		if(pedido == null)
			throw new IllegalArgumentException();
		else if(iDatabaseManager.get(pedido.getId()) != null) {
			iDatabaseManager.remove(pedido);
		}
	}
	
	/**
	 * Devuelve un pedido del gestor
	 * @param id Id del pedido a obtener
	 * @return El pedido que coincide con el id. Si no hay ninguno, se
	 * devuelve null
	 * @throws IllegalArgumentException si id es nulo
	 */
	public Order getPedido(String id) {
		if(id.isEmpty())
			throw new IllegalArgumentException();
		else
			return iDatabaseManager.get(id);
	} 

	/**
	 * Devuelve la fecha de los pedidos mas recientes
	 * @return Fecha del pedido mas reciente
	 */
	public LocalDate getFechaReciente() {
		ArrayList<Order> pedidos = getListaCronologica();
		return pedidos.get(pedidos.size()-1).getFechaCreacion();
	}

	/**
	 * Devuelve la fecha de los pedidos mas antiguos
	 * @return Fecha del pedido mas antiguo
	 */
	public LocalDate getFechaAntigua() {
		ArrayList<Order> pedidos = getListaCronologica();
		return pedidos.get(0).getFechaCreacion();
	}

	/**
	 * Devuelve una lista ordenada por antiguedad de pedidos mas antiguos a mas recientes
	 * @return Lista de pedidos
	 */
	public ArrayList<Order> getListaCronologica() {
		ArrayList<Order> pedidos = getPedidos();
		sortByDate(pedidos); 
		return pedidos;
	}
	
    private static void sortByDate(ArrayList<Order> list){
        list.sort((pedido1, pedido2)-> pedido1.getFechaCreacion().compareTo(pedido2.getFechaCreacion()));
    }
	
	/**
	 * Devuelve la lista de pedidos en el estado state y entre las fechas de creación
	 * date1 y date2 (incluidas date1 y date2). Si fecha1 y fecha2 son nulos
	 * se ignora este criterio para la búsqueda. Si fecha1 y fecha2 son iguales, 
	 * los pedidos que se obtengan serán los referidos a la fecha concreta de fecha1.
	 * @param estado El estado del que se quieres obtener los pedidos.
	 * Si es nulo, se ignora este criterio para la búsqueda
	 * @param fecha1 La fecha inferior del intervalo
	 * @param fecha2 La fecha superior del intervalo
	 * @return La lista de pedidos que cumplen con los criterios. Si no hay
	 * pedidos, se devuelve una lista vacía
	 * @throws IllegalArgumentException si fecha1 es nula y fecha2 no, o viceversa
	 */
	public ArrayList<Order> getGestorEstadoIntervalo(OrderState estado,LocalDate fecha1,LocalDate fecha2){
		if((fecha1 == null && fecha2 != null) || (fecha1 != null && fecha2 == null))
			throw new IllegalArgumentException();
		else
			return iDatabaseManager.getByStateAndDates(estado,fecha1,fecha2);
	}
	


}

