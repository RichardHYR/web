package com.ivyxo.web.controller;

import com.ivyxo.web.common.data.EStatusCode;
import com.ivyxo.web.common.data.ResponseObj;
import com.ivyxo.web.model.UUserVO;
import com.ivyxo.web.model.UserRegisterQuery;
import com.ivyxo.web.model.UserUpdateQuery;
import com.ivyxo.web.service.UserService;
import com.ivyxo.web.service.code.EUserServiceCode;
import com.ivyxo.web.service.utils.UserHolder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户操作控制层 Richard - 2019-12-2 12:16:18
 * @author HYR
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户注册 Richard - 2019-12-2 12:16:02
     * @param userRegisterQuery 用户注册相关信息
     * @return
     */
    @RequestMapping(value = "/v1/register",method = RequestMethod.POST)
    public ResponseObj<UUserVO> register(@RequestBody UserRegisterQuery userRegisterQuery){
        ResponseObj<UUserVO> res = new ResponseObj<>();
        if(StringUtils.isAnyEmpty(userRegisterQuery.getAccount(), userRegisterQuery.getName()
                                    , userRegisterQuery.getPsw1(), userRegisterQuery.getPsw2())){
            res.code = EStatusCode.INVALID_PARAM.getCode();
            res.msg = EStatusCode.INVALID_PARAM.getMsg();
            return res;
        }
        if(!userRegisterQuery.getPsw1().equals(userRegisterQuery.getPsw2())){
            res.code = EUserServiceCode.PASSWORD_FAIL.getCode().toString();
            res.msg = EUserServiceCode.PASSWORD_FAIL.getMsg();
            return res;
        }
        res = userService.register(userRegisterQuery);
        return res;
    }

    /**
     * 用户登录 Richard - 2019-12-2 12:17:03
     * @param account 账户
     * @param password 密码
     * @return
     */
    @RequestMapping(value = "/v1/login/{account}/{password}",method = RequestMethod.GET)
    public ResponseObj<UUserVO> login(@PathVariable("account")String account
            , @PathVariable("password")String password){
        ResponseObj<UUserVO> res = new ResponseObj<>();
        if(StringUtils.isAnyEmpty(account,password)){
            res.code = EStatusCode.INVALID_PARAM.getCode();
            res.msg = EStatusCode.INVALID_PARAM.getMsg();
            return res;
        }
        res = userService.login(account,password);
        return res;
    }

    /**
     * 退出登录 Richard - 2019-12-5 15:06:15
     * @return
     */
    @RequestMapping(value = "/v1/loginOut",method = RequestMethod.DELETE)
    public ResponseObj<Integer> loginOut(){
        ResponseObj<Integer> res = new ResponseObj<>();
        Long userId = (Long) UserHolder.get(UserHolder.USER_ID_KEY);
        if(userId == null){
            res.code = EStatusCode.NOTLOGIN.getCode();
            res.msg = EStatusCode.NOTLOGIN.getMsg();
            return res;
        }
        return userService.loginOut(userId);
    }

    /**
     * 用户更新 Richard - 2019-12-2 12:17:28
     * @param userUpdateQuery 用户更新信息
     * @return
     */
    @RequestMapping(value = "/v1/user",method = RequestMethod.PUT)
    public ResponseObj<Integer> update(@RequestBody UserUpdateQuery userUpdateQuery){
        ResponseObj<Integer> res = new ResponseObj<>();
        Long userId = (Long) UserHolder.get(UserHolder.USER_ID_KEY);
        res = userService.update(userId,userUpdateQuery);
        return res;
    }

}
