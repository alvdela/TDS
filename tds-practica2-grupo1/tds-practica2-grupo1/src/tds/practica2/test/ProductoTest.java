package tds.practica2.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import tds.practica2.Dimension3D;
import tds.practica2.Producto;
/**
 * @author Dario Gago Perez
 * @author Alvaro de la Rosa Zarzuelo
 *
 */

class ProductoTest {

	private String id;
	private String desc;
	private Dimension3D dim;
	private double ERROR;
	
	@BeforeEach
	void setUp(){
		id = "a34";
		desc = "Descripcion del producto";
		dim = new Dimension3D(1,1,1);
		ERROR = 0.1;
	}
	
	@Test
	@Tag("TDD")
	void testCreacion() {
		Producto producto = new Producto(id,desc,dim);
		assertEquals(producto.getId(), id);
		assertEquals(producto.getDescripcion(), desc);
		assertEquals(producto.getDimensiones().getAlto(), dim.getAlto(),ERROR);
		assertEquals(producto.getDimensiones().getAncho(), dim.getAncho(),ERROR);
		assertEquals(producto.getDimensiones().getLargo(), dim.getLargo(),ERROR);
	}
	
	@Test
	@Tag("TDD")
	void testCreacionIdLimiteSuperior() {
		Producto producto = new Producto("1234567890",desc,dim);
		assertEquals(producto.getId(), id);
	}
	
	@Test
	@Tag("TDD")
	void testCreacionIdDescripcionLimiteInferior() {
		Producto producto = new Producto("a","a",dim);
		assertEquals(producto.getId(), id);
		assertEquals(producto.getDescripcion(), desc);
	}
	
	@Test
	@Tag("TDD")
	void testCreacionIdMenorLimiteInferior(){
		assertThrows(IllegalArgumentException.class,
				()->{new Producto("",desc,dim);});
	}
	
	@Test
	@Tag("TDD")
	void testCreacionIdMayorLimiteSuperior(){
		assertThrows(IllegalArgumentException.class,
				()->{new Producto("12345678901",desc,dim);});
	}
	
	@Test
	@Tag("TDD")
	void testCreacionIdNull(){
		assertThrows(IllegalArgumentException.class,
				()->{new Producto(null,desc,dim);});
	}
	
	@Test
	@Tag("TDD")
	void testCreacionDescripcionVacia(){
		assertThrows(IllegalArgumentException.class,
				()->{new Producto(id,"",dim);});
	}
	
	@Test
	@Tag("TDD")
	void testCreacionDescripcionNull(){
		assertThrows(IllegalArgumentException.class,
				()->{new Producto(id,null,dim);});
	}
	
	@Test
	@Tag("TDD")
	void testCreacionDimensionesNull(){
		assertThrows(IllegalArgumentException.class,
				()->{new Producto(id,desc,null);});
	}
	
}
