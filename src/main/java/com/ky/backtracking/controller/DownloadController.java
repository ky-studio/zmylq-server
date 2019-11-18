//package com.ky.backtracking.controller;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.*;
//import java.net.URLEncoder;
//
//@Controller
//public class DownloadController {
//
//    @RequestMapping(value = "/btl")
//    public String download() {
//        return "BacktrackingLife";
//    }
//
//    @RequestMapping(value = "/btl/download/btl.apk")
//    @ResponseBody
//    public String downloadFile(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
//
//        // 获取指定目录下的第一个文件
//        final String fileName = "D:\\btl\\app\\btl.apk"; //下载的文件名
//
//        File file = new File(fileName);
//
//        // 如果文件名存在，则进行下载
//        if (file.exists()) {
//
//            // 配置文件下载
//            response.setHeader("content-type", "application/octet-stream");
//            response.setContentType("application/octet-stream");
//            // 下载文件能正常显示中文
//            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
//
//            // 实现文件下载
//            byte[] buffer = new byte[1024];
//            FileInputStream fis = null;
//            BufferedInputStream bis = null;
//            try {
//                fis = new FileInputStream(file);
//                bis = new BufferedInputStream(fis);
//                OutputStream os = response.getOutputStream();
//                int i = bis.read(buffer);
//                while (i != -1) {
//                    os.write(buffer, 0, i);
//                    i = bis.read(buffer);
//                }
//                System.out.println("Download the app successfully!");
//            }
//            catch (Exception e) {
//                e.printStackTrace();
//                System.out.println("Download the app failed!");
//            }
//            finally {
//                if (bis != null) {
//                    try {
//                        bis.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//                if (fis != null) {
//                    try {
//                        fis.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
//        return null;
//    }
//
//}
