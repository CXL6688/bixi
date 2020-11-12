package com.bixi.crud.register;

/**
 * @author cao xueliang
 * @Description: auto regist different kind of bean by entity class type
 * @date 2020/9/19:14
 */
public interface BeanRegister {
    /**
     * @param entityClass: entity class
     * @throws
     * @Description: regist entity business bean
     * @return: boolean
     * @author Cao Xueliang
     * @date 2020/9/2 11:02
     **/
    boolean regist(Class entityClass);
}
