package com.bixi.crud.register;
/**  
 * @Description: auto regist different kind of bean by entity class type
 * @author cao xueliang
 * @date 2020/9/19:14
*/
public interface BeanRegister {
    boolean regist(Class entityClass);
}
