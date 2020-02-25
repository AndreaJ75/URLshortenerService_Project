package com.bnppf.upskilling.project.urlshortener.repository;

import com.bnppf.upskilling.project.urlshortener.model.UrlLink;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlLinkRepository extends PagingAndSortingRepository <UrlLink, Long> {

       Optional<UrlLink> findOneByUrlShortKey(String urlKeyToCheck);

//public interface UrlLinkRepository extends JpaRepository<UrlLink, Long> {}
//       List<UrlLink> findByAppUserOrderByCreationDateAsc(AppUser appUser);
//
//       List<UrlLink> findByAppUserOrderByExpirationDateAsc(AppUser appUser);
//
//       List<UrlLink> findByAppUserOrderByClickNumberDesc(AppUser appUser);


}
