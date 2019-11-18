package com.ky.backtracking.model;

import javax.persistence.*;

@Entity
@Table(name = "feedback")
public class FeedBack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fid;
    private Long uuid;
    private String fbType;
    private String discription;
    private String feedbackTime;
    private String operatingsystem;
    private String deviceModel;
    private String networkType;
    private String clientVersion;
    private String isAgreeContact;
    private String contact;
    private String base64img;

    public FeedBack() {

    }

    public FeedBack(FeedBack feedBack) {
        this.uuid = feedBack.uuid;
        this.fbType = feedBack.fbType;
        this.discription = feedBack.discription;
        this.feedbackTime = feedBack.feedbackTime;
        this.operatingsystem = feedBack.operatingsystem;
        this.deviceModel = feedBack.deviceModel;
        this.networkType = feedBack.networkType;
        this.clientVersion = feedBack.clientVersion;
        this.isAgreeContact = feedBack.isAgreeContact;
        this.contact = feedBack.contact;
    }

    public Long getFid() {
        return fid;
    }

    public void setFid(Long fid) {
        this.fid = fid;
    }

    public void setUuid(Long uuid) {
        this.uuid = uuid;
    }

    public Long getUuid() {
        return uuid;
    }

    public String getFbType() {
        return fbType;
    }

    public void setFbType(String fbType) {
        this.fbType = fbType;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getFeedbackTime() {
        return feedbackTime;
    }

    public void setFeedbackTime(String feedbackTime) {
        this.feedbackTime = feedbackTime;
    }

    public String getOperatingsystem() {
        return operatingsystem;
    }

    public void setOperatingsystem(String operatingsystem) {
        this.operatingsystem = operatingsystem;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public void setNetworkType(String networkType) {
        this.networkType = networkType;
    }

    public String getNetworkType() {
        return networkType;
    }

    public void setClientVersion(String clientVersion) {
        this.clientVersion = clientVersion;
    }

    public String getClientVersion() {
        return clientVersion;
    }

    public String getIsAgreeContact() {
        return isAgreeContact;
    }

    public void setIsAgreeContact(String isAgreeContact) {
        this.isAgreeContact = isAgreeContact;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getBase64img() {
        return base64img;
    }

    public void setBase64img(String base64img) {
        this.base64img = base64img;
    }
}
