package com.criteo.thespywholovedme.tokenizer;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.criteo.thespywholovedme.model.TermIDF;

public class DictionaryHelperTest {
    static Logger log = LoggerFactory.getLogger(DictionaryHelperTest.class);

    @Test
    public void test() {
        List<TermIDF> dict = new ArrayList<TermIDF>();
        dict.add(new TermIDF("A", 1.));
        dict.add(new TermIDF("B", 0.));
        String path = "dict.txt";
        DictionaryHelper.Save(dict, path);

        List<TermIDF> dict2 = DictionaryHelper.Load(path);
        File file = new File(path);
        boolean success = file.delete();
        log.info("file deleted ?" + success);

        assertTrue(dict.size() == dict2.size());
        for (TermIDF t: dict) {
            assertTrue(dict2.contains(t));
        }
    }

    @Test
    public void realMatrixSerializationTest() throws Exception {
        double[][] input = new double[][] { { 0.1d, 0.2d }, { 1.1d, 1.2d } };
        String savePath = "target/realMatrices/realMatrix.txt";
        RealMatrix realMatrix = MatrixUtils.createRealMatrix(input);
        DictionaryHelper.saveMatrix(realMatrix, savePath);

        RealMatrix readMatrix = DictionaryHelper.loadMatrix(savePath);
        double[][] output = readMatrix.getData();

        assertEquals(0.1d, output[0][0], 0.01d);
        assertEquals(0.2d, output[0][1], 0.01d);
        assertEquals(1.1d, output[1][0], 0.01d);
        assertEquals(1.2d, output[1][1], 0.01d);
    }

}
