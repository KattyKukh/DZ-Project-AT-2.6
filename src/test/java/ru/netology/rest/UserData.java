package ru.netology.rest;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserData {
    private String login;
    private String password;
    private String status;
}