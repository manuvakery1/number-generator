package com.poc.numbergenerator.service;

public interface NumberGeneratorService {
	
	public String submitTaskAsync(int step, int goal);
	
	public String getTaskStatus(String tasId);
	
	public String getNumberList(String tasId);

}
