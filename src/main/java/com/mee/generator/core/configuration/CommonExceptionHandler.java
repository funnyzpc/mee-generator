package com.mee.generator.core.configuration;

import com.mee.generator.util.ResultBuild;
import com.mee.generator.util.JacksonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * @author shadow
 * @description 全局异常代理~
 */
@ControllerAdvice
public class CommonExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(CommonExceptionHandler.class);

    @ExceptionHandler(value =Exception.class)
    @ResponseBody
//    @ResponseStatus(reason = "ERROR",code = HttpStatus.INTERNAL_SERVER_ERROR )
    public void exceptionHandler(Exception e, HttpServletResponse response)throws Exception{
        // 先记录
        log.error("异常了:",e);
        String msg = e.getMessage();
        String json_str = JacksonUtil.toJsonString(ResultBuild.fail(null == msg ? "异常:未知原因~" : "异常:" + msg));
//        response.setContentType(UTF_8.INSTANCE.name());
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.getWriter().write(json_str);
        // 再返回
//        return ResultBuild.fail(null==msg?"异常:未知原因~":"异常:"+msg);
    }

}
