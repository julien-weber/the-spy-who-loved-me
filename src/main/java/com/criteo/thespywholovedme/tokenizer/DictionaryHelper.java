package com.criteo.thespywholovedme.tokenizer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.criteo.thespywholovedme.model.SVD;
import com.criteo.thespywholovedme.model.TermIDF;
import com.google.common.collect.Lists;

/*
 * for save and load dictionary
 */
public class DictionaryHelper {
    static Logger log = LoggerFactory.getLogger(DictionaryHelper.class);

    public static RealMatrix loadMatrix(String path) {
        List<List<Double>> lists = Lists.newArrayList();
        File file = new File(path);
        if (!file.exists()) {
            log.error("ERROR: " + path + " does not exist!");
        }

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                List<Double> doubleLine = parseDoubleArray(currentLine.split(","));
                lists.add(doubleLine);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        double[][] result = SVD.convertListToArray(lists);
        return MatrixUtils.createRealMatrix(result);
    }

    public static List<Double> parseDoubleArray(String[] splitLine) {
        List<Double> doubleList = Lists.newArrayList();
        for (String item: splitLine) {
            doubleList.add(Double.parseDouble(item));
        }

        return doubleList;
    }

    public static List<TermIDF> Load(String path) {
        List<TermIDF> dict = new ArrayList<TermIDF>();
        File file = new File(path);
        if (!file.exists()) {
            log.error("ERROR: " + path + " does not exist!");
            return dict;
        }

        BufferedReader br = null;
        try {
            String sCurrentLine;
            br = new BufferedReader(new FileReader(path));
            while ((sCurrentLine = br.readLine()) != null) {
                String[] termIDFStrs = sCurrentLine.split(":");
                if (termIDFStrs.length == 2) {
                    TermIDF termIDF = new TermIDF(termIDFStrs[0], Double.parseDouble(termIDFStrs[1]));
                    dict.add(termIDF);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        return dict;
    }

    public static void saveMatrix(RealMatrix realMatrix, String path) {
        File file = new File(path);
        if (file.exists()) {
            if (file.isFile()) {
                log.info("file " + path + "exists, will be overwrotten.");
            } else {
                log.error("ERROR file " + path + "is a folder.");
                return;
            }
        } else {
            try {
                FileUtils.touch(file);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        try (BufferedWriter output = new BufferedWriter(new FileWriter(file))) {
            double[][] data = realMatrix.getData();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < data.length; i++) {
                sb.append(StringUtils.join(data[i], ','));
                sb.append("\n");
            }
            output.write(sb.toString());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void Save(List<TermIDF> dict, String path) {
        File file = new File(path);
        if (file.exists()) {
            if (file.isFile()) {
                log.info("file " + path + "exists, will be overwrotten.");
            } else {
                log.error("ERROR file " + path + "is a folder.");
                return;
            }
        }

        // save to file
        try(BufferedWriter output = new BufferedWriter(new FileWriter(file))) {
            StringBuilder sb = new StringBuilder();
            for (TermIDF termIdf: dict) {
                sb.append(termIdf.getTerm());
                sb.append(":");
                sb.append(termIdf.getIdf());
                sb.append("\n");
            }
            output.write(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
