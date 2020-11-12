package com.bixi.crud.test.domain;

import com.bixi.crud.config.BixiEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@BixiEntity
@Table(name = "sys_config")
public class Config {
    @Id
    @Column(name = "config_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String config_key;

    private String config_value;
}
