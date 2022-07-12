package com.fse.userskillcombiner.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;


public class Config {
    @Bean
    Logger.Level feigenLoggerLevel(){
        return Logger.Level.FULL;
    }
}
