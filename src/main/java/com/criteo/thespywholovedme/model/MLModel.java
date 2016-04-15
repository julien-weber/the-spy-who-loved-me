package com.criteo.thespywholovedme.model;

import org.python.util.PythonInterpreter; 
import java.io.File;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MLModel {
	private static String filePath = "src/test/resources/model/featureVectorFile";
	private static String weightsFilePath = "src/test/resources/model/weights.txt";

	private static List<TermIDF> masterDictionary;
	
	public static void createModel(List<TermIDF> dictionary, List<Map<String, Integer>> posResumes, List<Map<String, Integer>> negResumes) {
		FeatureExtractor featureExtractor = new FeatureExtractor();
		featureExtractor.writeFeatures(filePath, dictionary, posResumes, negResumes);
		masterDictionary = dictionary;
		
		PythonInterpreter interp = new PythonInterpreter();
		File file = new File(filePath);
		String absPath = file.getAbsolutePath();
	//	String command = " src/main/python/script " + absPath;

		try {
			//Runtime.getRuntime().exec(command);	
			
		//  interp.exec(" python src/main/python/learning_model.py  " + absPath);
		}
		catch (Exception ex) {
		  ex.printStackTrace();
		}
				
	}
	
	
	public static List<Double> getPredictionSVDX(Map<String, Integer> resume) {
		FeatureExtractor featureExtractor = new FeatureExtractor();
		return featureExtractor.getSVDX(masterDictionary, resume);
		
	}
	
	public static List<Double> getWeightVector() {
		BufferedReader br;
		List<Double> weights = new ArrayList<Double>();
		try {
			br = new BufferedReader(new FileReader(weightsFilePath));
			String s = br.readLine();
			for(String t: s.split(",")) {
				weights.add(NumberFormat.getInstance(Locale.US).parse(t).doubleValue());
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return weights;
	}

}
