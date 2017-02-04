package seckill.util;

import org.springframework.util.DigestUtils;

/**
 * Created by taowang on 1/21/2017.
 */
public class Md5 {
    static public String getMd5String(String str, String salt) {
        String base = str + '/' + salt;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }
}
