package top.ivyxo.web.dao;

import top.ivyxo.web.entity.SySConfigDO;

import java.util.List;

/**
 * 系统配置表DAO Richard - 2019-12-12 21:41:43
 * @author HYR
 */
public interface SysConfigDao {

    /**
     * 获取配置表记录 Richard - 2019-12-12 21:45:45
     * @param id 表id
     * @return
     */
    SySConfigDO selectById(Long id);

    /**
     * 获取配置表记录 Richard - 2019-12-12 21:46:18
     * @param key key值
     * @return
     */
    SySConfigDO selectByKey(String key);

    /**
     * 获取配置表记录列表 Richard - 2019-12-12 21:47:55
     * @param key 相似key
     * @return
     */
    List<SySConfigDO> list(String key);

    /**
     * 插入记录 Richard - 2019-12-12 21:59:12
     * @param sySConfigDO 记录信息
     * @return
     */
    Integer insert(SySConfigDO sySConfigDO);

    /**
     * 更新记录 Richard - 2019-12-12 22:00:15
     * @param sySConfigDO
     * @return
     */
    Integer update(SySConfigDO sySConfigDO);

}
