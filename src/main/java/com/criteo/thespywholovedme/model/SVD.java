package com.criteo.thespywholovedme.model;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.SingularValueDecomposition;
import java.util.List;
import java.util.ArrayList;

public class SVD {
	 private static RealMatrix UHalfS;
	 
	   public static RealMatrix getSVD(double[][] matrixData) { 

	      // create M-by-N matrix
	   
	  //double[][] matrixData = { {1d,2d,3d,4d}, {2d,5d,3d,5d}};
	  RealMatrix m = MatrixUtils.createRealMatrix(matrixData);
	  System.out.println(m.getColumnDimension());
	  System.out.println(m.getRowDimension());
	  SingularValueDecomposition svd = new SingularValueDecomposition(m);
	   
	      // compute the singular vallue decomposition
	      RealMatrix U = svd.getU();
	      RealMatrix V = svd.getV();
	      double[] sv = svd.getSingularValues();
	      double[] squareSV = square(sv);
	      RealMatrix S = convertToDiagMatrix(squareSV);
	      UHalfS = svd.getU().multiply(S); // store
	      RealMatrix VHalfS = svd.getV().multiply(S);
	      return VHalfS;
	   }
	   
	   public static double[] square (double [] array) {
	  double[] result = new double[array.length];
	  for(int i = 0; i < array.length ; i++ )
	    result[i] = Math.sqrt(array[i]);

	  return result;
	}
	   public static RealMatrix convertToDiagMatrix (double [] array) {
	  double[][] result = new double[array.length][array.length];
	  for(int i = 0; i < array.length ; i++ )
	    result[i][i] = (array[i]);

	  RealMatrix fResult = MatrixUtils.createRealMatrix(result);
	  return fResult;
	}
	   
	   
	   public static List<Double> computeUHalfs(List<Double> X) {
		   List<List<Double>> Xlist = new ArrayList<List<Double>>();
		   Xlist.add(X);
		   
		   double[][] arr = convertListToArray(Xlist);
		   
		   RealMatrix XM = MatrixUtils.createRealMatrix(arr);
		   XM.multiply(UHalfS);
		   
		   return null;
		   
	   }
	   
	   public static double[][] convertListToArray(List<List<Double>> xnlist) {

	        int rowSize = xnlist.size();
	        int colSize = xnlist.get(0).size();
	        double[][] arr = new double[rowSize][colSize];
	        for (int i = 0; i < rowSize; i++) {
	            for (int j = 0; j < colSize; j++) {
	                arr[i][j] = xnlist.get(i).get(j);
	            }
	        }
	        return arr;
	    }
	}
