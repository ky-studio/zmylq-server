package com.ky.backtracking.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "achievement")
public class Achievement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long aid;

    @NotNull
    private Long uuid;
    @NotNull
    private boolean homework;
    @NotNull
    private boolean bedroom;
    @NotNull
    private boolean wajue;
    @NotNull
    private boolean chuji;
    @NotNull
    private boolean gaoji;
    @NotNull
    private boolean zhiwang;

    private Float njmaxtime;
    @NotNull
    private boolean versatile;
    @NotNull
    private boolean programer;
    @NotNull
    private boolean dredger;
    @NotNull
    private boolean childhood;
    @NotNull
    private boolean university;
    @NotNull
    private Float chtime;
    @NotNull
    private Float untime;

    public Achievement() {

    }

    public Achievement(Long uuid) {
        this.uuid = uuid;
        this.homework = false;
        this.bedroom = false;
        this.wajue = false;
        this.chuji = false;
        this.gaoji = false;
        this.zhiwang = false;
        this.njmaxtime = 0f;
        this.childhood = false;
        this.university = false;
        this.chtime = 0f;
        this.untime = 0f;
    }

    public Achievement(Long uuid, boolean homework, boolean bedroom, boolean wajue, boolean chuji,
                       boolean gaoji, boolean zhiwang, Float njmaxtime, boolean versatile, boolean programer,
                       boolean dredger, boolean childhood, boolean university, Float chtime, Float untime) {
        this.uuid = uuid;
        this.homework = homework;
        this.bedroom = bedroom;
        this.wajue = wajue;
        this.chuji = chuji;
        this.gaoji = gaoji;
        this.zhiwang = zhiwang;
        this.njmaxtime = njmaxtime;
        this.versatile = versatile;
        this.programer = programer;
        this.dredger = dredger;
        this.childhood = childhood;
        this.university = university;
        this.chtime = chtime;
        this.untime = untime;
    }

    public Long getAid() {
        return aid;
    }

    public void setAid(Long aid) {
        this.aid = aid;
    }

    public Long getUuid() {
        return uuid;
    }

    public void setUuid(Long uuid) {
        this.uuid = uuid;
    }

    public void setHomework(boolean homework) {
        this.homework = homework;
    }

    public boolean isHomework() {
        return homework;
    }

    public boolean isBedroom() {
        return bedroom;
    }

    public void setBedroom(boolean bedroom) {
        this.bedroom = bedroom;
    }

    public boolean isWajue() {
        return wajue;
    }

    public void setWajue(boolean wajue) {
        this.wajue = wajue;
    }

    public boolean isChuji() {
        return chuji;
    }

    public void setChuji(boolean chuji) {
        this.chuji = chuji;
    }

    public boolean isGaoji() {
        return gaoji;
    }

    public void setGaoji(boolean gaoji) {
        this.gaoji = gaoji;
    }

    public boolean isZhiwang() {
        return zhiwang;
    }

    public void setZhiwang(boolean zhiwang) {
        this.zhiwang = zhiwang;
    }

    public Float getNjmaxtime() {
        return njmaxtime;
    }

    public void setNjmaxtime(Float njmaxtime) {
        this.njmaxtime = njmaxtime;
    }

    public boolean isVersatile() {
        return versatile;
    }

    public void setVersatile(boolean versatile) {
        this.versatile = versatile;
    }

    public boolean isProgramer() {
        return programer;
    }

    public void setProgramer(boolean programer) {
        this.programer = programer;
    }

    public boolean isDredger() {
        return dredger;
    }

    public void setDredger(boolean dredger) {
        this.dredger = dredger;
    }

    public boolean isChildhood() {
        return childhood;
    }

    public void setChildhood(boolean childhood) {
        this.childhood = childhood;
    }

    public boolean isUniversity() {
        return university;
    }

    public void setUniversity(boolean university) {
        this.university = university;
    }

    public Float getChtime() {
        return chtime;
    }

    public void setChtime(Float chtime) {
        this.chtime = chtime;
    }

    public Float getUntime() {
        return untime;
    }

    public void setUntime(Float untime) {
        this.untime = untime;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
