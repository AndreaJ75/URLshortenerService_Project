package com.bnppf.upskilling.project.urlshortener.model;

import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.persistence.*;
import java.net.URL;
import java.util.Date;

@Entity
@Table(name="url-link")
public class UrlLink {


    @Id
    @SequenceGenerator(name="url-link-seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "url-link-seq")
    private Long id;
    @Column(name="url-long")
    private URL urlLong;
    @Column(name="url-short-key")
    private String urlShortKey;
    @Column(name="password")
    private BCrypt urlpassword;
    @Column(name="url-title")
    private String urlTitle;
    @Column(name="click-number")
    private Double clickNumber;
    @Column(name="max-click-number")
    private Double maxClickNumber = Double.MAX_VALUE;
    @Column(name="expiration-date")
    private Date expirationDate;
    @Column(name="creation-date")
    private Date creationDate;
    @Column(name="update-date")
    private Date updateDate;

    /**
     * Many UrlLink belong to one appUser
     */
    @ManyToOne
    private AppUser appUser;

    /**
     * GETTER accessor for all attributes
     * @return
     */

    public Long getId() {
        return id;
    }

    public URL getUrlLong() {
        return urlLong;
    }

    public String getUrlShortKey() {
        return urlShortKey;
    }

    public BCrypt getUrlpassword() {
        return urlpassword;
    }

    public String getUrlTitle() {
        return urlTitle;
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
        this.urlLong = urlLong;
    }

    public void setUrlShortKey(String urlShortKey) {
        this.urlShortKey = urlShortKey;
    }

    public void setUrlpassword(BCrypt urlpassword) {
        this.urlpassword = urlpassword;
    }

    public void setUrlTitle(String urlTitle) {
        this.urlTitle = urlTitle;
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


    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof UrlLink) || obj == null) {
            return false;
        } else {
            UrlLink urlLink = (UrlLink) obj;
            return (urlLink.id == this.id
                    && urlLink.urlShortKey.equals(this.urlShortKey)
                    && urlLink.urlpassword == this.urlpassword
                    && urlLink.urlLong.equals(this.urlLong));
        }
    }
}
