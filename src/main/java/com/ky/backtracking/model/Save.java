package com.ky.backtracking.model;

import javax.persistence.*;

@Entity
@Table(name = "saves")
public class Save {
    @EmbeddedId
    private SaveMultiKey saveMultiKey;

    private String content;

    public Save() {

    }

    public Save(SaveMultiKey saveMultiKey, String content) {
        this.saveMultiKey = saveMultiKey;
        this.content = content;
    }

    public void setSaveMultiKey(SaveMultiKey saveMultiKey) {
        this.saveMultiKey = saveMultiKey;
    }

    public SaveMultiKey getSaveMultiKey() {
        return saveMultiKey;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
