package com.bnppf.upskilling.project.urlshortener.service;

import com.bnppf.upskilling.project.urlshortener.model.Authority;

public interface AuthorizationService {


    /**
     * Creation of an Authority level
     * @param authorityToBeCreated
     * @return autorization created
     */
    Authority createAuthorization(Authority authorityToBeCreated);


    /**
     * Authority to be updated
     * @param authorityToBeUpdated
     * @return authorization updated
     */
    Authority updateAuthorization(Authority authorityToBeUpdated);

}
