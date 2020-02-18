package com.bnppf.upskilling.project.urlshortener.service;

import com.bnppf.upskilling.project.urlshortener.model.Authorization;

public interface AuthorizationService {


    /**
     * Creation of an Authorization level
     * @param authorizationToBeCreated
     * @return autorization created
     */
    Authorization createAuthorization(Authorization authorizationToBeCreated);


    /**
     * Authorization to be updated
     * @param authorizationToBeUpdated
     * @return authorization updated
     */
    Authorization updateAuthorization(Authorization authorizationToBeUpdated);

}
