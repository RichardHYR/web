package top.ivyxo.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.ivyxo.web.common.data.PageInfo;
import top.ivyxo.web.common.data.ResponseObj;
import top.ivyxo.web.model.NoteQuery;
import top.ivyxo.web.service.NoteService;
import top.ivyxo.web.service.utils.UserHolder;


/**
 * 笔记控制层 Richard - 2020-1-15 21:38:32
 * @author HYR
 */
@RestController
@RequestMapping("/v1/note")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public ResponseObj<PageInfo> page(@RequestParam(value = "page", required = false)Integer page
            , @RequestParam(value = "pageSize", required = false)Integer pageSize
            , @RequestParam(value = "search", required = false)String search){
        ResponseObj<PageInfo> res = new ResponseObj<>();
        Long userId = (Long) UserHolder.get(UserHolder.USER_ID_KEY);
        res = noteService.page(userId, search, page, pageSize);
        return res;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseObj<Integer> insert(@RequestBody NoteQuery noteQuery){
        ResponseObj<Integer> res = new ResponseObj<>();
        Long userId = (Long) UserHolder.get(UserHolder.USER_ID_KEY);
        res = noteService.insert(userId, noteQuery);
        return res;
    }

    @RequestMapping(value = "/{noteId}", method = RequestMethod.PUT)
    public ResponseObj<Integer> update(@PathVariable("noteId") Long noteId
                                        ,@RequestBody NoteQuery noteQuery){
        ResponseObj<Integer> res = new ResponseObj<>();
        Long userId = (Long) UserHolder.get(UserHolder.USER_ID_KEY);
        res = noteService.update(userId, noteId, noteQuery);
        return res;
    }

}
