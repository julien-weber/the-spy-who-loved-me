package com.criteo.hackathon.LinkedInOutput;

public class Experience {

    private String company;
    private String title;
    private String description;

    public Experience(String company, String title, String description) {
        this.company = company;
        this.title = title;
        this.description = description;
    }

    @Override
    public String toString() {
        return this.company + " " + this.title + " " + this.description + " ";
    }
}
