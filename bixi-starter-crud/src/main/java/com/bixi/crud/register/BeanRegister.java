package com.bixi.crud.register;
/**  
 * @Description: auto regist different kind of bean by entity class type
 * @author cao xueliang
 * @date 2020/9/19:14
*/
public interface BeanRegister {
    /**
     * @Description: regist entity business bean
     * @param entityClass: entity class
     * @return: boolean
     * @throws
     * @author Cao Xueliang
     * @date 2020/9/2 11:02
     **/
    boolean regist(Class entityClass);
}
