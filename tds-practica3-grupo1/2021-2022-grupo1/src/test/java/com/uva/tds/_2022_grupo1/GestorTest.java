package com.uva.tds._2022_grupo1;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.ArrayList;
import org.easymock.EasyMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


/**
 * @author Dario Gago Perez
 * @author Alvaro de la Rosa Zarzuelo
 *
 */

class GestorTest {
	
	private Dimension3D dim;
	private Producto producto;
	private HashMap<Producto, Integer> listaProductos;
	private LocalDate fechaCreac;
	private Order pedido;
	private ArrayList<Order> pedidos;
	private String direccion;
	private Gestor gestor;	
	private IDatabaseManager iDatabaseManager;
	
	@BeforeEach
	void setUp() {
		//Creamos un producto
		dim = new Dimension3D(1,1,1);
		producto = new Producto("1234", "producto prueba", dim);
		//Creamos un pedido
		listaProductos = new HashMap<>();
		listaProductos.put(producto, 7);
		fechaCreac = LocalDate.of(2021, 7, 21);
		pedido = new Order("a1",listaProductos,fechaCreac);
		direccion = "Calle Labradores,N 3";
		//Creamos gestor
		pedidos = new ArrayList<Order>();
		pedidos.add(pedido);
		gestor = new Gestor();
		iDatabaseManager = EasyMock.mock(IDatabaseManager.class);
	}
	
	@Test
	@Tag("Insolation")
	void testCreacionGestor() {
		EasyMock.expect(iDatabaseManager.getByStateAndDates(null, null, null)).andReturn(new ArrayList<Order>()).times(1);
		EasyMock.replay(iDatabaseManager);
		gestor.setIDatabaseManager(iDatabaseManager);
		assertEquals(0,gestor.getNumPedidos());
	}
	
	@Test
	@Tag("Insolation")
	void testAnnadirPedido() {
		EasyMock.expect(iDatabaseManager.get(pedido.getId())).andReturn(null).times(1);
		iDatabaseManager.add(EasyMock.anyObject());
		EasyMock.expect(iDatabaseManager.get(pedido.getId())).andReturn(pedido).times(1);
		EasyMock.expect(iDatabaseManager.getByStateAndDates(null, null, null)).andReturn(pedidos).times(1);
		EasyMock.replay(iDatabaseManager);
		gestor.setIDatabaseManager(iDatabaseManager);
		gestor.annadirPedido(pedido);
		assertEquals(pedido,gestor.getPedido(pedido.getId()));
		assertEquals(1,gestor.getNumPedidos());
		EasyMock.verify(iDatabaseManager);
	}
	
	
	@Test
	@Tag("Insolation")
	void testEliminarPedido() {
		EasyMock.expect(iDatabaseManager.get(pedido.getId())).andReturn(null).times(1);
		iDatabaseManager.add(EasyMock.anyObject());
		EasyMock.expect(iDatabaseManager.get(pedido.getId())).andReturn(pedido).times(1);
		iDatabaseManager.remove(EasyMock.anyObject());
		EasyMock.expect(iDatabaseManager.get(pedido.getId())).andReturn(null).times(1);
		EasyMock.expect(iDatabaseManager.getByStateAndDates(null, null, null)).andReturn(new ArrayList<Order>()).times(1);
		EasyMock.replay(iDatabaseManager);
		gestor.setIDatabaseManager(iDatabaseManager);
		gestor.annadirPedido(pedido);
		gestor.eliminarProducto(pedido);
		assertEquals(null,gestor.getPedido(pedido.getId()));
		assertEquals(0,gestor.getNumPedidos());
		EasyMock.verify(iDatabaseManager);
	}
	
	@Test
	@Tag("WhiteBox")
	void testEliminarPedidoNoExist() {
		EasyMock.expect(iDatabaseManager.get(pedido.getId())).andReturn(null).times(1);
		EasyMock.expect(iDatabaseManager.get(pedido.getId())).andReturn(null).times(1);
		EasyMock.expect(iDatabaseManager.getByStateAndDates(null, null, null)).andReturn(new ArrayList<Order>()).times(1);
		EasyMock.replay(iDatabaseManager);
		gestor.setIDatabaseManager(iDatabaseManager);
		gestor.eliminarProducto(pedido);
		assertEquals(null,gestor.getPedido(pedido.getId()));
		assertEquals(0,gestor.getNumPedidos());
		EasyMock.verify(iDatabaseManager);
	}
	
	@Test
	@Tag("Insolation")
	void testAnnadirPedidoIgual() {
		EasyMock.expect(iDatabaseManager.get(pedido.getId())).andReturn(null).times(1);
		iDatabaseManager.add(EasyMock.anyObject());
		EasyMock.expect(iDatabaseManager.get(pedido.getId())).andReturn(pedido).times(1);
		EasyMock.replay(iDatabaseManager);
		gestor.setIDatabaseManager(iDatabaseManager);
		gestor.annadirPedido(pedido);
		assertThrows(IllegalArgumentException.class,
				()->{gestor.annadirPedido(pedido);});
	}
	
	@Test
	@Tag("TDD")
	void testGetPedidoIdVacio() {
		assertThrows(IllegalArgumentException.class,
				()->{gestor.getPedido("");});
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
	void testGetListaCronologica() {
		LocalDate fechaNueva = LocalDate.of(2021, 9, 21);
		Order nuevoPedido = new Order("a",listaProductos,fechaNueva);
		ArrayList<Order> pedidos = new ArrayList<Order>();
		pedidos.add(pedido);pedidos.add(nuevoPedido);
		ArrayList<Order> listaOrdenada = new ArrayList<>();
		listaOrdenada.add(pedido);
		listaOrdenada.add(nuevoPedido);
		EasyMock.expect(iDatabaseManager.get(pedido.getId())).andReturn(null).times(1);
		iDatabaseManager.add(EasyMock.anyObject());
		EasyMock.expect(iDatabaseManager.get(nuevoPedido.getId())).andReturn(null).times(1);
		iDatabaseManager.add(EasyMock.anyObject());
		EasyMock.expect(iDatabaseManager.getByStateAndDates(null, null, null)).andReturn(pedidos).times(1);
		EasyMock.replay(iDatabaseManager);
		gestor.setIDatabaseManager(iDatabaseManager);
		gestor.annadirPedido(pedido);
		gestor.annadirPedido(nuevoPedido);
		assertTrue(fechaNueva.compareTo(fechaCreac) > 0);	
		assertEquals(listaOrdenada, gestor.getListaCronologica());
		EasyMock.verify(iDatabaseManager);
	}
	
	@Test
	@Tag("TDD")
	void testGetFechaReciente() {
		LocalDate fechaNueva = LocalDate.of(2021, 9, 21);
		Order nuevoPedido = new Order("a2",listaProductos,fechaNueva);
		ArrayList<Order> pedidos = new ArrayList<Order>();
		pedidos.add(pedido);pedidos.add(nuevoPedido);
		EasyMock.expect(iDatabaseManager.get(pedido.getId())).andReturn(null).times(1);
		iDatabaseManager.add(EasyMock.anyObject());
		EasyMock.expect(iDatabaseManager.get(nuevoPedido.getId())).andReturn(null).times(1);
		iDatabaseManager.add(EasyMock.anyObject());
		EasyMock.expect(iDatabaseManager.getByStateAndDates(null, null, null)).andReturn(pedidos).times(1);
		EasyMock.replay(iDatabaseManager);
		gestor.setIDatabaseManager(iDatabaseManager);
		gestor.annadirPedido(pedido);
		gestor.annadirPedido(nuevoPedido);
		assertEquals(fechaNueva,gestor.getFechaReciente());
		EasyMock.verify(iDatabaseManager);
	}
	
	@Test
	@Tag("TDD")
	void testGetFechaAntigua() {
		LocalDate fechaNueva = LocalDate.of(2021, 9, 21);
		Order nuevoPedido = new Order("a2",listaProductos,fechaNueva);
		ArrayList<Order> pedidos = new ArrayList<Order>();
		pedidos.add(pedido);pedidos.add(nuevoPedido);
		EasyMock.expect(iDatabaseManager.get(pedido.getId())).andReturn(null).times(1);
		iDatabaseManager.add(EasyMock.anyObject());
		EasyMock.expect(iDatabaseManager.get(nuevoPedido.getId())).andReturn(null).times(1);
		iDatabaseManager.add(EasyMock.anyObject());
		EasyMock.expect(iDatabaseManager.getByStateAndDates(null, null, null)).andReturn(pedidos).times(1);
		EasyMock.replay(iDatabaseManager);
		gestor.setIDatabaseManager(iDatabaseManager);
		gestor.annadirPedido(pedido);
		gestor.annadirPedido(nuevoPedido);
		assertEquals(fechaCreac,gestor.getFechaAntigua());
		EasyMock.verify(iDatabaseManager);
	}
	
	@Test
	@Tag("TDD")
	void testGetGestorEstadoIntervaloFecha1Null() {
		assertThrows(IllegalArgumentException.class,
				()->{gestor.getGestorEstadoIntervalo(OrderState.Recibido, null, LocalDate.of(2021, 9, 21));});
	}
	
	@Test
	@Tag("TDD")
	void testGetGestorEstadoIntervaloFecha2Null() {
		assertThrows(IllegalArgumentException.class,
				()->{gestor.getGestorEstadoIntervalo(OrderState.Recibido, LocalDate.of(2021, 9, 21), null);});
	}
	
	@Test
	@Tag("Insolation")
	void testGetGestorEstadoIntervaloEstadoRecibido() {
		EasyMock.expect(iDatabaseManager.get(pedido.getId())).andReturn(null).times(1);
		iDatabaseManager.add(EasyMock.anyObject());
		EasyMock.expect(iDatabaseManager.getByStateAndDates(OrderState.Recibido, null, null)).andReturn(pedidos).times(1);
		EasyMock.replay(iDatabaseManager);
		gestor.setIDatabaseManager(iDatabaseManager);
		gestor.annadirPedido(pedido);
		assertEquals(pedidos,gestor.getGestorEstadoIntervalo(OrderState.Recibido, null, null));
		EasyMock.verify(iDatabaseManager);
	}
	
	@Test
	@Tag("Insolation")
	void testGetGestorEstadoIntervaloEstadoRecibidoNoEncontrado() {
		EasyMock.expect(iDatabaseManager.get(pedido.getId())).andReturn(null).times(1);
		iDatabaseManager.add(EasyMock.anyObject());
		EasyMock.expect(iDatabaseManager.getByStateAndDates(OrderState.Recibido, null, null)).andReturn(new ArrayList<Order>()).times(1);
		EasyMock.replay(iDatabaseManager);
		gestor.setIDatabaseManager(iDatabaseManager);
		pedido.pagar(fechaCreac);
		gestor.annadirPedido(pedido);
		assertEquals(new ArrayList<Order>(),gestor.getGestorEstadoIntervalo(OrderState.Recibido, null, null));
		EasyMock.verify(iDatabaseManager);
	}
	
	@Test
	@Tag("Insolation")
	void testGetGestorEstadoIntervaloEstadoPagado() {
		pedido.pagar(fechaCreac);
		ArrayList<Order> pedidosPagados = new ArrayList<Order>();
		pedidosPagados.add(pedido); 
		EasyMock.expect(iDatabaseManager.get(pedido.getId())).andReturn(null).times(1);
		iDatabaseManager.add(EasyMock.anyObject());
		EasyMock.expect(iDatabaseManager.getByStateAndDates(OrderState.Pagado, null, null)).andReturn(pedidosPagados).times(1);
		EasyMock.replay(iDatabaseManager);
		gestor.setIDatabaseManager(iDatabaseManager);
		gestor.annadirPedido(pedido);
		assertEquals(pedidosPagados,gestor.getGestorEstadoIntervalo(OrderState.Pagado, null, null));
		EasyMock.verify(iDatabaseManager);
	}
	
	@Test
	@Tag("Insolation")
	void testGetGestorEstadoIntervaloEstadoPagadoNoEncontrado() {
		pedido.pagar(fechaCreac);
		pedido.enviar(fechaCreac, direccion);
		EasyMock.expect(iDatabaseManager.get(pedido.getId())).andReturn(null).times(1);
		iDatabaseManager.add(EasyMock.anyObject());
		EasyMock.expect(iDatabaseManager.getByStateAndDates(OrderState.Pagado, null, null)).andReturn(new ArrayList<Order>()).times(1);
		EasyMock.replay(iDatabaseManager);
		gestor.setIDatabaseManager(iDatabaseManager);
		gestor.annadirPedido(pedido);
		assertEquals(new ArrayList<Order>(),gestor.getGestorEstadoIntervalo(OrderState.Pagado, null, null));
		EasyMock.verify(iDatabaseManager);
	}
	
	@Test
	@Tag("Insolation")
	void testGetGestorEstadoIntervaloEstadoCancelado() {
		pedido.cancelar(fechaCreac);
		ArrayList<Order> pedidosCancelados = new ArrayList<Order>();
		pedidosCancelados.add(pedido); 
		EasyMock.expect(iDatabaseManager.get(pedido.getId())).andReturn(null).times(1);
		iDatabaseManager.add(EasyMock.anyObject());
		EasyMock.expect(iDatabaseManager.getByStateAndDates(OrderState.Cancelado, null, null)).andReturn(pedidosCancelados).times(1);
		EasyMock.replay(iDatabaseManager);
		gestor.setIDatabaseManager(iDatabaseManager);
		gestor.annadirPedido(pedido);
		assertEquals(pedidosCancelados,gestor.getGestorEstadoIntervalo(OrderState.Cancelado, null, null));
		EasyMock.verify(iDatabaseManager);
	}
	
	@Test
	@Tag("Insolation")
	void testGetGestorEstadoIntervaloEstadoCanceladoNoEncontrado() {
		pedido.pagar(fechaCreac);
		EasyMock.expect(iDatabaseManager.get(pedido.getId())).andReturn(null).times(1);
		iDatabaseManager.add(EasyMock.anyObject());
		EasyMock.expect(iDatabaseManager.getByStateAndDates(OrderState.Cancelado, null, null)).andReturn(new ArrayList<Order>()).times(1);
		EasyMock.replay(iDatabaseManager);
		gestor.setIDatabaseManager(iDatabaseManager);
		gestor.annadirPedido(pedido);
		assertEquals(new ArrayList<Order>(),gestor.getGestorEstadoIntervalo(OrderState.Cancelado, null, null));
		EasyMock.verify(iDatabaseManager);
	}
	
	@Test
	@Tag("Insolation")
	void testGetGestorEstadoIntervaloEstadoEnviado() {
		pedido.pagar(fechaCreac);
		pedido.enviar(fechaCreac, direccion);
		ArrayList<Order> pedidosEnviados = new ArrayList<Order>();
		pedidosEnviados.add(pedido); 
		EasyMock.expect(iDatabaseManager.get(pedido.getId())).andReturn(null).times(1);
		iDatabaseManager.add(EasyMock.anyObject());
		EasyMock.expect(iDatabaseManager.getByStateAndDates(OrderState.Enviado, null, null)).andReturn(pedidosEnviados).times(1);
		EasyMock.replay(iDatabaseManager);
		gestor.setIDatabaseManager(iDatabaseManager);
		gestor.annadirPedido(pedido);
		assertEquals(pedidosEnviados,gestor.getGestorEstadoIntervalo(OrderState.Enviado, null, null));
		EasyMock.verify(iDatabaseManager);
	}
	
	@Test
	@Tag("Insolation")
	void testGetGestorEstadoIntervaloEstadoEnviadoNoEncontrado() {
		pedido.pagar(fechaCreac);
		EasyMock.expect(iDatabaseManager.get(pedido.getId())).andReturn(null).times(1);
		iDatabaseManager.add(EasyMock.anyObject());
		EasyMock.expect(iDatabaseManager.getByStateAndDates(OrderState.Enviado, null, null)).andReturn(new ArrayList<Order>()).times(1);
		EasyMock.replay(iDatabaseManager);
		gestor.setIDatabaseManager(iDatabaseManager);
		gestor.annadirPedido(pedido);
		assertEquals(new ArrayList<Order>(),gestor.getGestorEstadoIntervalo(OrderState.Enviado, null, null));
		EasyMock.verify(iDatabaseManager);
	}
	
	@Test
	@Tag("Insolation")
	void testGetGestorEstadoIntervaloEstadoEntregado() {
		pedido.pagar(fechaCreac);
		pedido.enviar(fechaCreac, direccion);
		pedido.entregar(fechaCreac);
		ArrayList<Order> pedidosEntregados = new ArrayList<Order>();
		pedidosEntregados.add(pedido); 
		EasyMock.expect(iDatabaseManager.get(pedido.getId())).andReturn(null).times(1);
		iDatabaseManager.add(EasyMock.anyObject());
		EasyMock.expect(iDatabaseManager.getByStateAndDates(OrderState.Entregado, null, null)).andReturn(pedidosEntregados).times(1);
		EasyMock.replay(iDatabaseManager);
		gestor.setIDatabaseManager(iDatabaseManager);
		gestor.annadirPedido(pedido);
		assertEquals(pedidosEntregados,gestor.getGestorEstadoIntervalo(OrderState.Entregado, null, null));
		EasyMock.verify(iDatabaseManager);
	}
	
	@Test
	@Tag("Insolation")
	void testGetGestorEstadoIntervaloEstadoEntregadoNoEncontrado() {
		pedido.pagar(fechaCreac);
		EasyMock.expect(iDatabaseManager.get(pedido.getId())).andReturn(null).times(1);
		iDatabaseManager.add(EasyMock.anyObject());
		EasyMock.expect(iDatabaseManager.getByStateAndDates(OrderState.Entregado, null, null)).andReturn(new ArrayList<Order>()).times(1);
		EasyMock.replay(iDatabaseManager);
		gestor.setIDatabaseManager(iDatabaseManager);
		gestor.annadirPedido(pedido);
		assertEquals(new ArrayList<Order>(),gestor.getGestorEstadoIntervalo(OrderState.Entregado, null, null));
		EasyMock.verify(iDatabaseManager);
	}
	
	@Test
	@Tag("Insolation")
	void testGetGestorEstadoIntervaloUnaFecha() {
		EasyMock.expect(iDatabaseManager.get(pedido.getId())).andReturn(null).times(1);
		iDatabaseManager.add(EasyMock.anyObject());
		EasyMock.expect(iDatabaseManager.getByStateAndDates(null, fechaCreac, fechaCreac)).andReturn(pedidos).times(1);
		EasyMock.replay(iDatabaseManager);
		gestor.setIDatabaseManager(iDatabaseManager);
		gestor.annadirPedido(pedido);
		assertEquals(pedidos,gestor.getGestorEstadoIntervalo(null, fechaCreac, fechaCreac));
		EasyMock.verify(iDatabaseManager);
	}
	
	@Test
	@Tag("Insolation")
	void testGetGestorEstadoIntervaloUnaFechaNoEncontrado() { 
		EasyMock.expect(iDatabaseManager.get(pedido.getId())).andReturn(null).times(1);
		iDatabaseManager.add(EasyMock.anyObject());
		EasyMock.expect(iDatabaseManager.getByStateAndDates(null, LocalDate.of(2020, 7, 21), LocalDate.of(2020, 7, 21))).andReturn(new ArrayList<Order>()).times(1);
		EasyMock.replay(iDatabaseManager);
		gestor.setIDatabaseManager(iDatabaseManager);
		gestor.annadirPedido(pedido);
		assertEquals(new ArrayList<Order>(),gestor.getGestorEstadoIntervalo(null, LocalDate.of(2020, 7, 21), LocalDate.of(2020, 7, 21)));
		EasyMock.verify(iDatabaseManager);
	}
	
	@Test
	@Tag("Insolation")
	void testGetGestorEstadoIntervaloDosFechasExtremoInf() {
		EasyMock.expect(iDatabaseManager.get(pedido.getId())).andReturn(null).times(1);
		iDatabaseManager.add(EasyMock.anyObject());
		EasyMock.expect(iDatabaseManager.getByStateAndDates(null, fechaCreac, LocalDate.of(2021, 8, 21))).andReturn(pedidos).times(1);
		EasyMock.replay(iDatabaseManager);
		gestor.setIDatabaseManager(iDatabaseManager);
		gestor.annadirPedido(pedido);
		assertEquals(pedidos,gestor.getGestorEstadoIntervalo(null, fechaCreac, LocalDate.of(2021, 8, 21)));
		EasyMock.verify(iDatabaseManager);
	}
	
	@Test
	@Tag("Insolation")
	void testGetGestorEstadoIntervaloDosFechasExtremoSup() { 
		// pedido = fechaCreac = LocalDate.of(2021, 7, 21)
		EasyMock.expect(iDatabaseManager.get(pedido.getId())).andReturn(null).times(1);
		iDatabaseManager.add(EasyMock.anyObject());
		EasyMock.expect(iDatabaseManager.getByStateAndDates(null, LocalDate.of(2021, 6, 21), fechaCreac)).andReturn(pedidos).times(1);
		EasyMock.replay(iDatabaseManager);
		gestor.setIDatabaseManager(iDatabaseManager);
		gestor.annadirPedido(pedido);
		assertEquals(pedidos,gestor.getGestorEstadoIntervalo(null, LocalDate.of(2021, 6, 21), fechaCreac));
		EasyMock.verify(iDatabaseManager);
	}
	
	@Test
	@Tag("Insolation")
	void testGetGestorEstadoIntervaloDosFechasFueraIntervalo() { 
		// pedido = fechaCreac = LocalDate.of(2021, 7, 21)
		EasyMock.expect(iDatabaseManager.get(pedido.getId())).andReturn(null).times(1);
		iDatabaseManager.add(EasyMock.anyObject());
		EasyMock.expect(iDatabaseManager.getByStateAndDates(null, LocalDate.of(2021, 5, 21), LocalDate.of(2021, 6, 21))).andReturn(new ArrayList<Order>()).times(1);
		EasyMock.replay(iDatabaseManager);
		gestor.setIDatabaseManager(iDatabaseManager);
		gestor.annadirPedido(pedido);
		assertEquals(new ArrayList<Order>(),gestor.getGestorEstadoIntervalo(null, LocalDate.of(2021, 5, 21), LocalDate.of(2021, 6, 21)));
		EasyMock.verify(iDatabaseManager);
	}
	
	@Test
	@Tag("Insolation")
	void testGetGestorEstadoIntervaloEstado1FechaRecibido() {
		EasyMock.expect(iDatabaseManager.get(pedido.getId())).andReturn(null).times(1);
		iDatabaseManager.add(EasyMock.anyObject());
		EasyMock.expect(iDatabaseManager.getByStateAndDates(OrderState.Recibido, fechaCreac, fechaCreac)).andReturn(pedidos).times(1);
		EasyMock.replay(iDatabaseManager);
		gestor.setIDatabaseManager(iDatabaseManager);
		gestor.annadirPedido(pedido);
		assertEquals(pedidos,gestor.getGestorEstadoIntervalo(OrderState.Recibido, fechaCreac, fechaCreac));
		EasyMock.verify(iDatabaseManager);
	}
	
	@Test
	@Tag("Insolation")
	void testGetGestorEstadoIntervaloEstadoRecibido1FechaNoEncontrado() {
		EasyMock.expect(iDatabaseManager.get(pedido.getId())).andReturn(null).times(1);
		iDatabaseManager.add(EasyMock.anyObject());
		EasyMock.expect(iDatabaseManager.getByStateAndDates(OrderState.Recibido, fechaCreac, fechaCreac)).andReturn(new ArrayList<Order>()).times(1);
		EasyMock.replay(iDatabaseManager);
		gestor.setIDatabaseManager(iDatabaseManager);
		pedido.pagar(fechaCreac);
		gestor.annadirPedido(pedido);
		assertEquals(new ArrayList<Order>(),gestor.getGestorEstadoIntervalo(OrderState.Recibido, fechaCreac, fechaCreac));
		EasyMock.verify(iDatabaseManager);
	}
	
	@Test
	@Tag("Insolation")
	void testGetGestorEstadoIntervaloEstadoRecibido1FechaFuera() {
		EasyMock.expect(iDatabaseManager.get(pedido.getId())).andReturn(null).times(1);
		iDatabaseManager.add(EasyMock.anyObject());
		EasyMock.expect(iDatabaseManager.getByStateAndDates(OrderState.Recibido, LocalDate.of(2021, 8, 21), LocalDate.of(2021, 8, 21))).andReturn(new ArrayList<Order>()).times(1);
		EasyMock.replay(iDatabaseManager);
		gestor.setIDatabaseManager(iDatabaseManager);
		gestor.annadirPedido(pedido);
		assertEquals(new ArrayList<Order>(),gestor.getGestorEstadoIntervalo(OrderState.Recibido, LocalDate.of(2021, 8, 21), LocalDate.of(2021, 8, 21)));
		EasyMock.verify(iDatabaseManager);
	}
	
	@Test
	@Tag("Insolation")
	void testGetGestorEstadoIntervaloEstadoPagado1Fecha() {
		pedido.pagar(fechaCreac);
		ArrayList<Order> pedidosPagados = new ArrayList<Order>();
		pedidosPagados.add(pedido); 
		EasyMock.expect(iDatabaseManager.get(pedido.getId())).andReturn(null).times(1);
		iDatabaseManager.add(EasyMock.anyObject());
		EasyMock.expect(iDatabaseManager.getByStateAndDates(OrderState.Pagado, fechaCreac, fechaCreac)).andReturn(pedidosPagados).times(1);
		EasyMock.replay(iDatabaseManager);
		gestor.setIDatabaseManager(iDatabaseManager);
		gestor.annadirPedido(pedido);
		assertEquals(pedidosPagados,gestor.getGestorEstadoIntervalo(OrderState.Pagado, fechaCreac, fechaCreac));
		EasyMock.verify(iDatabaseManager);
	}
	
	@Test
	@Tag("Insolation")
	void testGetGestorEstadoIntervaloEstadoPagado1FechaNoEncontrado() {
		pedido.pagar(fechaCreac);
		pedido.enviar(fechaCreac, direccion);
		EasyMock.expect(iDatabaseManager.get(pedido.getId())).andReturn(null).times(1);
		iDatabaseManager.add(EasyMock.anyObject());
		EasyMock.expect(iDatabaseManager.getByStateAndDates(OrderState.Pagado, fechaCreac, fechaCreac)).andReturn(new ArrayList<Order>()).times(1);
		EasyMock.replay(iDatabaseManager);
		gestor.setIDatabaseManager(iDatabaseManager);
		gestor.annadirPedido(pedido);
		assertEquals(new ArrayList<Order>(),gestor.getGestorEstadoIntervalo(OrderState.Pagado, fechaCreac, fechaCreac));
		EasyMock.verify(iDatabaseManager);
	}
	
	@Test
	@Tag("Insolation")
	void testGetGestorEstadoIntervaloEstadoPagado1FechaFuera() {
		EasyMock.expect(iDatabaseManager.get(pedido.getId())).andReturn(null).times(1);
		iDatabaseManager.add(EasyMock.anyObject());
		EasyMock.expect(iDatabaseManager.getByStateAndDates(OrderState.Pagado, LocalDate.of(2021, 8, 21), LocalDate.of(2021, 8, 21))).andReturn(new ArrayList<Order>()).times(1);
		EasyMock.replay(iDatabaseManager);
		gestor.setIDatabaseManager(iDatabaseManager);
		gestor.annadirPedido(pedido);
		assertEquals(new ArrayList<Order>(),gestor.getGestorEstadoIntervalo(OrderState.Pagado, LocalDate.of(2021, 8, 21), LocalDate.of(2021, 8, 21)));
		EasyMock.verify(iDatabaseManager);
	}
	
	@Test
	@Tag("Insolation")
	void testGetGestorEstadoIntervaloEstadoCancelado1Fecha() {
		pedido.cancelar(fechaCreac);
		ArrayList<Order> pedidosCancelados = new ArrayList<Order>();
		pedidosCancelados.add(pedido); 
		EasyMock.expect(iDatabaseManager.get(pedido.getId())).andReturn(null).times(1);
		iDatabaseManager.add(EasyMock.anyObject());
		EasyMock.expect(iDatabaseManager.getByStateAndDates(OrderState.Cancelado, fechaCreac, fechaCreac)).andReturn(pedidosCancelados).times(1);
		EasyMock.replay(iDatabaseManager);
		gestor.setIDatabaseManager(iDatabaseManager);
		gestor.annadirPedido(pedido);
		assertEquals(pedidosCancelados,gestor.getGestorEstadoIntervalo(OrderState.Cancelado, fechaCreac, fechaCreac));
		EasyMock.verify(iDatabaseManager);
	}
	
	@Test
	@Tag("Insolation")
	void testGetGestorEstadoIntervaloEstadoCancelado1FechaNoEncontrado() {
		pedido.pagar(fechaCreac);
		EasyMock.expect(iDatabaseManager.get(pedido.getId())).andReturn(null).times(1);
		iDatabaseManager.add(EasyMock.anyObject());
		EasyMock.expect(iDatabaseManager.getByStateAndDates(OrderState.Cancelado, fechaCreac, fechaCreac)).andReturn(new ArrayList<Order>()).times(1);
		EasyMock.replay(iDatabaseManager);
		gestor.setIDatabaseManager(iDatabaseManager);
		gestor.annadirPedido(pedido);
		assertEquals(new ArrayList<Order>(),gestor.getGestorEstadoIntervalo(OrderState.Cancelado, fechaCreac, fechaCreac));
		EasyMock.verify(iDatabaseManager);
	}
	
	@Test
	@Tag("Insolation")
	void testGetGestorEstadoIntervaloEstadoCancelado1FechaFuera() {
		pedido.cancelar(fechaCreac);
		EasyMock.expect(iDatabaseManager.get(pedido.getId())).andReturn(null).times(1);
		iDatabaseManager.add(EasyMock.anyObject());
		EasyMock.expect(iDatabaseManager.getByStateAndDates(OrderState.Cancelado, LocalDate.of(2021, 8, 21), LocalDate.of(2021, 8, 21))).andReturn(new ArrayList<Order>()).times(1);
		EasyMock.replay(iDatabaseManager);
		gestor.setIDatabaseManager(iDatabaseManager);
		gestor.annadirPedido(pedido);
		assertEquals(new ArrayList<Order>(),gestor.getGestorEstadoIntervalo(OrderState.Cancelado, LocalDate.of(2021, 8, 21), LocalDate.of(2021, 8, 21)));
		EasyMock.verify(iDatabaseManager);
	}
	
	@Test
	@Tag("Insolation")
	void testGetGestorEstadoIntervaloEstadoEnviado1Fecha() {
		pedido.pagar(fechaCreac);
		pedido.enviar(fechaCreac, direccion);
		ArrayList<Order> pedidosEnviados = new ArrayList<Order>();
		pedidosEnviados.add(pedido); 
		EasyMock.expect(iDatabaseManager.get(pedido.getId())).andReturn(null).times(1);
		iDatabaseManager.add(EasyMock.anyObject());
		EasyMock.expect(iDatabaseManager.getByStateAndDates(OrderState.Enviado, fechaCreac, fechaCreac)).andReturn(pedidosEnviados).times(1);
		EasyMock.replay(iDatabaseManager);
		gestor.setIDatabaseManager(iDatabaseManager);
		gestor.annadirPedido(pedido);
		assertEquals(pedidosEnviados,gestor.getGestorEstadoIntervalo(OrderState.Enviado, fechaCreac, fechaCreac));
		EasyMock.verify(iDatabaseManager);
	}
	
	@Test
	@Tag("Insolation")
	void testGetGestorEstadoIntervaloEstadoEnviado1FechaNoEncontrado() {
		pedido.pagar(fechaCreac);
		EasyMock.expect(iDatabaseManager.get(pedido.getId())).andReturn(null).times(1);
		iDatabaseManager.add(EasyMock.anyObject());
		EasyMock.expect(iDatabaseManager.getByStateAndDates(OrderState.Enviado, fechaCreac, fechaCreac)).andReturn(new ArrayList<Order>()).times(1);
		EasyMock.replay(iDatabaseManager);
		gestor.setIDatabaseManager(iDatabaseManager);
		gestor.annadirPedido(pedido);
		assertEquals(new ArrayList<Order>(),gestor.getGestorEstadoIntervalo(OrderState.Enviado, fechaCreac, fechaCreac));
		EasyMock.verify(iDatabaseManager);
	}
	
	@Test
	@Tag("Insolation")
	void testGetGestorEstadoIntervaloEstadoEnviado1FechaFuera() {
		pedido.pagar(fechaCreac);
		pedido.enviar(fechaCreac, direccion);
		EasyMock.expect(iDatabaseManager.get(pedido.getId())).andReturn(null).times(1);
		iDatabaseManager.add(EasyMock.anyObject());
		EasyMock.expect(iDatabaseManager.getByStateAndDates(OrderState.Enviado, LocalDate.of(2021, 8, 21), LocalDate.of(2021, 8, 21))).andReturn(new ArrayList<Order>()).times(1);
		EasyMock.replay(iDatabaseManager);
		gestor.setIDatabaseManager(iDatabaseManager);
		gestor.annadirPedido(pedido);
		assertEquals(new ArrayList<Order>(),gestor.getGestorEstadoIntervalo(OrderState.Enviado, LocalDate.of(2021, 8, 21), LocalDate.of(2021, 8, 21)));
		EasyMock.verify(iDatabaseManager);
	}
	
	@Test
	@Tag("Insolation")
	void testGetGestorEstadoIntervaloEstadoEntregado1Fecha() {
		pedido.pagar(fechaCreac);
		pedido.enviar(fechaCreac, direccion);
		pedido.entregar(fechaCreac);
		ArrayList<Order> pedidosEntregados = new ArrayList<Order>();
		pedidosEntregados.add(pedido); 
		EasyMock.expect(iDatabaseManager.get(pedido.getId())).andReturn(null).times(1);
		iDatabaseManager.add(EasyMock.anyObject());
		EasyMock.expect(iDatabaseManager.getByStateAndDates(OrderState.Entregado, fechaCreac, fechaCreac)).andReturn(pedidosEntregados).times(1);
		EasyMock.replay(iDatabaseManager);
		gestor.setIDatabaseManager(iDatabaseManager);
		gestor.annadirPedido(pedido);
		assertEquals(pedidosEntregados,gestor.getGestorEstadoIntervalo(OrderState.Entregado, fechaCreac, fechaCreac));
		EasyMock.verify(iDatabaseManager);
	}
	
	@Test
	@Tag("Insolation")
	void testGetGestorEstadoIntervaloEstadoEntregado1FechaNoEncontrado() {
		pedido.pagar(fechaCreac);
		EasyMock.expect(iDatabaseManager.get(pedido.getId())).andReturn(null).times(1);
		iDatabaseManager.add(EasyMock.anyObject());
		EasyMock.expect(iDatabaseManager.getByStateAndDates(OrderState.Entregado, fechaCreac, fechaCreac)).andReturn(new ArrayList<Order>()).times(1);
		EasyMock.replay(iDatabaseManager);
		gestor.setIDatabaseManager(iDatabaseManager);
		gestor.annadirPedido(pedido);
		assertEquals(new ArrayList<Order>(),gestor.getGestorEstadoIntervalo(OrderState.Entregado, fechaCreac, fechaCreac));
		EasyMock.verify(iDatabaseManager);
	}
	
	@Test
	@Tag("Insolation")
	void testGetGestorEstadoIntervaloEstadoEntregado1FechaFuera() {
		pedido.pagar(fechaCreac);
		pedido.enviar(fechaCreac, direccion);
		pedido.entregar(fechaCreac);
		EasyMock.expect(iDatabaseManager.get(pedido.getId())).andReturn(null).times(1);
		iDatabaseManager.add(EasyMock.anyObject());
		EasyMock.expect(iDatabaseManager.getByStateAndDates(OrderState.Entregado, LocalDate.of(2021, 8, 21), LocalDate.of(2021, 8, 21))).andReturn(new ArrayList<Order>()).times(1);
		EasyMock.replay(iDatabaseManager);
		gestor.setIDatabaseManager(iDatabaseManager);
		gestor.annadirPedido(pedido);
		assertEquals(new ArrayList<Order>(),gestor.getGestorEstadoIntervalo(OrderState.Entregado, LocalDate.of(2021, 8, 21), LocalDate.of(2021, 8, 21)));
		EasyMock.verify(iDatabaseManager);
	}
	
	@Test
	@Tag("Insolation")
	void testGetGestorEstadoIntervaloEstadoRecibido2FechaInf() {
		// pedido = fechaCreac = LocalDate.of(2021, 7, 21)
		EasyMock.expect(iDatabaseManager.get(pedido.getId())).andReturn(null).times(1);
		iDatabaseManager.add(EasyMock.anyObject());
		EasyMock.expect(iDatabaseManager.getByStateAndDates(OrderState.Recibido, fechaCreac, LocalDate.of(2021, 8, 21))).andReturn(pedidos).times(1);
		EasyMock.replay(iDatabaseManager);
		gestor.setIDatabaseManager(iDatabaseManager);
		gestor.annadirPedido(pedido);
		assertEquals(pedidos,gestor.getGestorEstadoIntervalo(OrderState.Recibido, fechaCreac, LocalDate.of(2021, 8, 21)));
		EasyMock.verify(iDatabaseManager);
	}
	
	
	@Test
	@Tag("Insolation")
	void testGetGestorEstadoIntervaloEstadoRecibido2FechaSup() {
		// pedido = fechaCreac = LocalDate.of(2021, 7, 21)
		EasyMock.expect(iDatabaseManager.get(pedido.getId())).andReturn(null).times(1);
		iDatabaseManager.add(EasyMock.anyObject());
		EasyMock.expect(iDatabaseManager.getByStateAndDates(OrderState.Recibido, LocalDate.of(2021, 6, 21), fechaCreac)).andReturn(pedidos).times(1);
		EasyMock.replay(iDatabaseManager);
		gestor.setIDatabaseManager(iDatabaseManager);
		gestor.annadirPedido(pedido);
		assertEquals(pedidos,gestor.getGestorEstadoIntervalo(OrderState.Recibido, LocalDate.of(2021, 6, 21), fechaCreac));
		EasyMock.verify(iDatabaseManager);
	}
	
	@Test
	@Tag("Insolation")
	void testGetGestorEstadoIntervaloEstadoRecibido2FechaNoEncontrado() {
		EasyMock.expect(iDatabaseManager.get(pedido.getId())).andReturn(null).times(1);
		iDatabaseManager.add(EasyMock.anyObject());
		EasyMock.expect(iDatabaseManager.getByStateAndDates(OrderState.Recibido, LocalDate.of(2021, 6, 21), LocalDate.of(2021, 8, 21))).andReturn(pedidos).times(1);
		EasyMock.replay(iDatabaseManager);
		gestor.setIDatabaseManager(iDatabaseManager);
		pedido.pagar(fechaCreac);
		gestor.annadirPedido(pedido);
		assertEquals(pedidos,gestor.getGestorEstadoIntervalo(OrderState.Recibido, LocalDate.of(2021, 6, 21), LocalDate.of(2021, 8, 21)));
		EasyMock.verify(iDatabaseManager);
	}
	
	@Test
	@Tag("Insolation")
	void testGetGestorEstadoIntervaloEstadoRecibido2FechaFuera() {
		EasyMock.expect(iDatabaseManager.get(pedido.getId())).andReturn(null).times(1);
		iDatabaseManager.add(EasyMock.anyObject());
		EasyMock.expect(iDatabaseManager.getByStateAndDates(OrderState.Recibido, LocalDate.of(2021, 8, 21), LocalDate.of(2021, 9, 21))).andReturn(new ArrayList<Order>()).times(1);
		EasyMock.replay(iDatabaseManager);
		gestor.setIDatabaseManager(iDatabaseManager);
		gestor.annadirPedido(pedido);
		assertEquals(new ArrayList<Order>(),gestor.getGestorEstadoIntervalo(OrderState.Recibido, LocalDate.of(2021, 8, 21), LocalDate.of(2021, 9, 21)));
		EasyMock.verify(iDatabaseManager);
	}
	
	@Test
	@Tag("Insolation")
	void testGetGestorEstadoIntervaloEstadoPagado2FechaInf() {
		pedido.pagar(fechaCreac);
		ArrayList<Order> pedidosPagados = new ArrayList<Order>();
		pedidosPagados.add(pedido); 
		EasyMock.expect(iDatabaseManager.get(pedido.getId())).andReturn(null).times(1);
		iDatabaseManager.add(EasyMock.anyObject());
		EasyMock.expect(iDatabaseManager.getByStateAndDates(OrderState.Pagado, fechaCreac, LocalDate.of(2021, 8, 21))).andReturn(pedidosPagados).times(1);
		EasyMock.replay(iDatabaseManager);
		gestor.setIDatabaseManager(iDatabaseManager);
		gestor.annadirPedido(pedido);
		assertEquals(pedidosPagados,gestor.getGestorEstadoIntervalo(OrderState.Pagado, fechaCreac, LocalDate.of(2021, 8, 21)));
		EasyMock.verify(iDatabaseManager);
	}
	
	@Test
	@Tag("Insolation")
	void testGetGestorEstadoIntervaloEstadoPagado2FechaSup() {
		pedido.pagar(fechaCreac);
		ArrayList<Order> pedidosPagados = new ArrayList<Order>();
		pedidosPagados.add(pedido); 
		EasyMock.expect(iDatabaseManager.get(pedido.getId())).andReturn(null).times(1);
		iDatabaseManager.add(EasyMock.anyObject());
		EasyMock.expect(iDatabaseManager.getByStateAndDates(OrderState.Pagado, LocalDate.of(2021, 6, 21), fechaCreac)).andReturn(pedidosPagados).times(1);
		EasyMock.replay(iDatabaseManager);
		gestor.setIDatabaseManager(iDatabaseManager);
		gestor.annadirPedido(pedido);
		assertEquals(pedidosPagados,gestor.getGestorEstadoIntervalo(OrderState.Pagado, LocalDate.of(2021, 6, 21), fechaCreac));
		EasyMock.verify(iDatabaseManager);
	}
	
	@Test
	@Tag("Insolation")
	void testGetGestorEstadoIntervaloEstadoPagado2FechaNoEncontrado() {
		pedido.pagar(fechaCreac);
		pedido.enviar(fechaCreac, direccion);
		EasyMock.expect(iDatabaseManager.get(pedido.getId())).andReturn(null).times(1);
		iDatabaseManager.add(EasyMock.anyObject());
		EasyMock.expect(iDatabaseManager.getByStateAndDates(OrderState.Pagado, LocalDate.of(2021, 6, 21), LocalDate.of(2021, 8, 21))).andReturn(new ArrayList<Order>()).times(1);
		EasyMock.replay(iDatabaseManager);
		gestor.setIDatabaseManager(iDatabaseManager);
		gestor.annadirPedido(pedido);
		assertEquals(new ArrayList<Order>(),gestor.getGestorEstadoIntervalo(OrderState.Pagado, LocalDate.of(2021, 6, 21), LocalDate.of(2021, 8, 21)));
		EasyMock.verify(iDatabaseManager);
	}
	
	@Test
	@Tag("Insolation")
	void testGetGestorEstadoIntervaloEstadoPagado2FechaFuera() {
		EasyMock.expect(iDatabaseManager.get(pedido.getId())).andReturn(null).times(1);
		iDatabaseManager.add(EasyMock.anyObject());
		EasyMock.expect(iDatabaseManager.getByStateAndDates(OrderState.Pagado, LocalDate.of(2021, 8, 21), LocalDate.of(2021, 9, 21))).andReturn(new ArrayList<Order>()).times(1);
		EasyMock.replay(iDatabaseManager);
		gestor.setIDatabaseManager(iDatabaseManager);
		pedido.pagar(fechaCreac);
		gestor.annadirPedido(pedido);
		assertEquals(new ArrayList<Order>(),gestor.getGestorEstadoIntervalo(OrderState.Pagado, LocalDate.of(2021, 8, 21), LocalDate.of(2021, 9, 21)));
		EasyMock.verify(iDatabaseManager);
	}
	
	@Test
	@Tag("Insolation")
	void testGetGestorEstadoIntervaloEstadoCancelado2FechaInf() {
		pedido.cancelar(fechaCreac);
		ArrayList<Order> pedidosCancelados = new ArrayList<Order>();
		pedidosCancelados.add(pedido); 
		EasyMock.expect(iDatabaseManager.get(pedido.getId())).andReturn(null).times(1);
		iDatabaseManager.add(EasyMock.anyObject());
		EasyMock.expect(iDatabaseManager.getByStateAndDates(OrderState.Cancelado, fechaCreac, LocalDate.of(2021, 8, 21))).andReturn(pedidosCancelados).times(1);
		EasyMock.replay(iDatabaseManager);
		gestor.setIDatabaseManager(iDatabaseManager);
		gestor.annadirPedido(pedido);
		assertEquals(pedidosCancelados,gestor.getGestorEstadoIntervalo(OrderState.Cancelado, fechaCreac, LocalDate.of(2021, 8, 21)));
		EasyMock.verify(iDatabaseManager);
	}
	
	@Test
	@Tag("Insolation")
	void testGetGestorEstadoIntervaloEstadoCancelado2FechaSup() {
		pedido.cancelar(fechaCreac);
		ArrayList<Order> pedidosCancelados = new ArrayList<Order>();
		pedidosCancelados.add(pedido); 
		EasyMock.expect(iDatabaseManager.get(pedido.getId())).andReturn(null).times(1);
		iDatabaseManager.add(EasyMock.anyObject());
		EasyMock.expect(iDatabaseManager.getByStateAndDates(OrderState.Cancelado, LocalDate.of(2021, 6, 21), fechaCreac)).andReturn(pedidosCancelados).times(1);
		EasyMock.replay(iDatabaseManager);
		gestor.setIDatabaseManager(iDatabaseManager);
		gestor.annadirPedido(pedido);
		assertEquals(pedidosCancelados,gestor.getGestorEstadoIntervalo(OrderState.Cancelado, LocalDate.of(2021, 6, 21), fechaCreac));
		EasyMock.verify(iDatabaseManager);
	}
	
	@Test
	@Tag("Insolation")
	void testGetGestorEstadoIntervaloEstadoCancelado2FechaNoEncontrado() {
		EasyMock.expect(iDatabaseManager.get(pedido.getId())).andReturn(null).times(1);
		iDatabaseManager.add(EasyMock.anyObject());
		EasyMock.expect(iDatabaseManager.getByStateAndDates(OrderState.Cancelado, LocalDate.of(2021, 6, 21), LocalDate.of(2021, 8, 21))).andReturn(new ArrayList<Order>()).times(1);
		EasyMock.replay(iDatabaseManager);
		gestor.setIDatabaseManager(iDatabaseManager);
		gestor.annadirPedido(pedido);
		assertEquals(new ArrayList<Order>(),gestor.getGestorEstadoIntervalo(OrderState.Cancelado, LocalDate.of(2021, 6, 21), LocalDate.of(2021, 8, 21)));
		EasyMock.verify(iDatabaseManager);
	}
	
	@Test
	@Tag("Insolation")
	void testGetGestorEstadoIntervaloEstadoCancelado2FechaFuera() {
		EasyMock.expect(iDatabaseManager.get(pedido.getId())).andReturn(null).times(1);
		iDatabaseManager.add(EasyMock.anyObject());
		EasyMock.expect(iDatabaseManager.getByStateAndDates(OrderState.Cancelado, LocalDate.of(2021, 8, 21), LocalDate.of(2021, 9, 21))).andReturn(new ArrayList<Order>()).times(1);
		EasyMock.replay(iDatabaseManager);
		gestor.setIDatabaseManager(iDatabaseManager);
		pedido.cancelar(fechaCreac);
		gestor.annadirPedido(pedido);
		assertEquals(new ArrayList<Order>(),gestor.getGestorEstadoIntervalo(OrderState.Cancelado, LocalDate.of(2021, 8, 21), LocalDate.of(2021, 9, 21)));
		EasyMock.verify(iDatabaseManager);
	}
	
	@Test
	@Tag("Insolation")
	void testGetGestorEstadoIntervaloEstadoEnviado2FechaInf() {
		pedido.pagar(fechaCreac);
		pedido.enviar(fechaCreac, direccion);
		ArrayList<Order> pedidosEnviados = new ArrayList<Order>();
		pedidosEnviados.add(pedido); 
		EasyMock.expect(iDatabaseManager.get(pedido.getId())).andReturn(null).times(1);
		iDatabaseManager.add(EasyMock.anyObject());
		EasyMock.expect(iDatabaseManager.getByStateAndDates(OrderState.Enviado, fechaCreac, LocalDate.of(2021, 8, 21))).andReturn(pedidosEnviados).times(1);
		EasyMock.replay(iDatabaseManager);
		gestor.setIDatabaseManager(iDatabaseManager);
		gestor.annadirPedido(pedido);
		assertEquals(pedidosEnviados,gestor.getGestorEstadoIntervalo(OrderState.Enviado, fechaCreac, LocalDate.of(2021, 8, 21)));
		EasyMock.verify(iDatabaseManager);
	}
	
	@Test
	@Tag("Insolation")
	void testGetGestorEstadoIntervaloEstadoEnviado2FechaSup() {
		pedido.pagar(fechaCreac);
		pedido.enviar(fechaCreac, direccion);
		ArrayList<Order> pedidosEnviados = new ArrayList<Order>();
		pedidosEnviados.add(pedido); 
		EasyMock.expect(iDatabaseManager.get(pedido.getId())).andReturn(null).times(1);
		iDatabaseManager.add(EasyMock.anyObject());
		EasyMock.expect(iDatabaseManager.getByStateAndDates(OrderState.Enviado, LocalDate.of(2021, 6, 21), fechaCreac)).andReturn(pedidosEnviados).times(1);
		EasyMock.replay(iDatabaseManager);
		gestor.setIDatabaseManager(iDatabaseManager);
		gestor.annadirPedido(pedido);
		assertEquals(pedidosEnviados,gestor.getGestorEstadoIntervalo(OrderState.Enviado, LocalDate.of(2021, 6, 21), fechaCreac));
		EasyMock.verify(iDatabaseManager);
	}
	
	@Test
	@Tag("Insolation")
	void testGetGestorEstadoIntervaloEstadoEnviado2FechaNoEncontrado() {
		EasyMock.expect(iDatabaseManager.get(pedido.getId())).andReturn(null).times(1);
		iDatabaseManager.add(EasyMock.anyObject());
		EasyMock.expect(iDatabaseManager.getByStateAndDates(OrderState.Enviado, LocalDate.of(2021, 6, 21), LocalDate.of(2021, 8, 21))).andReturn(new ArrayList<Order>()).times(1);
		EasyMock.replay(iDatabaseManager);
		gestor.setIDatabaseManager(iDatabaseManager);
		gestor.annadirPedido(pedido);
		assertEquals(new ArrayList<Order>(),gestor.getGestorEstadoIntervalo(OrderState.Enviado, LocalDate.of(2021, 6, 21), LocalDate.of(2021, 8, 21)));
		EasyMock.verify(iDatabaseManager);
	}
	
	@Test
	@Tag("Insolation")
	void testGetGestorEstadoIntervaloEstadoEnviado2FechaFuera() {
		EasyMock.expect(iDatabaseManager.get(pedido.getId())).andReturn(null).times(1);
		iDatabaseManager.add(EasyMock.anyObject());
		EasyMock.expect(iDatabaseManager.getByStateAndDates(OrderState.Enviado, LocalDate.of(2021, 8, 21), LocalDate.of(2021, 9, 21))).andReturn(new ArrayList<Order>()).times(1);
		EasyMock.replay(iDatabaseManager);
		gestor.setIDatabaseManager(iDatabaseManager);
		pedido.pagar(fechaCreac);
		pedido.enviar(fechaCreac, direccion);
		gestor.annadirPedido(pedido);
		assertEquals(new ArrayList<Order>(),gestor.getGestorEstadoIntervalo(OrderState.Enviado, LocalDate.of(2021, 8, 21), LocalDate.of(2021, 9, 21)));
		EasyMock.verify(iDatabaseManager);
	}
	
	@Test
	@Tag("Insolation")
	void testGetGestorEstadoIntervaloEstadoEntregado2FechaInf() {
		pedido.pagar(fechaCreac);
		pedido.enviar(fechaCreac, direccion);
		pedido.entregar(fechaCreac);
		ArrayList<Order> pedidosEntregados = new ArrayList<Order>();
		pedidosEntregados.add(pedido); 
		EasyMock.expect(iDatabaseManager.get(pedido.getId())).andReturn(null).times(1);
		iDatabaseManager.add(EasyMock.anyObject());
		EasyMock.expect(iDatabaseManager.getByStateAndDates(OrderState.Entregado, fechaCreac, LocalDate.of(2021, 8, 21))).andReturn(pedidosEntregados).times(1);
		EasyMock.replay(iDatabaseManager);
		gestor.setIDatabaseManager(iDatabaseManager);
		gestor.annadirPedido(pedido);
		assertEquals(pedidosEntregados,gestor.getGestorEstadoIntervalo(OrderState.Entregado, fechaCreac, LocalDate.of(2021, 8, 21)));
		EasyMock.verify(iDatabaseManager);
	}
	
	@Test
	@Tag("Insolation")
	void testGetGestorEstadoIntervaloEstadoEntregado2FechaSup() {
		pedido.pagar(fechaCreac);
		pedido.enviar(fechaCreac, direccion);
		pedido.entregar(fechaCreac);
		ArrayList<Order> pedidosEntregados = new ArrayList<Order>();
		pedidosEntregados.add(pedido); 
		EasyMock.expect(iDatabaseManager.get(pedido.getId())).andReturn(null).times(1);
		iDatabaseManager.add(EasyMock.anyObject());
		EasyMock.expect(iDatabaseManager.getByStateAndDates(OrderState.Entregado, LocalDate.of(2021, 6, 21), fechaCreac)).andReturn(pedidosEntregados).times(1);
		EasyMock.replay(iDatabaseManager);
		gestor.setIDatabaseManager(iDatabaseManager);
		gestor.annadirPedido(pedido);
		assertEquals(pedidosEntregados,gestor.getGestorEstadoIntervalo(OrderState.Entregado, LocalDate.of(2021, 6, 21), fechaCreac));
		EasyMock.verify(iDatabaseManager);
	}
	
	@Test
	@Tag("Insolation")
	void testGetGestorEstadoIntervaloEstadoEntregado2FechaNoEncontrado() {
		EasyMock.expect(iDatabaseManager.get(pedido.getId())).andReturn(null).times(1);
		iDatabaseManager.add(EasyMock.anyObject());
		EasyMock.expect(iDatabaseManager.getByStateAndDates(OrderState.Entregado, LocalDate.of(2021, 6, 21), LocalDate.of(2021, 8, 21))).andReturn(new ArrayList<Order>()).times(1);
		EasyMock.replay(iDatabaseManager);
		gestor.setIDatabaseManager(iDatabaseManager);
		gestor.annadirPedido(pedido);
		assertEquals(new ArrayList<Order>(),gestor.getGestorEstadoIntervalo(OrderState.Entregado, LocalDate.of(2021, 6, 21), LocalDate.of(2021, 8, 21)));
		EasyMock.verify(iDatabaseManager);
	}
	
	@Test
	@Tag("Insolation")
	void testGetGestorEstadoIntervaloEstadoEntregado2FechaFuera() {
		EasyMock.expect(iDatabaseManager.get(pedido.getId())).andReturn(null).times(1);
		iDatabaseManager.add(EasyMock.anyObject());
		EasyMock.expect(iDatabaseManager.getByStateAndDates(OrderState.Entregado, LocalDate.of(2021, 8, 21), LocalDate.of(2021, 9, 21))).andReturn(new ArrayList<Order>()).times(1);
		EasyMock.replay(iDatabaseManager);
		gestor.setIDatabaseManager(iDatabaseManager);
		pedido.pagar(fechaCreac);
		pedido.enviar(fechaCreac, direccion);
		pedido.entregar(fechaCreac);
		gestor.annadirPedido(pedido);
		assertEquals(new ArrayList<Order>(),gestor.getGestorEstadoIntervalo(OrderState.Entregado, LocalDate.of(2021, 8, 21), LocalDate.of(2021, 9, 21)));
		EasyMock.verify(iDatabaseManager);
	}
	
}
