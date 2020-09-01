package com.bixi.crud.finder;

import java.util.List;

/**
 * @Description: finder for detect entities delegate by bixi
 * @author cao xueliang
 * @date 2020/9/19:00
*/
public interface BixiEntityFinder {
    /**
     * @Description: find all entities delegate by bixi
     * @return: java.util.List<java.lang.Class> all entity classes
     * @throws
     * @author Cao Xueliang
     * @date 2020/9/1 9:01
     **/
    List<Class> findAll();
}
