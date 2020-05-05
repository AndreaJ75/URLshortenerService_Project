package com.bnppf.upskilling.project.urlshortener.controller;

import com.bnppf.upskilling.project.urlshortener.exception.UrlExpireDateException;
import com.bnppf.upskilling.project.urlshortener.model.UrlLink;
import com.bnppf.upskilling.project.urlshortener.service.UrlLinkService;
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
public class RedirectController {

    private UrlLinkService urlLinkService;

    public RedirectController(UrlLinkService urlLinkService) {
        this.urlLinkService = urlLinkService;
    }

    @GetMapping("/{urlKey}")
    public ResponseEntity<String> getUrlRedirection(@PathVariable String urlKey)  {

        Optional<UrlLink> urlLongRetrieved = urlLinkService.getUrlLongFromShortUrl(urlKey);

        if (!urlLongRetrieved.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            // for UrlLink without password, we redirect directly to UrlLink
            if (urlLongRetrieved.get().getUrlPassword() == null ||
                urlLongRetrieved.get().getUrlPassword().isEmpty()) {
                if (LocalDateTime.now()
                        .isAfter(urlLongRetrieved.get().getExpirationDate())
                || (urlLongRetrieved.get().getClickNumber()
                        >= urlLongRetrieved.get().getMaxClickNumber())) {
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
            } else {
                // if password required => Open a window to request for Password
                HttpHeaders headers = new HttpHeaders();
                String urlForPassword = "http://localhost:4200/url-password/" + urlKey;
                headers.set("Location", urlForPassword);

                return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).headers(headers).build();
             }
        }
    }
}
