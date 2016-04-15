package com.criteo.thespywholovedme.tokenizer;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import org.junit.Test;

import com.criteo.thespywholovedme.tokenizer.Processor;
import com.criteo.*;
public class TokenizerTest {

    @Test
	public void TestTokenizer() {

		Processor processor = new Processor();
		List<File> positive = new LinkedList<File>();
		List<File> negative = new LinkedList<File>();
		
		File positivResumesTxt = new File("src/test/resources/txts/positive");

		if (positivResumesTxt.isDirectory()) {
			for (File nextFile : positivResumesTxt.listFiles()) {
				positive.add(nextFile);
			}
		}

		File negativeResumesTxt = new File("src/test/resources/txts/negative");
		if (negativeResumesTxt.isDirectory()) {
			for (File nextFile : negativeResumesTxt.listFiles()) {
				negative.add(nextFile);
			}
		}

		processor.process(positive, negative);
		
		System.out.println("TestTokenizer done");
	}
}
