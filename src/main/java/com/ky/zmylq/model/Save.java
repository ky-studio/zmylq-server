package com.ky.zmylq.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "saves")
public class Save {
    @EmbeddedId
    private SaveMultiKey saveMultiKey;
    private String playername;
    private String scence;
    private String chapter;
    @NotNull
    private Integer fans;
    @NotNull
    private Integer intelligence;
    @NotNull
    private Integer favor1;
    @NotNull
    private Integer favor2;
    @NotNull
    private Integer favor3;

    public Save() {

    }

    public Save(SaveMultiKey saveMultiKey, String playername, String scence, String chapter,
                Integer fans, Integer intelligence, Integer favor1, Integer favor2, Integer favor3) {
        this.saveMultiKey = saveMultiKey;
        this.playername = playername;
        this.scence = scence;
        this.chapter = chapter;
        this.fans = fans;
        this.intelligence = intelligence;
        this.favor1 = favor1;
        this.favor2 = favor2;
        this.favor3 = favor3;
    }

    public void setSaveMultiKey(SaveMultiKey saveMultiKey) {
        this.saveMultiKey = saveMultiKey;
    }

    public SaveMultiKey getSaveMultiKey() {
        return saveMultiKey;
    }

    public void setPlayername(String playername) {
        this.playername = playername;
    }

    public String getPlayername() {
        return playername;
    }

    public void setScence(String scence) {
        this.scence = scence;
    }

    public String getScence() {
        return scence;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public String getChapter() {
        return chapter;
    }

    public void setFans(Integer fans) {
        this.fans = fans;
    }

    public Integer getFans() {
        return fans;
    }

    public void setIntelligence(Integer intelligence) {
        this.intelligence = intelligence;
    }

    public Integer getIntelligence() {
        return intelligence;
    }

    public void setFavor1(Integer favor1) {
        this.favor1 = favor1;
    }

    public Integer getFavor1() {
        return favor1;
    }

    public void setFavor2(Integer favor2) {
        this.favor2 = favor2;
    }

    public Integer getFavor2() {
        return favor2;
    }

    public void setFavor3(Integer favor3) {
        this.favor3 = favor3;
    }

    public Integer getFavor3() {
        return favor3;
    }
}
