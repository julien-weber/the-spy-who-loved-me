package com.criteo.thespywholovedme.tokenizer;

import java.io.*;
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
	public void TestTokenizer(String[] args) {
		int argCount = args.length;
		if (argCount != 2)
		{
			System.out.println("Please provide locations of two directories: \n"
					+ "1. positive reusmes \n"
					+ "2. negative resumes");
			return;
		}

		Processor processor = new Processor(args);
		processor.process();
		
		System.out.println("Done");
		return;
	}
}
