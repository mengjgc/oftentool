package com.my.xa.core.application;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication( scanBasePackages = { "com.xabkwd" } )
@EnableJpaRepositories(basePackages = "com.xabkwd" )
@EntityScan( basePackages = "com.xabkwd" )
@EnableJpaAuditing
@EnableCaching
@EnableRedisRepositories
@EnableSpringDataWebSupport
@ServletComponentScan( basePackages = "com.xabkwd" )
//开启定时任务
@EnableScheduling
@EnableAsync
public class CoreApplication {
    private static final Logger LOG = LoggerFactory.getLogger( CoreApplication.class.getName() );
    public static ApplicationContext AppContext;
    public static void main(String[] args) {
        AppContext = SpringApplication.run( CoreApplication.class, args );
    }
    @Bean
    public CommandLineRunner beanInfo(ApplicationContext ctx ){
        return args -> {
            LOG.info( "xabkdatahandler构建完成!" );
        };
    }
    @Bean
    public ObjectMapper cwcObjectMapper() {
        return new ObjectMapper();
    }
}