package com.my.xa.core.application;

import com.my.xa.core.application.converter.StringToCalendar;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.format.FormatterRegistry;
import org.springframework.stereotype.Component;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@Component
public class SpringWebMvcCust extends WebMvcConfigurationSupport {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter( new StringToCalendar() );
        super.addFormatters(registry);
    }

    @Override
    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new PageableHandlerMethodArgumentResolver());
        super.addArgumentResolvers(argumentResolvers);
    }
}
