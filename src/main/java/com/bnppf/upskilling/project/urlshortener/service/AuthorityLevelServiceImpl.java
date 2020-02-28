package com.bnppf.upskilling.project.urlshortener.service;

import com.bnppf.upskilling.project.urlshortener.model.Authority;
import com.bnppf.upskilling.project.urlshortener.repository.AuthorityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorityLevelServiceImpl implements AuthorityService {

    private AuthorityRepository authorityRepository;

    public AuthorityLevelServiceImpl(AuthorityRepository authorityRepository){
        this.authorityRepository = authorityRepository;
    }

    @Override
    public List<Authority> getAuthorityLevel(Iterable<Long> authorityId) {
        return authorityRepository.findAllById(authorityId);

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
