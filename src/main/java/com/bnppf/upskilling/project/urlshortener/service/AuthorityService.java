package com.bnppf.upskilling.project.urlshortener.service;

import com.bnppf.upskilling.project.urlshortener.model.Authority;

import java.util.Optional;


public interface AuthorityService  {

    Optional<Authority> getAuthorityLevel(Long authorityId);

    Authority createAuthoritylevel(Authority authority);

    Authority updateAuthorityLevel(Authority authority);

    void deleteAuthorityLevel(Long authorityId);

}
