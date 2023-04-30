package com.uva.tds._2022_grupo1;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Clase encargada de gestionar la conexión con una base de datos. Incluye
 * operaciones para añadir pedidos, eliminarlos, obtenerlos a partir de su id
 * y la obtención de una lista de pedidos según el estado y fechas de creación
 * @author marcorr
 *
 */
public interface IDatabaseManager {
	
	/**
	 * Añade un pedido a la base de datos
	 * @param order El pedido a añadir
	 * @throws IllegalArgumentException si order es nulo
	 * @throws IllegalStateException si order ya existe en la bbdd
	 */
	public void add(Order order);
	
	/**
	 * Elimina un pedido a la base de datos
	 * @param order El pedido a eliminar
	 * @throws IllegalArgumentException si order es nulo
	 * @throws IllegalStateException si order no existe en la bbdd
	 */
	public void remove(Order order);
	
	/**
	 * Devuelve un pedido de la base de datos
	 * @param id Id del pedido a obtener
	 * @return El pedido que coincide con el id. Si no hay ninguno, se
	 * devuelve null
	 * @throws IllegalArgumentException si id es nulo
	 */
	public Order get(String id);
	

	
	/**
	 * Devuelve la lista de pedidos en el estado state y entre las fechas de creación
	 * date1 y date2 (incluidas date1 y date2). Si date1 y date2 son nulos
	 * se ignora este criterio para la búsqueda. Si date1 y date2 son iguales, 
	 * los pedidos que se obtengan serán los referidos a la fecha concreta de date1.
	 * @param state El estado del que se quieres obtener los pedidos.
	 * Si es nulo, se ignora este criterio para la búsqueda
	 * @param date1 La fecha inferior del intervalo
	 * @param date2 La fecha superior del intervalo
	 * @return La lista de pedidos que cumplen con los criterios. Si no hay
	 * pedidos, se devuelve una lista vacía
	 * @throws IllegalArgumentException si date1 es nulo y date2 no, o viceversa
	 */
	public ArrayList<Order> getByStateAndDates(OrderState state,LocalDate date1,LocalDate date2);
	


}