package com.ky.backtracking.controller;

import com.ky.backtracking.service.SystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class AppinfoController {

    private static final Logger LOG = LoggerFactory.getLogger(AppinfoController.class);

    @Autowired
    private SystemService systemService;
    /*
     * 获取最新的app版本号
     */
    @RequestMapping(value = "/btl/appinfo/version", method = RequestMethod.GET)
    @ResponseBody
    public String getLatestVersion() {
        Map<String, String> appinfo = systemService.getAppinfo();
        LOG.info("get latest app version: {}", appinfo.get("version"));
        return appinfo.get("version");
    }

    /*
     * 获取最新的APP信息
     */
    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/btl/appinfo", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> getLatestAppinfo() {
        Map<String, String> appinfo = systemService.getAppinfo();
        if (appinfo == null) {
            return null;
        }
        LOG.info("get latest appinfo<vesion: {}, date: {}, size: {}>", appinfo.get("version"), appinfo.get("date"), appinfo.get("size"));
        return appinfo;
    }

    /*
     * 设置APP信息
     */
    @RequestMapping(value = "/btl/appinfo", method = RequestMethod.PUT)
    @ResponseBody
    public void setAppinfo(@RequestParam(name = "version", required = false) String version,
                                 @RequestParam(name = "date", required = false) String date,
                                 @RequestParam(name = "size", required = false) Float size) {
        systemService.setAppinfo(version, date, size);
        LOG.info("set latest app info <version: {}, date: {}, size: {}>", version, date, size);
    }

    /*
     * 下载点击统计
     */
    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/btl/downloads", method = RequestMethod.PUT)
    @ResponseBody
    public String setDownloads(@RequestParam(name = "clicks", required = false) Long val) {
        if (val != null) {
            systemService.setDownloadClickNumber(val);
            LOG.info("set download clicks: {}", val);
            return val.toString();
        }
        else {
            String clicks = systemService.add1DownloadClickNumber();
            LOG.info("download clicked, current clicks: {}", clicks);
            return clicks;
        }
    }


    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/btl/downloads", method = RequestMethod.GET)
    @ResponseBody
    public String getDownloads() {
        String clicks = systemService.getDownloadClickNumber();
        LOG.info("current clicks: {}", clicks);
        return clicks;
    }

    /*
     * 下载玩完成统计
     */
    @RequestMapping(value = "/btl/downloaded", method = RequestMethod.GET)
    @ResponseBody
    public String downloadCompleted() {
        String clicks = systemService.add1DownloadClickNumber();
        LOG.info("download clicked, current clicks: {}", clicks);
        return clicks;
    }

//    /*
//     * 清理数据库
//     */
//    @RequestMapping(value = "/btl/manage/clear", method = RequestMethod.DELETE)
//    @ResponseBody
//    public String clearData(@RequestParam(name = "passwd") String passwd) {
//        if (passwd.equals("AE8^>PuwHMEeQg")) {
//            systemService.clearDatabase();
//            LOG.info("clear database success");
//            return "Success";
//        } else {
//            LOG.info("clear database fail");
//            return "Fail";
//        }
//
//    }
}
