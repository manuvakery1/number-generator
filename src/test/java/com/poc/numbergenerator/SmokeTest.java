package com.poc.numbergenerator;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.poc.numbergenerator.controller.NumberGeneratorController;

@SpringBootTest
public class SmokeTest {

	@Autowired
	private NumberGeneratorController controller;

	@Test
	public void contexLoads() throws Exception {
		assertThat(controller).isNotNull();
	}
}
