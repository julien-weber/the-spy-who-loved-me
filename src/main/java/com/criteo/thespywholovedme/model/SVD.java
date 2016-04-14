package com.criteo.thespywholovedme.model;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.SingularValueDecomposition;

public class SVD {
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
	      RealMatrix UHalfS = svd.getU().multiply(S);
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
	}
