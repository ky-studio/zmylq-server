package com.ky.backtracking.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Decoder;

import java.io.FileOutputStream;
import java.io.OutputStream;

@Component
public class Base64Util {
    private static final Logger LOG = LoggerFactory.getLogger(Base64Util.class);
    @Autowired
    private BtlConfig btlConfig;

    public boolean Base64str2Image(String imgStr, String imagename)
    {
        //LOG.info("imgstr: {}", imgStr);
        //对字节数组字符串进行Base64解码并生成图片
        if (imgStr == null) //图像数据为空
        {
            LOG.info("Base64str2Image: imagestr is null");
            return false;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try
        {
            //Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for(int i = 0; i < b.length; ++i)
            {
                if(b[i] < 0)
                {//调整异常数据
                    b[i] += 256;
                }
            }
            //生成jpeg图片
            String imgFilePath = String.format("%s/%s.png", btlConfig.getFbImagePath(), imagename); //新生成的图片
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

}
