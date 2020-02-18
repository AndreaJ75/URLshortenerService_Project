package com.bnppf.upskilling.project.urlshortener.repository;

import com.bnppf.upskilling.project.urlshortener.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository <AppUser, Long>{
}
