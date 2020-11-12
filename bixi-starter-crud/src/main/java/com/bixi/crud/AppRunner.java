package com.bixi.crud;

import com.bixi.crud.config.EnableCrud;
import com.bixi.crud.dto.QueryCriteria;
import com.bixi.crud.template.controller.BaseController;
import com.bixi.crud.template.controller.impl.BaseControllerImpl;
import com.bixi.crud.test.domain.User;
import com.bixi.crud.template.service.BaseService;
import com.bixi.crud.utils.NameUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.List;

@SpringBootApplication
@EnableCrud
public class AppRunner {
    public static void main(String[] args) {
//        SpringApplication.run(AppRunner.class, args);
        System.out.println(sign("VjTXigmjaOdRTp83OULSTk5HZUUmRUk7uhnTWuAcYoHEIwPcWI2GKkI1JT1AhlrP1odpbBqPx5FDEFir7zWsHY", "dmsd", 1599551474000L, "http://123.182.167.54:10080/"));
    }

    public static String sign(String ticket, String nonceStr, long timeStamp, String url) {
        String plain = "jsapi_ticket=" + ticket + "&noncestr=" + nonceStr + "&timestamp=" + String.valueOf(timeStamp)
                + "&url=" + url;
        try {
            MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
            sha1.reset();
            sha1.update(plain.getBytes("UTF-8"));
            return byteToHex(sha1.digest());
        } catch (Exception e) {

        }
        return "failed";
    }

    // 字节数组转化成十六进制字符串
    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
}
