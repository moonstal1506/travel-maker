package com.ssafy.travelmaker.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(
		basePackages = "com.ssafy.travelmaker.model.mapper"
)
public class DatabaseConfiguration {}