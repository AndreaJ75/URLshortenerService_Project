package com.bnppf.upskilling.project.urlshortener.controller;

import com.bnppf.upskilling.project.urlshortener.model.UrlLink;
import com.bnppf.upskilling.project.urlshortener.service.UrlLinkService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/")
public class RedirectWithPassController {

    private UrlLinkService urlLinkService;

    public RedirectWithPassController(UrlLinkService urlLinkService) {
        this.urlLinkService = urlLinkService;
    }

    @GetMapping("/{urlKey}/{urlPass}")
    public ResponseEntity<String> getUrlRedirection(@PathVariable String urlKey,
                                                    @PathVariable(required = false) String urlPass) {
        Optional<UrlLink> urlLongRetrieved = urlLinkService.getUrlLongFromShortUrl(urlKey);

        if (!urlLongRetrieved.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            if (urlLongRetrieved.get().getUrlPassword() != null &&
                    (urlLongRetrieved.get().getUrlPassword().equals(urlPass))) {
                HttpHeaders headers = new HttpHeaders();
                headers.set("Location", urlLongRetrieved.get().getUrlLong());

                // Update Number of click for given clicked UrlshortLink :
                Double clickNumberForGivenUrl = urlLongRetrieved.get().getClickNumber() + 1D;
                urlLongRetrieved.get().setClickNumber(clickNumberForGivenUrl);
                urlLinkService.updateUrlLink(urlLongRetrieved.get());

                // Redirect to Long url
                return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).headers(headers).build();
            } else {
                // if wrong password provided do not follow the link and provide error message
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        }
    }
}
