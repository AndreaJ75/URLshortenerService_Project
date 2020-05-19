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


       Page <UrlLink> findAllByAppUserContainsOrUrlLongContainsOrExpirationDateBetween(
               String name,
               String urlLong,
               LocalDateTime startDate,
               LocalDateTime endDate,
               @PageableDefault(sort = { "updateDate",
                       "maxClickNumber" }, value = 10) Pageable pageable);


       Page <UrlLink> findAllByAppUserOrUrlLongContainsOrExpirationDateBetween(
               AppUser appUser,
               String urlLong,
               LocalDateTime startDate,
               LocalDateTime endDate,
               @PageableDefault(sort = { "updateDate",
                       "maxClickNumber" }, value = 10) Pageable pageable);


       Page<UrlLink> findAll(@PageableDefault (sort = { "urlLong",
               "maxClickNumber" }, value = 10) Pageable pageable);


}
