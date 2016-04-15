package com.criteo.hackathon.LinkedInOutput;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ProfileParser {

    List<Education> getEducations() {
        List<Education> ret = new ArrayList<Education>();
        // Elements entries = htmlDoc.select(
        // "#background-education-container > div > div > div");

        Elements entries = htmlDoc.select("section#education > ul > li");
        for (Element entry: entries) {
            String name = "";
            String degree = "";
            String description = "";
            // Element en = entry.select(":root > div > header > h4 > a[href]").first();
            Element en = entry.select(":root > header > h4 > a[href]").first();
            if (en == null)
                en = entry.select(":root > header> h4").first();
            // Element ed = entry.select(":root > div > header > h5").first();
            // Element ed = entry.select(":root > div > header > h5").first();
            Element ed = entry.select(":root > header > h5").first();
            Elements edpns = entry.select(":root > div > p");
            if (en != null)
                name = en.text();
            if (ed != null)
                degree = ed.text();
            if (edpns != null) {
                for (Element edpn: edpns) {
                    description += edpn.text();
                }
            }
            ret.add(new Education(name, degree, description));
        }
        return ret;
    }
    List<String> getSkills() {
        List<String> ret = new ArrayList<String>();
        // Elements entries = htmlDoc.select(
        // "#background-skills-container > div > div > div > div > ul > li");
        Elements entries = htmlDoc.select(
                "section#skills > ul > li > a > span");
        for (Element entry: entries) {
            // String name = entry.attr("data-endorsed-item-name");
            String name = entry.text();
            if (name != null && name.length() > 0)
                ret.add(name);
        }
        return ret;
    }
    List<Experience> getExperiences() {
        List<Experience> ret = new ArrayList<Experience>();
        // Elements headers = htmlDoc
        // .select("#background-experience-container > div > div > div");
        Elements entries = htmlDoc.select("section#experience > ul > li");
        for (Element header: entries) {
            String title = "";
            String company = "";
            String description = "";
            Element et = header.select(":root > header > h4  a[href]").first();

            Element ec = header
                    // .select(":root > header span[data-tracking=mcp_profile_sum] a[href]")
                    .select(":root > header > h5.item-subtitle > a")
                    .first();
            Elements eds = header.select(":root > p");
            if (et != null) {
                title = et.text();
            }
            if (ec != null) {
                company = ec.text();
            }
            if (eds != null) {
                for (Element el: eds) {
                    description += el.text();
                }
            }
            Experience ex = new Experience(title, company, description);
            ret.add(ex);
        }
        return ret;
    }

    private String htmlPath;
    private String htmlStr;
    private Document htmlDoc;

    public ProfileParser(String path) {
        this.htmlPath = path;
        this.htmlStr = readFile(htmlPath);
        this.htmlDoc = Jsoup.parse(htmlStr);
    }


    private String readFile(String path) {
        StringBuilder sb = new StringBuilder();
        try {
            FileReader fr = new FileReader(new File(path));
            BufferedReader br = new BufferedReader(fr);
            String input = br.readLine();
            while (input != null) {
                sb.append(input);
                input = br.readLine();
            }
            fr.close();
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }


}
