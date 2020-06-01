package com.bnppf.upskilling.project.urlshortener.repository;

// TODO check how to retrieve appUser based on object not DB SQL select

import com.bnppf.upskilling.project.urlshortener.model.AppUser;
import com.bnppf.upskilling.project.urlshortener.model.UrlLink;
import com.bnppf.upskilling.project.urlshortener.vm.AppUserAng;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long>{

    Optional<AppUser> findAppUserByUid(String uid);

    Page<AppUser> findAll(@PageableDefault(sort = { "uid"}, value = 10)
                                  Pageable pageable);
    @Query(nativeQuery = true,
            value=
            " SELECT DISTINCT u.id as id, " +
                    " u.creation_date as creationDate, " +
                    " u.email as email, " +
                    " u.first_name as firstName, " +
                    " u.name as name, " +
                    " u.uid as uid, " +
                    " u.update_date as updateDate, " +
                    " au.authority_level as highestAuthorityLevel" +
            " FROM app_user u, authority au, app_user_authority uau " +
            "  WHERE u.id = uau.app_user_id " +
            "  AND   au.id = uau.authority_id " +
                    "AND au.authority_level = " +
                    "( " +
                    "   SELECT au2.authority_level " +
                    "     FROM authority au2 " +
                    "     WHERE au2.id = 1  " +
                    " ) " +
            " GROUP BY u.id, au.authority_level " +
            " ORDER BY u.id ASC "
//            ,
//             countQuery =
//                "SELECT COUNT(DISTINCT u.id, u.creation_date, " +
//                        " u.email, u.first_name, u.name, " +
//                        " u.uid, u.update_date, " +
//                        " au.authority_level) " +
//                        " FROM app_user u, authority au, app_user_authority uau " +
//                        "  WHERE u.id = uau.app_user_id " +
//                        "  AND   au.id = uau.authority_id " +
//                        "  AND au.authority_level = " +
//                        " ( " +
//                        "   SELECT au2.authority_level " +
//                        "     FROM authority au2 " +
//                        "     WHERE au2.id = 1  " +
//                        " ) " +
//                        " GROUP BY u.id, au.authority_level " +
//                        " ORDER BY u.id ASC "
    )
    Page <AppUserAng> findAllAppUserWithHighestAuthority(Pageable pageable);

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
//                    " ORDER BY ap_au.app_user_id ASC  ",
//        countQuery =
//                "SELECT COUNT(*) " +
//                        " FROM app_user ap " +
//                        "  JOIN app_user_authority ap_au ON ap.id = ap_au.app_user.id " +
//                        " ORDER BY au.app_user.id ASC ",
//
//        nativeQuery = true
//    )
//    Page <AppUserAng> findAllAppUserWithHighestAuthority(Pageable pageable);

}
