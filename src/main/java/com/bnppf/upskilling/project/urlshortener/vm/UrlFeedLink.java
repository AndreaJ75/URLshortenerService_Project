package com.bnppf.upskilling.project.urlshortener.vm;

import java.time.LocalDateTime;

public class UrlFeedLink {

    private String urlLong;

    private Double maxClickNumber;

    private LocalDateTime expirationDate;

    private String appPassword;

    public String getUrlLong(){
        return urlLong;
    }

    public Double getMaxClickNumber(){
        return maxClickNumber;
    }

    public LocalDateTime getExpirationDate(){
        return expirationDate;
    }

    public String getAppPassword(){
        return appPassword;
    }



}
