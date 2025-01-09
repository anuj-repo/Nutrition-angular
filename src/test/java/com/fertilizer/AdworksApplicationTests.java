package com.fertilizer;

import static org.junit.Assert.assertNotEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("junit")

class FertilizerApplicationTests {

	@LocalServerPort
    int randomServerPort;

	@Test
	public void contextLoads() {
		assertNotEquals(0, randomServerPort);
	}
}
