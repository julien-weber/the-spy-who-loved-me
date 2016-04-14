package com.criteo.thespywholovedme.prediction;

import java.nio.file.Path;
import java.util.List;

import org.apache.log4j.Logger;

import com.criteo.thespywholovedme.tools.Log4j;

/**
 * prediction: the final score
 * S = 1/(1+e^(-s))
 * s = sum(w_i * _i)
 * where w is the weight, 
 *       x (XWithTfIdf) is the projection of the resume into the dictionary with tf-idf 
 */

public class Prediction {
    static {
      Log4j.initialize();
    }
    static Logger log = Logger.getLogger(Prediction.class.getName());

    private Prediction() {
    }

    private static class Holder {
        private static final Prediction instance = new Prediction();
    }

    public static Prediction getInstance() {
        return Holder.instance;
    }

    public double getFinalScore(List<Double> weight, Path pathToResume, List<Double> XWithTfIdf) {
        assert(weight.size() == XWithTfIdf.size());
        double score = 0.0;
        for (int ii = 0; ii < weight.size(); ++ii) {
            score += weight.get(ii) * XWithTfIdf.get(ii); 
        }
        double finalScore = 1/(1 + Math.exp(-1.0 * score));
        log.info("the final score is " + finalScore);
        return finalScore;
    }
}
