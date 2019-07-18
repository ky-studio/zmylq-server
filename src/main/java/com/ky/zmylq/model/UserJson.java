package com.ky.zmylq.model;

import java.util.List;

public class UserJson {
    private Long uuid;
    private List<SaveJson> saves;

    public UserJson() {

    }

    public void setUuid(Long uuid) {
        this.uuid = uuid;
    }

    public Long getUuid() {
        return uuid;
    }

    public void setSaves(List<SaveJson> saves) {
        this.saves = saves;
    }

    public List<SaveJson> getSaves() {
        return saves;
    }
}
