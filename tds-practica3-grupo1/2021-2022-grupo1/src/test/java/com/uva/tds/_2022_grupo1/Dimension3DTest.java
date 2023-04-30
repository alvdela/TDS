package com.uva.tds._2022_grupo1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
/**
 * @author Dario Gago Perez
 * @author Alvaro de la Rosa Zarzuelo
 *
 */

class Dimension3DTest {
	private double alto;
	private double ancho;
	private double largo;
	private double ERROR;

	@BeforeEach
	void setUp(){
		ancho = 1.3;
		largo = 3.5;
		alto = 2.1;
		ERROR = 0.1;
	}

	@Test
	@Tag("TDD")
	void testCreacionLimiteInferior() {
		Dimension3D dim = new Dimension3D(ancho,largo,alto);
		assertEquals(ancho, dim.getAncho(),ERROR);
		assertEquals(largo, dim.getLargo(),ERROR);
		assertEquals(alto, dim.getAlto(),ERROR);
	}
	
	@Test
	@Tag("TDD")
	void testCreacionAnchoMenorLimiteInferior(){
		assertThrows(IllegalArgumentException.class,
				()->{new Dimension3D(0,largo,alto);});
	}
	
	@Test
	@Tag("TDD")
	void testCreacionLargoMenorLimiteInferior(){
		assertThrows(IllegalArgumentException.class,
				()->{new Dimension3D(ancho,0,alto);});
	}
	
	@Test
	@Tag("TDD")
	void testCreacionAltoMenorLimiteInferior(){
		assertThrows(IllegalArgumentException.class,
				()->{new Dimension3D(ancho,largo,0);});
	}
	

	@Test
	@Tag("TDD")
	void testCreacionAnchoNegativo(){
		assertThrows(IllegalArgumentException.class,
				()->{new Dimension3D(-1,largo,alto);});
	}
	
	@Test
	@Tag("TDD")
	void testCreacionLargoNegativo(){
		assertThrows(IllegalArgumentException.class,
				()->{new Dimension3D(ancho,-1,alto);});
	}
	
	@Test
	@Tag("TDD")
	void testCreacionAltoNegativo(){
		assertThrows(IllegalArgumentException.class,
				()->{new Dimension3D(ancho,largo,-1);});
	}
}
