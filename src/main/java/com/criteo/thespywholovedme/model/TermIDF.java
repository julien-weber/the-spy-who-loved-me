package com.criteo.thespywholovedme.model;


public  class TermIDF {
    private String term;
    private Double idf;

    public TermIDF(String term, Double idf) {
        this.term = term;
        this.idf = idf;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public void setIdf(Double idf) {
        this.idf = idf;
    }

    public Double getIdf() {
        return idf;
    }
}

