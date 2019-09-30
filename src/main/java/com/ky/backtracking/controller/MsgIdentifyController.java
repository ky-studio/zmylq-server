package com.ky.backtracking.controller;

import com.ky.backtracking.common.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class MsgIdentifyController {

    private static final Logger LOG = LoggerFactory.getLogger(MsgIdentifyController.class);
    private static final String KEY = "backtrackdinglife";

    @ResponseBody
    @RequestMapping(value = "/btl/msg/send", method = RequestMethod.GET)
    public Map<String, Object> sendMessage(@RequestParam(name = "pnumber") String pnumber) {
        String randomCode = CommonUtil.randomNum(6);
        LOG.info("randomCode: {}", randomCode);
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MINUTE, 2);
        String currentTime = sf.format(c.getTime());// 生成5分钟后时间，用户校验是否过期
        long time = new Date().getTime();
        // CommonUtil.sendMessage(pnumber, randomCode, String.valueOf(time));
        String hashMd5 = CommonUtil.generateMd5Hex(KEY + "@" + pnumber + "@" + currentTime + "@" + randomCode);
        Map<String, Object> ret = new HashMap<>();
        ret.put("md5", hashMd5);
        ret.put("time", currentTime);
        return ret;
    }

    @ResponseBody
    @RequestMapping(value = "/btl/msg/identify", method = RequestMethod.POST)
    public String identifyCode(@RequestBody Map<String, Object> reqData) {
        String pnumber = reqData.get("pnumber").toString();
        String reqMd5 = reqData.get("md5").toString();
        String reqTime = reqData.get("time").toString();
        String reqCode = reqData.get("code").toString();
        String hashMd5 = CommonUtil.generateMd5Hex(KEY + "@" + pnumber + "@" + reqTime + "@" + reqCode);
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar c = Calendar.getInstance();
        String currentTime = sf.format(c.getTime());
        LOG.info("request md5: {}", reqMd5);
        LOG.info("hashMd5: {}", hashMd5);
        if (reqTime.compareTo(currentTime) > 0) {
            if (hashMd5.equalsIgnoreCase(reqMd5)) {
                // success
                LOG.info("identify success, mobile: {}", pnumber);
                return "IDF_SUCCESS";
            } else {
                // fail
                LOG.info("identify fail, mobile: {}", pnumber);
                return "IDF_FAIL";
            }
        } else {
            // timeout
            LOG.info("identify timeout, mobile: {}", pnumber);
            return "IDF_TIMEOUT";
        }
    }
}
