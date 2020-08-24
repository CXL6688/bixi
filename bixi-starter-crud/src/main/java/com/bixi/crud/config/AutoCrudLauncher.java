package com.bixi.crud.config;

import com.bixi.crud.reflect.JpaRepositoryAutoRegister;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

public class AutoCrudLauncher {
    @Autowired
    private JpaRepositoryAutoRegister jpaRepositoryAutoRegister;

    @PostConstruct
    private void start(){
        this.jpaRepositoryAutoRegister.detect();
    }
}
