package top.ivyxo.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.ivyxo.web.common.data.PageInfo;
import top.ivyxo.web.common.data.ResponseObj;
import top.ivyxo.web.model.NoteQuery;
import top.ivyxo.web.model.UNoteDetailVO;
import top.ivyxo.web.service.NoteService;
import top.ivyxo.web.common.tools.UserHolder;


/**
 * 笔记控制层 Richard - 2020-1-15 21:38:32
 * @author HYR
 */
@RestController
@RequestMapping("/v1/note")
@Api(tags = "笔记接口")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ApiOperation(value = "获取笔记列表", notes = "获取用户笔记列表")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "页码", name = "page"),
            @ApiImplicitParam(value = "单页大小", name = "pageSize"),
            @ApiImplicitParam(value = "搜索的内容", name = "search")
    })
    public ResponseObj<PageInfo> page(@RequestParam(value = "page", required = false)Integer page
            , @RequestParam(value = "pageSize", required = false)Integer pageSize
            , @RequestParam(value = "search", required = false)String search){
        ResponseObj<PageInfo> res = new ResponseObj<>();
        Long userId = (Long) UserHolder.get(UserHolder.USER_ID_KEY);
        res = noteService.page(userId, search, page, pageSize);
        return res;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "添加笔记", notes = "添加笔记")
    public ResponseObj<Integer> insert(@RequestBody NoteQuery noteQuery){
        ResponseObj<Integer> res = new ResponseObj<>();
        Long userId = (Long) UserHolder.get(UserHolder.USER_ID_KEY);
        res = noteService.insert(userId, noteQuery);
        return res;
    }

    @RequestMapping(value = "/{noteId}", method = RequestMethod.PUT)
    @ApiOperation(value = "更新笔记", notes = "更新笔记")
    @ApiImplicitParam(value = "笔记id", name = "noteId", required = true)
    public ResponseObj<Integer> update(@PathVariable("noteId") Long noteId
                                        ,@RequestBody NoteQuery noteQuery){
        ResponseObj<Integer> res = new ResponseObj<>();
        Long userId = (Long) UserHolder.get(UserHolder.USER_ID_KEY);
        res = noteService.update(userId, noteId, noteQuery);
        return res;
    }

    @RequestMapping(value = "/{noteId}", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除笔记", notes = "删除笔记")
    @ApiImplicitParam(value = "笔记id", name = "noteId", required = true)
    public ResponseObj<Integer> delete(@PathVariable("noteId") Long noteId){
        ResponseObj<Integer> res = new ResponseObj<>();
        NoteQuery noteQuery = new NoteQuery();
        noteQuery.setDel(1);
        Long userId = (Long) UserHolder.get(UserHolder.USER_ID_KEY);
        res = noteService.update(userId, noteId, noteQuery);
        return res;
    }

    @RequestMapping(value = "/{noteId}", method = RequestMethod.GET)
    @ApiOperation(value = "获取笔记详情", notes = "获取笔记详情")
    @ApiImplicitParam(value = "笔记id", name = "noteId", required = true)
    public ResponseObj<UNoteDetailVO> get(@PathVariable("noteId") Long noteId){
        ResponseObj<UNoteDetailVO> res = new ResponseObj<>();
        Long userId = (Long) UserHolder.get(UserHolder.USER_ID_KEY);
        res = noteService.get(userId, noteId);
        return res;
    }

}
