package com.bnppf.upskilling.project.urlshortener.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name="app-user")
public class AppUser {

    @Id
    @SequenceGenerator(name="app-user-seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "app-user-seq")
    private Long id;
    @Column(name="UID")
    private String uid;
    @Column(name="name")
    private String name;
    @Column(name="firstName")
    private String firstName;
    @Column(name="email")
    private String email;
    @Column(name="creation-date")
    private Date creationDate;
    @Column(name="update-date")
    private Date updateDate;

    /**
     * Many user are linked to many authorization role
     * we generation a jointure table to show this relation
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "app-user-autorization",
            joinColumns = @JoinColumn(name = "app-user-id"),
            inverseJoinColumns = @JoinColumn(name = "authorization-id"))
    private Set<AuthorizationLevel> authorizationLevelSet;

    /**
     * One user can possess many UrlLink
     */
    @OneToMany(mappedBy = "appUser")
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

    public Set<AuthorizationLevel> getAuthorizationLevelList() {
        return authorizationLevelSet;
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

    public void setAuthorizationLevelSet(Set<AuthorizationLevel> authorizationLevelList) {
        this.authorizationLevelSet = authorizationLevelSet;
    }

    public void setUrlLinkSet(Set<UrlLink> urlLinkSet) {
        this.urlLinkSet = urlLinkSet;
    }
}
