package com.bixi.crud;

import com.bixi.crud.config.EnableCrud;
import com.bixi.crud.dto.QueryCriteria;
import com.bixi.crud.template.controller.BaseController;
import com.bixi.crud.template.controller.impl.BaseControllerImpl;
import com.bixi.crud.test.domain.User;
import com.bixi.crud.template.service.BaseService;
import com.bixi.crud.utils.NameUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

@SpringBootApplication
@EnableCrud
public class AppRunner {
    public static void main(String[] args) {
        SpringApplication.run(AppRunner.class, args);
    }
}
