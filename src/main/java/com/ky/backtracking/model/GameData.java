package com.ky.backtracking.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "gamedata")
public class GameData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gid;
    @NotNull
    private Long uuid; // 用户id
    private int gamecode; // 游戏码
    private boolean gamestatus; // 游戏状态
    private long clicks; //
    private float temptime; // 游戏时长（线索类为登录到游戏通关或退出登录的时长，其他游戏设为0）
    private float duration; // 累计通关时长
    private int savenum;

    public  GameData() {

    }

    public GameData(GameData data) {
        uuid = data.uuid;
        gamecode = data.gamecode;
        gamestatus = data.gamestatus;
        clicks = data.clicks;
        temptime = data.temptime;
        duration = data.duration;
        savenum = data.savenum;
    }

    public Long getGid() {
        return gid;
    }

    public void setGid(Long gid) {
        this.gid = gid;
    }

    public Long getUuid() {
        return uuid;
    }

    public void setUuid(Long uuid) {
        this.uuid = uuid;
    }

    public int getGamecode() {
        return gamecode;
    }

    public void setGamecode(int gamecode) {
        this.gamecode = gamecode;
    }

    public boolean isGamestatus() {
        return gamestatus;
    }

    public void setGamestatus(boolean gamestatus) {
        this.gamestatus = gamestatus;
    }

    public long getClicks() {
        return clicks;
    }

    public void setClicks(long clicks) {
        this.clicks = clicks;
    }

    public float getTemptime() {
        return temptime;
    }

    public void setTemptime(float temptime) {
        this.temptime = temptime;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public int getSavenum() {
        return savenum;
    }

    public void setSavenum(int savenum) {
        this.savenum = savenum;
    }
}
