package com.bixi.crud.test.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.bixi.crud.config.BixiEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@BixiEntity
@Table(name="sys_user")
public class User extends BaseEntity implements Serializable {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Excel(name = "姓名", orderNum = "0")
    private String name;
    @Excel(name = "年龄", orderNum = "1")
    private Integer age;
}
