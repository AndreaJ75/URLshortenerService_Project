package com.bnppf.upskilling.project.urlshortener.service;

import com.bnppf.upskilling.project.urlshortener.model.Authority;
import com.bnppf.upskilling.project.urlshortener.repository.AuthorityRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AuthorityLevelServiceImpl implements AuthorityLevelService {

    private AuthorityRepository authorityRepository;

    public AuthorityLevelServiceImpl(AuthorityRepository authorityRepository){
        this.authorityRepository = authorityRepository;
    }

    @Override
    public Optional<Authority> getAuthorityLevel(Long authorityId) {
        Optional<Authority> authorityOptional = authorityRepository.findById(authorityId);
        return authorityOptional;

    }

    @Override
    public Authority createAuthoritylevel(Authority authority) {
        return authorityRepository.save(authority);
    }

    @Override
    public Authority updateAuthorityLevel(Authority authority) {
        return authorityRepository.save(authority);
    }

    @Override
    public void deleteAuthorityLevel(Long authorityId) {
        authorityRepository.deleteById(authorityId);
    }
}
