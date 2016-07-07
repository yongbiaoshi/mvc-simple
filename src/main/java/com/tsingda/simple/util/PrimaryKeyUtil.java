package com.tsingda.simple.util;

import java.nio.ByteBuffer;
import java.util.Date;
import java.util.UUID;

import org.bson.types.ObjectId;

public class PrimaryKeyUtil {

    private static String digits(long val, int digits) {
        long hi = 1L << (digits * 4);
        return Numbers.toString(hi | (val & (hi - 1)), Numbers.MAX_RADIX).substring(1);
    }

    /**
     * 以62进制（字母加数字）生成19位UUID，最短的UUID
     * 
     * @return
     */
    public static String uuid() {
        UUID uuid = UUID.randomUUID();
        StringBuilder sb = new StringBuilder();
        sb.append(digits(uuid.getMostSignificantBits() >> 32, 8));
        sb.append(digits(uuid.getMostSignificantBits() >> 16, 4));
        sb.append(digits(uuid.getMostSignificantBits(), 4));
        sb.append(digits(uuid.getLeastSignificantBits() >> 48, 4));
        sb.append(digits(uuid.getLeastSignificantBits(), 12));
        return sb.toString();
    }

    /**
     * 生成32位带前缀的PK
     *
     * @param prefix 前缀
     * @return PK（32位）
     */
    public static String generatePkWithPrefix(String prefix) {
        int pkLength = 32;
        if (prefix == null || "".equals(prefix)) {
            throw new IllegalArgumentException("prefix must not be null!");
        }
        String uuid = uuid();
        int left = pkLength - prefix.length() - uuid.length();

        if (left == 8) {
            String timestamp = getTimestamp();
            return String.format("%s%s%s", prefix, timestamp, uuid);
        } else if (left > 8) {
            String timestamp = getTimestamp();
            return String.format("%s%s%0" + (left - timestamp.length()) + "d%s", prefix, timestamp, 0, uuid);
        } else {
            return String.format("%s%0" + left + "d%s", prefix, 0, uuid);
        }
    }

    public static byte[] toByteArray(int time) {
        byte b[] = new byte[4];
        ByteBuffer bb = ByteBuffer.wrap(b);
        bb.putInt(time);
        return b;
    }

    /**
     * 取秒级 8位 时间戳
     *
     * @return 时间戳
     */
    public static String getTimestamp() {
        Date d = new Date();
        return getTimestamp((int) (d.getTime() / 1000));
    }

    public static String getTimestamp(int time) {
        StringBuffer buf = new StringBuffer();
        for (final byte b : toByteArray(time)) {
            buf.append(String.format("%02x", b & 0xff));
        }
        return buf.toString();
    }

    public static void main(String[] args) {
        ObjectId id = new ObjectId();
        System.out.println(id.toHexString());
        System.out.println(getTimestamp(id.getTimestamp()));
        System.out.println(generatePkWithPrefix("JYCSHD_TEST_"));
        System.out.println(generatePkWithPrefix("JYCSHD_"));
        System.out.println(generatePkWithPrefix("JYCS_"));
        System.out.println(generatePkWithPrefix("JBDX_TEST_"));
    }
}
