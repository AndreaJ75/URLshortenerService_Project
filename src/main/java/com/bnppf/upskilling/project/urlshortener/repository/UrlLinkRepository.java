package com.bnppf.upskilling.project.urlshortener.repository;

import com.bnppf.upskilling.project.urlshortener.model.AppUser;
import com.bnppf.upskilling.project.urlshortener.model.UrlLink;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UrlLinkRepository extends PagingAndSortingRepository <UrlLink, Long> {

       Optional<UrlLink> findOneByUrlShortKey(String urlKeyToCheck);

       Page <UrlLink> findAllByAppUser(AppUser appUser,
                                       @PageableDefault(sort = { "updateDate",
               "maxClickNumber" }, value = 10) Pageable pageable);

       /**
        * FOR ADMIN : Get all UrlLinks having provided appuserName, or containing
        * UrlLong, or expiration date between defined start and end date
        * @param appUserName
        * @param urlLong
        * @param startDate
        * @param endDate
        * @param pageable
        * @return
        */

       Page<UrlLink> findAllByAppUserNameAndUrlLongContainsAndExpirationDateBetween(
               String appUserName,
               String urlLong,
               LocalDateTime startDate,
               LocalDateTime endDate,
               @PageableDefault(sort = { "updateDate",
                       "maxClickNumber" }, value = 10) Pageable pageable);

       /**
        * FOR USER : Get all its UrlLinks having provided
        * Urllong, or expiration start and date date
        * @param appUser
        * @param urlLong
        * @param startDate
        * @param endDate
        * @param pageable
        * @return
        */

       Page <UrlLink> findAllByAppUserAndUrlLongContainsAndExpirationDateBetween(
               AppUser appUser,
               String urlLong,
               LocalDateTime startDate,
               LocalDateTime endDate,
               @PageableDefault(sort = { "updateDate",
                       "maxClickNumber" }, value = 10) Pageable pageable);


       Page<UrlLink> findAll(@PageableDefault (sort = { "urlLong",
               "maxClickNumber" }, value = 10) Pageable pageable);


}
