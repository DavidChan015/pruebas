package com.darkcode.spring.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class DemoApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void holaMundoRespondeTextoEsperado() {
		HelloController controller = new HelloController();
		assertEquals("Hola Mundo 2", controller.holaMundo());
	}

}
