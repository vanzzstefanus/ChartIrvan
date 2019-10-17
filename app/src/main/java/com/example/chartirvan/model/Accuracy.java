package com.example.chartirvan.model;

public class Accuracy {

    private String Label;
    private int iteracy;
    private int accuracy;

    public Accuracy(){

    }

    public String getLabel() {
        return Label;
    }

    public void setLabel(String label) {
        Label = label;
    }

    public int getIteracy() {
        return iteracy;
    }

    public void setIteracy(int iteracy) {
        this.iteracy = iteracy;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }
}
