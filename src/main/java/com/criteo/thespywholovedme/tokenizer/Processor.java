package com.criteo.thespywholovedme.tokenizer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
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

import com.criteo.thespywholovedme.model.FeatureExtractor;
import com.criteo.thespywholovedme.model.MLModel;
import com.criteo.thespywholovedme.model.TermIDF;

public class Processor {
	private Set<String> uniqueWords = new HashSet<>();
	private Map<String, Integer> dictionary = new HashMap<>();
	private Map<String, Double> dictionaryIDF = new HashMap<>();
	private int resumeCount = 0;

	private List<Map<String, Integer>> positive_tokenInfoList = new ArrayList<>();
	private List<Map<String, Integer>> negative_tokenInfoList = new ArrayList<>();
	private List<TermIDF> IDFList = new ArrayList<>();

	void process(String[] dirs) {		
		if (dirs == null || dirs.length == 0)
			return;

		// assume the first directory is for resumes that receive positive reviews
		for (int i=0; i< dirs.length; i++) {
			if (dirs[i] == null || dirs[i].isEmpty())
				continue;

			Path dir = new File(dirs[i]).toPath();

			try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*.txt"))
			{
				for (Path entry: stream) {
					resumeCount++;
					processFile(entry.toFile(), i==0);
				}

			} catch (DirectoryIteratorException ex) {
				ex.printStackTrace();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		System.out.println("size of dictionary: " + dictionary.size());

		computeIDF();

		MLModel.createModel(IDFList, positive_tokenInfoList, negative_tokenInfoList);
		
		System.out.println();
		outputResumeTokenInfo();
	}

	public void process(List<File> positive, List<File> negative) {

		for (File file : positive) {
			if (file == null || !file.exists() || file.isDirectory())
				continue;

			resumeCount++;
			processFile(file, true);

		}

		for (File file : negative) {
			if (file == null || !file.exists() || file.isDirectory())
				continue;

			resumeCount++;
			processFile(file, true);

		}

		System.out.println("size of dictionary: " + dictionary.size());

		computeIDF();

		System.out.println();
		outputResumeTokenInfo();
	}

	public List<Double> GetXWithTfIdf(File resumeFile)
	{
		return null;
	}

	void computeIDF() {
		for (String key : dictionary.keySet()) {
			double idf = Math.log(resumeCount / dictionary.get(key));
			dictionaryIDF.put(key, idf);
			IDFList.add(new TermIDF(key, idf));
			//System.out.println(key + ": " + dictionaryIDF.get(key));
		}
	}

	void outputResumeTokenInfo() {
		for (Map<String, Integer> tokenInfo: positive_tokenInfoList) {
			for (String token : tokenInfo.keySet()) {
				//System.out.println(token + ": " + tokenInfo.get(token));
			}
			System.out.println();
		}

		for (Map<String, Integer> tokenInfo: negative_tokenInfoList) {
			for (String token : tokenInfo.keySet()) {
				//System.out.println(token + ": " + tokenInfo.get(token));
			}
			System.out.println();
		}
	}

	void processFile(File file, boolean positive) {
		// read file line by line
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
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
