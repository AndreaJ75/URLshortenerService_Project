package com.bnppf.upskilling.project.urlshortener.controller;

import com.bnppf.upskilling.project.urlshortener.model.UrlLink;
import com.bnppf.upskilling.project.urlshortener.service.UrlLinkService;
import com.bnppf.upskilling.project.urlshortener.vm.UrlString;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/api/bnppf/urlservice/urllink")
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
    public ResponseEntity<UrlLink> createUrlLink(@RequestBody UrlString urlLinkToBeCreated) {
        return ResponseEntity.ok(urlLinkService.createUrlLink(urlLinkToBeCreated));
    }


    /**
     * CREATE URLLink for USER
     * @param urlLinkToBeCreated
     * @return
     */
    @PostMapping("/user")
    public ResponseEntity<UrlLink> createUrlForUser(@RequestBody UrlString urlLinkToBeCreated) {
        /**
         * Check if token is OK for considered User
         * If so, create the corresponding URLLink -> createUrlLinkFor User
         * Else sout "Wrong token"
         */
        return ResponseEntity.ok(urlLinkService.createUrlForUser(urlLinkToBeCreated));
    }


//    @PostMapping
//    public ResponseEntity<UrlLink> createUrlLink(@RequestBody UrlLink urlLinkToBeCreated) {
//        return ResponseEntity.ok(urlLinkService.createUrl(urlLinkToBeCreated));
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

    @GetMapping("/all")
    public ResponseEntity<List<UrlLink>> getAllUrlLinks(){
        return ResponseEntity.ok(urlLinkService.getAllUrlLinks(0, 15, "creationDate"));
    }

    @PutMapping("updateurl/user")
    public ResponseEntity<UrlLink> updateAppUserUrl(@RequestBody UrlLink urlLink){

        UrlLink urlLinkToUpdate = urlLinkService.updateUrlLink(urlLink);

        if (urlLinkToUpdate != null) {
            return ResponseEntity.ok(urlLinkToUpdate);
        } else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("updateurls/user")
    public ResponseEntity<List<UrlLink>> updateAppUserUrls(@RequestBody List<UrlLink> urlLinkList){

        List<UrlLink> urlLinkListToUpdate = urlLinkService.updateUrlLinkList(urlLinkList);

        if (urlLinkListToUpdate != null) {
            return ResponseEntity.ok(urlLinkListToUpdate);
        } else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("deleteurl/user")
    public ResponseEntity<HttpStatus> deleteAppUserUrlLink(@PathParam("urlId") Long urlId){

        boolean urlDeleteStatus = urlLinkService.deleteUrlLink(urlId);

        if(urlDeleteStatus == true) {
            return ResponseEntity.ok().body(null);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("deleurls/user")
    public ResponseEntity<List<HttpStatus>> deleteAppUserUrlLinkList(@PathParam("urlIdList") List<Long> urlIdList) {

        List<Boolean> urlDeleteListStatus = urlLinkService.deleteUrlLinkList(urlIdList);
        ResponseEntity <List<HttpStatus>> urlDeleteStatusList = null;

        for (int i = 0; i < urlDeleteListStatus.size(); i++) {
            if (urlDeleteListStatus.get(i) == true) {
                urlDeleteStatusList = ResponseEntity.ok().body(null);
            } else
                urlDeleteStatusList = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return urlDeleteStatusList;
    }

}
