package com.example.MyBookShopApp.security;

import lombok.Value;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "sms_keys")
public class SmsCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private LocalDateTime exploreTime;

    public SmsCode(String code, Integer exploreIn) {
        this.code = code;
        this.exploreTime = LocalDateTime.now().plusSeconds(exploreIn);
    }

    public SmsCode() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getExploreTime() {
        return exploreTime;
    }

    public void setExploreTime(LocalDateTime exploreTime) {
        this.exploreTime = exploreTime;
    }

    public Boolean isExplored(){
        return LocalDateTime.now().isAfter(exploreTime);
    }
}
