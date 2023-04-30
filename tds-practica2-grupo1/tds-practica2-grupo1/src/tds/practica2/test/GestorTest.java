package tds.practica2.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import tds.practica2.Dimension3D;
import tds.practica2.EstadoPedido;
import tds.practica2.Gestor;
import tds.practica2.Pedido;
import tds.practica2.Producto;

/**
 * @author Dario Gago Perez
 * @author Alvaro de la Rosa Zarzuelo
 *
 */
class GestorTest {
	
	private Dimension3D dim;
	private Producto producto;
	private HashMap<Producto, Integer> listaProductos;
	private Calendar fechaCreac;
	private Pedido pedido;
	private Set<Pedido> pedidos;
	private String direccion;
	private Gestor gestor;	
	
	@BeforeEach
	void setUp() {
		//Creamos un producto
		dim = new Dimension3D(1,1,1);
		producto = new Producto("1234", "producto prueba", dim);
		//Creamos un pedido
		listaProductos = new HashMap<>();
		listaProductos.put(producto, 7);
		fechaCreac = new GregorianCalendar(2021, 7, 21);
		pedido = new Pedido("a1",listaProductos,fechaCreac);
		direccion = "Calle Labradores,N 3";
		//Creamos gestor
		pedidos = new HashSet<>();
		pedidos.add(pedido);
		gestor = new Gestor(pedidos);
	}
	
	@Test
	@Tag("TDD")
	void testCreacionGestor() {
		assertEquals(gestor.getPedidos(), pedidos);
		assertEquals(gestor.getNumPedidos(), 1);
	}
	
	@Test
	@Tag("TDD")
	void testCreacionGestorNull() {
		assertThrows(IllegalArgumentException.class,
				()->{new Gestor(null);});
	}
	
	@Test
	@Tag("TDD")
	void testCreacionGestorVacio() {
		Set<Pedido> vacio = new HashSet<>();
		assertThrows(IllegalArgumentException.class,
				()->{new Gestor(vacio);});
	}
	
	@Test
	@Tag("TDD")
	void testAnnadirPedido() {
		Pedido nuevoPedido = new Pedido("a2",listaProductos,fechaCreac);
		gestor.annadirPedido(nuevoPedido);
		assertFalse(pedido.equals(nuevoPedido));
		assertTrue(gestor.getPedidos().contains(nuevoPedido));
		assertEquals(gestor.getNumPedidos(), 2);
	}
	
	@Test
	@Tag("TDD")
	void testEliminarPedido() {
		Pedido nuevoPedido = new Pedido("a2",listaProductos,fechaCreac);
		gestor.annadirPedido(nuevoPedido);
		gestor.eliminarProducto(pedido);
		assertFalse(gestor.getPedidos().contains(pedido));
		assertEquals(gestor.getNumPedidos(), 1);
	}
	
	@Test
	@Tag("TDD")
	void testAnnadirPedidoIgual() {
		assertThrows(IllegalArgumentException.class,
				()->{gestor.annadirPedido(pedido);});
	}
	
	@Test
	@Tag("TDD")
	void testAnnadirPedidoNull() {
		assertThrows(IllegalArgumentException.class,
				()->{gestor.annadirPedido(null);});
	}
	
	@Test
	@Tag("TDD")
	void testEliminarPedidoNull() {
		assertThrows(IllegalArgumentException.class,
				()->{gestor.eliminarProducto(null);});
	}
	
	@Test
	@Tag("TDD")
	void testGetFechaReciente() {
		Calendar fechaNueva = new GregorianCalendar(2021, 9, 21);
		Pedido nuevoPedido = new Pedido("a2",listaProductos,fechaNueva);
		gestor.annadirPedido(nuevoPedido);
		assertTrue(fechaNueva.compareTo(fechaCreac) < 0);
		assertEquals(gestor.getFechaReciente(), fechaNueva);
	}
	
	@Test
	@Tag("TDD")
	void testGetFechaAntigua() {
		Calendar fechaNueva = new GregorianCalendar(2021, 9, 21);
		Pedido nuevoPedido = new Pedido("a2",listaProductos,fechaNueva);
		gestor.annadirPedido(nuevoPedido);
		assertTrue(fechaNueva.compareTo(fechaCreac) < 0);
		assertEquals(gestor.getFechaAntigua(), fechaCreac);
	}
	
	@Test
	@Tag("TDD")
	void testGetListaCronologica() {
		Calendar fechaNueva = new GregorianCalendar(2021, 5, 21);
		Pedido nuevoPedido = new Pedido("a2",listaProductos,fechaNueva);
		gestor.annadirPedido(nuevoPedido);
		Set<Pedido> listaOrdenada = new HashSet<>();
		listaOrdenada.add(nuevoPedido);
		listaOrdenada.add(pedido);
		assertTrue(fechaNueva.compareTo(fechaCreac) > 0);	
		assertEquals(gestor.getListaCronologica(), listaOrdenada);
	}
	
	
	/*Test metodo getGestorFecha*/
	
	
	@Test
	@Tag("TDD")
	void testGetGestorFecha() {
		Calendar fechaNueva = new GregorianCalendar(2021, 5, 21);
		Pedido nuevoPedido = new Pedido("a2",listaProductos,fechaNueva);
		gestor.annadirPedido(nuevoPedido);
		Gestor nuevoGestor = new Gestor(pedidos);
		assertEquals(gestor.getGestorFecha(fechaCreac).getPedidos(), nuevoGestor.getPedidos());
		try {
			fail("Metodo no implementado todavia");
		}catch(Exception e) {}
	}
	
	@Test
	@Tag("TDD")
	void testGetGestorFechaNull() {
		assertThrows(IllegalArgumentException.class,
				()->{gestor.getGestorFecha(null);});
	}
	
	@Test
	@Tag("TDD")
	void testGetGestorFechaNoEncontrado() {
		Calendar fechaNueva = new GregorianCalendar(2021, 5, 21);
		assertTrue(fechaNueva.compareTo(fechaCreac) > 0);
		assertThrows(IllegalArgumentException.class,
				()->{gestor.getGestorFecha(fechaNueva);});
	}
	
	
	/*Test metodo getGestorFecha2*/
	
	
	@Test
	@Tag("TDD")
	void testGetGestorDosFechas() {
		Calendar fechaNueva = new GregorianCalendar(2021, 5, 21);
		Pedido nuevoPedido = new Pedido("a2",listaProductos,fechaNueva);
		gestor.annadirPedido(nuevoPedido);
		Calendar fechaIntervalo1 = new GregorianCalendar(2021, 6, 21);
		Calendar fechaIntervalo2 = new GregorianCalendar(2021, 8, 21);
		Gestor nuevoGestor = new Gestor(pedidos);
		assertTrue(fechaIntervalo1.compareTo(fechaCreac) >= 0);
		assertTrue(fechaIntervalo2.compareTo(fechaCreac) <= 0);
		assertEquals(gestor.getGestorFecha(fechaIntervalo1,fechaIntervalo2).getPedidos(), nuevoGestor.getPedidos());
	}
	
	@Test
	@Tag("TDD")
	void testGetGestorDosFecha1Null() {
		Calendar fechaIntervalo = new GregorianCalendar(2021, 6, 21);
		assertThrows(IllegalArgumentException.class,
				()->{gestor.getGestorFecha(null,fechaIntervalo);});
	}
	
	@Test
	@Tag("TDD")
	void testGetGestorDosFecha2Null() {
		Calendar fechaIntervalo = new GregorianCalendar(2021, 6, 21);
		assertThrows(IllegalArgumentException.class,
				()->{gestor.getGestorFecha(fechaIntervalo,null);});
	}
	
	@Test
	@Tag("TDD")
	void testGetGestorDosFechasNoEncontrada() {
		Calendar fechaNueva1 = new GregorianCalendar(2021, 5, 21);
		Calendar fechaNueva2 = new GregorianCalendar(2021, 6, 21);
		assertTrue(fechaNueva1.compareTo(fechaCreac) > 0);
		assertTrue(fechaNueva2.compareTo(fechaCreac) > 0);
		assertThrows(IllegalArgumentException.class,
				()->{gestor.getGestorFecha(fechaNueva1,fechaNueva2);});
	}
	
	
	/*Test metodo getGestorEstado*/
	
	
	@Test
	@Tag("TDD")
	void testGetGestorEstadoRecibido() {
		assertEquals(gestor.getGestorEstado(EstadoPedido.Recibido).getPedidos(), pedidos);
	}
	
	@Test
	@Tag("TDD")
	void testGetGestorEstadoRecibidoNoEncontrado() {
		Calendar fechaNueva = new GregorianCalendar(2021, 8, 21);
		pedido.pagar(fechaNueva);
		Set<Pedido> nuevosPedidos = new HashSet<Pedido>();
		nuevosPedidos.add(pedido);
		Gestor nuevoGestor = new Gestor(nuevosPedidos);
		assertThrows(IllegalArgumentException.class,
				()->{nuevoGestor.getGestorEstado(EstadoPedido.Recibido);});
	}
	
	@Test
	void testGetGestorEstadoPagado() {
		Calendar fechaNueva = new GregorianCalendar(2021, 5, 21);
		Pedido nuevoPedidoPagado = new Pedido("a2",listaProductos,fechaNueva);
		nuevoPedidoPagado.pagar(fechaNueva);	
		gestor.annadirPedido(nuevoPedidoPagado);
		Set<Pedido> pagados = new HashSet<Pedido>();
		pagados.add(nuevoPedidoPagado);
		assertEquals(gestor.getGestorEstado(EstadoPedido.Pagado).getPedidos(), pagados);
	}
	
	@Test
	void testGetGestorEstadoPagadoNoEncontrado() {
		assertThrows(IllegalArgumentException.class,
				()->{gestor.getGestorEstado(EstadoPedido.Pagado);});
	}
	
	@Test
	void testGetGestorEstadoCancelado() {
		Calendar fechaNueva = new GregorianCalendar(2021, 5, 21);
		Pedido nuevoPedidoCancelado = new Pedido("a2",listaProductos,fechaNueva);
		nuevoPedidoCancelado.cancelar(fechaNueva);
		gestor.annadirPedido(nuevoPedidoCancelado);
		Set<Pedido> cancelados = new HashSet<Pedido>();
		cancelados.add(nuevoPedidoCancelado);
		assertEquals(gestor.getGestorEstado(EstadoPedido.Cancelado).getPedidos(), cancelados);
	}
	
	@Test
	void testGetGestorEstadoCanceladoNoEncontrado() {
		assertThrows(IllegalArgumentException.class,
				()->{gestor.getGestorEstado(EstadoPedido.Cancelado);});
	}
	
	@Test
	void testGetGestorEstadoEnviado() {
		Calendar fechaNueva = new GregorianCalendar(2021, 5, 21);
		Pedido nuevoPedidoEnviado = new Pedido("a2",listaProductos,fechaNueva);
		nuevoPedidoEnviado.pagar(fechaNueva);
		nuevoPedidoEnviado.enviar(fechaNueva, direccion);
		gestor.annadirPedido(nuevoPedidoEnviado);
		Set<Pedido> enviados = new HashSet<Pedido>();
		enviados.add(nuevoPedidoEnviado);
		assertEquals(gestor.getGestorEstado(EstadoPedido.Enviado).getPedidos(), enviados);
	}
	
	@Test
	void testGetGestorEstadoEnviadoNoEncontrado() {
		assertThrows(IllegalArgumentException.class,
				()->{gestor.getGestorEstado(EstadoPedido.Enviado);});
	}
	
	@Test
	void testGetGestorEstadoEntregado() {
		Calendar fechaNueva = new GregorianCalendar(2021, 5, 21);
		Pedido nuevoPedidoEntregado = new Pedido("a2",listaProductos,fechaNueva);
		nuevoPedidoEntregado.pagar(fechaNueva);
		nuevoPedidoEntregado.enviar(fechaNueva, direccion);
		nuevoPedidoEntregado.entregar(fechaNueva);
		gestor.annadirPedido(nuevoPedidoEntregado);
		Set<Pedido> entregados = new HashSet<Pedido>();
		entregados.add(nuevoPedidoEntregado);
		assertEquals(gestor.getGestorEstado(EstadoPedido.Entregado).getPedidos(), entregados);
	}
	
	@Test
	void testGetGestorEstadoEntregadoNoEncontrado() {
		assertThrows(IllegalArgumentException.class,
				()->{gestor.getGestorEstado(EstadoPedido.Entregado);});
	}
	
	
	
	/*Test metodo getGestorEstadoFecha*/
	
	
	@Test
	@Tag("TDD")
	void testGetGestorEstadoFechaRecibido() {
		assertEquals(gestor.getGestorEstadoFecha(EstadoPedido.Recibido,fechaCreac).getPedidos(), pedidos);
	}
	
	@Test
	@Tag("TDD")
	void testGetGestorEstadoFechaNull() {
		assertThrows(IllegalArgumentException.class,
				()->{gestor.getGestorEstadoFecha(EstadoPedido.Pagado,null);});
	}
	
	@Test
	@Tag("TDD")
	void testGetGestorEstadoFechaRecibidoNoEncontradoEstadoDistinto() {
		Calendar fechaNueva = new GregorianCalendar(2021, 8, 21);
		pedido.pagar(fechaNueva);
		Set<Pedido> nuevosPedidos = new HashSet<Pedido>();
		nuevosPedidos.add(pedido);
		Gestor nuevoGestor = new Gestor(nuevosPedidos);
		assertThrows(IllegalArgumentException.class,
				()->{nuevoGestor.getGestorEstadoFecha(EstadoPedido.Recibido,fechaNueva);});
	}
	
	@Test
	void testGetGestorEstadoFechaRecibidoNoEncontradoFechaDistinta() {
		Calendar fechaNueva = new GregorianCalendar(2021, 8, 21);
		assertThrows(IllegalArgumentException.class,
				()->{gestor.getGestorEstadoFecha(EstadoPedido.Recibido,fechaNueva);});
	}
	
	@Test
	void testGetGestorEstadoFechaPagado() {
		Calendar fechaNueva = new GregorianCalendar(2021, 5, 21);
		Pedido nuevoPedidoPagado = new Pedido("a2",listaProductos,fechaNueva);
		nuevoPedidoPagado.pagar(fechaNueva);	
		gestor.annadirPedido(nuevoPedidoPagado);
		Set<Pedido> pagados = new HashSet<Pedido>();
		pagados.add(nuevoPedidoPagado);
		assertEquals(gestor.getGestorEstadoFecha(EstadoPedido.Pagado,fechaNueva).getPedidos(), pagados);
	}
	
	@Test
	void testGetGestorEstadoFechaPagadoNoEncontrado() {
		Calendar fechaNueva = new GregorianCalendar(2021, 5, 21);
		Pedido nuevoPedidoPagado = new Pedido("a2",listaProductos,fechaNueva);
		gestor.annadirPedido(nuevoPedidoPagado);
		assertThrows(IllegalArgumentException.class,
				()->{gestor.getGestorEstadoFecha(EstadoPedido.Pagado,fechaNueva);});
	}
	
	@Test
	void testGetGestorEstadoFechaCancelado() {
		Calendar fechaNueva = new GregorianCalendar(2021, 5, 21);
		Pedido nuevoPedidoCancelado = new Pedido("a2",listaProductos,fechaNueva);
		nuevoPedidoCancelado.cancelar(fechaNueva);
		gestor.annadirPedido(nuevoPedidoCancelado);
		Set<Pedido> cancelados = new HashSet<Pedido>();
		cancelados.add(nuevoPedidoCancelado);
		assertEquals(gestor.getGestorEstadoFecha(EstadoPedido.Cancelado,fechaNueva).getPedidos(), cancelados);
	}
	
	@Test
	void testGetGestorEstadoFechaCanceladoNoEncontrado() {
		Calendar fechaNueva = new GregorianCalendar(2021, 5, 21);
		Pedido nuevoPedidoPagado = new Pedido("a2",listaProductos,fechaNueva);
		gestor.annadirPedido(nuevoPedidoPagado);
		assertThrows(IllegalArgumentException.class,
				()->{gestor.getGestorEstadoFecha(EstadoPedido.Cancelado,fechaNueva);});
	}
	
	@Test
	void testGetGestorEstadoFechaEnviado() {
		Calendar fechaNueva = new GregorianCalendar(2021, 5, 21);
		Pedido nuevoPedidoEnviado = new Pedido("a2",listaProductos,fechaNueva);
		nuevoPedidoEnviado.pagar(fechaNueva);
		nuevoPedidoEnviado.enviar(fechaNueva, direccion);
		gestor.annadirPedido(nuevoPedidoEnviado);
		Set<Pedido> enviados = new HashSet<Pedido>();
		enviados.add(nuevoPedidoEnviado);
		assertEquals(gestor.getGestorEstadoFecha(EstadoPedido.Enviado,fechaNueva).getPedidos(), enviados);
	}
	
	@Test
	void testGetGestorEstadoFechaEnviadoNoEncontrado() {
		Calendar fechaNueva = new GregorianCalendar(2021, 5, 21);
		Pedido nuevoPedidoPagado = new Pedido("a2",listaProductos,fechaNueva);
		gestor.annadirPedido(nuevoPedidoPagado);
		assertThrows(IllegalArgumentException.class,
				()->{gestor.getGestorEstadoFecha(EstadoPedido.Enviado,fechaNueva);});
	}
	
	@Test
	void testGetGestorEstadoFechaEntregado() {
		Calendar fechaNueva = new GregorianCalendar(2021, 5, 21);
		Pedido nuevoPedidoEntregado = new Pedido("a2",listaProductos,fechaNueva);
		nuevoPedidoEntregado.pagar(fechaNueva);
		nuevoPedidoEntregado.enviar(fechaNueva, direccion);
		nuevoPedidoEntregado.entregar(fechaNueva);
		gestor.annadirPedido(nuevoPedidoEntregado);
		Set<Pedido> entregados = new HashSet<Pedido>();
		entregados.add(nuevoPedidoEntregado);
		assertEquals(gestor.getGestorEstadoFecha(EstadoPedido.Entregado,fechaNueva).getPedidos(), entregados);
	}
	
	@Test
	void testGetGestorEstadoFechaEntregadoNoEncontrado() {
		Calendar fechaNueva = new GregorianCalendar(2021, 5, 21);
		Pedido nuevoPedidoPagado = new Pedido("a2",listaProductos,fechaNueva);
		gestor.annadirPedido(nuevoPedidoPagado);
		assertThrows(IllegalArgumentException.class,
				()->{gestor.getGestorEstadoFecha(EstadoPedido.Entregado,fechaNueva);});
	}
	
	
	/*Test metodo getGestorEstadoIntervalo*/
	
	@Test
	@Tag("TDD")
	void testGetGestorEstadoIntervaloRecibido() {
		Calendar fechaIntervalo1 = new GregorianCalendar(2021, 6, 21);
		Calendar fechaIntervalo2 = new GregorianCalendar(2021, 8, 21);
		assertTrue(fechaIntervalo1.compareTo(fechaCreac) >= 0);
		assertTrue(fechaIntervalo2.compareTo(fechaCreac) <= 0);
		assertEquals(gestor.getGestorEstadoIntervalo(EstadoPedido.Recibido,fechaIntervalo1,fechaIntervalo2).getPedidos(), pedidos);
	}
	
	@Test
	@Tag("TDD")
	void testGetGestorEstadoIntervaloFecha1Null() {		
		Calendar fechaIntervalo = new GregorianCalendar(2021, 6, 21);
		assertThrows(IllegalArgumentException.class,
				()->{gestor.getGestorEstadoIntervalo(EstadoPedido.Recibido,null,fechaIntervalo);});
	}
	
	@Test
	@Tag("TDD")
	void testGetGestorEstadoIntervaloFecha2Null() {		
		Calendar fechaIntervalo = new GregorianCalendar(2021, 6, 21);
		assertThrows(IllegalArgumentException.class,
				()->{gestor.getGestorEstadoIntervalo(EstadoPedido.Recibido,fechaIntervalo,null);});
	}
	
	@Test
	@Tag("TDD")
	void testGetGestorEstadointervaloRecibidoNoEncontradoEstadoDistinto() {
		Calendar fechaNueva = new GregorianCalendar(2021, 8, 21);
		Calendar fechaIntervalo1 = new GregorianCalendar(2021, 6, 21);	
		pedido.pagar(fechaNueva);
		Set<Pedido> nuevosPedidos = new HashSet<Pedido>();
		nuevosPedidos.add(pedido);
		Gestor nuevoGestor = new Gestor(nuevosPedidos);
		assertThrows(IllegalArgumentException.class,
				()->{nuevoGestor.getGestorEstadoIntervalo(EstadoPedido.Recibido,fechaIntervalo1,fechaNueva);});
	}
	
	@Test
	@Tag("TDD")
	void testGetGestorEstadointervaloRecibidoNoEncontradoFechaFueraIntervalo() {
		Calendar fechaNueva = new GregorianCalendar(2021, 5, 21);
		Calendar fechaIntervalo = new GregorianCalendar(2021, 6, 21);	
		assertThrows(IllegalArgumentException.class,
				()->{gestor.getGestorEstadoIntervalo(EstadoPedido.Recibido,fechaNueva,fechaIntervalo);});
	}
	
	@Test
	void testGetGestorEstadointervaloPagado() {
		Calendar fechaIntervalo1 = new GregorianCalendar(2021, 6, 21);
		Calendar fechaIntervalo2 = new GregorianCalendar(2021, 8, 21);
		Pedido nuevoPedidoPagado = new Pedido("a2",listaProductos,fechaIntervalo1);
		nuevoPedidoPagado.pagar(fechaIntervalo1);	
		gestor.annadirPedido(nuevoPedidoPagado);
		Set<Pedido> pagados = new HashSet<Pedido>();
		pagados.add(nuevoPedidoPagado);
		assertEquals(gestor.getGestorEstadoIntervalo(EstadoPedido.Pagado,fechaIntervalo1,fechaIntervalo2).getPedidos(), pagados);
	}
	
	@Test
	void testGetGestorEstadointervaloPagadoNoEncontrado() {
		Calendar fechaNueva = new GregorianCalendar(2021, 5, 21);
		Calendar fechaIntervalo = new GregorianCalendar(2021, 8, 21);
		Pedido nuevoPedidoPagado = new Pedido("a2",listaProductos,fechaNueva);
		gestor.annadirPedido(nuevoPedidoPagado);
		assertThrows(IllegalArgumentException.class,
				()->{gestor.getGestorEstadoIntervalo(EstadoPedido.Pagado,fechaNueva,fechaIntervalo);});
	}
	
	@Test
	void testGetGestorEstadointervaloCancelado() {
		Calendar fechaNueva = new GregorianCalendar(2021, 5, 21);
		Calendar fechaIntervalo = new GregorianCalendar(2021, 8, 21);
		Pedido nuevoPedidoCancelado = new Pedido("a2",listaProductos,fechaNueva);
		nuevoPedidoCancelado.cancelar(fechaNueva);
		gestor.annadirPedido(nuevoPedidoCancelado);
		Set<Pedido> cancelados = new HashSet<Pedido>();
		cancelados.add(nuevoPedidoCancelado);
		assertEquals(gestor.getGestorEstadoIntervalo(EstadoPedido.Cancelado,fechaNueva, fechaIntervalo).getPedidos(), cancelados);
	}
	
	@Test
	void testGetGestorEstadoIntervaloCanceladoNoEncontrado() {
		Calendar fechaNueva = new GregorianCalendar(2021, 5, 21);
		Calendar fechaIntervalo = new GregorianCalendar(2021, 8, 21);
		Pedido nuevoPedidoPagado = new Pedido("a2",listaProductos,fechaNueva);
		gestor.annadirPedido(nuevoPedidoPagado);
		assertThrows(IllegalArgumentException.class,
				()->{gestor.getGestorEstadoIntervalo(EstadoPedido.Cancelado,fechaNueva,fechaIntervalo);});
	}
	
	@Test
	void testGetGestorEstadoIntervaloEnviado() {
		Calendar fechaNueva = new GregorianCalendar(2021, 5, 21);
		Calendar fechaIntervalo = new GregorianCalendar(2021, 8, 21);
		Pedido nuevoPedidoEnviado = new Pedido("a2",listaProductos,fechaNueva);
		nuevoPedidoEnviado.pagar(fechaNueva);
		nuevoPedidoEnviado.enviar(fechaNueva, direccion);
		gestor.annadirPedido(nuevoPedidoEnviado);
		Set<Pedido> enviados = new HashSet<Pedido>();
		enviados.add(nuevoPedidoEnviado);
		assertEquals(gestor.getGestorEstadoIntervalo(EstadoPedido.Enviado,fechaNueva,fechaIntervalo).getPedidos(), enviados);
	}
	
	@Test
	void testGetGestorEstadoIntervaloEnviadoNoEncontrado() {
		Calendar fechaNueva = new GregorianCalendar(2021, 5, 21);
		Calendar fechaIntervalo = new GregorianCalendar(2021, 8, 21);
		Pedido nuevoPedidoPagado = new Pedido("a2",listaProductos,fechaNueva);
		gestor.annadirPedido(nuevoPedidoPagado);
		assertThrows(IllegalArgumentException.class,
				()->{gestor.getGestorEstadoIntervalo(EstadoPedido.Enviado,fechaNueva,fechaIntervalo);});
	}
	
	@Test
	void testGetGestorEstadointervaloEntregado() {
		Calendar fechaNueva = new GregorianCalendar(2021, 5, 21);
		Calendar fechaIntervalo = new GregorianCalendar(2021, 8, 21);
		Pedido nuevoPedidoEntregado = new Pedido("a2",listaProductos,fechaNueva);
		nuevoPedidoEntregado.pagar(fechaNueva);
		nuevoPedidoEntregado.enviar(fechaNueva, direccion);
		nuevoPedidoEntregado.entregar(fechaNueva);
		gestor.annadirPedido(nuevoPedidoEntregado);
		Set<Pedido> entregados = new HashSet<Pedido>();
		entregados.add(nuevoPedidoEntregado);
		assertEquals(gestor.getGestorEstadoIntervalo(EstadoPedido.Entregado,fechaNueva,fechaIntervalo).getPedidos(), entregados);
	}
	
	@Test
	void testGetGestorEstadoIntervaloEntregadoNoEncontrado() {
		Calendar fechaNueva = new GregorianCalendar(2021, 5, 21);
		Calendar fechaIntervalo = new GregorianCalendar(2021, 8, 21);
		Pedido nuevoPedidoPagado = new Pedido("a2",listaProductos,fechaNueva);
		gestor.annadirPedido(nuevoPedidoPagado);
		assertThrows(IllegalArgumentException.class,
				()->{gestor.getGestorEstadoIntervalo(EstadoPedido.Entregado,fechaNueva,fechaIntervalo);});
	}
	
}
