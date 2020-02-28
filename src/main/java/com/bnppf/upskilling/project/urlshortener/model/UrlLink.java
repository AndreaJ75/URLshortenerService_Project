package com.bnppf.upskilling.project.urlshortener.model;

//import org.springframework.security.crypto.bcrypt.BCrypt;


import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name="url_link")
public class UrlLink {


    @Id
    @SequenceGenerator(name="url_link_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "url_link_seq")
    private Long id;

    @Column(name="url_long")
    private String urlLong;
    @Column(name="url_short_key")
    private String urlShortKey;
    @Column(name="url_password")
    private String urlPassword;
    @Column(name="click_number")
    private Double clickNumber;
    @Column(name="max_click_number")
    private Double maxClickNumber = Double.MAX_VALUE;
    @Column(name="expiration_date")
    private LocalDateTime expirationDate;
    @Column(name="creation_date")
    private LocalDateTime creationDate;
    @Column(name="update_date")
    private LocalDateTime updateDate;

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

    public String getUrlLong() {
        return urlLong;
    }

    public String getUrlShortKey() {
        return urlShortKey;
    }

    public String getUrlPassword() {
        return urlPassword;
    }

    public Double getClickNumber() {
        return clickNumber;
    }

    public Double getMaxClickNumber() {
        return maxClickNumber;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public LocalDateTime getUpdateDate() {
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

    public void setUrlLong(String urlLong) {
        this.urlLong = urlLong;
    }

    public void setUrlShortKey(String urlShortKey) {
        this.urlShortKey = urlShortKey;
    }

    public void setUrlPassword(String urlPassword) {
        this.urlPassword = urlPassword;
    }

    public void setClickNumber(Double clickNumber) {
        this.clickNumber = clickNumber;
    }

    public void setMaxClickNumber(Double maxClickNumber) {
        this.maxClickNumber = maxClickNumber;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
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
                    && urlLink.urlPassword == this.urlPassword
                    && urlLink.urlLong.equals(this.urlLong));
        }
    }
}
