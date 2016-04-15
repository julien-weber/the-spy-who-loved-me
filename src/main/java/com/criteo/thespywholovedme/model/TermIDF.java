package com.criteo.thespywholovedme.model;


public  class TermIDF {
    private static final double DELTA = 1e-15;
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

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof TermIDF))
            return false;
        if (obj == this)
            return true;
  
        TermIDF rhs = (TermIDF) obj;
        return term.equals(rhs.getTerm()) && Math.abs(idf - rhs.getIdf()) < DELTA;
    }
}

