package com.bixi.crud.template.service.impl;

import com.bixi.crud.dto.QueryCriteria;
import com.bixi.crud.template.service.BaseService;
import com.bixi.crud.utils.QueryHelp;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
public class BaseServiceImpl<T> implements BaseService<T> {

    private JpaRepositoryImplementation jpaRepositoryImplementation;

    @Override
    public Map<String, Object> queryAll(QueryCriteria criteria, Pageable pageable) {
        Page<T> page = jpaRepositoryImplementation.findAll((root, query, cb) -> QueryHelp.getPredicate(root, criteria, cb), pageable);
        return new HashMap<>();
    }

    @Override
    public List<T> queryAll(QueryCriteria criteria) {
        List<T> list = jpaRepositoryImplementation.findAll((root, query, cb) -> QueryHelp.getPredicate(root, criteria, cb));
        return list;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(T resources) {
        this.jpaRepositoryImplementation.save(resources);
    }

    @Override
    public void update(T resources) {

    }

    @Override
    public void delete(Set<Long> ids) {

    }

    /**
     * @Description: TODO
     * @param queryAll:
     * @param response: 
     * @return: void
     * @throws
     * @author Cao Xueliang
     * @date 2020/9/1 9:04
     **/
    @Override
    public void download(List<T> queryAll, HttpServletResponse response) throws IOException {

    }
}
