package top.ivyxo.web.dao;

import org.apache.ibatis.annotations.Param;
import top.ivyxo.web.entity.UNoteDO;

import java.util.List;

/**
 * 笔记列表操作层 Richard - 2020-1-18 19:35:59
 * @author HYR
 */
public interface UNoteDao {

    /**
     * 获取列表个数 Richard - 2020-1-18 20:52:24
     * @param userId 用户id
     * @param search 查询参数
     * @return
     */
    Integer countList(@Param("userId") Long userId, @Param("search") String search);

    /**
     * 获取分页列表 Richard - 2020-1-18 20:50:50
     * @param userId 用户id
     * @param search 查询参数
     * @param page 页码
     * @param pageSize 单页大小
     * @return
     */
    List<UNoteDO> page(@Param("userId") Long userId, @Param("search") String search
            , @Param("page") Integer page, @Param("pageSize") Integer pageSize);

    /**
     * 根据id获取笔记记录 Richard - 2020-1-20 15:28:19
     * @param id 笔记列表id
     * @return
     */
    UNoteDO selectById(@Param("id") Long id);

    /**
     * 插入笔记记录 Richard - 2020-1-19 15:41:48
     * @param noteDO 笔记实体
     * @return
     */
    Integer insert(UNoteDO noteDO);


    /**
     * 更新笔记列表信息 Richard - 2020-1-19 15:55:53
     * @param noteDO 更新实体
     * @return
     */
    Integer update(UNoteDO noteDO);

}
