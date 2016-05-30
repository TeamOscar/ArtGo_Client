package com.seuic.sell.constant;

import com.seuic.sell.entity.User;

import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/5/12.
 */
public class Common {
    public static User mUser;
    public static String serviceUrl = "http://172.25.210.134:8080/Sell/servlet/";
    //public static String uploadPicUrl = "http://192.168.56.1:8080/Sell/";
    public static String uploadPicUrl = "http://172.25.210.134:8080/Sell/";
    /*
  * 判断是否为整数
  * @param str 传入的字符串
  * @return 是整数返回true,否则返回false
  */
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    /*
  * 判断是否为浮点数，包括double和float
  * @param str 传入的字符串
  * @return 是浮点数返回true,否则返回false
*/
    public static boolean isDouble(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
        return pattern.matcher(str).matches();
    }
}
