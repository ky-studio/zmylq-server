package com.ky.backtracking.model;

import javax.persistence.*;

@Entity
@Table(name = "qstnaire")
public class Qstnaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long qid;
    private Long uuid;
    private Float gamestars;
    private Float gamediffculties;
    private String suggesstionsdetails;
    private String uploadtime;

    public Qstnaire() {

    }

    public Qstnaire(Qstnaire qstnaire) {
        this.uuid = qstnaire.uuid;
        this.gamestars = qstnaire.gamestars;
        this.gamediffculties = qstnaire.gamediffculties;
        this.suggesstionsdetails = qstnaire.suggesstionsdetails;
        this.uploadtime = qstnaire.uploadtime;
    }

    public Long getQid() {
        return qid;
    }

    public void setQid(Long qid) {
        this.qid = qid;
    }

    public Long getUuid() {
        return uuid;
    }

    public void setUuid(Long uuid) {
        this.uuid = uuid;
    }

    public Float getGamestars() {
        return gamestars;
    }

    public void setGamestars(Float gamestars) {
        this.gamestars = gamestars;
    }

    public Float getGamediffculties() {
        return gamediffculties;
    }

    public void setGamediffculties(Float gamediffculties) {
        this.gamediffculties = gamediffculties;
    }

    public String getSuggesstionsdetails() {
        return suggesstionsdetails;
    }

    public void setSuggesstionsdetails(String suggesstionsdetails) {
        this.suggesstionsdetails = suggesstionsdetails;
    }

    public String getUploadtime() {
        return uploadtime;
    }

    public void setUploadtime(String uploadtime) {
        this.uploadtime = uploadtime;
    }
}
