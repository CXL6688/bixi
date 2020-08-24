package com.bixi.crud.config;

import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import java.lang.annotation.*;

@Component
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface BixiEntity {

}
