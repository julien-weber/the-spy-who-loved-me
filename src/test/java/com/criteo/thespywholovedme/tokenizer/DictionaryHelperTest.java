package com.criteo.thespywholovedme.tokenizer;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.criteo.thespywholovedme.model.TermIDF;

public class DictionaryHelperTest {
    static Logger log = LoggerFactory.getLogger(DictionaryHelperTest.class);
    @Test
    public void test() {
      List<TermIDF> dict = new ArrayList<TermIDF> ();
      dict.add(new TermIDF("A", 1.));
      dict.add(new TermIDF("B", 0.));
      String path = "dict.txt";
      DictionaryHelper.Save(dict, path);

      List<TermIDF> dict2 = DictionaryHelper.Load(path);
      File file = new File(path);
      boolean success = file.delete();
      log.info("file deleted ?" + success);

      assertTrue(dict.size() == dict2.size());
      for (TermIDF t : dict) {
          assertTrue(dict2.contains(t));
      }
    }
}
