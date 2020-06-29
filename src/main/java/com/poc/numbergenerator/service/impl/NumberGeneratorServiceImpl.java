package com.poc.numbergenerator.service.impl;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Hashtable;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.poc.numbergenerator.service.NumberGeneratorService;

@Service
public class NumberGeneratorServiceImpl implements NumberGeneratorService {

	enum TaskStatus {
		COMPLETED("COMPLETED"), INPROCSS("INPROCSS"), ERROR("ERROR");

		private String status;

		public String getStatus() {
			return this.status;
		}

		private TaskStatus(String status) {
			this.status = status;
		}
	}

	// hashtable is used to simulate a persistence layer
	static Hashtable<String, TaskStatus> taskStatus = new Hashtable<>();

	public String submitTaskAsync(int step, int goal) {
		String taskId = UUID.randomUUID().toString();
		Runnable runnable = () -> {
			taskStatus.put(taskId, TaskStatus.INPROCSS);
			FileWriter writer;
			try {
				writer = new FileWriter(prepareTargetFileName(taskId));
				for (int i = goal; i >= 0; i--) {
					writer.write(String.valueOf(i) + System.lineSeparator());
				}
				writer.close();
				taskStatus.put(taskId, TaskStatus.COMPLETED);
			} catch (IOException e) {
				e.printStackTrace();
				taskStatus.put(taskId, TaskStatus.ERROR);
			}

		};
		Thread thread = new Thread(runnable);
		thread.start();
		return taskId;
	}

	@Override
	public String getTaskStatus(String taskId) {
		TaskStatus status = taskStatus.get(taskId);
		if (null != status) {
			return status.getStatus();
		}
		return null;
	}

	@Override
	public String getNumberList(String taskId) {
		TaskStatus status = taskStatus.get(taskId);
		if (null != status) {
			if (status == TaskStatus.COMPLETED) {
				try (Stream<String> stream = Files.lines(Paths.get(prepareTargetFileName(taskId)))) {
					return stream.collect(Collectors.joining(","));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return status.getStatus();
		}
		return null;

	}
	
	static String prepareTargetFileName(String taskId) {
		return "/tmp/"+taskId+"_output.txt";
	}

}
