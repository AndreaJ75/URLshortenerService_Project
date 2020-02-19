package com.bnppf.upskilling.project.urlshortener.repository;

import com.bnppf.upskilling.project.urlshortener.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AppUserRepository extends JpaRepository <AppUser, Long>{

    @Query("SELECT appUser FROM AppUser appUser where appUser.UID = :UID")
    Optional<AppUser> findByUidEquals(String UID);


    @Query("SELECT appUserList FROM AppUser appUser where appUser.name = :name ORDER BY name ASC")
    Optional<List<AppUser>> findAppUsersSortByName(String name);

}
