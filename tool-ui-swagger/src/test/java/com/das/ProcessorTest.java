package com.das;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.das.processor.Processor;
import com.drishlent.dlite.junit.DliteTestRunner;

@RunWith(DliteTestRunner.class)
public class ProcessorTest {

	@Test
	public void testProcess() throws Exception { 
		Processor proc = new Processor(); 
		proc.processExm("thing.json");
	}
	
}
