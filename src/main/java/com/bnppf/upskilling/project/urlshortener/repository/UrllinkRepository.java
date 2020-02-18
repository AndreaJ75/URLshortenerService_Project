package com.bnppf.upskilling.project.urlshortener.repository;

import com.bnppf.upskilling.project.urlshortener.model.UrlLink;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrllinkRepository extends JpaRepository<UrlLink, Long> {
}
