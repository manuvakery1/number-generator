package com.poc.numbergenerator.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.poc.numbergenerator.service.NumberGeneratorService;

@RestController
public class NumberGeneratorController {
	
	@Autowired
	NumberGeneratorService numberGeneratorService;
	
	@ResponseStatus(HttpStatus.ACCEPTED )
	@PostMapping("/api/generate")
	public HashMap<String, String> generateNumber(@RequestBody Map<String, Integer> input) {
		
		String taskId = numberGeneratorService.submitTaskAsync(input.get("Step"), input.get("Goal"));
		return new HashMap<String, String>() {
		    {
		        put("task", taskId);
		    }
		};

	}
	
	@ResponseStatus(HttpStatus.OK )
	@GetMapping("/api/tasks/{task_id}/status")
	public HashMap<String, String> getTaskStatus(@PathVariable String task_id){
		return new HashMap<String, String>() {
		    {
		        put("result", numberGeneratorService.getTaskStatus(task_id));
		    }
		};
	}
	
	@ResponseStatus(HttpStatus.OK )
	@GetMapping("/api/tasks/{task_id}")
	public HashMap<String, String> getTaskDetails(@PathVariable String task_id, @RequestParam(value = "action") String action){
		return new HashMap<String, String>() {
		    {
		        put("result", numberGeneratorService.getNumberList(task_id));
		    }
		};
	}
	

}
