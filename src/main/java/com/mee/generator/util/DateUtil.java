package com.mee.generator.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

/**
 * @auther shadow
 * @description process date or dateTime
 */
public class DateUtil {
    private static final Logger log = LoggerFactory.getLogger(DateUtil.class);

    public static final DateTimeFormatter FORMAT_DAY = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter FORMAT_DAY2 = DateTimeFormatter.ofPattern("yyyyMMdd");
    public static final DateTimeFormatter FORMAT_DAY_TIME = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final ZoneId zoneId = ZoneId.of("Asia/Shanghai");

    /**
     * 当前时间添加秒数
     * @param seconds
     * @return
     */
    public static LocalDateTime addSeconds(long seconds){
        return LocalDateTime.now(zoneId).plusSeconds(seconds);
    }

    /**
     * 当前时间加减天数
     * @param days
     * @return
     */
    public static String addDays(long days){
        return LocalDateTime.now(zoneId).plusDays(days).format(FORMAT_DAY);
    }

    public static LocalDateTime now(){
        return LocalDateTime.now(zoneId);
    }
    public static LocalDate nowDay(){
        return LocalDate.now(zoneId);
    }
    /**
     * 日期转换成时间字符串
     * @param date
     * @param formatStr
     */
    public static String format(Date date, String formatStr){
        if(date == null || StringUtils.isEmpty(formatStr)){
            return null;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatStr);
        return simpleDateFormat.format(date);
    }

    /**
     * LocalDate、LocalDateTime 格式化字符串
     */
    public static String format(TemporalAccessor localDate, String fmtPattern){
        if(localDate == null || StringUtils.isEmpty(fmtPattern)){
            return null;
        }
        return DateTimeFormatter.ofPattern(fmtPattern).format(localDate);
    }

//    /**
//     * LocalDate、LocalDateTime 格式化字符串
//     */
//    public static String format(LocalDate localDate, String fmtPattern){
//        if(localDate == null || StringUtils.isEmpty(fmtPattern)){
//            return null;
//        }
//        return DateTimeFormatter.ofPattern(fmtPattern).format(localDate);
//    }
//    public static String format(LocalDateTime localDateTime, String fmtPattern){
//        if(localDateTime == null || StringUtils.isEmpty(fmtPattern)){
//            return null;
//        }
//        return DateTimeFormatter.ofPattern(fmtPattern).format(localDateTime);
//    }

    /** 同期年份 input:yyyy-MM **/
    public static String conYear(String date){
        if(StringUtils.isEmpty(date)){
            return null;
        }
        // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        LocalDate ldt = LocalDate.parse(date,FORMAT_DAY);
        LocalDate ldtc = ldt.plusMonths(-12L);
        return ldtc.format(FORMAT_DAY).substring(0,7);
    }

    /** 计算同期月份时间: 存在?返回时间:返回null **/
    public static String conMonth(String date){
        if(StringUtils.isEmpty(date)){
            return null;
        }
        LocalDate ldt = LocalDate.parse(date,FORMAT_DAY);
        LocalDate ldtc = ldt.plusMonths(-12L);
        String tdate = ldtc.format(FORMAT_DAY);
        // 计算同期时间
        if(date.substring(7).equals(tdate.substring(7))){
            return tdate;
        }else{
            log.info("不存在同期时间:{}",date);
            return null;
        }
    }

    /** 相距天数 **/
    public static int rangeDays(String start_date,String end_date,DateTimeFormatter fmt){
        return rangeDays(LocalDate.parse(start_date,(null==fmt)?DateUtil.FORMAT_DAY:fmt),LocalDate.parse(end_date,(null==fmt)?DateUtil.FORMAT_DAY:fmt));
    }
    public static int rangeDays(LocalDate start_date,LocalDate end_date){
//        return Period.between(start_date,end_date).getDays();
        return  (int)start_date.until(end_date, ChronoUnit.DAYS);
    }
    public static Long rangeMinutes(LocalDateTime start_date,LocalDateTime end_date){
        return  start_date.until(end_date, ChronoUnit.MINUTES);
    }
    public static long betweenNanos(LocalDateTime start,LocalDateTime end){
        if( null== start || null==end){
            return 0L;
        }
        return Duration.between(start,end).toMillis()/1000;
    }

//    /**
//     * 获取服务器启动时间
//     */
//    public static LocalDateTime getServerStartDate() {
//        long timestamp = ManagementFactory.getRuntimeMXBean().getStartTime();
//        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp),zoneId);
//    }
//    public static String getServerStartDateStr() {
//        long timestamp = ManagementFactory.getRuntimeMXBean().getStartTime();
//        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp),zoneId).format(FORMAT_DAY_TIME);
//    }
//
//    public static void main(String[] args) {
//        System.out.println(addDays(-1L));
//    }
}
