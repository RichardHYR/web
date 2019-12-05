package com.ivyxo.web.dao;

import com.ivyxo.web.entity.UUserDO;

/**
 * 用户数据表DAO Richard - 2019-12-1 23:44:47
 * @author HYR
 */
public interface UUserDao {

    /**
     * 插入用户 Richard - 2019-12-1 23:48:25
     * @param userEntity 用户信息
     * @return
     */
    Integer insert(UUserDO userEntity);

    /**
     * 查找用户 Richard - 2019-12-1 23:48:37
     * @param id 用户Id
     * @return
     */
    UUserDO selectById(Long id);

    /**
     * 查找用户 Richard - 2019-12-1 23:48:56
     * @param account 用户账户
     * @return
     */
    UUserDO selectByAccount(String account);

    /**
     * 更新用户 Richard - 2019-12-1 23:49:09
     * @param userEntity 用户信息
     * @return
     */
    Integer update(UUserDO userEntity);

}
