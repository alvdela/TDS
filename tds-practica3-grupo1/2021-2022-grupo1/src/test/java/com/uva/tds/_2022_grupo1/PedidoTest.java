package com.uva.tds._2022_grupo1;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * @author Dario Gago Perez
 * @author Alvaro de la Rosa Zarzuelo
 *
 */
class PedidoTest {	
	private String id;
	private Map<Producto, Integer> productos;
	private LocalDate fechaCreacion;
	private Producto producto; 
	private Order pedido;
	private String direccion;
	private String descripcion;
	private Dimension3D dimensiones;
	
	@BeforeEach
	void setUp(){
		id = "1234";
		productos = new HashMap<Producto, Integer>();
		fechaCreacion = LocalDate.of(2005,1,2);
		dimensiones = new Dimension3D(1,2,3);
		descripcion = "Descripcion del producto";
		producto = new Producto("678",descripcion,dimensiones);
	    productos.put(producto,1);
	    pedido = new Order(id,productos,fechaCreacion);
	    direccion = "Calle El Rocio, N 4";
	}
	
/*Tests Creacion*/

	@Test
	@Tag("TDD")
	void testCreacionPedidoIdLimiteInferior() {
		Order nuevoPedido = new Order("a",productos,fechaCreacion);
		assertEquals("a", nuevoPedido.getId());
		assertEquals(productos,nuevoPedido.getProductos());
		assertEquals(OrderState.Recibido, nuevoPedido.getEstado());
		assertEquals(nuevoPedido.getFechaCreacion(),fechaCreacion);
		assertEquals(nuevoPedido.getFechaCreacion(),nuevoPedido.getFechaModificacion());
	}
	
	@Test
	void testCreacionPedidoIdLimiteSuperior() {
		Order nuevoPedido = new Order("123456789111",productos,fechaCreacion);
		assertEquals("123456789111", nuevoPedido.getId());
	}
	
	@Test
	@Tag("TDD")
	void testCreacionPedidoIdMayorLimiteSuperior() {
		assertThrows(IllegalArgumentException.class,()->{new Order("1234567891111",productos,fechaCreacion);});
	}

	@Test
	@Tag("TDD")
	void testCreacionPedidoIdMenorLimiteInferior() {
		assertThrows(IllegalArgumentException.class,()->{new Order("",productos,fechaCreacion);});
	}
	
	@Test
	@Tag("TDD")
	void testCreacionPedidoListaProductosNula() {
		assertThrows(IllegalArgumentException.class,()->{new Order(id,null,fechaCreacion);});
	}
	
	@Test
	@Tag("TDD")
	void testCreacionPedidoListaProductosVacia() {
		HashMap<Producto, Integer> productosVacia = new HashMap<Producto, Integer>();
		assertThrows(IllegalArgumentException.class,()->{new Order(id,productosVacia,fechaCreacion);});
	}
	
	@Test
	@Tag("TDD")
	void testCreacionPedidoFechaCreacionNula() {
		assertThrows(IllegalArgumentException.class,()->{new Order(id,productos,null);});
	}
	
	@Test
	@Tag("WhiteBox")
	void testCreacionIdEmpty() {
		assertThrows(IllegalArgumentException.class,()->{new Order(null,productos,fechaCreacion);});
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
		assertEquals(OrderState.Recibido, pedido.getEstado());
		assertEquals(fechaCreacion,pedido.getFechaCreacion());
		assertEquals(pedido.getFechaCreacion(),pedido.getFechaModificacion());
	}
	
	@Test
	void testEstadoRecibidoAnnadirProductoExistente() {
		pedido.annadirProducto(producto);
		assertEquals(id, pedido.getId());
		assertEquals(2,pedido.getProductos().get(producto));
		assertEquals(OrderState.Recibido, pedido.getEstado());
		assertEquals(fechaCreacion,pedido.getFechaCreacion());
		assertEquals(pedido.getFechaCreacion(),pedido.getFechaModificacion());
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
		assertEquals(productos,pedido.getProductos());
		assertEquals(OrderState.Recibido, pedido.getEstado());
		assertEquals(fechaCreacion,pedido.getFechaCreacion());
		assertEquals(pedido.getFechaCreacion(),pedido.getFechaModificacion());
	}
	
	@Test
	@Tag("WhiteBox")
	void testEstadoRecibidoEliminarProductoNoExist() {
		Producto producto2 = new Producto("443",descripcion,dimensiones);
		pedido.eliminarProducto(producto2);
		assertEquals(productos,pedido.getProductos());
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
		assertEquals(OrderState.Recibido, pedido.getEstado());
		assertEquals(fechaCreacion,pedido.getFechaCreacion());
		assertEquals(pedido.getFechaCreacion(),pedido.getFechaModificacion());
	}
	
	@Test
	@Tag("TDD")
	void testEstadoRecibidoEliminarProductoNulo() {
		assertThrows(IllegalArgumentException.class,()->{pedido.eliminarProducto(null);});
	}
	
	@Test
	@Tag("TDD")
	void testEstadoRecibidoCancelar() {
		LocalDate fechaModificacion = LocalDate.of(2006,4,5);
		pedido.cancelar(fechaModificacion);
		assertEquals(OrderState.Cancelado,pedido.getEstado());
		assertEquals(id, pedido.getId());
		assertEquals(productos,pedido.getProductos());
		assertEquals(fechaCreacion,pedido.getFechaCreacion());
		assertEquals(fechaModificacion,pedido.getFechaModificacion());
	}
	
	@Test
	@Tag("TDD")
	void testEstadoRecibidoCancelarFechaModificacionNula() {
		assertThrows(IllegalArgumentException.class,()->{pedido.cancelar(null);});
	}
	
	@Test
	@Tag("TDD")
	void testEstadoRecibidoCancelarFechaAnterior() {
		LocalDate fechaModificacion = LocalDate.of(2004,4,5);
		assertTrue(fechaCreacion.compareTo(fechaModificacion) > 0);
		assertThrows(IllegalArgumentException.class,()->{pedido.cancelar(fechaModificacion);});
	}
	
	@Test
	@Tag("TDD")
	void testEstadoRecibidoPagar() {
		LocalDate fechaModificacion = LocalDate.of(2006,4,5);
		pedido.pagar(fechaModificacion);
		assertEquals(OrderState.Pagado,pedido.getEstado());
		assertEquals(id, pedido.getId());
		assertEquals(productos,pedido.getProductos());
		assertEquals(fechaCreacion,pedido.getFechaCreacion());
		assertEquals(fechaModificacion,pedido.getFechaModificacion());
	}
	
	@Test
	@Tag("TDD")
	void testEstadoRecibidoPagarFechaAnterior() {
		LocalDate fechaModificacion = LocalDate.of(2004,4,5);
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
		LocalDate fechaModificacion = LocalDate.of(2006,4,5);
		assertThrows(IllegalStateException.class,()->{pedido.enviar(fechaModificacion,direccion);});
	}
	
	@Test
	void testEstadoRecibidoEntregar() {
		LocalDate fechaModificacion = LocalDate.of(2006,4,5);
		assertThrows(IllegalStateException.class,()->{pedido.entregar(fechaModificacion);});
	}
	
	@Test
	@Tag("TDD")
	void testEstadoRecibidoGetDireccion() {
		assertEquals(null,pedido.getDireccion());
	}
	
	
/*Tests Estado Pagado*/

	
	@Test
	void testEstadoPagadoAnnadirProducto() {
		Producto producto2 = new Producto("443",descripcion,dimensiones);
		LocalDate fechaModificacion = LocalDate.of(2006,4,5);
		pedido.pagar(fechaModificacion);
		assertThrows(IllegalStateException.class,()->{pedido.annadirProducto(producto2);});
	}	
	
	@Test
	void testEstadoPagadoEliminarProducto() {
		Producto producto2 = new Producto("443",descripcion,dimensiones);
		LocalDate fechaModificacion = LocalDate.of(2006,4,5);
		pedido.pagar(fechaModificacion);
		assertThrows(IllegalStateException.class,()->{pedido.annadirProducto(producto2);});
	}
	
	@Test
	void testEstadoPagadoEntregar() {
		LocalDate fechaModificacion = LocalDate.of(2006,4,5);
		pedido.pagar(fechaModificacion);
		assertThrows(IllegalStateException.class,()->{pedido.entregar(fechaModificacion);});
	}
	
	@Test
	void testEstadoPagadoCancelar() {
		LocalDate fechaModificacion = LocalDate.of(2006,4,5);
		pedido.pagar(fechaModificacion);
		assertThrows(IllegalStateException.class,()->{pedido.cancelar(fechaModificacion);});
	}
	
	@Test
	@Tag("TDD")
	void testEstadoPagadoEnviar() {
		LocalDate fechaModificacion = LocalDate.of(2006,4,5);
		pedido.pagar(fechaModificacion);
		LocalDate fechaModificacion2 = LocalDate.of(2006,6,9);
		pedido.enviar(fechaModificacion2,direccion);
		assertEquals(OrderState.Enviado,pedido.getEstado());
		assertEquals(pedido.getFechaModificacion(),fechaModificacion2);
	}
	
	@Test
	@Tag("TDD")
	void testEstadoPagadoEnviarFechaAnterior() {
		LocalDate fechaModificacion = LocalDate.of(2006,4,5);
		pedido.pagar(fechaModificacion);
		LocalDate fechaModificacion2 = LocalDate.of(2002,6,9);
		assertTrue(fechaModificacion.compareTo(fechaModificacion2) > 0);
		assertThrows(IllegalArgumentException.class,()->{pedido.enviar(fechaModificacion2,direccion);});
	}
	
	@Test
	@Tag("TDD")
	void testEstadoPagadoEnviarFechaModificacionNula() {
		LocalDate fechaModificacion = LocalDate.of(2006,4,5);
		pedido.pagar(fechaModificacion);
		assertThrows(IllegalArgumentException.class,()->{pedido.enviar(null,direccion);});
	}
	
	@Test
	@Tag("TDD")
	void testEstadoPagadoEnviarDireccionNull() {
		LocalDate fechaModificacion = LocalDate.of(2006,4,5);
		pedido.pagar(fechaModificacion);
		assertThrows(IllegalArgumentException.class,()->{pedido.enviar(fechaModificacion,null);});
	}
	
	@Test
	@Tag("WhiteBox")
	void testSetDireccionVacia() {
		pedido.pagar(fechaCreacion);
		assertThrows(IllegalArgumentException.class,()->{pedido.enviar(fechaCreacion,"");});
	}
	
	@Test
	void testEstadoPagadoGetDireccion() {
		LocalDate fechaModificacion = LocalDate.of(2006,4,5);
		pedido.pagar(fechaModificacion);
		assertEquals(null,pedido.getDireccion());
	}
	
	
/*Tests Estado Cancelado */	

	@Test
	void testEstadoCanceladoAnnadirProducto() {
		Producto producto2 = new Producto("443",descripcion,dimensiones);
		LocalDate fechaModificacion = LocalDate.of(2006,4,5);
		pedido.cancelar(fechaModificacion);
		assertThrows(IllegalStateException.class,()->{pedido.annadirProducto(producto2);});
	}
	
	@Test
	void testEstadoCanceladoEliminarProducto() {
		Producto producto2 = new Producto("443",descripcion,dimensiones);
		pedido.annadirProducto(producto2);
		LocalDate fechaModificacion = LocalDate.of(2006,4,5);
		pedido.cancelar(fechaModificacion);
		assertThrows(IllegalStateException.class,()->{pedido.eliminarProducto(producto2);});
	}
	
	@Test
	void testEstadoCanceladoPagar() {
		Producto producto2 = new Producto("443",descripcion,dimensiones);
		pedido.annadirProducto(producto2);
		LocalDate fechaModificacion = LocalDate.of(2006,4,5);
		pedido.cancelar(fechaModificacion);
		assertThrows(IllegalStateException.class,()->{pedido.pagar(fechaModificacion);});
	}
	
	@Test
	void testEstadoCanceladoEnviar() {
		Producto producto2 = new Producto("443",descripcion,dimensiones);
		pedido.annadirProducto(producto2);
		LocalDate fechaModificacion = LocalDate.of(2006,4,5);
		pedido.cancelar(fechaModificacion);
		assertThrows(IllegalStateException.class,()->{pedido.enviar(fechaModificacion,direccion);});
	}
	
	@Test
	void testEstadoCanceladoEntregar() {
		Producto producto2 = new Producto("443",descripcion,dimensiones);
		pedido.annadirProducto(producto2);
		LocalDate fechaModificacion = LocalDate.of(2006,4,5);
		pedido.cancelar(fechaModificacion);
		assertThrows(IllegalStateException.class,()->{pedido.entregar(fechaModificacion);});
	}
	
	@Test
	void testEstadoCanceladoGetDireccion() {
		LocalDate fechaModificacion = LocalDate.of(2006,4,5);
		pedido.cancelar(fechaModificacion);
		assertEquals(null,pedido.getDireccion());
	}
	
	
/*Tests Estado Enviado */	

	
	@Test
	void testEstadoEnviadoAnnadirProducto() {
		Producto producto2 = new Producto("443",descripcion,dimensiones);
		LocalDate fechaModificacion = LocalDate.of(2006,4,5);
		pedido.pagar(fechaModificacion);
		pedido.enviar(fechaModificacion,direccion);
		assertThrows(IllegalStateException.class,()->{pedido.annadirProducto(producto2);});
	}
	
	@Test
	void testEstadoEnviadoEliminarProducto() {
		Producto producto2 = new Producto("443",descripcion,dimensiones);
		pedido.annadirProducto(producto2);
		LocalDate fechaModificacion = LocalDate.of(2006,4,5);
		pedido.pagar(fechaModificacion);
		pedido.enviar(fechaModificacion,direccion);
		assertThrows(IllegalStateException.class,()->{pedido.eliminarProducto(producto2);});
	}
	
	@Test
	void testEstadoEnviadoPagar() {
		LocalDate fechaModificacion = LocalDate.of(2006,4,5);
		pedido.pagar(fechaModificacion);
		pedido.enviar(fechaModificacion,direccion);
		assertThrows(IllegalStateException.class,()->{pedido.pagar(fechaModificacion);});
	}
	
	@Test
	void testEstadoEnviadoCancelar() {
		LocalDate fechaModificacion = LocalDate.of(2006,4,5);
		pedido.pagar(fechaModificacion);
		pedido.enviar(fechaModificacion,direccion);
		assertThrows(IllegalStateException.class,()->{pedido.cancelar(fechaModificacion);});
	}
	
	@Test
	@Tag("TDD")
	void testEstadoEnviadoEntregar() {
		LocalDate fechaModificacion = LocalDate.of(2006,4,5);
		pedido.pagar(fechaModificacion);
		LocalDate fechaModificacion2 = LocalDate.of(2006,6,9);
		pedido.enviar(fechaModificacion2,direccion);
		LocalDate fechaModificacion3 = LocalDate.of(2006,8,5);
		pedido.entregar(fechaModificacion3);
		assertEquals(OrderState.Entregado,pedido.getEstado());
		assertEquals(fechaModificacion3,pedido.getFechaModificacion());
	}
	
	@Test
	@Tag("TDD")
	void testEstadoEnviadoEntregarFechaAnterior() {
		LocalDate fechaModificacion = LocalDate.of(2006,4,5);
		pedido.pagar(fechaModificacion);
		LocalDate fechaModificacion2 = LocalDate.of(2006,6,9);
		pedido.enviar(fechaModificacion2,direccion);
		LocalDate fechaModificacion3 = LocalDate.of(20,3,5);
		assertTrue(fechaModificacion2.compareTo(fechaModificacion3) > 0);
		assertThrows(IllegalArgumentException.class,()->{pedido.entregar(fechaModificacion3);});
	}
	
	@Test
	@Tag("TDD")
	void testEstadoEnviadoEntregarFechaNula() {
		LocalDate fechaModificacion = LocalDate.of(2006,4,5);
		pedido.pagar(fechaModificacion);
		LocalDate fechaModificacion2 = LocalDate.of(2006,6,9);
		pedido.enviar(fechaModificacion2,direccion);
		assertThrows(IllegalArgumentException.class,()->{pedido.entregar(null);});
	}
	
	
	
	
/*Tests Estado Entregado*/
	
	
	@Test
	void testEstadoEntregadoAnnadirProducto() {
		Producto producto2 = new Producto("443",descripcion,dimensiones);
		LocalDate fechaModificacion = LocalDate.of(2006,4,5);
		pedido.pagar(fechaModificacion);
		pedido.enviar(fechaModificacion,direccion);
		pedido.entregar(fechaModificacion);
		assertThrows(IllegalStateException.class,()->{pedido.annadirProducto(producto2);});
	}
	
	@Test
	void testEstadoEntregadoEliminarProducto() {
		Producto producto2 = new Producto("443",descripcion,dimensiones);
		pedido.annadirProducto(producto2);
		LocalDate fechaModificacion = LocalDate.of(2006,4,5);
		pedido.pagar(fechaModificacion);
		pedido.enviar(fechaModificacion,direccion);
		pedido.entregar(fechaModificacion);
		assertThrows(IllegalStateException.class,()->{pedido.eliminarProducto(producto2);});
	}
	
	@Test
	void testEstadoEntregadoPagar() {
		LocalDate fechaModificacion = LocalDate.of(2006,4,5);
		pedido.pagar(fechaModificacion);
		pedido.enviar(fechaModificacion,direccion);
		pedido.entregar(fechaModificacion);
		assertThrows(IllegalStateException.class,()->{pedido.pagar(fechaModificacion);});
	}
	
	@Test
	void testEstadoEntregadoEnviar() {
		LocalDate fechaModificacion = LocalDate.of(2006,4,5);
		pedido.pagar(fechaModificacion);
		pedido.enviar(fechaModificacion,direccion);
		pedido.entregar(fechaModificacion);
		assertThrows(IllegalStateException.class,()->{pedido.enviar(fechaModificacion,direccion);});
	}
	
	@Test
	void testEstadoEntregadoCancelar() {
		LocalDate fechaModificacion = LocalDate.of(2006,4,5);
		pedido.pagar(fechaModificacion);
		pedido.enviar(fechaModificacion,direccion);
		pedido.entregar(fechaModificacion);
		assertThrows(IllegalStateException.class,()->{pedido.cancelar(fechaModificacion);});
	}
	
/*Tests metodo Comparar*/
	
	
	@Test
	@Tag("TDD")
	void testCompararFechasCreacionIgual() {
		Order nuevoPedido = new Order("123",productos,fechaCreacion);
		assertEquals(0,pedido.comparar(nuevoPedido));
	}
	
	@Test
	@Tag("TDD")
	void testCompararFechaCreacionPosterior() {
		LocalDate nuevaFechaCreacion = LocalDate.of(2002,2,2);
		Order nuevoPedido = new Order("123",productos,nuevaFechaCreacion);
		assertTrue(fechaCreacion.compareTo(nuevaFechaCreacion) > 0);
		assertEquals(1,pedido.comparar(nuevoPedido));
	}
	
	@Test
	@Tag("TDD")
	void testCompararFechaCreacionAnterior() {
		LocalDate nuevaFechaCreacion = LocalDate.of(2008,1,3);
		Order nuevoPedido = new Order("123",productos,nuevaFechaCreacion);
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
		LocalDate nuevaFechaCreacion = LocalDate.of(2005,1,2);
		Producto nuevoProducto = new Producto("456",descripcion,dimensiones);
		nuevoProductos.add(nuevoProducto);
	    nuevoproductos.put(nuevoProducto,3);
		Order nuevoPedido = new Order(id,nuevoproductos,nuevaFechaCreacion);
		assertEquals(pedido.getId(),nuevoPedido.getId());
		assertEquals(pedido,nuevoPedido);
	}
	
	@Test
	void testEqualsPedidosIdDiferentes() {
		Order nuevoPedido = new Order("456789",productos,fechaCreacion);
		assertNotEquals(pedido.getId(),nuevoPedido.getId());
		assertNotEquals(pedido,nuevoPedido);
	}
	
	@Test
	@Tag("WhiteBox")
	void testEqualsPedidosMismoObjeto() {
		assertEquals(pedido,pedido);
	}
	
	@Test
	@Tag("WhiteBox")
	void testEqualsPedidosDistintoObjeto() {
		assertNotEquals(pedido,producto);
	}
	
}
