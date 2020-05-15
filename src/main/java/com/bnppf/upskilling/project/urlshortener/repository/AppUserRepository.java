package com.bnppf.upskilling.project.urlshortener.repository;

// TODO check how to retrieve appUser based on object not DB SQL select

import com.bnppf.upskilling.project.urlshortener.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long>{

    Optional<AppUser> findAppUserByUid(String uid);

    List<AppUser> findByFirstNameAndName(String firstName, String name);



    // Problème de creation de VIEW (ici AppUserAngl <=> Entité considéré) à partir
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
//                "SELECT COUNT(*) " +
//                        "FROM app_user ap " +
//                        "  JOIN app_user_authority ap_au ON ap.id = ap_au.app_user.id " +
//                        "ORDER BY au.app_user.id ASC",
//
//        nativeQuery = true
//    )
//    Page <AppUserAng> findAllAppUserWithHighestAuthority(Pageable pageable);

}
