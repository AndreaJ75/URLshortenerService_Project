package com.bnppf.upskilling.project.urlshortener.repository;

import com.bnppf.upskilling.project.urlshortener.model.AppUser;
import com.bnppf.upskilling.project.urlshortener.vm.AppUserAng;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long>{

    Optional<AppUser> findAppUserByUid(String uid);

    // Problème de creation de VIEW (ici AppUserAngl) à partir
    // de AppUserRepository + AuthorityRepository
//
//    @Query(value=
//            "SELECT " +
//            "  ap.id as id, " +
//            "  ap.uid as uid," +
//            "  ap.name as completeName, " +
//            "  ap.email as email, " +
//            "  ap.creation_date as creationDate, " +
//            "  ap.update_date as updateDate," +
//            "  MIN(ap_au.authority_id) as highestAuthLevel " +
//            "  FROM app_user ap " +
//            "  JOIN app_user_authority ap_au ON ap.id = ap_au.app_user_id " +
//                    " GROUP BY ap.id, ap_au.app_user_id " +
//                    " ORDER BY ap_au.app_user_id ASC",
//        countQuery =
//                "SELECT COUT(*) " +
//                        "FROM app_user ap " +
//                        "  JOIN app_user_authority ap_au ON ap.id = ap_au.app_user.id " +
//                        "ORDER BY au.app_user.id ASC",
//
//        nativeQuery = true
//    )
//    Page <AppUserAng> findAllAppUserWithHighestAuthority(Pageable pageable);

}
