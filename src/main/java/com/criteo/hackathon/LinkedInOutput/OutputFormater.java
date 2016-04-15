package com.criteo.hackathon.LinkedInOutput;

import org.json.simple.JSONObject;

public class OutputFormater {

    private final static String[] HEADER_FIELDS = new String[] { "id", "title",
            "name", "price", "description", "link", "image_link",
            "availability", "identifier exists", "condition", "brand",
            "google_product_category", "ios_url", "android_url",
            "android_package" };
    static {
        StringBuilder sb = new StringBuilder();
        for (String field: HEADER_FIELDS) {
            if (0 != sb.length())
                sb.append('\t');
            sb.append(field);
        }
        String HEADER = sb.toString();
    }
    String getString(JSONObject json,
            String[] fields) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < HEADER_FIELDS.length; i++) {
            try {
                Object s = json.get(HEADER_FIELDS[i]);
                if (s == null) {
                    sb.append("null");
                } else {
                    sb.append(s);
                }

            } catch (Exception e) {
                e.printStackTrace();
                sb.append("null");
            } finally {
                sb.append("\t");
            }
        }
        return sb.toString();
    }

}
