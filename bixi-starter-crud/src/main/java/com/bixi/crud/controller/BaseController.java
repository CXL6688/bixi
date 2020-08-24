package com.bixi.crud.controller;

import com.bixi.crud.dto.QueryCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

public interface BaseController<T> {
    void download(HttpServletResponse response, QueryCriteria criteria) throws IOException;

    ResponseEntity<Object> queryAll();

    ResponseEntity<Object> query(QueryCriteria resources, Pageable pageable);

    ResponseEntity<Object> create(@Validated @RequestBody T resources);

    ResponseEntity<Object> update( @RequestBody T resources);

    ResponseEntity<Object> delete(@RequestBody Set<Long> ids);
}
