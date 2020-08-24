package com.bixi.crud.service;

import com.bixi.crud.dto.QueryCriteria;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface BaseService<T> {

    /**
     * 分页查询
     * @param criteria 条件
     * @param pageable 分页参数
     * @return /
     */
    Map<String,Object> queryAll(QueryCriteria criteria, Pageable pageable);

    /**
     * 查询全部数据
     * @param criteria /
     * @return /
     */
    List<T> queryAll(QueryCriteria criteria);

    /**
     * 创建
     * @param resources /
     * @return /
     */
    void create(T resources);

    /**
     * 编辑
     * @param resources /
     */
    void update(T resources);

    /**
     * 删除
     * @param ids /
     */
    void delete(Set<Long> ids);

    /**
     * 导出数据
     * @param queryAll 待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<T> queryAll, HttpServletResponse response) throws IOException;
}