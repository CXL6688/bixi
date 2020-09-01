package com.bixi.crud.config;

import com.bixi.crud.finder.BixiEntityFinder;
import com.bixi.crud.register.BeanRegister;
import com.bixi.crud.register.JpaRepositoryAutoRegister;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;

public class AutoCrudLauncher {
    @Autowired
    private BixiEntityFinder bixiEntityFinder;

    @Autowired
    private List<BeanRegister> beanRegisters;

    @Autowired
    private JpaRepositoryAutoRegister jpaRepositoryAutoRegister;

    @PostConstruct
    private void start(){
        List<Class> entities = bixiEntityFinder.findAll();
        entities.stream().forEach(entity->{
            beanRegisters.forEach(beanRegister -> {
                beanRegister.regist(entity);
            });
        });
    }
}
