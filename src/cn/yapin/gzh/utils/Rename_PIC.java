package cn.yapin.gzh.utils;

import org.apache.commons.io.FilenameUtils;

import java.util.ArrayList;
import java.util.List;

//对上传图片重命名防止有不规范命名
public final class Rename_PIC {

    public static List<String> renameUtil(List<String> filenames) {
        List<String> list = new ArrayList<>(filenames.size());
        for (String str : filenames) {
            list.add(Uuid16.create().toString() + "." + FilenameUtils.getExtension(str));
        }
        return list;
    }

    public static String renameUtil(String filenames) {
        return Uuid16.create().toString() + "." + FilenameUtils.getExtension(filenames);
    }

    public static String renameUtil() {
        return Uuid16.create().toString();
    }

}
