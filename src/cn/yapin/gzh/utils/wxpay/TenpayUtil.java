package cn.yapin.gzh.utils.wxpay;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class TenpayUtil {

    /**
     * 把对象转换成字符串
     *
     * @param obj
     * @return String 转换成字符串,若对象为null,则返回空字符串.
     */
    public static String toString(Object obj) {
        if (obj == null)
            return "";
        return obj.toString();
    }


    /**
     * 获取编码字符集
     *
     * @return String
     */
    public static String getCharacterEncoding(HttpServletRequest request,
                                              HttpServletResponse response) {

        if (null == request || null == response) {
            return "gbk";
        }

        String enc = request.getCharacterEncoding();
        if (null == enc || "".equals(enc)) {
            enc = response.getCharacterEncoding();
        }

        if (null == enc || "".equals(enc)) {
            enc = "gbk";
        }
        return enc;
    }
}
