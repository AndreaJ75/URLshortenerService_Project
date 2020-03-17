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

import java.util.Optional;

@Controller
@RequestMapping("/")
public class RedirectController {

    private UrlLinkService urlLinkService;

    public RedirectController(UrlLinkService urlLinkService) {
        this.urlLinkService = urlLinkService;
    }

    @GetMapping("/{urlKey}")
    public ResponseEntity<String> getUrlRedirection (@PathVariable String urlKey){
        Optional<UrlLink> urlLongRetrieved = urlLinkService.getUrlLongFromShortUrl(urlKey);

        if (!urlLongRetrieved.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("Location", urlLongRetrieved.get().getUrlLong());

        // Update Number of click for given clicked UrlshortLink :
        Double clickNumberForGivenUrl = urlLongRetrieved.get().getClickNumber() + 1D;
        urlLongRetrieved.get().setClickNumber(clickNumberForGivenUrl);
        urlLinkService.updateUrlLink(urlLongRetrieved.get());

        // Redirect to Long url
        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).headers(headers).build();
    }
}
