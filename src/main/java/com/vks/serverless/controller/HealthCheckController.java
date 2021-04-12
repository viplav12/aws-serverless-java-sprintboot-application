package com.vks.serverless.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;


@RestController
public class HealthCheckController {

    private String content = null;

    /**
     * Method used to return the healthcheck (a html page with http status 200) which indicates that the application is up
     *
     * @return health check
     * @throws ParseException the parse exception
     */
    @GetMapping(value = "/healthcheck.html", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> getHealthCheck() throws ParseException {
        if (content == null) {
            content = getContent();
        }
        return new ResponseEntity<>(content, HttpStatus.OK);
    }

    /**
     * Gets the health check content
     *
     * @return content
     * @throws ParseException the parse exception
     */
    private String getContent() {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("<html><body>");
        addElement(strBuilder, "Health check", HttpStatus.OK.getReasonPhrase());
        strBuilder.append("</body></html>");
        return strBuilder.toString();
    }

    /**
     * Adds the element
     *
     * @param strBuilder the html string
     * @param key        the key to append
     * @param value      the value to append
     */
    private void addElement(StringBuilder strBuilder, String key, String value) {
        strBuilder.append("<p>");
        strBuilder.append(key);
        strBuilder.append(": <b>");
        strBuilder.append(value);
        strBuilder.append("</b></p>");
    }
}



