package com.ky.backtracking.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uuid;

    private  String pNumber;

    @NotNull
    private boolean status;

    @NotNull
    private String lastLoginDate;

    private String totalPlayTime;

    public User() {

    }

    public User(String pNumber, boolean status, String date) {
        this.pNumber = pNumber;
        this.status = status;
        this.lastLoginDate = date;
    }

    public User(Long uuid, String pNumber, boolean status, String date, String totalPlayTime) {
        this.uuid = uuid;
        this.pNumber = pNumber;
        this.status = status;
        this.lastLoginDate = date;
        this.totalPlayTime = totalPlayTime;
    }

    public Long getUuid() {
        return uuid;
    }

    public boolean getStatus() {
        return status;
    }

    public String getLastLoginDate() {
        return lastLoginDate;
    }

    public String getpNumber() {
        return pNumber;
    }

    public String getTotalPlayTime() {
        return totalPlayTime;
    }

    public void setUuid(Long uuid) {
        this.uuid = uuid;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setLastLoginDate(String lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public void setpNumber(String pNumber) {
        this.pNumber = pNumber;
    }

    public void setTotalPlayTime(String totalPlayTime) {
        this.totalPlayTime = totalPlayTime;
    }
}
