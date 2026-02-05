package com.example.sendgridemail.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sendgridemail.dto.EmailDTO;
import com.example.sendgridemail.service.EmailService;

@RestController
@RequestMapping("/email")
public class EmailController {

    private final EmailService service;

    public EmailController(EmailService service) {
        this.service = service;
    }

    @PostMapping
    public void sendEmail(@RequestBody EmailDTO email) {
        service.sendEmail(email);
    }
}
