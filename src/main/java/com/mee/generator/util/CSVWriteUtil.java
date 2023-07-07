package com.mee.generator.util;

import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;
import com.opencsv.RFC4180Parser;
import com.opencsv.RFC4180ParserBuilder;
import com.opencsv.enums.CSVReaderNullFieldIndicator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * csv写入工具类
 *
 * @author shadow
 * @version v1.0
 * @className CSVWriteUtil
 * @date 2023/7/6 8:40 PM
 */
public class CSVWriteUtil {
    /**
     * 日志
     */
    private static final Logger LOG = LoggerFactory.getLogger(CSVWriteUtil.class);


    public static  <T> File toCSV(List<T[]> data_list) {
        long start=System.currentTimeMillis();
        RFC4180Parser CSV_PARSER = new RFC4180ParserBuilder().withFieldAsNull(CSVReaderNullFieldIndicator.EMPTY_SEPARATORS).build();
        File file = new File(SystemUtil.file_base_path + File.separator + SeqGenUtil.genSeq() + ".csv");
        file.getParentFile().mkdirs();
        try(FileWriter fileWriter = new FileWriter(file);
            ICSVWriter icsvWriter = new CSVWriterBuilder(fileWriter).withParser(CSV_PARSER).build();){
            List<String[]> full_data_list = new ArrayList<>(data_list.size()+1);
            // body data
            int len = data_list.get(0).length;
            for(Object[] item:data_list){
                String[] str_item = new String[len];
                for(int i=0;i<len;i++){
                    if(item[i] instanceof  String){
                        str_item[i]=(String)item[i];
                    }else{
                        //str_item[i]=JacksonUtil.toJsonString(item[i]);
                        str_item[i]=toStr(item[i]);
                    }
                }
                full_data_list.add(str_item);
            }
            icsvWriter.writeAll(full_data_list);
        }catch (Exception e){
            e.printStackTrace();
            LOG.error("写入CSV异常了。。。",e);
        }
        LOG.info("CSVWriteUtil::" + Thread.currentThread().getName() + "::文件 " + file.getName() + " ->写入" + data_list.size() + "条 耗时:" + (System.currentTimeMillis() - start) + "毫秒");
        return file;
    }

    private static String toStr(Object obj){
        if( null==obj ){
            return "";
        }
        final String name = obj.getClass().getName();
        switch (name){
            case "java.math.BigDecimal":
                return "'"+((BigDecimal)obj).toPlainString();
            case "java.lang.Long":
                return ((Long)obj).toString();
            case "java.lang.Double":
                return ((Double)obj).toString();
            case "java.sql.Timestamp":
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                return df.format((Timestamp)obj);
            case "java.sql.Date":
                return  new SimpleDateFormat("yyyy-MM-dd").format((java.sql.Date)obj);
            default:
                return JacksonUtil.toJsonString(obj);
        }
    }

}
