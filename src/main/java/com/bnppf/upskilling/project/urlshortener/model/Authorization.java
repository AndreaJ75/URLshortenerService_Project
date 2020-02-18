package com.bnppf.upskilling.project.urlshortener.model;

import java.util.Set;

public class Authorization {

    private Long id;
    private AuthorizationLevel authorizationLevel;
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
