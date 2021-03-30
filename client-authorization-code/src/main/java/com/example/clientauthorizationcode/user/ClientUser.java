package com.example.clientauthorizationcode.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Entity
@Setter
@Getter
public class ClientUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String accessToken;
    private Calendar accessTokenValidity;
    private String refreshToken;

    @Transient
    private List<Entry> entries = new ArrayList<>();
}
