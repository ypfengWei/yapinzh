package cn.yapin.gzh.utils;

import java.io.File;

public final class UrlTools {

    public static final File PICTURE_DIR;//商品图片目录

    static {
        ///www/wechat/picture

        PICTURE_DIR = new File("/www/wechat/picture");//文件系统目录
        if (!PICTURE_DIR.exists()) {
            PICTURE_DIR.mkdirs();
        }
    }
}
