package com.autoserve.abc.web.module.screen.account.myAward.json;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.web.helper.MatrixToImageWriter;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

/**
 * 类QRCodeJSON.java的实现描述：生成用户的分享二维码
 * 
 * @author WangMing 2015年5月16日 下午2:53:31
 */
public class QRCodeJSON {

    @Resource
    private HttpServletResponse response;

    public void execute(Context context, ParameterParser params) {

        String url = params.getString("shareUrl");
        System.out.println("=======================>url:" + url);
        int width = 400;
        int height = 400;
        OutputStream out = null;
        //二维码的图片格式  
        String format = "png";
        Hashtable hints = new Hashtable();
        //内容所使用编码  
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");

        try {
            out = response.getOutputStream();
            BitMatrix bitMatrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, width, height, hints);
            MatrixToImageWriter.writeToStream(bitMatrix, format, out);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("NND 我抛异常了！");
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
