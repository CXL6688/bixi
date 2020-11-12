package com.bixi.crud.utils;

import org.springframework.data.domain.Page;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author cao xueliang
 * @Description: convert entities to page
 * @date 2020/9/2 7:59
 */
public class PageUtil {
    public static Map<String, Object> toPage(Page page) {
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", page.getContent());
        map.put("totalElements", page.getTotalElements());
        return map;
    }
}
