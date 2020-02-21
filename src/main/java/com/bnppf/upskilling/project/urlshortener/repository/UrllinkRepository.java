package com.bnppf.upskilling.project.urlshortener.repository;

import com.bnppf.upskilling.project.urlshortener.model.AppUser;
import com.bnppf.upskilling.project.urlshortener.model.UrlLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UrllinkRepository extends JpaRepository<UrlLink, Long> {

       List<UrlLink> findByAppUserOrderByCreationDateAsc(AppUser appUser);


}
