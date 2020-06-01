package com.bnppf.upskilling.project.urlshortener.vm;

import com.bnppf.upskilling.project.urlshortener.model.AppUser;

import javax.persistence.Column;
import java.time.LocalDateTime;

public class AppUserAng {

    private AppUser appUser;

    private String highestAuthorityLevel;

    /**
     * GETTER accessor for all attributes
     * @return
     */

   public AppUser getAppUser() {
       return appUser;
    }



    public String getHighestAuthorityLevel() {
        return highestAuthorityLevel;
    }

    /**
     * SETTER accessor for all attributes
     * @return
     */

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public void setHighestAuthorityLevel(String highestAuthorityLevel) {
        this.highestAuthorityLevel = highestAuthorityLevel;
    }


}
