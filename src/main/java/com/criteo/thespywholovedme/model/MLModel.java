package com.criteo.thespywholovedme.model;

import org.python.core.Py; 
import org.python.core.PyString; 
import org.python.core.PySystemState; 
import org.python.util.PythonInterpreter; 
import java.io.File;

import java.util.List;
import java.util.Map;

public class MLModel {
	private static String filePath = "src/test/resources/model/featureVectorFile";
	
	private static List<Double> weightVector;
	private static List<TermIDF> masterDictionary;
	
	public static void createModel(List<TermIDF> dictionary, List<Map<String, Integer>> posResumes, List<Map<String, Integer>> negResumes) {
		FeatureExtractor featureExtractor = new FeatureExtractor();
		featureExtractor.writeFeatures(filePath, dictionary, posResumes, negResumes);
		masterDictionary = dictionary;
		
		PythonInterpreter interp = new PythonInterpreter();
		File file = new File(filePath);
		String absPath = file.getAbsolutePath();
		String command = " scr/main/python/script " + absPath;

		try {
		//	Runtime.getRuntime().exec(command);	
			
		  interp.exec("src/main/python/learning_model.py learning " + absPath);
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
		return weightVector;
		
	}

}
