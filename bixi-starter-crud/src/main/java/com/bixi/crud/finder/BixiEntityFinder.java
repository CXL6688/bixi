package com.bixi.crud.finder;

import java.util.List;

/**
 * @author cao xueliang
 * @Description: finder for detect entities delegate by bixi
 * @date 2020/9/19:00
 */
public interface BixiEntityFinder {
    /**
     * @throws
     * @Description: find all entities delegate by bixi
     * @return: java.util.List<java.lang.Class> all entity classes
     * @author Cao Xueliang
     * @date 2020/9/1 9:01
     **/
    List<Class> findAll();
}
