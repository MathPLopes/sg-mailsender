package com.example.sendgridemail.dto;

public record EmailDTO(
        String name,
        String from,
        String email,
        String phone,
        String date,
        String time,
        String to) {

}
