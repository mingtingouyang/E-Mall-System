package org.oza.ego.base.utils;

import java.util.Random;

/**
 * 随机 ID 生成工具
 */
public class IdUtil {

    /**
     * 生成随机文件名，生成方式为：当前时间戳 + 随机三位数
     * @return 文件名字符串
     */
    public static String getFileName() {
        //获取当前时间戳
        long time = System.currentTimeMillis();
        //再增加三位随机数
        int randomEnd = new Random().nextInt(999);
        //如果三位随机数不足三位则补0
        return time + String.format("%03d", randomEnd);
    }

    /**
     * 根据时间戳加上两位随机数，随机生成 ID
     * @return
     */
    public static long getItemId() {
        long millis = System.currentTimeMillis();
        int randomInt = new Random().nextInt(99);
        //不足两位需要补0
        String idStr = millis + String.format("%02d", randomInt);
        return Long.valueOf(idStr);
    }
}
