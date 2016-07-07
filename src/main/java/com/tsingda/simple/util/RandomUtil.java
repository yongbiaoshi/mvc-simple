package com.tsingda.simple.util;

import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;

public class RandomUtil {
    public static String genRandom() {
        int len = 10;

        String uid = UUID.randomUUID().toString();

        String uidmd5 = DigestUtils.md5Hex(uid);
        int op = uidmd5.length() / 2;
        String uidmd5B = uidmd5.substring(0, op);
        String uidmd5A = uidmd5.substring(op);

        long a = System.nanoTime();

        double b = Math.random() * 9999;

        long c = System.currentTimeMillis();

        double ab = a + b;

        double ac = a + c;

        StringBuilder random = new StringBuilder();
        random.append(uidmd5B);
        random.append(Double.toHexString(ab));
        random.append(Double.toHexString(ac));
        random.append(uidmd5A);

        return random.substring(0, len).toUpperCase();

    }
    
    public static void main(String[] args) {
        System.out.println(RandomUtil.genRandom());
    }
}
