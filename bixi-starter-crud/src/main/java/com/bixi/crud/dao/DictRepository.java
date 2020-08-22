package com.bixi.crud.dao;

import com.bixi.crud.domain.Dict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DictRepository  extends JpaRepository<Dict,Long>, JpaSpecificationExecutor<Dict> {

}
