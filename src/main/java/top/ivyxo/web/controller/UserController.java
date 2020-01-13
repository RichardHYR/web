package top.ivyxo.web.controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.ivyxo.web.common.data.EStatusCode;
import top.ivyxo.web.common.data.ResponseObj;
import top.ivyxo.web.model.UUserVO;
import top.ivyxo.web.model.UserRegisterQuery;
import top.ivyxo.web.model.UserUpdateQuery;
import top.ivyxo.web.service.UserService;
import top.ivyxo.web.service.utils.UserHolder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户操作控制层 Richard - 2019-12-2 12:16:18
 * @author HYR
 */
@RestController
@RequestMapping("/v1")
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    private UserService userService;

    /**
     * 用户注册 Richard - 2019-12-2 12:16:02
     * @param userRegisterQuery 用户注册相关信息
     * @return
     */
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public ResponseObj<UUserVO> register(@RequestBody UserRegisterQuery userRegisterQuery){
        ResponseObj<UUserVO> res = new ResponseObj<>();
        if(StringUtils.isAnyEmpty(userRegisterQuery.getAccount(), userRegisterQuery.getName()
                                    , userRegisterQuery.getPsw1(), userRegisterQuery.getPsw2())){
            res.code = EStatusCode.INVALID_PARAM.getCode();
            res.msg = EStatusCode.INVALID_PARAM.getMsg();
            LOG.info("register warning:{},param:{}",res.msg, JSONObject.toJSONString(userRegisterQuery));
            return res;
        }
        if(!userRegisterQuery.getPsw1().equals(userRegisterQuery.getPsw2())){
            res.code = EStatusCode.ACCOUNT_PASSWORD_FAIL.getCode();
            res.msg = EStatusCode.ACCOUNT_PASSWORD_FAIL.getMsg();
            LOG.info("register warning:{},param:{}",res.msg,JSONObject.toJSONString(userRegisterQuery));
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
    @RequestMapping(value = "/login/{account}/{password}",method = RequestMethod.GET)
    public ResponseObj<UUserVO> login(@PathVariable("account")String account
            , @PathVariable("password")String password){
        ResponseObj<UUserVO> res = new ResponseObj<>();
        if(StringUtils.isAnyEmpty(account,password)){
            res.code = EStatusCode.INVALID_PARAM.getCode();
            res.msg = EStatusCode.INVALID_PARAM.getMsg();
            LOG.info("login warning:{},param:{}",res.msg,account + ":" + password);
            return res;
        }
        res = userService.login(account,password);
        return res;
    }

    /**
     * 退出登录 Richard - 2019-12-5 15:06:15
     * @return
     */
    @RequestMapping(value = "/loginOut",method = RequestMethod.DELETE)
    public ResponseObj<Integer> loginOut(){
        ResponseObj<Integer> res = new ResponseObj<>();
        String userIdStr = httpServletRequest.getHeader("user_id");
        String userSession = httpServletRequest.getHeader("user_session");
        if(StringUtils.isAnyEmpty(userIdStr, userSession)
            || !userSession.equals(DigestUtils.md5Hex(userIdStr))){
            res.code = EStatusCode.NOT_LOGIN.getCode();
            res.msg = EStatusCode.NOT_LOGIN.getMsg();
            LOG.info("loginOut warning:{}",res.msg);
            return res;
        }
        Long userId = Long.valueOf(userIdStr);
        return userService.loginOut(userId);
    }

    /**
     * 用户更新 Richard - 2019-12-2 12:17:28
     * @param userUpdateQuery 用户更新信息
     * @return
     */
    @RequestMapping(value = "/user/{userSession}",method = RequestMethod.PUT)
    public ResponseObj<Integer> update(@PathVariable("userSession") String userSession
                                        ,@RequestBody UserUpdateQuery userUpdateQuery){
        ResponseObj<Integer> res = new ResponseObj<>();
        Long userId = (Long) UserHolder.get(UserHolder.USER_ID_KEY);
        if(!userSession.equals(DigestUtils.md5Hex(userId + ""))){
            res.code = EStatusCode.NOT_LOGIN.getCode();
            res.msg = EStatusCode.NOT_LOGIN.getMsg();
            LOG.info("update warning:{},params=userSession:{},body:{}", res.msg, userSession, userUpdateQuery.toString());
            return res;
        }
        Integer type = userUpdateQuery.getType();
        //TODO: 提取类型魔法值，在实体类获取
        if (StringUtils.isAnyEmpty(type+"") || type <= 0 || type >= 3){
            LOG.info("updatePsw warning,params=userSession:{},body:{}"
                    ,userSession, userUpdateQuery.toString());
            res.code = EStatusCode.INVALID_PARAM.getCode();
            res.msg = EStatusCode.INVALID_PARAM.getMsg();
            return res;
        }
        if(type == 2 && StringUtils.isAnyEmpty(userUpdateQuery.getOldPsw(), userUpdateQuery.getNewPsw()
                ,userUpdateQuery.getConfirmPsw())){
            LOG.info("updatePsw warning,params=userSession:{},body:{}"
                    ,userSession, userUpdateQuery.toString());
            res.code = EStatusCode.INVALID_PARAM.getCode();
            res.msg = EStatusCode.INVALID_PARAM.getMsg();
            return res;
        }
        if(type == 1) {
            res = userService.update(userId, userUpdateQuery);
        }else if(type == 2){
            res = userService.updatePassword(userId, userUpdateQuery.getOldPsw()
                    ,userUpdateQuery.getNewPsw(), userUpdateQuery.getConfirmPsw());
        }
        return res;
    }

}
