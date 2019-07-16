package com.ky.zmylq.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int uuid;

    @NotNull
    private boolean status;

    @NotNull
    private String lastLoginDate;

    public User() {

    }

    public User(boolean status, String date) {
        this.status = status;
        this.lastLoginDate = date;
    }

    public User(int uuid, boolean status, String date) {
        this.uuid = uuid;
        this.status = status;
        this.lastLoginDate = date;
    }

    public int getUuid() {
        return uuid;
    }

    public boolean getStatus() {
        return status;
    }

    public String getLastLoginDate() {
        return lastLoginDate;
    }

    public void setUuid(int uuid) {
        this.uuid = uuid;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setLastLoginDate(String lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }
}
