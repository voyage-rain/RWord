package com.it.rword.utils;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class CodeUtil {

    private final String[] patch = {"000000","00000","0000","000","00","0",""};

    public String generator(String phone) {
        int hash = phone.hashCode();
        int encryption = 20236666;
        long result = hash ^ encryption;
        long nowTime = System.currentTimeMillis();
        result = result ^ nowTime;
        long code = result % 1000000;
        code = code < 0 ? -code : code;
        String codeStr = code + "";
        int len = codeStr.length();
        return patch[len] + codeStr;
    }

    @Cacheable(value = "smsCode", key = "#phone")
    public String get(String phone) {
        return null;
    }
}
