package com.poc.numbergenerator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.io.FileWriter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.poc.numbergenerator.service.NumberGeneratorService;
import com.poc.numbergenerator.service.impl.NumberGeneratorServiceImpl;

@ExtendWith(MockitoExtension.class)
public class NumberGeneratorServiceTests {

	NumberGeneratorService service = new NumberGeneratorServiceImpl();
	
	@Test
	void testSerivice() {
		FileWriter mockFileWriter = mock(FileWriter.class);
		String result = service.submitTaskAsync(2, 10);
		assertThat(result).isNotNull();
		
		// test status
		String status = service.getTaskStatus(result);
		assertThat(status).isEqualTo("COMPLETED");
		
		// test status for invalid task id
		status = service.getTaskStatus("invalid");
		assertThat(status).isNull();
		
		// test status for invalid task id
		String numList = service.getNumberList("invalid");
		assertThat(numList).isNull();
		
		numList = service.getNumberList(result);
		assertThat(result).isNotNull();
		
	}
	
	
}
