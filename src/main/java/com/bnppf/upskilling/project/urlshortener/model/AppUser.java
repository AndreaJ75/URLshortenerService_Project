package com.bnppf.upskilling.project.urlshortener.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name="app_user")
public class AppUser {

    @Id
    @SequenceGenerator(name="app_user_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "app_user_seq")
    private Long id;
    @Column(name="UID", unique = true)
    private String uid;
    @Column(name="name")
    private String name;
    @Column(name="firstName")
    private String firstName;
    @Column(name="email")
    private String email;
    @Column(name="creation_date")
    private Date creationDate;
    @Column(name="update_date")
    private Date updateDate;

    /**
     * Many user are linked to many authorization role
     * we generation a jointure table to show this relation
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "app_user_autorization",
            joinColumns = @JoinColumn(name = "app_user_id"),
            inverseJoinColumns = @JoinColumn(name = "authorization_id"))
    private Set<Authority> authorities;

    /**
     * One user can possess many UrlLink
     */
    @OneToMany(mappedBy = "appUser")
    @JsonIgnore
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

    public Set<Authority> getAuthorities() {
        return authorities;
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

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public void setUrlLinkSet(Set<UrlLink> urlLinkSet) {
        this.urlLinkSet = urlLinkSet;
    }

    /**
     * Override on equals method to check equality between 2 AppUser
     * @param obj
     * @return
     */
//    @Override
//    public boolean equals(Object obj) {
//        return this.uid.equals((AppUser) obj.);
//    }
}
