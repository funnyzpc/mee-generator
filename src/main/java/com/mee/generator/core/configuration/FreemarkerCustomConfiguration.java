package com.mee.generator.core.configuration;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.PostConstruct;

@Configuration
public class FreemarkerCustomConfiguration {
    private static final Logger log = LoggerFactory.getLogger(FreemarkerCustomConfiguration.class);

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Autowired
    private freemarker.template.Configuration configuration;

    @Value("${server.servlet.context-path}")
    private String ctxPath;

    @Value("${spring.application.name}")
    private String applicationName;


    /** 使freemarker支持shiro标签 **/
    @Bean
    public freemarker.template.Configuration getFreemarkerConfiguration(){
        freemarker.template.Configuration configuration = freeMarkerConfigurer.getConfiguration();
//        configuration.setTemplateUpdateDelayMilliseconds(60 * 1000L);
//        configuration.setCacheStorage(new freemarker.cache.MruCacheStorage(24, 96));
        return configuration;
    }


    /** 设置全局环境变量 **/
    @PostConstruct
    public void setConfigure() throws Exception {
        configuration.setSharedVariable("ctxPath", ctxPath);
        configuration.setSharedVariable("applicationName", applicationName);
    }
}
