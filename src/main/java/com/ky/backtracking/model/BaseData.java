package com.ky.backtracking.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "basedata")
public class BaseData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bid;
    @NotNull
    private String timestamp; // 时间戳（服务器时间）
    private int optype; // 操作类型
    private int opstatus; // 操作状态
    private float optime; // 操作耗时
    @NotNull
    private Long uuid; // 用户id
    private boolean isbind; // 是否绑定手机号
    private float playtime; // 单次游戏时长
    private String clientversion; // 客户端版本
    private String mid; // 设备id
    private String mtype; // 设备类型
    private String screen; // 屏幕分辨率
    private String os; // 操作系统
    private String network; // 网络情况
    private String ip;
    private int port;

    public BaseData() {

    }

    public BaseData(BaseData data) {
        timestamp = data.timestamp;
        optype = data.optype;
        opstatus = data.opstatus;
        optime = data.optime;
        uuid = data.uuid;
        isbind = data.isbind;
        playtime = data.playtime;
        clientversion = data.clientversion;
        mid = data.mid;
        mtype = data.mtype;
        screen = data.screen;
        os = data.os;
        network = data.network;
        ip = data.ip;
        port = data.port;
    }

    public Long getBid() {
        return bid;
    }

    public void setBid(Long bid) {
        this.bid = bid;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getOptype() {
        return optype;
    }

    public void setOptype(int optype) {
        this.optype = optype;
    }

    public int getOpstatus() {
        return opstatus;
    }

    public void setOpstatus(int opstatus) {
        this.opstatus = opstatus;
    }

    public float getOptime() {
        return optime;
    }

    public void setOptime(float optime) {
        this.optime = optime;
    }

    public void setUuid(Long uuid) {
        this.uuid = uuid;
    }

    public Long getUuid() {
        return uuid;
    }

    public boolean isIsbind() {
        return isbind;
    }

    public void setIsbind(boolean isbind) {
        this.isbind = isbind;
    }

    public float getPlaytime() {
        return playtime;
    }

    public void setPlaytime(float playtime) {
        this.playtime = playtime;
    }

    public String getClientversion() {
        return clientversion;
    }

    public void setClientversion(String clientversion) {
        this.clientversion = clientversion;
    }

    public String getMid() {
        return mid;
    }

    public String getMtype() {
        return mtype;
    }

    public void setMtype(String mtype) {
        this.mtype = mtype;
    }

    public String getScreen() {
        return screen;
    }

    public void setScreen(String screen) {
        this.screen = screen;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getOs() {
        return os;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
