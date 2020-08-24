package com.bixi.crud.controller.impl;

import com.bixi.crud.controller.BaseController;
import com.bixi.crud.dto.QueryCriteria;
import com.bixi.crud.service.BaseService;
import lombok.Data;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Data
public class BaseControllerImpl<T> implements BaseController<T> {

    private BaseService baseService;

    @Override
    @GetMapping(value = "/download")
    public void download(HttpServletResponse response, QueryCriteria criteria) throws IOException {
        baseService.download(baseService.queryAll(criteria), response);
    }

    @Override
    @GetMapping(value = "/all")
    public ResponseEntity<Object> queryAll() {
        return new ResponseEntity<>(this.baseService.queryAll(new QueryCriteria()),HttpStatus.OK);
    }

    @Override
    @GetMapping
    public ResponseEntity<Object> query(QueryCriteria resources, Pageable pageable) {
        return null;
    }

    @Override
    @PostMapping
    public ResponseEntity<Object> create(T resources) {
        baseService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    @PutMapping
    public ResponseEntity<Object> update(T resources) {
        return null;
    }

    @Override
    @DeleteMapping
    public ResponseEntity<Object> delete(Set<Long> ids) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}