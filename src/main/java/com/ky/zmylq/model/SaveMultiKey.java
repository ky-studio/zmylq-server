package com.ky.zmylq.model;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.io.Serializable;

@Embeddable
public class SaveMultiKey implements Serializable {
    private Integer uuid;
    private Integer saveid;

    public SaveMultiKey() {

    }

    public SaveMultiKey(Integer uuid, Integer saveid) {
        this.uuid = uuid;
        this.saveid = saveid;
    }

    public void setUuid(Integer uuid) {
        this.uuid = uuid;
    }

    public Integer getUuid() {
        return uuid;
    }

    public void setSaveid(Integer saveid) {
        this.saveid = saveid;
    }

    public Integer getSaveid() {
        return saveid;
    }
}
