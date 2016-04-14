package com.criteo.thespywholovedme.prediction;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import com.criteo.thespywholovedme.tools.Log4j;

public class PredictionTest {
    private static final double DELTA = 1e-15;
    static {
        Log4j.initialize();
    }
   static Logger log = Logger.getLogger(PredictionTest.class.getName());

    @Test
    public void testGetScore() {
        Prediction prediction = Prediction.getInstance();

        List<Double> weight = Arrays.asList(0.1, 0.5, 1.0);
        List<Double> XWithTfIdf = Arrays.asList(1.0, 1.0, 2.0);
        Path pathToResume = null;
        double finalScore = prediction.getFinalScore(weight, pathToResume, XWithTfIdf);

        double score = 0.0;
        for (int ii = 0; ii < weight.size(); ++ii) {
            score += weight.get(ii) * XWithTfIdf.get(ii); 
        }
        double expectedFinalScore = 1.0 / (1.0 + Math.exp(-1.0 * score));

        log.info("the expected final score is " + expectedFinalScore);
        Assert.assertEquals(expectedFinalScore, finalScore, DELTA);
    }
}
