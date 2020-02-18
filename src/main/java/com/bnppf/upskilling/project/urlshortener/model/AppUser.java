package com.bnppf.upskilling.project.urlshortener.model;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class AppUser {

    private Long id;
    private String uid;
    private String name;
    private String firstName;
    private String email;
    private Date creationDate;
    private Date updateDate;

    private List<AuthorizationLevel> authorizationLevelList;
    private Set<UrlLink> urlLinkSet;


    /**
     * GETTER accessor for all attributes
     * @return
     */

    public Long getId() {
        return id;
    }

    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getEmail() {
        return email;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public List<AuthorizationLevel> getAuthorizationLevelList() {
        return authorizationLevelList;
    }

    public Set<UrlLink> getUrlLinkSet() {
        return urlLinkSet;
    }

    /**
     * SETTER accessor for all attributes
     * @return
     */

    public void setId(Long id) {
        this.id = id;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public void setAuthorizationLevelList(List<AuthorizationLevel> authorizationLevelList) {
        this.authorizationLevelList = authorizationLevelList;
    }

    public void setUrlLinkSet(Set<UrlLink> urlLinkSet) {
        this.urlLinkSet = urlLinkSet;
    }
}
