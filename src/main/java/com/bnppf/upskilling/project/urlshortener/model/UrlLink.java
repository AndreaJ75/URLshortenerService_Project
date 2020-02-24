package com.bnppf.upskilling.project.urlshortener.model;

//import org.springframework.security.crypto.bcrypt.BCrypt;


import javax.persistence.*;
import java.net.URL;
import java.util.Date;

@Entity
@Table(name="url_link")
public class UrlLink {


    @Id
    @SequenceGenerator(name="url_link_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "url_link_seq")
    private Long id;

    @Column(name="url_long")
    private URL urlLong;
    @Column(name="url_short_key")
    private String urlShortKey;
    @Column(name="password")
    private String urlpassword;
    @Column(name="url_title")
    private String urlTitle;
    @Column(name="click_number")
    private Double clickNumber;
    @Column(name="max_click_number")
    private Double maxClickNumber = Double.MAX_VALUE;
    @Column(name="expiration_date")
    private Date expirationDate;
    @Column(name="creation_date")
    private Date creationDate;
    @Column(name="update_date")
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

    public String getUrlpassword() {
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

    public void setUrlpassword(String urlpassword) {
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
