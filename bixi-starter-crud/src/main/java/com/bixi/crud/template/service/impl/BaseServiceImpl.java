package com.bixi.crud.template.service.impl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import com.bixi.crud.dto.QueryCriteria;
import com.bixi.crud.template.service.BaseService;
import com.bixi.crud.test.domain.BaseEntity;
import com.bixi.crud.utils.ExcelUtils;
import com.bixi.crud.utils.PageUtil;
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
import java.util.Optional;

/**
 * @Description: service template implementation
 * @author Cao Xueliang
 * @date 2020/9/1 21:46
 **/
@Data
public class BaseServiceImpl<T> implements BaseService<T> {

    private JpaRepositoryImplementation jpaRepositoryImplementation;

    private Class<T> entityClazz;

    @Override
    public Map<String, Object> queryAll(QueryCriteria criteria, Pageable pageable) {
        Page<T> page = jpaRepositoryImplementation.findAll((root, query, cb) -> QueryHelp.getPredicate(root, criteria, cb), pageable);
        return PageUtil.toPage(page);
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
        this.jpaRepositoryImplementation.save(resources);
    }

    @Override
    public void delete(List<Long> ids) {
        for (Long id:ids){
            this.jpaRepositoryImplementation.deleteById(id);
        }
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
        String tableName=this.entityClazz.getSimpleName();
        Long currentTimeStamp=System.currentTimeMillis();
        String title=tableName;
        String sheetName=tableName;
        String fileName=tableName+currentTimeStamp;
        ExcelUtils.exportExcel(queryAll,title,sheetName,entityClazz,fileName,response);
    }
}
