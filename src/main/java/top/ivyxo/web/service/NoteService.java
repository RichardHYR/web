package top.ivyxo.web.service;

import top.ivyxo.web.common.data.PageInfo;
import top.ivyxo.web.common.data.ResponseObj;
import top.ivyxo.web.model.NoteQuery;
import top.ivyxo.web.model.UNoteDetailVO;


/**
 * 笔记服务 Richard - 2020-1-15 16:29:55
 * @author HYR
 */
public interface NoteService {

    /**
     * 获取分页笔记方法 Richard - 2020-1-15 16:42:55
     * @param userId 用户id
     * @param page 页码
     * @param pageSize 单页数量
     * @param search 笔记标题内容
     * @return
     */
    ResponseObj<PageInfo> page(Long userId, String search, Integer page, Integer pageSize);

    /**
     * 插入笔记 Richard - 2020-1-19 16:10:05
     * @param userId 用户id
     * @param noteQuery 笔记内容
     * @return
     */
    ResponseObj<Integer> insert(Long userId, NoteQuery noteQuery);

    /**
     * 更新笔记信息 Richard - 2020-1-19 16:10:26
     * @param userId 用户id
     * @param noteId 笔记列表id
     * @param noteQuery 笔记实体
     * @return
     */
    ResponseObj<Integer> update(Long userId, Long noteId, NoteQuery noteQuery);

    /**
     * 根据笔记id获取笔记详情记录 Richard - 2020-2-12 21:16:46
     * @param userId 用户Id
     * @param noteId 笔记id
     * @return
     */
    ResponseObj<UNoteDetailVO> get(Long userId, Long noteId);

}
