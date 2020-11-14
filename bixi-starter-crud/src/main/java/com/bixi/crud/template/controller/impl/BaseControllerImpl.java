package com.bixi.crud.template.controller.impl;

import com.bixi.crud.template.controller.BaseController;
import com.bixi.crud.dto.QueryCriteria;
import com.bixi.crud.template.service.BaseService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Data
public class BaseControllerImpl<T> implements BaseController<T> {

    private BaseService baseService;

    private Class<T> entityClazz;

    private ObjectMapper objectMapper;

    @Override
    @RequestMapping(path = "/download", method = RequestMethod.GET)
    public void download(HttpServletResponse response, QueryCriteria criteria) throws IOException {
        baseService.download(baseService.queryAll(criteria), response);
    }

    @Override
    @RequestMapping(path = "/all", method = RequestMethod.GET)
    public ResponseEntity<Object> queryAll() {
        return new ResponseEntity<>(this.baseService.queryAll(new QueryCriteria()), HttpStatus.OK);
    }

    @Override
    @RequestMapping(path = "", method = RequestMethod.GET)
    public ResponseEntity<Object> query(QueryCriteria queryCriteria, Pageable pageable) {
        return new ResponseEntity<>(this.baseService.queryAll(queryCriteria, pageable), HttpStatus.OK);
    }

    @Override
    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<Object> create(@RequestBody String json) {
        try {
            baseService.create(objectMapper.readValue(json, entityClazz));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("创建成功", HttpStatus.OK);
    }

    @Override
    @RequestMapping(path = "", method = RequestMethod.PUT)
    public ResponseEntity<Object> update(@RequestBody String json) {
        try {
            baseService.update(objectMapper.readValue(json, entityClazz));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("更新成功", HttpStatus.OK);
    }

    @Override
    @RequestMapping(path = "", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@RequestBody List<Long> ids) {
        this.baseService.delete(ids);
        return new ResponseEntity<>("删除成功", HttpStatus.OK);
    }
}
