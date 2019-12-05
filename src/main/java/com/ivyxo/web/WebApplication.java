package com.ivyxo.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan(basePackages = {"com.ivyxo.web.dao","com.ivyxo.web.test.dao"})
/**
 * 开启spring boot事务支持
 */
@EnableTransactionManagement
public class WebApplication {

	public static void main(String[] args) {
		System.out.println("启动中");
		SpringApplication.run(WebApplication.class, args);
		System.out.println("启动完成");
	}

}
