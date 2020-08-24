package com.bixi.crud.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @author Zheng Jie
 * @date 2019-6-4 14:59:48
 */
@Slf4j
@SuppressWarnings({"unchecked","all"})
public class QueryHelp {

    public static <R, Q> Predicate getPredicate(Root<R> root, Q query, CriteriaBuilder cb) {
        List<Predicate> list = new ArrayList<>();
        if(query == null){
            return cb.and(list.toArray(new Predicate[0]));
        }
        List<Field> fields = getAllFields(query.getClass(), new ArrayList<>());
        List<String> blurrys = Arrays.asList("name");
        if(CollectionUtils.isEmpty(blurrys)){

        }
        for (Field field : fields) {
            boolean accessible = field.isAccessible();
            field.setAccessible(true);
        }
        return cb.and(list.toArray(new Predicate[0]));
    }

    public static List<Field> getAllFields(Class clazz, List<Field> fields) {
        if (clazz != null) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            getAllFields(clazz.getSuperclass(), fields);
        }
        return fields;
    }
}
