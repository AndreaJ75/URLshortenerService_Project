package com.bnppf.upskilling.project.urlshortener.repository;

import com.bnppf.upskilling.project.urlshortener.model.Authorization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorizationRepository extends JpaRepository<Authorization, Long> {
}
