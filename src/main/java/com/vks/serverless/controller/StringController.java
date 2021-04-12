package com.vks.serverless.controller;

import com.vks.serverless.domain.InputRequest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Arrays;


/**
 * Controller to perform calculations on two numbers
 */
@RestController
@RequestMapping("input/")
public class StringController {
    String[] items = {"Google", "Oracle", "Microsoft", "Amazon", "Deloitte"};

    @PostMapping(value = "replace")
    public ResponseEntity<String> replaceString(@RequestBody InputRequest input) {
        if (Arrays.stream(items).anyMatch(input.getUserInput()::contains)) {
            return new ResponseEntity(input.getUserInput()+ "©", HttpStatus.OK);
        }
        return new ResponseEntity(input.getUserInput(), HttpStatus.OK);
    }
}
