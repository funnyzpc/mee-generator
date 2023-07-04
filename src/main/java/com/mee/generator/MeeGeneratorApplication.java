package com.mee.generator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;

/**
* 代码生成启动类
* @className    MeeGeneratorApplication
* @author       shadow
* @date         2023/5/15 10:05
* @version      1.0
*/
@SpringBootApplication
public class MeeGeneratorApplication {
    private static final Logger LOG= LoggerFactory.getLogger(MeeGeneratorApplication.class);

    public static void main(String[] args) throws Exception{
        ConfigurableApplicationContext application = SpringApplication.run(MeeGeneratorApplication.class, args);
        Environment env = application.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        String path = env.getProperty("server.servlet.context-path");
        LOG.info("\n\t----------------------------------------------------------\n\t" +
                "Application MeeGeneratorApplication is running!\n\t" +
                "Local: \t\thttp://localhost:" + port + path + "/\n\t" +
                "External: \thttp://" + ip + ":" + port + path + "/\n\t" +
                "----------------------------------------------------------");
    }
}
