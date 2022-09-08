package com.demo.app;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = { "spring.config.location=classpath:test.yaml"})
public class SpringBootDemoApplicationTests {

	@Test
	public void contextLoads() {
	}

}
