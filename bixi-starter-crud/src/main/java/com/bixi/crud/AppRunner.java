package com.bixi.crud;

import com.bixi.crud.config.EnableCrud;
import com.bixi.crud.template.controller.BaseController;
import com.bixi.crud.template.controller.impl.BaseControllerImpl;
import com.bixi.crud.test.domain.User;
import com.bixi.crud.template.service.BaseService;
import com.bixi.crud.utils.NameUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

@SpringBootApplication
@EnableCrud
public class AppRunner {
    public static void main(String[] args) {
        SpringApplication.run(AppRunner.class, args);
    }
}
