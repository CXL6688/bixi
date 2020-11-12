package com.bixi.crud.config;

import com.bixi.crud.finder.BixiEntityFinder;
import com.bixi.crud.register.BeanRegister;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;

public class AutoCrudLauncher {
    @Autowired
    private BixiEntityFinder bixiEntityFinder;

    @Autowired
    private List<BeanRegister> beanRegisters;

    /**
     * @throws
     * @Description: invoke
     * @return: void
     * @author Cao Xueliang
     * @date 2020/9/2 10:59
     **/
    @PostConstruct
    private void start() {
        List<Class> entities = bixiEntityFinder.findAll();
        entities.stream().forEach(entity -> {
            beanRegisters.forEach(beanRegister -> {
                beanRegister.regist(entity);
            });
        });
    }
}
