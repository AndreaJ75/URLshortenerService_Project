package com.bnppf.upskilling.project.urlshortener.controller;

import com.bnppf.upskilling.project.urlshortener.model.UrlLink;
import com.bnppf.upskilling.project.urlshortener.service.UrlLinkService;
import com.bnppf.upskilling.project.urlshortener.vm.UrlRedirect;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@RequestMapping("/")
@CrossOrigin("*")
public class RedirectWithPassController {

    private UrlLinkService urlLinkService;

    public RedirectWithPassController(UrlLinkService urlLinkService) {
        this.urlLinkService = urlLinkService;
    }

    @GetMapping("/{urlKey}/{urlPass}")
    public ResponseEntity<UrlRedirect> getUrlRedirection(@PathVariable String urlKey,
                                                    @PathVariable(required = true) String urlPass) {
        Optional<UrlLink> urlLongRetrieved = urlLinkService.getUrlLongFromShortUrl(urlKey);

        if (!urlLongRetrieved.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            if (urlLongRetrieved.get().getUrlPassword() != null &&
                    (urlLongRetrieved.get().getUrlPassword().equals(urlPass))) {

                if (LocalDateTime.now()
                        .isAfter(urlLongRetrieved.get().getExpirationDate())
                        || (urlLongRetrieved.get().getClickNumber()
                        >= urlLongRetrieved.get().getMaxClickNumber())) {

                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                }

                // Update Number of click for given clicked UrlshortLink :
                Double clickNumberForGivenUrl = urlLongRetrieved.get().getClickNumber() + 1D;
                urlLongRetrieved.get().setClickNumber(clickNumberForGivenUrl);
                urlLinkService.updateUrlLink(urlLongRetrieved.get());

                // create JSON object urlRedirect to provide to Angular for redirection
                UrlRedirect urlRedirect = new UrlRedirect();
                urlRedirect.setUrlLongForRedirect(urlLongRetrieved.get().getUrlLong());
                // Redirect to Long url is done from Angular with provided UrlLong
                return ResponseEntity.ok(urlRedirect);
            } else {
                // if wrong password provided do not follow the link and provide error message
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        }
    }
}
