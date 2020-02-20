package com.bnppf.upskilling.project.urlshortener.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Authority {

    @Id
    @SequenceGenerator(name="app_user_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "app_user_seq")
    private Long id;

    @Column(name="authorization_level")
    @Enumerated(EnumType.STRING)
    private AuthorizationLevel authorizationLevel;

    /**
     * Many user are linked to many authorization role
     * we link the set of appuser to the related set of Authority level
     */
    @ManyToMany(mappedBy = "authorities")
    private Set<AppUser> appUserSet;

    /**
     * GETTER accessor for all attributes
     * @return
     */

    public Long getId() {
        return id;
    }

    public AuthorizationLevel getAuthorizationLevel() {
        return authorizationLevel;
    }

    public Set<AppUser> getAppUserSet() {
        return appUserSet;
    }

    /**
     * SETTER accessor for all attributes
     * @return
     */
    public void setId(Long id) {
        this.id = id;
    }

    public void setAuthorizationLevel(AuthorizationLevel authorizationLevel) {
        this.authorizationLevel = authorizationLevel;
    }

    public void setAppUserSet(Set<AppUser> appUserSet) {
        this.appUserSet = appUserSet;
    }
}
