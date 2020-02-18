package com.bnppf.upskilling.project.urlshortener.model;

import org.springframework.security.crypto.bcrypt.BCrypt;

import java.net.URL;
import java.util.Date;

public class UrlLink {

    private Long id;
    private URL UrlLong;
    private String UrlShortKey;
    private BCrypt Urlpassword;
    private String UrlTitle;
    private Double clickNumber;
    private Double maxClickNumber = Double.MAX_VALUE;
    private Date expirationDate;
    private Date creationDate;
    private Date updateDate;

    private AppUser appUser;

    /**
     * GETTER accessor for all attributes
     * @return
     */

    public Long getId() {
        return id;
    }

    public URL getUrlLong() {
        return UrlLong;
    }

    public String getUrlShortKey() {
        return UrlShortKey;
    }

    public BCrypt getUrlpassword() {
        return Urlpassword;
    }

    public String getUrlTitle() {
        return UrlTitle;
    }

    public Double getClickNumber() {
        return clickNumber;
    }

    public Double getMaxClickNumber() {
        return maxClickNumber;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    /**
     * SETTER accessor for all attributes
     * @return
     */

    public void setId(Long id) {
        this.id = id;
    }

    public void setUrlLong(URL urlLong) {
        UrlLong = urlLong;
    }

    public void setUrlShortKey(String urlShortKey) {
        UrlShortKey = urlShortKey;
    }

    public void setUrlpassword(BCrypt urlpassword) {
        Urlpassword = urlpassword;
    }

    public void setUrlTitle(String urlTitle) {
        UrlTitle = urlTitle;
    }

    public void setClickNumber(Double clickNumber) {
        this.clickNumber = clickNumber;
    }

    public void setMaxClickNumber(Double maxClickNumber) {
        this.maxClickNumber = maxClickNumber;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }
}
