package com.criteo.thespywholovedme.tokenizer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.criteo.thespywholovedme.model.TermIDF;
import com.criteo.thespywholovedme.prediction.Prediction;

/*
 * for save and load dictionary
 */
public class DictionaryHelper {
    static Logger log = LoggerFactory.getLogger(DictionaryHelper.class);

    public static List<TermIDF> Load(String path) {
        return null;
    }

    public static void Save(List<TermIDF> dict, String path) throws IOException {
      File file = new File(path);
      if (file.isFile()) {
          if (file.exists()) {
            log.info("file " + path + "exists, will be overwrotten.");
          } else {
              String parent = file.getParent();
              File parentFolder = new File(parent);
              if (!parentFolder.exists()) {
                  boolean successful = parentFolder.mkdirs();
                  if (!successful) {
                    log.error("ERROR: failed trying to create the directory " + parent);
                    return;
                  }
              }
          }
      } else {
          log.error("ERROR: need file instead of folder.");
          return;
      }

      // save to file
      BufferedWriter output = null;
      try {
          output = new BufferedWriter(new FileWriter(file));
          for (TermIDF termIdf : dict) {
            output.write(termIdf.toString());
          }
      } catch ( IOException e ) {
          e.printStackTrace();
      } finally {
        if ( output != null ) {
          output.close();
        }
      }
    }
}
