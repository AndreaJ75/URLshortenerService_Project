package com.bnppf.upskilling.project.urlshortener.controller;

import com.bnppf.upskilling.project.urlshortener.configuration.SecurityConfig;
import com.bnppf.upskilling.project.urlshortener.configuration.utils.SecurityUtils;
import com.bnppf.upskilling.project.urlshortener.model.AppUser;
import com.bnppf.upskilling.project.urlshortener.model.AuthorityLevel;
import com.bnppf.upskilling.project.urlshortener.model.UrlLink;
import com.bnppf.upskilling.project.urlshortener.service.AppUserService;
import com.bnppf.upskilling.project.urlshortener.service.UrlLinkService;
import com.bnppf.upskilling.project.urlshortener.vm.UrlFeedLink;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/urlLinks")
@CrossOrigin("*")
public class UrlLinkController {

    /**
     * Declaration du service pour access aux données en Base de données via repository
     */
    private UrlLinkService urlLinkService;
    private AppUserService appUserService;

    public UrlLinkController(UrlLinkService urlLinkService,
                             AppUserService appUserService) {
        this.urlLinkService = urlLinkService;
        this.appUserService = appUserService;}

    /**
     * CREATE URLLink for GUEST
     * @param urlLinkToBeCreated
     * @return
     */

    @PostMapping("/guest")
    public ResponseEntity<UrlLink> createUrlLink(@RequestBody UrlFeedLink urlLinkToBeCreated) {
        return ResponseEntity.ok(urlLinkService.createUrlLink(urlLinkToBeCreated));
    }

    /**
     * CREATE URLLink for USER
     * @param urlLinkToBeCreated
     * @return
     */

    @PostMapping("/user")
    public ResponseEntity<UrlLink> createUrlForUser(@RequestBody UrlFeedLink urlLinkToBeCreated) {
        /**
         * Check if token is OK for considered User
         * If so, create the corresponding URLLink -> createUrlLinkFor User
         * Else sout "Wrong token"
         */
        return ResponseEntity.ok(urlLinkService.createUrlForUser(urlLinkToBeCreated));
    }


    @GetMapping("/user/getById/{urlLinkId}")
    public ResponseEntity<UrlLink> getUrlLinkById(@PathVariable Long urlLinkId) {

        Optional<UrlLink> urlLinkOptional = urlLinkService.getUrlLinkfromUrlId(urlLinkId);
        if (urlLinkOptional.isPresent()) {
            return ResponseEntity.ok(urlLinkOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/user/getsorted")
    public ResponseEntity<Page<UrlLink>> getUrlLinksSortedBySortCriteriaandOrder(
            @PageableDefault(size=10, page = 0, sort = {"updateDate"},
                    direction = Sort.Direction.ASC)
            Pageable pageable) {
        return ResponseEntity.ok(urlLinkService.getUrlLinksSortedBySortCriteriaandOrder(pageable));
    }

    @GetMapping("/user/getFiltered")
    public ResponseEntity<Page<UrlLink>> getUrlLinkFilteredForOneUser(
            @RequestParam(value="urlLong", required = false) String urlLong,
            @RequestParam(value="startDate", required = false)
                    String startDate,
            @RequestParam(value="endDate", required = false)
                    String endDate,

            @PageableDefault(size=10, page = 0, sort = {"updateDate"},
                    direction = Sort.Direction.ASC)
                    Pageable pageable){
        return ResponseEntity.ok(urlLinkService.getUrlLinkFilteredForOneUser(
                urlLong,
                startDate,
                endDate,
                pageable));
    }

    // Get all Urls from all users (for Admin only)
    @GetMapping("/admin/urlsAll")
    public ResponseEntity<Page<UrlLink>> getUrlLinksPage (
            @PageableDefault(size=10, page = 0, sort = {"updateDate"},
                    direction = Sort.Direction.ASC)
                    Pageable pageable) {
        return ResponseEntity.ok(urlLinkService.getUrlPageAllSorted(pageable));
    }

    // Get all Urls filtered from all users (for Admin only)
    @GetMapping("admin/getFiltered")
    public ResponseEntity<Page<UrlLink>> getUrlLinkFilteredOnAppUserForAdmin(
        @RequestParam(value="name", required = false) String name,
        @RequestParam(value="urlLong", required = false) String urlLong,
        @RequestParam(value="startDate", required=false) String startDate,
        @RequestParam(value ="endDate", required = false) String endDate,
            @PageableDefault(size=10, page = 0, sort = {"updateDate"},
                    direction = Sort.Direction.DESC) Pageable pageable){

        return ResponseEntity.ok(urlLinkService
                .getUrlLinkFilteredOnAppUserForAdmin(name, urlLong,
                startDate, endDate,
                pageable));

    }

    // Update urlLink only allowed on 3 attributes (check in service)
    @PutMapping("/user")
    public ResponseEntity<UrlLink> updateAppUserUrl(@RequestBody UrlFeedLink urlLinkToUpdate){

        Optional<UrlLink> urlLinkOptional =
                urlLinkService.getUrlLinkfromUrlId(urlLinkToUpdate.getId());
        if (urlLinkOptional.isPresent())  {
            // prevent from updating urlLink not linked to authenticated user
            // (if direct access done by appUser with urlId)
             Optional<AppUser> appUserOptional=appUserService
                     .getAppUserByUID(SecurityUtils.getCurrentUserLogin());
             if (appUserOptional.isPresent() &&
                appUserOptional.get() == urlLinkOptional.get().getAppUser()
             || (appUserOptional.get().getAuthorities()
                     .get(0).getAuthorityLevel() == AuthorityLevel.ROLE_ADMIN
             ||  appUserOptional.get().getAuthorities()
                     .get(1).getAuthorityLevel() == AuthorityLevel.ROLE_ADMIN)) {
                 return ResponseEntity.ok(urlLinkService.updateUrlFeedLink(urlLinkToUpdate));
             } else {
                 return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
             }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/user/deleteUrl/{urlId}")
    public void deleteAppUserUrlLink(@PathVariable Long urlId){
        urlLinkService.deleteUrlLink(urlId);
    }

    //=> To be tested (does not work yet)
    @DeleteMapping("/user/deleteUrls/{urlIdList}")
    public void deleteAppUserUrlLinkList(@PathVariable List<Long> urlIdList) {
        urlLinkService.deleteUrlLinkList(urlIdList);
    }

}
