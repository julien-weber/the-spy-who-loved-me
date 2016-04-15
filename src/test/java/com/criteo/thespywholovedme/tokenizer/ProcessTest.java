package com.criteo.thespywholovedme.tokenizer;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ProcessTest {
  static Logger log = LoggerFactory.getLogger(ProcessTest.class);
    @Test
    public void testProcessFile() {
        Processor processor = new Processor();
        List<Map<String, Integer>> tokenInfoList = new ArrayList<Map<String, Integer>> ();

        String [] resumes = {"java, python, perl, word, \n, java",
                              "java, python, c++, ppt, \n, python"};

        for (int ii = 0; ii < resumes.length; ++ii) {
            String path = "testResume.txt";
            File file = new File(path);
            BufferedWriter output = null;
            try {
                output = new BufferedWriter(new FileWriter(file));
                output.write(resumes[ii]);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (output != null) {
                    try {
                        output.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            processor.processFile(file, tokenInfoList);

            boolean success = file.delete();
            log.info("file deleted ?" + success);
        }

        Set<String> expectedUniqueWords = new HashSet<String>(Arrays.asList("python", "perl", "c++", "java", "word", "ppt"));
        assertTrue( expectedUniqueWords.equals(processor.uniqueWords) );
        Map<String, Integer> expectedDict = new HashMap<String, Integer>();
        expectedDict.put("python", 2);
        expectedDict.put("perl", 1);
        expectedDict.put("c++", 1);
        expectedDict.put("java", 2);
        expectedDict.put("word", 1);
        expectedDict.put("ppt", 1);
        assertTrue( expectedDict.equals(processor.dictionary) );

        {
            Map<String, Integer> expectedTokenInfo = new HashMap<String, Integer>();
            expectedTokenInfo.put("python", 1);
            expectedTokenInfo.put("perl", 1);
            expectedTokenInfo.put("java", 2);
            expectedTokenInfo.put("word", 1);
            assertTrue( expectedTokenInfo.equals(tokenInfoList.get(0)) );
        }
        {
          Map<String, Integer> expectedTokenInfo = new HashMap<String, Integer>();
          expectedTokenInfo.put("python", 2);
          expectedTokenInfo.put("c++", 1);
          expectedTokenInfo.put("java", 1);
          expectedTokenInfo.put("ppt", 1);
          assertTrue( expectedTokenInfo.equals(tokenInfoList.get(1)) );
      }
    }
}
