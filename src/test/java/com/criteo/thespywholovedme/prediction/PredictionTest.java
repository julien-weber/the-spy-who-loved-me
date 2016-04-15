package com.criteo.thespywholovedme.prediction;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PredictionTest {
    private static final double DELTA = 1e-15;

    static Logger log = LoggerFactory.getLogger(PredictionTest.class);

    @Test
    public void testGetScore() {
        Prediction prediction = Prediction.getInstance();

        List<Double> weight = Arrays.asList(0.1, 0.5, 1.0);
        List<Double> XWithTfIdf = Arrays.asList(1.0, 1.0, 2.0);
        double finalScore = prediction.getFinalScore(weight, XWithTfIdf);

        double score = 0.0;
        for (int ii = 0; ii < weight.size(); ++ii) {
            score += weight.get(ii) * XWithTfIdf.get(ii);
        }
        double expectedFinalScore = 1.0 / (1.0 + Math.exp(-1.0 * score));

        log.info("the expected final score is " + expectedFinalScore);
        Assert.assertEquals(expectedFinalScore, finalScore, DELTA);
    }
}
