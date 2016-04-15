package com.criteo.thespywholovedme.model;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.SingularValueDecomposition;

import com.criteo.thespywholovedme.tokenizer.DictionaryHelper;

import java.util.List;
import java.util.ArrayList;

public class SVD {
    private static final String UHALFS_PATH = "src/test/resources/model/uHalfS.txt";
    private static RealMatrix UHalfS;

    public static RealMatrix getSVD(double[][] matrixData) {

        // create M-by-N matrix

        // double[][] matrixData = { {1d,2d,3d,4d}, {2d,5d,3d,5d}};
        RealMatrix m = MatrixUtils.createRealMatrix(matrixData);
        m = m.transpose();
        System.out.println(m.getColumnDimension());
        System.out.println(m.getRowDimension());
        SingularValueDecomposition svd = new SingularValueDecomposition(m);

        // compute the singular vallue decomposition
        RealMatrix U = svd.getU();
        RealMatrix V = svd.getV();
        double[] sv = svd.getSingularValues();
        double[] squareSV = square(sv);
        RealMatrix S = convertToDiagMatrix(squareSV);
        UHalfS = svd.getU().multiply(S);

        // store
        DictionaryHelper.saveMatrix(UHalfS, UHALFS_PATH);

        RealMatrix VHalfS = svd.getV().multiply(S);
        return VHalfS;
    }

    public static double[] square(double[] array) {
        double[] result = new double[array.length];
        for (int i = 0; i < array.length; i++)
            result[i] = Math.sqrt(array[i]);

        return result;
    }

    public static RealMatrix convertToDiagMatrix(double[] array) {
        double[][] result = new double[array.length][array.length];
        for (int i = 0; i < array.length; i++)
            result[i][i] = (array[i]);

        RealMatrix fResult = MatrixUtils.createRealMatrix(result);
        return fResult;
    }

    public static List<Double> computeUHalfs(List<Double> X) {
        List<List<Double>> Xlist = new ArrayList<List<Double>>();
        Xlist.add(X);

        double[][] arr = convertListToArray(Xlist);

        RealMatrix XM = MatrixUtils.createRealMatrix(arr);

        if (UHalfS == null) {
            UHalfS = DictionaryHelper.loadMatrix(UHALFS_PATH);
        }
        double[][] xmArray = UHalfS.getData();
        int rows = xmArray.length;

        double[][] firstNArray = new double[rows][8];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < 8; j++) {
                firstNArray[i][j] = xmArray[i][j];
            }

        }
        // XM = MatrixUtils.createRealMatrix(firstNArray);

        RealMatrix localUHalfS = MatrixUtils.createRealMatrix(firstNArray);

        XM.multiply(localUHalfS);

        double[] result = XM.getRow(0);
        List<Double> resultList = new ArrayList<Double>();
        for (int i = 0; i < result.length; i++) {
            resultList.add(result[i]);
        }
        return resultList;
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
