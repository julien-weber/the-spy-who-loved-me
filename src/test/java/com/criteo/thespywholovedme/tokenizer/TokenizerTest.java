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
	public void TestTokenizer() {

		Processor processor = new Processor();
		
		List<File> positive = new LinkedList<File>();
		List<File> negative = new LinkedList<File>();
		
		processor.process(positive, negative);
		
		System.out.println("Done");
		return;
	}

    @Test
	public void TestTokenizerWithDirs() {

		Processor processor = new Processor();
		
		String[] dirs = {"/Users/yu.li/hackathon/good", "/Users/yu.li/hackathon/bad"};
		
		processor.process(dirs);
		
		System.out.println("Done");
		return;
	}
}
