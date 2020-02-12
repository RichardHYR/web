package top.ivyxo.web.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import top.ivyxo.web.common.data.EStatusCode;
import top.ivyxo.web.common.data.PageInfo;
import top.ivyxo.web.common.data.ResponseObj;
import top.ivyxo.web.dao.UNoteDao;
import top.ivyxo.web.dao.UNoteDetailDao;
import top.ivyxo.web.entity.UNoteDO;
import top.ivyxo.web.entity.UNoteDetailDO;
import top.ivyxo.web.model.NoteQuery;
import top.ivyxo.web.model.UNoteDetailVO;
import top.ivyxo.web.model.UNoteVO;
import top.ivyxo.web.model.UUserVO;
import top.ivyxo.web.service.NoteService;
import org.springframework.stereotype.Service;
import top.ivyxo.web.service.UserService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 笔记服务实现 Richard - 2020-1-15 16:44:17
 * @author HYR
 */
@Service
public class NoteServiceImpl implements NoteService {

    private static final Logger LOG = LoggerFactory.getLogger(NoteServiceImpl.class);

    @Autowired
    private UNoteDao noteDao;

    @Autowired
    private UNoteDetailDao noteDatailDao;

    @Autowired
    private UserService userService;

    @Override
    public ResponseObj<PageInfo> page(Long userId, String search, Integer page, Integer pageSize) {
        ResponseObj<PageInfo> res = new ResponseObj<>();
        PageInfo pageInfo = pageList(userId, search, page, pageSize);
        res.data = pageInfo;
        return res;
    }

    @Override
    public ResponseObj<Integer> insert(Long userId, NoteQuery noteQuery) {
        /**
         * 插入笔记
         * 1.插入笔记，插入笔记详情
         * 2.笔记插入user_id,title,user_name,is_del,is_show
         * 3.笔记详情插入id,user_id,title
         */
        ResponseObj<Integer> res = new ResponseObj<Integer>();
        UUserVO userVO = userService.selectById(userId);
        if(userVO == null){
            LOG.warn("该用户id找不到用户信息:userId:{}",userId);
            res.code = EStatusCode.UNKNOWN_ERR.getCode();
            res.msg = EStatusCode.UNKNOWN_ERR.getMsg();
            return res;
        }

        UNoteDO noteDO = new UNoteDO();
        noteDO.setUserId(userId);
        noteDO.setUserName(userVO.getName());
        noteDO.setTitle(noteQuery.getTitle());

        if(!insert(noteDO)){
            LOG.warn("插入失败:noteDo:{}", noteDO.toString());
            res.code = EStatusCode.UNKNOWN_ERR.getCode();
            res.msg = EStatusCode.UNKNOWN_ERR.getMsg();
            return res;
        }

        UNoteDetailDO noteDetailDO = new UNoteDetailDO();
        noteDetailDO.setId(noteDO.getId());
        noteDetailDO.setUserId(userId);
        noteDetailDO.setTitle(noteQuery.getTitle());
        noteDetailDO.setKeywordContent(noteQuery.getKeywordContent());
        noteDetailDO.setMainContent(noteQuery.getMainContent());
        noteDetailDO.setSummaryContent(noteQuery.getSummaryContent());

        if(!insert(noteDetailDO)){
            LOG.warn("插入失败:noteDo:{}, noteDetailDO:{}", noteDO.toString(), noteDetailDO.toString());
            res.code = EStatusCode.UNKNOWN_ERR.getCode();
            res.msg = EStatusCode.UNKNOWN_ERR.getMsg();
            return res;
        }

        return res;
    }

    @Override
    public ResponseObj<Integer> update(Long userId, Long noteId, NoteQuery noteQuery) {
        /**
         * 1.根据传入的noteId获取笔记列表记录，对比转进来的userId是否一致
         * 2.以noteId去更新记录各字段的值
         * 3.成功/失败处理
         */
        ResponseObj<Integer> res = new ResponseObj<Integer>();
        UNoteDO noteDO = selectNoteById(noteId);
        if(noteDO == null){
            LOG.warn("该noteId查找的笔记记录不存在:noteId:{}",noteId);
            res.code = EStatusCode.UNKNOWN_ERR.getCode();
            res.msg = EStatusCode.UNKNOWN_ERR.getMsg();
            return res;
        }
        if(!noteDO.getUserId().equals(userId)){
            LOG.warn("该笔记记录下的用户id和登录用户的id不符合:note:{},userId:{}"
                    ,noteDO.toString(), userId);
            res.code = EStatusCode.UNKNOWN_ERR.getCode();
            res.msg = EStatusCode.UNKNOWN_ERR.getMsg();
            return res;
        }

        UNoteDetailDO noteDetailDO = selectNoteDetailById(noteId);
        if(noteDetailDO == null){
            /**
             * 1.同时插入笔记详情表记录时发现记录为空，再次执行插入操作
             */
            noteDetailDO = new UNoteDetailDO();
            noteDetailDO.setId(noteId);
            noteDetailDO.setUserId(userId);
            noteDetailDO.setTitle(noteDO.getTitle());
            if(!insert(noteDetailDO)){
                LOG.warn("插入笔记详情表失败:noteId:{}", noteId);
                res.code = EStatusCode.UNKNOWN_ERR.getCode();
                res.msg = EStatusCode.UNKNOWN_ERR.getMsg();
                return res;
            }
            noteDetailDO = selectNoteDetailById(noteId);
        }
        if(!noteDetailDO.getUserId().equals(userId)){
            LOG.warn("该笔记详情记录下的用户id和登录用户的id不符合:noteDetail:{},userId:{}"
                    ,noteDetailDO.toString(), userId);
            res.code = EStatusCode.UNKNOWN_ERR.getCode();
            res.msg = EStatusCode.UNKNOWN_ERR.getMsg();
            return res;
        }

        UNoteDO uNoteDO = new UNoteDO();
        uNoteDO.setId(noteId);
        uNoteDO.setTitle(noteQuery.getTitle());
        uNoteDO.setShow(noteQuery.getShow());
        uNoteDO.setDel(noteQuery.getDel());

        UNoteDetailDO uNoteDetailDO = new UNoteDetailDO();
        uNoteDetailDO.setId(noteId);
        uNoteDetailDO.setTitle(noteQuery.getTitle());
        uNoteDetailDO.setKeywordContent(noteQuery.getKeywordContent());
        uNoteDetailDO.setMainContent(noteQuery.getMainContent());
        uNoteDetailDO.setSummaryContent(noteQuery.getSummaryContent());

        if(!update(uNoteDO)){
            LOG.warn("更新笔记记录表失败:uNoteDO:{}", uNoteDO.toString());
            res.code = EStatusCode.UNKNOWN_ERR.getCode();
            res.msg = EStatusCode.UNKNOWN_ERR.getMsg();
            return res;
        }

        if(!update(uNoteDetailDO)){
            LOG.warn("更新笔记记录详情表失败:uNoteDetailDO:{}", uNoteDetailDO.toString());
            res.code = EStatusCode.UNKNOWN_ERR.getCode();
            res.msg = EStatusCode.UNKNOWN_ERR.getMsg();
            return res;
        }
        return res;
    }

    @Override
    public ResponseObj<UNoteDetailVO> get(Long userId, Long noteId) {
        /**
         * 1.根据noteId获取的记录是否存在，不存在返回错误码
         * 2.noteId获取的列表记录里的userId和登录的userId是否相同,不同返回错误码
         * 3.相同返回详情记录
         */
        ResponseObj<UNoteDetailVO> res = new ResponseObj<UNoteDetailVO>();
        UNoteDetailDO noteDetailDO = selectNoteDetailById(noteId);
        if(noteDetailDO == null){
            LOG.error("该记录下的noteId获取不到笔记详情:noteId:{}", noteId);
            res.code = EStatusCode.UNKNOWN_ERR.getCode();
            res.msg = EStatusCode.UNKNOWN_ERR.getMsg();
            return res;
        }
        UNoteDO noteDO = selectNoteById(noteId);
        if(noteDO == null){
            LOG.warn("获取不到该笔记id的记录:{}", noteId);
            res.code = EStatusCode.UNKNOWN_ERR.getCode();
            res.msg = EStatusCode.UNKNOWN_ERR.getMsg();
            return res;
        }
        if(!userId.equals(noteDO.getUserId())){
            LOG.warn("该记录下的userId和登录的userId不同:userId:{},note:{}", userId, noteDO.toString());
            res.code = EStatusCode.UNKNOWN_ERR.getCode();
            res.msg = EStatusCode.UNKNOWN_ERR.getMsg();
            return res;
        }
        UNoteDetailVO noteDetailVO = new UNoteDetailVO();
        BeanUtils.copyProperties(noteDetailDO, noteDetailVO);
        res.data = noteDetailVO;
        return res;
    }

    /**
     * 翻页获取笔记列表 Richard - 2020-1-19 15:36:25
     * @param userId 用户id
     * @param search 标题检索
     * @param page 页码
     * @param pageSize 分页大小
     * @return
     */
    private PageInfo pageList(Long userId, String search, Integer page, Integer pageSize){
        List<UNoteDO> doList = noteDao.page(userId, search, page, pageSize);
        List<UNoteVO> voList = new ArrayList<>();
        // TODO:提取转换列表实体方法
        for (UNoteDO noteDO:doList){
            UNoteVO vo = new UNoteVO();
            BeanUtils.copyProperties(noteDO,vo);
            voList.add(vo);
        }
        PageInfo pageInfo = new PageInfo(voList, noteDao.countList(userId, search), page, pageSize);
        return pageInfo;
    }

    /**
     * 根据id获取笔记列表记录
     * @param id 笔记列表记录id
     * @return
     */
    private UNoteDO selectNoteById(Long id){
        return noteDao.selectById(id);
    }

    /**
     * 根据id获取笔记详情记录
     * @param id 笔记列表记录id
     * @return
     */
    private UNoteDetailDO selectNoteDetailById(Long id){
        return noteDatailDao.selectById(id);
    }

    /**
     * 插入笔记记录 Richard - 2020-1-19 17:00:01
     * @param noteDO 笔记记录
     * @return
     */
    private boolean insert(UNoteDO noteDO){
        Date date = new Date();
        noteDO.setGmtCreate(date);
        noteDO.setGmtModified(date);
        noteDO.setDel(0);
        noteDO.setShow(1);
        return noteDao.insert(noteDO) > 0;
    }

    /**
     * 插入笔记详情 Richard - 2020-1-19 18:02:48
     * @param noteDatailDO 笔记详情
     * @return
     */
    private boolean insert(UNoteDetailDO noteDatailDO){
        Date date = new Date();
        noteDatailDO.setGmtCreate(date);
        noteDatailDO.setGmtModified(date);
        return noteDatailDao.insert(noteDatailDO) > 0;
    }

    /**
     * 更新笔记记录 Richard - 2020-1-19 17:00:17
     * @param noteDO
     * @return
     */
    private boolean update(UNoteDO noteDO){
        return noteDao.update(noteDO) > 0;
    }

    private boolean update(UNoteDetailDO noteDatailDO){
        return noteDatailDao.update(noteDatailDO) > 0;
    }

}
