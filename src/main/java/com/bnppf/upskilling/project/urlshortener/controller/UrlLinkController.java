package com.bnppf.upskilling.project.urlshortener.controller;

import com.bnppf.upskilling.project.urlshortener.model.UrlLink;
import com.bnppf.upskilling.project.urlshortener.service.UrlLinkService;
import com.bnppf.upskilling.project.urlshortener.vm.UrlFeedLink;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/api/urllinks")
public class UrlLinkController {

    /**
     * Declaration du service pour access aux données en Base de données via repository
     */
    private UrlLinkService urlLinkService;

    public UrlLinkController(UrlLinkService urlLinkService) { this.urlLinkService = urlLinkService;  }


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

//    @GetMapping("/all/{sortBy}")
//    public ResponseEntity<Page<UrlLink>> getAllUrlLinks(@RequestParam String sortBy) {
//        return ResponseEntity.ok(urlLinkService.getAllUrlLinks(sortBy));
//    }

//    @GetMapping("/all")
//    public ResponseEntity<List<UrlLink>> getUrlLinkList(){
//        return ResponseEntity.ok(urlLinkService.getUrlList());
//    }

//    @GetMapping("/listurlsortcreationdate/user")
//    public ResponseEntity<List<UrlLink>> getUrlLinkListForOneAppUserSortedByCreationDate(@RequestBody AppUser appUser){
//        return ResponseEntity.ok(urlLinkService.getUrlListForOneAppUserSortedByAscCreationDate(appUser));
//    }
//
//    @GetMapping("/listurlsortexpirationdate/user")
//    public ResponseEntity<List<UrlLink>> getUrlLinkListForOneAppUserSortedByExpirationDate(@RequestBody AppUser appUser){
//        return ResponseEntity.ok(urlLinkService.getUrlListForOneAppUserSortedByExpirationdate(appUser));
//    }
//
//    @GetMapping("/listurlsortclicknumberdesc/user")
//    public ResponseEntity<List<UrlLink>> getUrlLinkListForOneAppUserSortedByClickNumber(@RequestBody AppUser appUser){
//        return ResponseEntity.ok(urlLinkService.getUrlListForOneAppUserSortedByClickNumber(appUser));
//    }

//    @GetMapping("/all")
//    public ResponseEntity<List<UrlLink>> getAllUrlLinks(){
//        return ResponseEntity.ok(urlLinkService.getAllUrlLinks(0, 15, "creationDate"));
//    }

    @PutMapping()
    public ResponseEntity<UrlLink> updateAppUserUrl(@RequestBody UrlLink urlLink){

        UrlLink urlLinkToUpdate = urlLinkService.updateUrlLink(urlLink);

        if (urlLinkToUpdate != null) {
            return ResponseEntity.ok(urlLinkToUpdate);
        } else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping()
    public void deleteAppUserUrlLink(@PathParam("urlId") Long urlId){
        urlLinkService.deleteUrlLink(urlId);
    }

    @DeleteMapping("deleurls/user")
    public void deleteAppUserUrlLinkList(@PathParam("urlIdList") List<Long> urlIdList) {
        urlLinkService.deleteUrlLinkList(urlIdList);
    }

}
