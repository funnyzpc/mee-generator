package com.mee.generator.service.impl;


import com.mee.generator.service.SeqGenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author shadow
 * @description 序列生成器
 */
@Service
public class SeqGenServiceImpl implements SeqGenService {
    private static final Logger log = LoggerFactory.getLogger(SeqGenServiceImpl.class);

    private static final AtomicInteger IT = new AtomicInteger(1000);
    private static final AtomicInteger SHOT_STATIC_IT = new AtomicInteger(1000);
    private static final DateTimeFormatter DATE_SHORT_FORMAT = DateTimeFormatter.ofPattern("yyMMddHHmmss");
    private static final DateTimeFormatter DATE_LONG_FORMAT = DateTimeFormatter.ofPattern("yyMMddHHmmssSS");
    private static final ZoneId zoneId = ZoneId.of("Asia/Shanghai");

//    private static final ZoneOffset ZONE_OFF_SET = ZoneOffset.of("+8");
    private static final AtomicInteger BIG_IT = new AtomicInteger(100000);

    @Autowired
    private Environment environment;

    /** 生成主键(18位)：12(日期时间YYMMDDHHMISS)+2(端口)+4(有序序列) **/
    public String genPrimaryKey(){
        LocalDateTime dataTime = LocalDateTime.now(zoneId);
        String port =  environment.getProperty("local.server.port");
        if(IT.intValue()>9990){
            log.info("重置计数器:{}",IT.intValue());
            IT.getAndSet(1000);
        }
        if(null == port || port.length()!=2){
            port = "00"+(null==port?"":port);
            return dataTime.format(DATE_SHORT_FORMAT)+port.substring(port.length()-2)+ IT.getAndIncrement();
        }
        return dataTime.format(DATE_SHORT_FORMAT)+port+ IT.getAndIncrement();
    }

    /** 生成主键(22位) 14(日期时间YYMMDDHHMIssSS)+2(端口)+6(有序序列) **/
    public String genBigPrimaryKey(){
        LocalDateTime dataTime = LocalDateTime.now(zoneId);
        String port =  environment.getProperty("local.server.port");
        if(BIG_IT.intValue()>990000){
            log.info("重置计数器:{}",BIG_IT.intValue());
            BIG_IT.getAndSet(100000);
        }
        if(null == port || port.length()!=2){
            port = "00"+(null==port?"":port);
            return dataTime.format(DATE_LONG_FORMAT)+port.substring(port.length()-2)+ BIG_IT.getAndIncrement();
        }
        return dataTime.format(DATE_LONG_FORMAT)+port+ BIG_IT.getAndIncrement();
    }

    /** 16位： 12(日期时间yyMMddHHmmss)+4(有序序列) **/
    public String genShortPrimaryKey(){
        LocalDateTime dataTime = LocalDateTime.now(zoneId);
        if(SHOT_STATIC_IT.intValue()>9990){
            log.info("重置genSeq序列 1000");
            SHOT_STATIC_IT.getAndSet(1000);
        }
        return dataTime.format(DATE_SHORT_FORMAT)+ SHOT_STATIC_IT.getAndIncrement();
    }

    public Long shortKey(){
        return Long.parseLong(this.genShortPrimaryKey());
    }

    public Long mediumKey(){
        return Long.parseLong(this.genPrimaryKey());
    }


    /** 生成主键(18位)：12(日期时间YYMMDDHHMISS)+2(seq_key)+4(有序序列) **/
    private static final Map<String,Long>  TABLE_SEQ = new ConcurrentHashMap<String,Long>(16);
    private static  String THREAD_NAME = null;
    public  String genPrimaryKey(String table_name){
        if(null==table_name || "".equals(table_name.trim())){
            throw new RuntimeException("必要参数为空");
        }
        LocalDateTime dataTime = LocalDateTime.now(zoneId);
        String port =  environment.getProperty("local.server.port");
        final String table  = table_name.toUpperCase();
        Long seq = TABLE_SEQ.get(table);
        if( null==seq || seq>9996){
            // 睡眠以防止并发不够
            // TODO~ 这个地方其实是有问题的，这个THREAD_NAME其实应该跟当前table绑定才是
            if(Thread.currentThread().getName().equals(THREAD_NAME)){
                try {
                    log.info("开始休眠1s...");
                    TimeUnit.SECONDS.sleep(1);
                }catch (Exception e){
                    log.error("sleep异常...:{}",table,e);
                }
            }
            THREAD_NAME = Thread.currentThread().getName();
            log.info("重置计数器:{}",seq);
            seq = 999L;
        }
        ++seq;
        TABLE_SEQ.put(table,seq);
        if(null == port || port.length()!=2){
            port = "00"+(null==port?"":port);
            return dataTime.format(DATE_SHORT_FORMAT)+port.substring(port.length()-2)+ (seq);
        }
        return dataTime.format(DATE_SHORT_FORMAT)+port+ (seq);
    }

}
