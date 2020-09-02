package com.bixi.crud.template.controller;

import com.bixi.crud.dto.QueryCriteria;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface BaseController<T> {
    void download(HttpServletResponse response, QueryCriteria criteria) throws IOException;

    ResponseEntity<Object> queryAll();

    ResponseEntity<Object> query(QueryCriteria resources, Pageable pageable);

    ResponseEntity<Object> create(@Validated @RequestBody String json);

    ResponseEntity<Object> update( @RequestBody String json);

    ResponseEntity<Object> delete(@RequestBody List<Long> ids);
}
