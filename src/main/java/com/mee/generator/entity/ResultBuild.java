package com.mee.generator.entity;

import cn.hutool.core.io.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/***
 * @author shadow
 * @description 静态结构体(用于返回固定参数)
 */
public class ResultBuild implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(ResultBuild.class);

    /**
     * 状态值定义(status)：
     *     -1:  登录过期
     *      0:  失败e
     *      1:  成功
     *      2:  padding
     */

//    /** 保存成功 **/
//    @Deprecated
//    public static final Map<String,Object> SUCCESS =  new HashMap<String,Object>(2,1){{
//        put("status",1);
//        put("msg","成功");
//    }};
    /** 保存成功 **/
    public static final Map<String,Object> SUCCESS(){
        return new HashMap<String,Object>(2,1){{
            put("status",1);
            put("msg","成功");
            put("timestamp",System.currentTimeMillis());
        }};
    }

    /** 保存失败 **/
    @Deprecated
    public static final HashMap<String,Object> FAIL =  new HashMap<String,Object>(2,1){{
        put("status",0);
        put("msg","失败");
    }};

    /** 自定义失败内容 **/
    public static Map<String,Object> fail(String msg){
        return new HashMap<String,Object>(3,1){{
            put("status",0);
            put("msg", !StringUtils.hasText(msg)?"失败":msg);
            put("timestamp",System.currentTimeMillis());
        }};
    }

    /** 自定义失败内容 **/
    public static Map<String,Object> success(String msg){
        return new HashMap<String,Object>(3,1){{
            put("status",1);
            put("msg", !StringUtils.hasText(msg)?"成功":msg);
            put("timestamp",System.currentTimeMillis());
        }};
    }

    public static<T extends Object> Map<String,Object> success(T data){
        return new HashMap<String,Object>(4,1){{
            put("status",1);
            put("msg", "success");
            put("timestamp",System.currentTimeMillis());
            put("data",data);
        }};
    }

    /** 保存成功,处理中 **/
    public static final HashMap<String,Object> PADDING(){
        return new HashMap<String,Object>(2,1){{
            put("status",2);
            put("msg","处理中");
            put("timestamp",System.currentTimeMillis());
        }};
    }

    public static Map<String,Object> padding(String msg){
        return new HashMap<String,Object>(3,1){{
            put("status",2);
            put("msg", !StringUtils.hasText(msg)?"处理中":msg);
            put("timestamp",System.currentTimeMillis());
        }};
    }

    // 验证失败(会重定向至登录)
    public static Map<String,Object> toAuthFail(String msg){
        return new HashMap<String,Object>(3,1){{
            put("status",-1);
            put("msg", !StringUtils.hasText(msg)?"处理中":msg);
            put("timestamp",System.currentTimeMillis());
        }};
    }
    public static void toAuthFail(HttpServletResponse response,String msg){
        try {
            final String json_str =  "{\"msg\":\""+msg+"\",\"status\":-1,\"timestamp\":"+System.currentTimeMillis()+"}";
            // 由于状态在拦截器过后会被重写500，所以这里写401是没有意义的
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(HttpStatus.OK.value());
            response.getWriter().write(json_str);
        }catch (Exception e){
            e.printStackTrace();
            log.error("写入数据异常了:{}",msg,e);
        }
    }
    public static void toNoPermissionFail(HttpServletResponse response,String msg){
        try {
            String json_str =  "{\"msg\":\""+msg+"\",\"status\":0}";
//            String json_str =  "{\"msg\":\""+msg+"\",\"status\":-2}";
            // 由于状态在拦截器过后会被重写500，所以这里写401是没有意义的
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(HttpStatus.OK.value());
            response.getWriter().write(json_str);
        }catch (Exception e){
            e.printStackTrace();
            log.error("写入数据异常了:{}",msg,e);
        }
    }

//    public static void toAuthFail(HttpServletResponse response,String json_str){
//        try {
//            // 由于状态在拦截器过后会被重写500，所以这里写401是没有意义的
//            response.setContentType("application/json;charset=UTF-8");
////            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
////            response.setStatus(HttpStatus.UNAUTHORIZED.value());
//            response.setStatus(HttpStatus.OK.value());
//            response.getWriter().write(json_str);
//        }catch (Exception e){
//            e.printStackTrace();
//            log.error("写入数据异常了:{}",json_str,e);
//        }
//    }

//    public  static <T extends Object> void toResponse(HttpServletResponse response, T data){
//        String json_str = ( null!=data && data instanceof  String )?(String)data:JacksonUtil.toJsonString(data);
//        try {
//            response.setContentType("application/json;charset=UTF-8");
//            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
//            response.getWriter().write(json_str);
//        }catch (Exception e){
//            e.printStackTrace();
//            log.error("写入数据异常了:{}",data,e);
//        }
//    }

    public static void toResponse(HttpServletResponse response, File file) {
        toResponse(response,file,null);
    }
    /**
     * 文件写入至Response
     * @param response 响应
     * @param file  文件
     * @param fileName 下载文件名
     */
    public static void toResponse(HttpServletResponse response, File file, String fileName){
        if(null == fileName){
            fileName = file.getName();
        }
        try {
            try (OutputStream outputStream = response.getOutputStream()) {
                /**
                 * 设置attachment ，防止下载文件出现未知文件类型/文件名
                 */
                response.reset();
                response.setContentType("application/octet-stream");
                response.addHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "UTF-8"));
                outputStream.write(FileUtil.readBytes(file));
            } catch (Exception iOException) {
                log.error("文件 下载失败! ", iOException);
            }
        }finally {
            //文件删除
            try {
                // FileUtils.forceDelete(file);
                file.delete();
            } catch (Exception e) {
                log.error("删除文件出错...:", e);
            }
        }
    }

    // 是否OK
    public static boolean is_ok(Map result){
       if(null == result || !result.containsKey("status") || 1!=(Integer)result.get("status")){
           return Boolean.FALSE;
       }
       return Boolean.TRUE;
    }

}
