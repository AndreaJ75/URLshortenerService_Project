package com.bnppf.upskilling.project.urlshortener.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="app_user")
public class AppUser {

    @Id
    @SequenceGenerator(name="app_user_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "app_user_seq")
    private Long id;
    @Column(name="uid", unique = true)
    private String uid;
    @Column(name="firstName")
    private String firstName;
    @Column(name="name")
    private String name;
    @Column(name="email")
    private String email;
    @Column(name="creation_date")
    private LocalDateTime creationDate;
    @Column(name="update_date")
    private LocalDateTime updateDate;

    /**
     * Many user are linked to many authorization role
     * we generation a jointure table to show this relation
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "app_user_authority",
            joinColumns = @JoinColumn(name = "app_user_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id"))
    private List<Authority> authorities;

    /**
     * One user can possess many UrlLink
     */
    @OneToMany(mappedBy = "appUser", cascade = CascadeType.REMOVE)
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

    public String getFirstName() {
        return firstName;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public List<Authority> getAuthorities() {
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

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }

    public void setUrlLinkSet(Set<UrlLink> urlLinkSet) {
        this.urlLinkSet = urlLinkSet;
    }

}
