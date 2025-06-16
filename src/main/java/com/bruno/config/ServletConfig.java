package com.bruno.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.bruno.controller", "com.bruno.dao", "com.bruno.view", "com.bruno.model"})
public class ServletConfig {

}

