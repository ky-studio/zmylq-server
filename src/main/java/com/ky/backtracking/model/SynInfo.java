package com.ky.backtracking.model;

import java.util.List;

public class SynInfo {
    private Long uuid;
    private String pNumber;
    private String lastLoginDate;
    private String totalPlayTime;
    private List<String> saves;
    private Achievement achievement;

    public SynInfo() {

    }

    public void setUuid(Long uuid) {
        this.uuid = uuid;
    }

    public Long getUuid() {
        return uuid;
    }

    public void setSaves(List<String> saves) {
        this.saves = saves;
    }

    public List<String> getSaves() {
        return saves;
    }

    public String getpNumber() {
        return pNumber;
    }

    public void setpNumber(String pNumber) {
        this.pNumber = pNumber;
    }

    public void setTotalPlayTime(String totalPlayTime) {
        this.totalPlayTime = totalPlayTime;
    }

    public String getTotalPlayTime() {
        return totalPlayTime;
    }

    public void setLastLoginDate(String lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public String getLastLoginDate() {
        return lastLoginDate;
    }

    public Achievement getAchievement() {
        return achievement;
    }

    public void setAchievement(Achievement achievement) {
        this.achievement = achievement;
    }
}
