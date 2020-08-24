package com.bixi.crud;

import com.bixi.crud.config.EnableCrud;
import com.bixi.crud.domain.User;
import com.bixi.crud.dto.QueryCriteria;
import com.bixi.crud.service.BaseService;
import com.bixi.crud.utils.NameUtils;
import com.bixi.crud.utils.QueryHelp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import java.util.Arrays;

@SpringBootApplication
@EnableCrud
public class AppRunner {

    public static void main(String[] args) {
        ApplicationContext applicationContext= SpringApplication.run(AppRunner.class, args);
        String beanName= NameUtils.generateServiceNameByEntity(User.class);
        BaseService<User> baseService= (BaseService<User>) applicationContext.getBean(beanName);
        User user=new User();
        user.setName("小张");
        baseService.create(user);
        System.out.println("finished!");
    }

}
