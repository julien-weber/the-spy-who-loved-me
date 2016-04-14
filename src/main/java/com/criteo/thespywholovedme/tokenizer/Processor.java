package com.criteo.thespywholovedme.tokenizer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class Processor {

	public static class TermIDF {
	    private String term;
	    private Double idf;
	    
	    public TermIDF(String term, Double idf)
	    {
	    	this.term = term;
	    	this.idf = idf;
	    }
	}

	private String[] args;
	private Set<String> uniqueWords = new HashSet<>();
	private Map<String, Integer> dictionary = new HashMap<>();
	private Map<String, Double> dictionaryIDF = new HashMap<>();
	private List<Map<String, Integer>> positive_tokenInfoList = new ArrayList<>();
	private List<Map<String, Integer>> negative_tokenInfoList = new ArrayList<>();
	
	private List<TermIDF> IDFList = new ArrayList<>();
	
	private int resumeCount = 0;
	
	public Processor(String[] args) {
		this.args = args;
	}

	void process() {
		
		// assume the first directory is for resumes that receive positive reviews
		for (int i=0; i< args.length; i++) {
			if (args[i] == null || args[i].isEmpty())
				continue;

			Path dir = new File(args[i]).toPath();

			try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*.txt")) 
			{
				for (Path entry: stream) {
					resumeCount++;
					processFile(entry, i==0);
				}

			} catch (DirectoryIteratorException ex) {
				System.err.println(ex.getMessage());
			} catch (IOException ex) {
				System.err.println(ex.getMessage());
			}
		}
		System.out.println("size of dictionary: " + dictionary.size());
		
		computeIDF();
		
		System.out.println();
		outputResumeTokenInfo();
	}
	
	void computeIDF() {
		for (String key : dictionary.keySet()) {
			double idf = Math.log(resumeCount / dictionary.get(key));
			dictionaryIDF.put(key, idf);
			IDFList.add(new TermIDF(key, idf));
			System.out.println(key + ": " + dictionaryIDF.get(key));
		}
	}
	
	void outputResumeTokenInfo() {
		for (Map<String, Integer> tokenInfo: positive_tokenInfoList) {
			for (String token : tokenInfo.keySet()) {
				System.out.println(token + ": " + tokenInfo.get(token));	
			}
			System.out.println();
		}
		
		for (Map<String, Integer> tokenInfo: negative_tokenInfoList) {
			for (String token : tokenInfo.keySet()) {
				System.out.println(token + ": " + tokenInfo.get(token));	
			}
			System.out.println();
		}
	}

	void processFile(Path path, boolean positive) {
		// read file line by line
		try (BufferedReader br = new BufferedReader(new FileReader(path.toFile()))) {
			List<Map<String, Integer>> tokenInfoList = positive ?
					positive_tokenInfoList : negative_tokenInfoList;
			
			Map<String, Integer> tokenInfo = new HashMap<>();
			tokenInfoList.add(tokenInfo);
			
			String line;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
				// process the line.
				Set<String> tokens = Tokenizer.tokenize(line);
				if (tokens != null && !tokens.isEmpty()) {
					uniqueWords.addAll(tokens);

					for (String token : tokens)
					{
						if (!dictionary.containsKey(token)) {
							dictionary.put(token, 1);
						}
						
						if (!tokenInfo.containsKey(token)) {
							tokenInfo.put(token,  1);
						} else {
							tokenInfo.put(token, tokenInfo.get(token) + 1);
						}
					}
					System.out.println(Arrays.toString(tokens.toArray()));
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
