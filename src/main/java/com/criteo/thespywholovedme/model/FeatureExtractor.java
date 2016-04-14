package com.criteo.thespywholovedme.model;


//import org.apache.axis.utils.StringUtils;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.SingularValueDecomposition;

//import org.apache.log4j.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class FeatureExtractor  {


    private static RealMatrix US;
    

    public void  writeFeatures(String filePath, List<TermIDF> dictionary, List<Map<String,Integer>> posResumes,
                              List<Map<String,Integer>> negResumes) {
        String[] outputStringFeatures =  extractStringFeatures(dictionary,  posResumes, negResumes);
        
        writetoFile(filePath, outputStringFeatures);

    }
    
    public String[]  extractStringFeatures(List<TermIDF> dictionary, List<Map<String,Integer>> posResumes,
            List<Map<String,Integer>> negResumes) {
List<List<Double>> X1_N =  extractFeatures(dictionary,  posResumes, negResumes);
double[][] array = convertListToArray(X1_N);
RealMatrix vU = SVD.getSVD(array);

   return getFeatureString(vU, posResumes.size() );

}



    public List<List<Double>> extractFeatures(List<TermIDF> dictionary, List<Map<String,Integer>> posResumes,
                                              List<Map<String,Integer>> negResumes ) {

        List<List<Double>> X1_N = new ArrayList<List<Double>>();

        for (Map<String, Integer> resume : posResumes) {
            List<Double> X = extractFeatures(dictionary, resume);
            X1_N.add(X);
        }

        for (Map<String, Integer> resume : negResumes) {
            List<Double> X = extractFeatures(dictionary, resume);
            X1_N.add(X);
        }

        return X1_N;
    }

    public void writeFeatures(List<TermIDF> dictionary, Map<String,Integer> resume) {
        List<Double> X = extractFeatures(dictionary, resume);

    }



    public List<Double> extractFeatures(List<TermIDF> dictionary, Map<String,Integer> resume)  {

        List<Double> X = new ArrayList<Double>();

        for (TermIDF termIdf: dictionary) {
                String term = termIdf.getTerm();
                Integer count = resume.get(term);
                double tgidf = 0;
                if (count != null) {
                    tgidf = count*termIdf.getIdf();
                }
                X.add(tgidf);

        }

        return X;

    }

    private double[][] convertListToArray(List<List<Double>> xnlist) {

        int rowSize = xnlist.size();
        int colSize = xnlist.get(0).size();
        double[][] arr = new double[rowSize][colSize];
        for ( int i = 0; i < rowSize; i++)  {
            for (int j =0; j < colSize; j++) {
                arr[i][j] = xnlist.get(i).get(j);
            }
        }
        return arr;
    }
/*
    private RealMatrix SVD(double[][] matrixData) {
        // double[][] matrixData = { {1d,2d,3d}, {2d,5d,3d}};
        RealMatrix m = MatrixUtils.createRealMatrix(matrixData);
        SingularValueDecomposition svd = new SingularValueDecomposition(m);

        // compute the singular vallue decomposition
        RealMatrix U = svd.getU();
        RealMatrix S = svd.getS();
        RealMatrix V = svd.getV();
        US = svd.getU().multiply(S);
        RealMatrix VS = svd.getV().multiply(S);
        return VS;

    }
    
    */

    private void writetoFile(String filePath, String[] outputFeatures) {
        try {
            File file = new File(filePath);


            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            } else {
            	file.delete();
            	file.createNewFile();
            }
            	

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            PrintWriter pw = new PrintWriter(new BufferedWriter(fw));
            for (String str: outputFeatures) {
                pw.println(str);
            }
            pw.close();

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

    }
    
    private String[] getFeatureString(RealMatrix vU,  int posSize) {
        
            int cols = vU.getColumnDimension();
            int rows = vU.getRowDimension();


            String[] outputFeatures = new String[rows];

            for (int i = 0; i < rows; i++) {
                StringBuilder s = new StringBuilder();
                for (int j = 0; j < cols; j++) {
                    double val = vU.getEntry(i,j);
                    if (j > 0) {
                        s.append(",");
                    }
                    s.append(val);
                }
                if (i < posSize) {
                    s.append(",1");
                } else {
                    s.append(",0");
                }
                outputFeatures[i] = s.toString();
            }


           return outputFeatures;

    }



}