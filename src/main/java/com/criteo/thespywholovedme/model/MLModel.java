package com.criteo.thespywholovedme.model;

import org.python.core.Py; 
import org.python.core.PyString; 
import org.python.core.PySystemState; 
import org.python.util.PythonInterpreter; 

import java.util.List;
import java.util.Map;

public class MLModel {
	private static String filePath = "src/test/resources/model/featureVectorFile";
	
	public void createModel(List<TermIDF> dictionary, List<Map<String, Integer>> posResumes, List<Map<String, Integer>> negResumes) {
		FeatureExtractor featureExtractor = new FeatureExtractor();
		featureExtractor.writeFeatures(filePath, dictionary, posResumes, negResumes);
		
		PythonInterpreter interp = new PythonInterpreter();
		try {
		  interp.exec("import re");
		} 
		catch (Exception ex) {
		  ex.printStackTrace();
		}
		
		
		
		
	}

}
