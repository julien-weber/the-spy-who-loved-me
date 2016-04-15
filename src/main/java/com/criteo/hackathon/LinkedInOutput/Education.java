package com.criteo.hackathon.LinkedInOutput;

public class Education {

    private String name;
    private String degree;
    private String description;

    public Education(String name, String degree, String description) {
        this.name = name;
        this.degree = degree;
        this.description = description;
    }

    @Override
    public String toString() {
        return this.name + " " + this.degree + " " + description + " ";
    }
}
