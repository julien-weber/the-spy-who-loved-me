package com.criteo.thespywholovedme.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;



public class FeatureExtractorTest {


    @Test
    public void testFeatureExtractor() {
        String file = "featureoutputFile";

        List<TermIDF> dictionary = new ArrayList<TermIDF>();

        dictionary.add( new TermIDF("Java", 1.0));
        dictionary.add( new TermIDF("C", 1.0));
        dictionary.add( new TermIDF("Python", 2.0));
        dictionary.add( new TermIDF("Hadoop", 2.0));
        dictionary.add( new TermIDF("Marketing", 2.0));

        List<Map<String,Integer>> posResumes = new ArrayList<Map<String,Integer>>();

        Map<String,Integer> resume1 = new HashMap<String, Integer>();
        resume1.put("Java", 2);
        resume1.put("Hadoop", 1);

        Map<String,Integer> resume2 = new HashMap<String, Integer>();
        resume2.put("C", 2);
        resume2.put("Python", 1);

        posResumes.add(resume1);
        posResumes.add(resume2);

        Map<String,Integer> resume3 = new HashMap<String, Integer>();
        resume3.put("Marketing", 2);
        resume3.put("Python", 1);

        List<Map<String,Integer>> negResumes = new ArrayList<Map<String,Integer>>();
        negResumes.add(resume3);

        FeatureExtractor featureExtractor = new FeatureExtractor();
        featureExtractor.writeFeatures(file, dictionary, posResumes, negResumes);

    }


}