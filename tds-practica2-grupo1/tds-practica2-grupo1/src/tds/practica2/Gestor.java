package tds.practica2;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Dario Gago Perez
 * @author Alvaro de la Rosa Zarzuelo
 *
 * Clase que modela un gestor de pedidos
 */

public class Gestor {

	/**
	 * Constructor de un Gestor de pedidos
	 * @param pedidos Lista de los pedidos gestionados por el gestor
	 * @throws IllegalArgumentException Lista de pedidos vacia o nula
	 */
	public Gestor(Set<Pedido> pedidos) {
	}

	/**
	 * Devuelve la lista de los pedidos contenidos en el gestor.
	 * @return Lista de los pedidos
	 */
	public Set<Pedido> getPedidos() {
		return new HashSet<Pedido>();
	}

	/**
	 * Devuelve el número de pedidos que contiene el gestor
	 * @return Número de pedidos
	 */
	public int getNumPedidos() {
		return -1;
	}

	/**
	 * Annade un nuevo pedido al gestor
	 * @param nuevoPedido Pedido a annadir
	 * @throws IllegalArgumentException pedido ya contenido en el gestor o nulo
	 */
	public void annadirPedido(Pedido nuevoPedido) {		
	}

	/**
	 * Elimina un pedido de la lista del gestor
	 * @param pedido Pedido que se desea eliminar de la lista
	 * @throws IllegalArgumentException pedido nulo
	 */
	public void eliminarProducto(Pedido pedido) {		
	}

	/**
	 * Devuelve la fecha de los pedidos mas recientes
	 * @return Fecha del pedido mas reciente
	 */
	public Calendar getFechaReciente() {
		return null;
	}

	/**
	 * Devuelve la fecha de los pedidos mas antiguos
	 * @return Fecha del pedido mas antiguo
	 */
	public Calendar getFechaAntigua() {
		return null;
	}

	/**
	 * Devuelve una lista ordenada por antiguedad de pedidos mas antiguos a mas recientes
	 * @return Lista de pedidos
	 */
	public Set<Pedido> getListaCronologica() {
		return null;
	}

	/**
	 * Devuelve un nuevo gestor con los pedidos cuya estado coincide con el proporcionado
	 * @param estadoPedido Estado del pedido
	 * @return nuevoGestor
	 * @throws IllegalArgumentException Ningún pedido del gestor posee el estado dado
	 */
	public Gestor getGestorEstado(EstadoPedido estadoPedido) {
		return new Gestor(null);
	}

	/**
	 * Devuelve un nuevo gestor con los pedidos cuya fecha de creacion esta dentro del intervalo
	 * @param fechaIntervalo1 Extremo del intervalo
	 * @param fechaIntervalo2 Extremo del intervalo
	 * @return nuevoGestor
	 * @throws IllegalArgumentException Fecha nula
	 * @throws IllegalArgumentException Ningún pedido del gestor posee una fecha de creacion dentro del intervalo
	 */
	public Gestor getGestorFecha(Calendar fechaIntervalo1, Calendar fechaIntervalo2) {
		return new Gestor(null);
	}

	/**
	 * Devuelve un nuevo gestor con los pedidos cuya fecha de creacion coincide con la proporcionada
	 * @param fecha Fecha de creación del pedido
	 * @return nuevoGestor 
	 * @throws IllegalArgumentException Fecha es nula
	 * @throws IllegalArgumentException Ningún pedido del gestor posee la fecha de creacion dada
	 */
	public Gestor getGestorFecha(Calendar fecha) {
		return new Gestor(null);
	}

	/**
	 * Devuelve un nuevo gestor con los pedidos cuyos fecha de creacion y estado coinciden con los proporcionados
	 * @param estado Estado del pedido
	 * @param fecha	Fecha de creacion del pedido
	 * @return nuevoGestor
	 * @throws IllegalArgumentException Fecha nula
	 * @throws IllegalArgumentException Ningún pedido del gestor posee la fecha y estado dados
	 */
	public Gestor getGestorEstadoFecha(EstadoPedido estado, Calendar fecha) {
		return new Gestor(null);
	}

	/**
	 * Devuelve un nuevo gestor con los pedidos cuya fecha de creacion esta dentro del intervalo y cuyo estado coincide con el proporcionado
	 * @param estado Estado del pedido
	 * @param fechaIntervalo1 Extremo del intervalo
	 * @param fechaIntervalo2 Extremo del intervalo
	 * @return nuevoGestor
	 * @throws IllegalArgumentException Fecha nula.
	 * @throws IllegalArgumentException Ningún pedido del gestor posee el estado dado y posee una fecha de creacion dentro en el intervalo
	 */
	public Gestor getGestorEstadoIntervalo(EstadoPedido estado, Calendar fechaIntervalo1, Calendar fechaIntervalo2) {
		return new Gestor(null);
	}

}

