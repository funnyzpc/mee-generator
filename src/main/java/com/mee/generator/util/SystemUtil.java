package com.mee.generator.util;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

/***
 * @author shadow
 * @description 系统类工具类
 */
public class SystemUtil {

    /** 操作系统临时目录位置 **/
    public static final String file_base_path;
    static {
        if(System.getProperty("file.separator").equals("/")){
            file_base_path = "/tmp";
        }else{
            file_base_path = "D:/tmp";
        }
        // 构建目录
        if(!new File(file_base_path).exists()){
            new File(file_base_path).mkdir();
        }
    }

    // 列出所有的文件(仅当前级及下一级)
    public static List<File> listAll(File dir) {
        List<File> fileList = new ArrayList<File>(8);
        for(File f:dir.listFiles()){
            if(f.isFile()){
                fileList.add(f);
                continue;
            }
            for(File innerFile:f.listFiles()){
                if(innerFile.isFile()){
                    fileList.add(innerFile);
                    continue;
                }
            }
        }
        return fileList;
    }


}
