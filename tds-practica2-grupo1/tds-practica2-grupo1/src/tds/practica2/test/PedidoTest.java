package tds.practica2.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import tds.practica2.Pedido;
import tds.practica2.Producto;
import tds.practica2.Dimension3D;
import tds.practica2.EstadoPedido;

/**
 * @author Dario Gago Perez
 * @author Alvaro de la Rosa Zarzuelo
 *
 */

class PedidoTest {	
	private String id;
	private Map<Producto, Integer> productos;
	private Calendar fechaCreacion;
	private Producto producto; 
	private Pedido pedido;
	private String direccion;
	private String descripcion;
	private Dimension3D dimensiones;
	
	@BeforeEach
	void setUp(){
		id = "1234";
		productos = new HashMap<Producto, Integer>();
		fechaCreacion = new GregorianCalendar(50,1,2);
		dimensiones = new Dimension3D(1,2,3);
		descripcion = "Descripcion del producto";
		producto = new Producto("678",descripcion,dimensiones);
	    productos.put(producto,1);
	    pedido = new Pedido(id,productos,fechaCreacion);
	    direccion = "Calle El Rocio, N 4";
	}
	
/*Tests Creacion*/

	@Test
	@Tag("TDD")
	void testCreacionPedidoIdLimiteInferior() {
		Pedido nuevoPedido = new Pedido("a",productos,fechaCreacion);
		assertEquals("a", nuevoPedido.getId());
		assertTrue(nuevoPedido.getProductos().equals(productos));
		assertEquals(EstadoPedido.Recibido, nuevoPedido.getEstado());
		assertEquals(fechaCreacion,Pedido.getFechaCreacion());
		assertEquals(Pedido.getFechaCreacion(),Pedido.getFechaModificacion());
	}
	
	@Test
	void testCreacionPedidoIdLimiteSuperior() {
		Pedido nuevoPedido = new Pedido("123456789111",productos,fechaCreacion);
		assertEquals("123456789111", nuevoPedido.getId());
		assertTrue(nuevoPedido.getProductos().equals(productos));
		assertEquals(EstadoPedido.Recibido, nuevoPedido.getEstado());
		assertEquals(fechaCreacion,Pedido.getFechaCreacion());
		assertEquals(Pedido.getFechaCreacion(),Pedido.getFechaModificacion());
	}
	
	@Test
	@Tag("TDD")
	void testCreacionPedidoIdMayorLimiteSuperior() {
		assertThrows(IllegalArgumentException.class,()->{new Pedido("1234567891111",productos,fechaCreacion);});
	}

	@Test
	@Tag("TDD")
	void testCreacionPedidoIdMenorLimiteInferior() {
		assertThrows(IllegalArgumentException.class,()->{new Pedido("",productos,fechaCreacion);});
	}
	
	@Test
	@Tag("TDD")
	void testCreacionPedidoListaProductosNula() {
		assertThrows(IllegalArgumentException.class,()->{new Pedido(id,null,fechaCreacion);});
	}
	
	@Test
	@Tag("TDD")
	void testCreacionPedidoListaProductosVacia() {
		HashMap<Producto, Integer> productosVacia = new HashMap<Producto, Integer>();
		assertThrows(IllegalArgumentException.class,()->{new Pedido(id,productosVacia,fechaCreacion);});
	}
	
	@Test
	@Tag("TDD")
	void testCreacionPedidoFechaCreacionNula() {
		assertThrows(IllegalArgumentException.class,()->{new Pedido(id,productos,null);});
	}
	
	
/*Tests Estado Recibido*/

	
	@Test
	@Tag("TDD")
	void testEstadoRecibidoAnnadirNuevoProducto() {
		Producto producto2 = new Producto("2,3,4",descripcion,dimensiones);
		pedido.annadirProducto(producto2);
		assertEquals(id, pedido.getId());
		assertTrue(pedido.getProductos().containsKey(producto2));
		assertEquals(1,pedido.getProductos().get(producto2));
		assertEquals(EstadoPedido.Recibido, pedido.getEstado());
		assertEquals(fechaCreacion,Pedido.getFechaCreacion());
		assertEquals(Pedido.getFechaCreacion(),Pedido.getFechaModificacion());
	}
	
	@Test
	void testEstadoRecibidoAnnadirProductoExistente() {
		pedido.annadirProducto(producto);
		assertEquals(id, pedido.getId());
		assertEquals(2,pedido.getProductos().get(producto));
		assertEquals(EstadoPedido.Recibido, pedido.getEstado());
		assertEquals(fechaCreacion,Pedido.getFechaCreacion());
		assertEquals(Pedido.getFechaCreacion(),Pedido.getFechaModificacion());
	}
	
	@Test
	@Tag("TDD")
	void testtEstadoRecibidoAnnadirProductoNulo() {
		assertThrows(IllegalArgumentException.class,()->{pedido.annadirProducto(null);});
	}
	
	@Test
	@Tag("TDD")
	void testEstadoRecibidoEliminarProducto() {
		Producto producto2 = new Producto("443",descripcion,dimensiones);
		pedido.annadirProducto(producto2);
		pedido.eliminarProducto(producto2);
		assertEquals(id, pedido.getId());
		assertTrue(pedido.getProductos().equals(productos));
		assertEquals(EstadoPedido.Recibido, pedido.getEstado());
		assertEquals(fechaCreacion,Pedido.getFechaCreacion());
		assertEquals(Pedido.getFechaCreacion(),Pedido.getFechaModificacion());
	}
	
	@Test
	void testEstadoRecibidoEliminarProductoCantidadMayor1() {
		Producto producto2 = new Producto("443",descripcion,dimensiones);
		pedido.annadirProducto(producto2);
		pedido.annadirProducto(producto2);
		pedido.eliminarProducto(producto2);
		assertEquals(id, pedido.getId());
		assertTrue(productos.containsKey(producto2));
		assertEquals(1,pedido.getProductos().get(producto2));
		assertEquals(EstadoPedido.Recibido, pedido.getEstado());
		assertEquals(fechaCreacion,Pedido.getFechaCreacion());
		assertEquals(Pedido.getFechaCreacion(),Pedido.getFechaModificacion());
	}
	
	@Test
	@Tag("TDD")
	void testEstadoRecibidoEliminarProductoNulo() {
		assertThrows(IllegalArgumentException.class,()->{pedido.eliminarProducto(null);});
	}
	
	@Test
	@Tag("TDD")
	void testEstadoRecibidoCancelar() {
		Calendar fechaModificacion = new GregorianCalendar(60,4,5);
		pedido.cancelar(fechaModificacion);
		assertEquals(EstadoPedido.Cancelado,pedido.getEstado());
		assertEquals(id, pedido.getId());
		assertTrue(productos.equals(pedido.getProductos()));
		assertEquals(fechaCreacion,Pedido.getFechaCreacion());
		assertEquals(fechaModificacion,Pedido.getFechaModificacion());
	}
	
	@Test
	@Tag("TDD")
	void testEstadoRecibidoCancelarFechaModificacionNula() {
		assertThrows(IllegalArgumentException.class,()->{pedido.cancelar(null);});
	}
	
	@Test
	@Tag("TDD")
	void testEstadoRecibidoCancelarFechaAnterior() {
		Calendar fechaModificacion = new GregorianCalendar(10,4,5);
		assertTrue(fechaCreacion.compareTo(fechaModificacion) > 0);
		assertThrows(IllegalArgumentException.class,()->{pedido.cancelar(fechaModificacion);});
	}
	
	@Test
	@Tag("TDD")
	void testEstadoRecibidoPagar() {
		Calendar fechaModificacion = new GregorianCalendar(60,4,5);
		pedido.pagar(fechaModificacion);
		assertEquals(EstadoPedido.Pagado,pedido.getEstado());
		assertEquals(id, pedido.getId());
		assertTrue(productos.equals(pedido.getProductos()));
		assertEquals(fechaCreacion,Pedido.getFechaCreacion());
		assertEquals(fechaModificacion,Pedido.getFechaModificacion());
	}
	
	@Test
	@Tag("TDD")
	void testEstadoRecibidoPagarFechaAnterior() {
		Calendar fechaModificacion = new GregorianCalendar(10,4,5);
		assertTrue(fechaCreacion.compareTo(fechaModificacion) > 0);
		assertThrows(IllegalArgumentException.class,()->{pedido.pagar(fechaModificacion);});
	}
	
	@Test
	@Tag("TDD")
	void testEstadoRecibidoPagarFechaModificacionNula() {
		assertThrows(IllegalArgumentException.class,()->{pedido.pagar(null);});
	}
	
	@Test
	void testEstadoRecibidoEnviar() {
		Calendar fechaModificacion = new GregorianCalendar(60,4,5);
		assertThrows(IllegalStateException.class,()->{pedido.enviar(fechaModificacion,direccion);});
	}
	
	@Test
	void testEstadoRecibidoEntregar() {
		Calendar fechaModificacion = new GregorianCalendar(60,4,5);
		assertThrows(IllegalStateException.class,()->{pedido.entregar(fechaModificacion);});
	}
	
	@Test
	@Tag("TDD")
	void testEstadoRecibidoGetDireccion() {
		assertEquals(null,pedido.getDireccion());
		try {
			fail("Metodo no implementado todavia");
		}catch(Exception e) {}
	}
	
	
/*Tests Estado Pagado*/

	
	@Test
	void testEstadoPagadoAnnadirProducto() {
		Producto producto2 = new Producto("443",descripcion,dimensiones);
		Calendar fechaModificacion = new GregorianCalendar(60,4,5);
		pedido.pagar(fechaModificacion);
		assertThrows(IllegalStateException.class,()->{pedido.annadirProducto(producto2);});
	}	
	
	@Test
	void testEstadoPagadoEliminarProducto() {
		Producto producto2 = new Producto("443",descripcion,dimensiones);
		Calendar fechaModificacion = new GregorianCalendar(60,4,5);
		pedido.pagar(fechaModificacion);
		assertThrows(IllegalStateException.class,()->{pedido.annadirProducto(producto2);});
	}
	
	@Test
	void testEstadoPagadoEntregar() {
		Calendar fechaModificacion = new GregorianCalendar(60,4,5);
		pedido.pagar(fechaModificacion);
		assertThrows(IllegalStateException.class,()->{pedido.entregar(fechaModificacion);});
	}
	
	@Test
	void testEstadoPagadoCancelar() {
		Calendar fechaModificacion = new GregorianCalendar(60,4,5);
		pedido.pagar(fechaModificacion);
		assertThrows(IllegalStateException.class,()->{pedido.cancelar(fechaModificacion);});
	}
	
	@Test
	@Tag("TDD")
	void testEstadoPagadoEnviar() {
		Calendar fechaModificacion = new GregorianCalendar(60,4,5);
		pedido.pagar(fechaModificacion);
		Calendar fechaModificacion2 = new GregorianCalendar(60,6,9);
		pedido.enviar(fechaModificacion,direccion);
		assertEquals(EstadoPedido.Enviado,pedido.getEstado());
		assertEquals(fechaModificacion2,Pedido.getFechaModificacion());
	}
	
	@Test
	@Tag("TDD")
	void testEstadoPagadoEnviarFechaAnterior() {
		Calendar fechaModificacion = new GregorianCalendar(60,4,5);
		pedido.pagar(fechaModificacion);
		Calendar fechaModificacion2 = new GregorianCalendar(20,6,9);
		assertTrue(fechaModificacion.compareTo(fechaModificacion2) > 0);
		assertThrows(IllegalArgumentException.class,()->{pedido.enviar(fechaModificacion2,direccion);});
	}
	
	@Test
	@Tag("TDD")
	void testEstadoPagadoEnviarFechaModificacionNula() {
		Calendar fechaModificacion = new GregorianCalendar(60,4,5);
		pedido.pagar(fechaModificacion);
		assertThrows(IllegalArgumentException.class,()->{pedido.enviar(null,direccion);});
	}
	
	@Test
	@Tag("TDD")
	void testEstadoPagadoEnviarDireccionVacia() {
		Calendar fechaModificacion = new GregorianCalendar(60,4,5);
		pedido.pagar(fechaModificacion);
		assertThrows(IllegalArgumentException.class,()->{pedido.enviar(fechaModificacion,"");});
	}
	
	@Test
	@Tag("TDD")
	void testEstadoPagadoEnviarDireccionNull() {
		Calendar fechaModificacion = new GregorianCalendar(60,4,5);
		pedido.pagar(fechaModificacion);
		assertThrows(IllegalArgumentException.class,()->{pedido.enviar(fechaModificacion,null);});
	}
	
	@Test
	void testEstadoPagadoGetDireccion() {
		Calendar fechaModificacion = new GregorianCalendar(60,4,5);
		pedido.pagar(fechaModificacion);
		assertEquals(null,pedido.getDireccion());
		try {
			fail("Metodo no implementado todavia");
		}catch(Exception e) {}
	}
	
	
/*Tests Estado Cancelado */	

	@Test
	void testEstadoCanceladoAnnadirProducto() {
		Producto producto2 = new Producto("443",descripcion,dimensiones);
		Calendar fechaModificacion = new GregorianCalendar(60,4,5);
		pedido.cancelar(fechaModificacion);
		assertThrows(IllegalStateException.class,()->{pedido.annadirProducto(producto2);});
	}
	
	@Test
	void testEstadoCanceladoEliminarProducto() {
		Producto producto2 = new Producto("443",descripcion,dimensiones);
		pedido.annadirProducto(producto2);
		Calendar fechaModificacion = new GregorianCalendar(60,4,5);
		pedido.cancelar(fechaModificacion);
		assertThrows(IllegalStateException.class,()->{pedido.eliminarProducto(producto2);});
	}
	
	@Test
	void testEstadoCanceladoPagar() {
		Producto producto2 = new Producto("443",descripcion,dimensiones);
		pedido.annadirProducto(producto2);
		Calendar fechaModificacion = new GregorianCalendar(60,4,5);
		pedido.cancelar(fechaModificacion);
		assertThrows(IllegalStateException.class,()->{pedido.pagar(fechaModificacion);});
	}
	
	@Test
	void testEstadoCanceladoEnviar() {
		Producto producto2 = new Producto("443",descripcion,dimensiones);
		pedido.annadirProducto(producto2);
		Calendar fechaModificacion = new GregorianCalendar(60,4,5);
		pedido.cancelar(fechaModificacion);
		assertThrows(IllegalStateException.class,()->{pedido.enviar(fechaModificacion,direccion);});
	}
	
	@Test
	void testEstadoCanceladoEntregar() {
		Producto producto2 = new Producto("443",descripcion,dimensiones);
		pedido.annadirProducto(producto2);
		Calendar fechaModificacion = new GregorianCalendar(60,4,5);
		pedido.cancelar(fechaModificacion);
		assertThrows(IllegalStateException.class,()->{pedido.entregar(fechaModificacion);});
	}
	
	@Test
	void testEstadoCanceladoGetDireccion() {
		Calendar fechaModificacion = new GregorianCalendar(60,4,5);
		pedido.cancelar(fechaModificacion);
		assertEquals(null,pedido.getDireccion());
		try {
			fail("Metodo no implementado todavia");
		}catch(Exception e) {}
	}
	
	
/*Tests Estado Enviado */	

	
	@Test
	void testEstadoEnviadoAnnadirProducto() {
		Producto producto2 = new Producto("443",descripcion,dimensiones);
		Calendar fechaModificacion = new GregorianCalendar(60,4,5);
		pedido.pagar(fechaModificacion);
		pedido.enviar(fechaModificacion,direccion);
		assertThrows(IllegalStateException.class,()->{pedido.annadirProducto(producto2);});
	}
	
	@Test
	void testEstadoEnviadoEliminarProducto() {
		Producto producto2 = new Producto("443",descripcion,dimensiones);
		pedido.annadirProducto(producto2);
		Calendar fechaModificacion = new GregorianCalendar(60,4,5);
		pedido.pagar(fechaModificacion);
		pedido.enviar(fechaModificacion,direccion);
		assertThrows(IllegalStateException.class,()->{pedido.eliminarProducto(producto2);});
	}
	
	@Test
	void testEstadoEnviadoPagar() {
		Calendar fechaModificacion = new GregorianCalendar(60,4,5);
		pedido.pagar(fechaModificacion);
		pedido.enviar(fechaModificacion,direccion);
		assertThrows(IllegalStateException.class,()->{pedido.pagar(fechaModificacion);});
	}
	
	@Test
	void testEstadoEnviadoCancelar() {
		Calendar fechaModificacion = new GregorianCalendar(60,4,5);
		pedido.pagar(fechaModificacion);
		pedido.enviar(fechaModificacion,direccion);
		assertThrows(IllegalStateException.class,()->{pedido.cancelar(fechaModificacion);});
	}
	
	@Test
	@Tag("TDD")
	void testEstadoEnviadoEntregar() {
		Calendar fechaModificacion = new GregorianCalendar(60,4,5);
		pedido.pagar(fechaModificacion);
		Calendar fechaModificacion2 = new GregorianCalendar(60,6,9);
		pedido.enviar(fechaModificacion2,direccion);
		Calendar fechaModificacion3 = new GregorianCalendar(68,3,5);
		pedido.entregar(fechaModificacion3);
		assertEquals(EstadoPedido.Entregado,pedido.getEstado());
		assertEquals(fechaModificacion3,Pedido.getFechaModificacion());
	}
	
	@Test
	@Tag("TDD")
	void testEstadoEnviadoEntregarFechaAnterior() {
		Calendar fechaModificacion = new GregorianCalendar(60,4,5);
		pedido.pagar(fechaModificacion);
		Calendar fechaModificacion2 = new GregorianCalendar(60,6,9);
		pedido.enviar(fechaModificacion2,direccion);
		Calendar fechaModificacion3 = new GregorianCalendar(20,3,5);
		assertTrue(fechaModificacion2.compareTo(fechaModificacion3) > 0);
		assertThrows(IllegalArgumentException.class,()->{pedido.entregar(fechaModificacion3);});
	}
	
	@Test
	@Tag("TDD")
	void testEstadoEnviadoEntregarFechaNula() {
		Calendar fechaModificacion = new GregorianCalendar(60,4,5);
		pedido.pagar(fechaModificacion);
		Calendar fechaModificacion2 = new GregorianCalendar(60,6,9);
		pedido.enviar(fechaModificacion2,direccion);
		assertThrows(IllegalArgumentException.class,()->{pedido.entregar(null);});
	}
	
	
/*Tests Estado Entregado*/
	
	
	@Test
	void testEstadoEntregadoAnnadirProducto() {
		Producto producto2 = new Producto("443",descripcion,dimensiones);
		Calendar fechaModificacion = new GregorianCalendar(60,4,5);
		pedido.pagar(fechaModificacion);
		pedido.enviar(fechaModificacion,direccion);
		pedido.entregar(fechaModificacion);
		assertThrows(IllegalStateException.class,()->{pedido.annadirProducto(producto2);});
	}
	
	@Test
	void testEstadoEntregadoEliminarProducto() {
		Producto producto2 = new Producto("443",descripcion,dimensiones);
		pedido.annadirProducto(producto2);
		Calendar fechaModificacion = new GregorianCalendar(60,4,5);
		pedido.pagar(fechaModificacion);
		pedido.enviar(fechaModificacion,direccion);
		pedido.entregar(fechaModificacion);
		assertThrows(IllegalStateException.class,()->{pedido.eliminarProducto(producto2);});
	}
	
	@Test
	void testEstadoEntregadoPagar() {
		Calendar fechaModificacion = new GregorianCalendar(60,4,5);
		pedido.pagar(fechaModificacion);
		pedido.enviar(fechaModificacion,direccion);
		pedido.entregar(fechaModificacion);
		assertThrows(IllegalStateException.class,()->{pedido.pagar(fechaModificacion);});
	}
	
	@Test
	void testEstadoEntregadoEnviar() {
		Calendar fechaModificacion = new GregorianCalendar(60,4,5);
		pedido.pagar(fechaModificacion);
		pedido.enviar(fechaModificacion,direccion);
		pedido.entregar(fechaModificacion);
		assertThrows(IllegalStateException.class,()->{pedido.enviar(fechaModificacion,direccion);});
	}
	
	@Test
	void testEstadoEntregadoCancelar() {
		Calendar fechaModificacion = new GregorianCalendar(60,4,5);
		pedido.pagar(fechaModificacion);
		pedido.enviar(fechaModificacion,direccion);
		pedido.entregar(fechaModificacion);
		assertThrows(IllegalStateException.class,()->{pedido.cancelar(fechaModificacion);});
	}
	
/*Tests metodo Comparar*/
	
	
	@Test
	@Tag("TDD")
	void testCompararFechasCreacionIgual() {
		Pedido nuevoPedido = new Pedido("123",productos,fechaCreacion);
		assertEquals(0,pedido.comparar(nuevoPedido));
	}
	
	@Test
	@Tag("TDD")
	void testCompararFechaCreacionPosterior() {
		Calendar nuevaFechaCreacion = new GregorianCalendar(20,2,2);
		Pedido nuevoPedido = new Pedido("123",productos,nuevaFechaCreacion);
		assertTrue(fechaCreacion.compareTo(nuevaFechaCreacion) > 0);
		assertEquals(1,pedido.comparar(nuevoPedido));
	}
	
	@Test
	@Tag("TDD")
	void testCompararFechaCreacionAnterior() {
		Calendar nuevaFechaCreacion = new GregorianCalendar(80,1,3);
		Pedido nuevoPedido = new Pedido("123",productos,nuevaFechaCreacion);
		assertTrue(fechaCreacion.compareTo(nuevaFechaCreacion) < 0);
		assertEquals(-1,pedido.comparar(nuevoPedido));
	}
	
	@Test
	@Tag("TDD")
	void testCompararConPedidoNull() {
		assertThrows(IllegalArgumentException.class,()->{pedido.comparar(null);});
	}
	
	
/*Tests metodo Equals*/
	
	
	@Test
	@Tag("TDD")
	void testEqualsPedidoIdIguales() {
		List<Producto> nuevoProductos = new ArrayList<Producto>();
		Map<Producto,Integer> nuevoproductos = new HashMap<Producto, Integer>();
		Calendar nuevaFechaCreacion = new GregorianCalendar(50,1,2);
		Producto nuevoProducto = new Producto("456",descripcion,dimensiones);
		nuevoProductos.add(nuevoProducto);
	    nuevoproductos.put(nuevoProducto,3);
		Pedido nuevoPedido = new Pedido(id,nuevoproductos,nuevaFechaCreacion);
		assertEquals(pedido.getId(),nuevoPedido.getId());
		assertTrue(pedido.equals(nuevoPedido));
		try {
			fail("Metodo no implementado todavia");
		}catch(Exception e) {}
	}
	
	@Test
	void testEqualsPedidosIdDiferentes() {
		Pedido nuevoPedido = new Pedido("456789",productos,fechaCreacion);
		assertNotEquals(pedido.getId(),nuevoPedido.getId());
		assertFalse(pedido.equals(nuevoPedido));
	}
	
	@Test
	@Tag("TDD")
	void testEqualsPedidoNulo() {
		assertThrows(IllegalArgumentException.class,()->{pedido.equals(null);});
	}
	
	@Test
	@Tag("TDD")
	void testEqualsNoPedido() {
		assertThrows(IllegalArgumentException.class,()->{pedido.equals(producto);});
	}
}
