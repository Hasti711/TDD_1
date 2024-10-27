package com.example.demo;

import com.example.demo.service.StringCalculatorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class DemoApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private StringCalculatorService service;

	@Test
	void testAdd_EmptyString() throws Exception {
		assertEquals(0, service.add(""));
	}

	@Test
	void testAdd_OneNumber() throws Exception {
		assertEquals(1, service.add("1"));
	}

	@Test
	void testAdd_TwoNumbers() throws Exception {
		assertEquals(3, service.add("1,2"));
	}

	@Test
	void testAdd_NewLineDelimiter() throws Exception {
		assertEquals(6, service.add("1\n2,3"));
	}

	@Test
	void testAdd_CustomDelimiter() throws Exception {
		assertEquals(3, service.add("//;\n1;2"));
	}

	@Test
	void testAdd_NegativeNumbers() {
		Exception exception = assertThrows(Exception.class, () -> {
			service.add("1,-2,3,-4");
		});

		assertEquals("Negatives not allowed: [-2, -4]", exception.getMessage());
	}

	@Test
	void testAdd_NumbersGreaterThan1000() throws Exception {
		assertEquals(2, service.add("2,1001"));
	}

	@Test
	void testAdd_MultipleDelimiters() throws Exception {
		assertEquals(6, service.add("//[*][%]\n1*2%3"));
	}

	@Test
	void testGetCallCount() throws Exception {
		service.add("1,2");
		service.add("3,4");
		assertEquals(6, service.getCallCount());
	}

}
