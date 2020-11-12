/*
 *  Copyright 2019-2020 Zheng Jie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.bixi.crud.test.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.bixi.crud.config.BixiEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;


import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Zheng Jie
 * @date 2019-04-10
 */
@BixiEntity
@Entity
@Getter
@Setter
@Component
@Table(name = "sys_dict")
public class Dict extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Excel(name = "名称", orderNum = "0")
    private String name;
    @Excel(name = "描述", orderNum = "1")
    private String description;
}