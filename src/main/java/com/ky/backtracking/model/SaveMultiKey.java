package com.ky.backtracking.model;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class SaveMultiKey implements Serializable {
    private Long uuid;
    private Integer saveid;

    public SaveMultiKey() {

    }

    public SaveMultiKey(Long uuid, Integer saveid) {
        this.uuid = uuid;
        this.saveid = saveid;
    }

    public void setUuid(Long uuid) {
        this.uuid = uuid;
    }

    public Long getUuid() {
        return uuid;
    }

    public void setSaveid(Integer saveid) {
        this.saveid = saveid;
    }

    public Integer getSaveid() {
        return saveid;
    }
}
