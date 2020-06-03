package com.bnppf.upskilling.project.urlshortener.repository;

// TODO check how to retrieve appUser based on object not DB SQL select

import com.bnppf.upskilling.project.urlshortener.model.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long>{

    Optional<AppUser> findAppUserByUid(String uid);

    Page<AppUser> findAll(@PageableDefault(sort = { "uid"}, value = 10)
                                  Pageable pageable);

    Page <AppUser> findAllByNameContains(String name,
            @PageableDefault(sort = { "updateDate" }, value = 10) Pageable pageable);
}
