package com.waflo.cooltimediaplattform.backend.beans;

public enum MediaType {
    MOVIE("Film"), AUDIO("Audio"), DOCUMENT("Dokument");
    private MediaType(String l){this.label=l;}
    private String label;

    public String getLabel() {
        return label;
    }
    public void setLabel(String label){
        //no-op
    }
}
