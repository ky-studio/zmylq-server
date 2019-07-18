package com.ky.zmylq.model;

public class SaveJson {
    private String playername;
    private String scence;
    private String chapter;
    private Integer intelligence;
    private Integer fans;
    private Integer favor1;
    private Integer favor2;
    private Integer favor3;

    public SaveJson() {

    }

    public void setIntelligence(Integer intelligence) {
        this.intelligence = intelligence;
    }

    public void setFavor3(Integer favor3) {
        this.favor3 = favor3;
    }

    public void setFavor2(Integer favor2) {
        this.favor2 = favor2;
    }

    public void setFavor1(Integer favor1) {
        this.favor1 = favor1;
    }

    public void setFans(Integer fans) {
        this.fans = fans;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public void setScence(String scence) {
        this.scence = scence;
    }

    public void setPlayername(String playername) {
        this.playername = playername;
    }

    public String getChapter() {
        return chapter;
    }

    public String getScence() {
        return scence;
    }

    public Integer getFans() {
        return fans;
    }

    public Integer getFavor1() {
        return favor1;
    }

    public Integer getFavor2() {
        return favor2;
    }

    public Integer getFavor3() {
        return favor3;
    }

    public Integer getIntelligence() {
        return intelligence;
    }

    public String getPlayername() {
        return playername;
    }

}
