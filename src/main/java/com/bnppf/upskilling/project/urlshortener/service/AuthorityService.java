package com.bnppf.upskilling.project.urlshortener.service;

import com.bnppf.upskilling.project.urlshortener.model.Authority;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface AuthorityService  {

    Optional<Authority> getAuthorityLevel(Long authorityId);

    Authority createAuthoritylevel(Authority authority);

    Authority updateAuthorityLevel(Authority authority);

    void deleteAuthorityLevel(Long authorityId);

}
