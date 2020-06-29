package com.poc.numbergenerator;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poc.numbergenerator.controller.NumberGeneratorController;
import com.poc.numbergenerator.service.NumberGeneratorService;

@WebMvcTest(NumberGeneratorController.class)
public class NumberGeneratorControllerTests {
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private NumberGeneratorService service;
	
	@Test
	public void testGenerateNumber() throws Exception {
		when(service.submitTaskAsync(2, 10)).thenReturn("test-uuid");
		Map<String, Integer> input = new HashMap<String, Integer>();
		input.put("Goal", 10);
		input.put("Step", 2);
		this.mockMvc.perform(post("/api/generate").contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(input))).andDo(print()).andExpect(status().isAccepted())
				.andExpect(jsonPath("$.task", is("test-uuid")));
	}
	
	@Test
	public void testGetTaskStatus() throws Exception {
		when(service.getTaskStatus("test-uuid")).thenReturn("INPROCESS");
		this.mockMvc.perform(get("/api/tasks/test-uuid/status")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.result", is("INPROCESS")));
	}
	
	@Test
	public void testGetTaskDetails() throws Exception {
		when(service.getNumberList("test-uuid")).thenReturn("5,4,3,2,1,0");
		this.mockMvc.perform(get("/api/tasks/test-uuid?action=getnumlist")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.result", is("5,4,3,2,1,0")));
	}
	
	public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
	

}

