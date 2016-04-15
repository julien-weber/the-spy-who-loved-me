package com.criteo.hackathon.LinkedInOutput;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class FileReader {

    public void processFile(File input,
            String outputFolder) {
        File outputDir = new File(outputFolder);
        if (input.isDirectory()
                || outputDir.exists() && !outputDir.isDirectory()) {
            return;
        }
        ProfileParser pp = new ProfileParser(input.getPath());
        StringBuilder sb = new StringBuilder();
        List<String> skills = pp.getSkills();
        List<Experience> experiences = pp.getExperiences();
        List<Education> educations = pp.getEducations();
        for (int i = 0; i < educations.size(); i++) {
            sb.append(educations.get(i).toString());
        }
        for (int i = 0; i < experiences.size(); i++) {
            sb.append(experiences.get(i).toString());
        }
        for (int i = 0; i < skills.size(); i++) {
            sb.append(skills.get(i));
        }

        if (!outputDir.exists()) {
            outputDir.mkdir();
        }
        String outputPath = outputFolder + "/" + input.getName() + ".output";
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(outputPath, "UTF-8");
            pw.println(sb.toString());
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }

    public static void main(String[] args) {
        // input args[0] as input folder, args[1] as output folder
        File inputFolder = new File(args[0]);
        FileReader fr = new FileReader();
        for (File file: inputFolder.listFiles()) {
            fr.processFile(file, args[1]);
        }
        System.out.println("Done");
    }
}
