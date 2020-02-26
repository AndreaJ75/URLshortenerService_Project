package com.bnppf.upskilling.project.urlshortener.vm;

public class UrlString {

    private String urlLong;

    public String getUrlLong(){
        return urlLong;
    }

    public void setUrlLong(String UrlLong){
        this.urlLong = UrlLong;
    }


    @Override
    public String toString(){
        return urlLong;
    }
}
