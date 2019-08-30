package com.ky.backtracking.model;

import java.util.List;

public class SaveInfo {
    private Long uuid;
    private List<String> saves;

    public SaveInfo() {

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
}
