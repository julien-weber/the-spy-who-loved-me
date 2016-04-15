package com.criteo.thespywholovedme.tokenizer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.criteo.thespywholovedme.model.TermIDF;

/*
 * for save and load dictionary
 */
public class DictionaryHelper {
    static Logger log = LoggerFactory.getLogger(DictionaryHelper.class);

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
      BufferedWriter output = null;
      try {
          output = new BufferedWriter(new FileWriter(file));
          StringBuilder sb = new StringBuilder();
          for (TermIDF termIdf : dict) {
            sb.append(termIdf.getTerm());
            sb.append(":");
            sb.append(termIdf.getIdf());
            sb.append("\n");
          }
          output.write(sb.toString());
      } catch ( IOException e ) {
          e.printStackTrace();
      } finally {
        if ( output != null ) {
          try {
            output.close();
          } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
        }
      }
    }
}
