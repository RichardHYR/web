package top.ivyxo.web.dao;

import org.apache.ibatis.annotations.Param;
import top.ivyxo.web.entity.UNoteDetailDO;

/**
 * 笔记详情Dao Richard - 2020-1-19 17:25:28
 * @author HYR
 */
public interface UNoteDetailDao {

    /**
     * 插入笔记详情 Richard - 2020-1-19 17:27:47
     * @param noteDatailDO 笔记详情
     * @return
     */
    Integer insert(UNoteDetailDO noteDatailDO);

    /**
     * 更新笔记详情 Richard - 2020-1-19 17:28:08
     * @param noteDatailDO 笔记详情
     * @return
     */
    Integer update(UNoteDetailDO noteDatailDO);

    /**
     * 查找笔记详情 Richard - 2020-1-19 17:28:23
     * @param id 笔记列表记录id
     * @return
     */
    UNoteDetailDO selectById(@Param("id") Long id);
}
