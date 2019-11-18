package com.ky.backtracking.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BtlConfig {

    @Value("${com.ky.btl.feedback-imgpath}")
    private String fbImagePath;


    public String getFbImagePath() {
        return fbImagePath;
    }

    public void setFbImagePath(String fbImagePath) {
        this.fbImagePath = fbImagePath;
    }

}
