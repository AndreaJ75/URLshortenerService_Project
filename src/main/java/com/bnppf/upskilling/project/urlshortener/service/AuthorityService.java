package com.bnppf.upskilling.project.urlshortener.service;

import com.bnppf.upskilling.project.urlshortener.model.Authority;

import java.util.List;


public interface AuthorityService  {

    List<Authority> getAuthorityLevel(Iterable<Long> authorityId);

    Authority createAuthoritylevel(Authority authority);

    Authority updateAuthorityLevel(Authority authority);

    void deleteAuthorityLevel(Long authorityId);

}
