package top.ivyxo.web.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.ivyxo.web.common.data.EStatusCode;
import top.ivyxo.web.common.data.ResponseObj;
import top.ivyxo.web.model.UUserVO;
import top.ivyxo.web.model.UserRegisterQuery;
import top.ivyxo.web.model.UserUpdateQuery;
import top.ivyxo.web.service.UserService;
import top.ivyxo.web.common.tools.UserHolder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户操作控制层 Richard - 2019-12-2 12:16:18
 * @author HYR
 */
@Api(tags = "用户接口")
@RestController
@RequestMapping("/v1")
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     * 用户注册 Richard - 2019-12-2 12:16:02
     * @param userRegisterQuery 用户注册相关信息
     * @return
     */
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    @ApiOperation(value = "注册", notes = "用户注册")
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
    @ApiOperation(value = "登录", notes = "用户登录，该接口为RESETful风格接口")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "账户", name = "account", required = true),
            @ApiImplicitParam(value = "密码", name = "password", required = true)
    })
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
    @ApiOperation(value = "退出登录", notes = "用户退出登录")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "用户id", name = "user_id", required = true),
            @ApiImplicitParam(value = "用户session", name = "user_session", required = true)
    })
    public ResponseObj<Integer> loginOut(@RequestHeader("user_id")String userIdStr
                                            ,@RequestHeader("user_session")String userSession){
        ResponseObj<Integer> res = new ResponseObj<>();
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
    @ApiOperation(value = "更新", notes = "用户更新")
    @ApiImplicitParam(value = "登录凭证", name = "userSession", required = true)
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

    /**
     * 根据用户id获取用户信息 Richard - 2020-3-10 20:52:38
     * @param userId
     * @return
     */
    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    @ApiOperation(value = "获取用户记录", notes = "根据id获取用户记录")
    @ApiImplicitParam(value = "用户id", name = "userId", required = true)
    ResponseObj<UUserVO> getById(@PathVariable("userId") Long userId){
        ResponseObj<UUserVO> res = new ResponseObj<UUserVO>();
        UUserVO userVO = userService.selectById(userId);
        if(userVO == null){
            LOG.info("获取不到该id的用户信息: userId:{}", userId);
            res.code = EStatusCode.UNKNOWN_ERR.getCode();
            res.msg = EStatusCode.UNKNOWN_ERR.getMsg();
        }
        res.data = userVO;
        return res;
    }

    /**
     * 获取用户设置界面的信息 Richard - 2020-3-11 14:55:45
     * @param userId 用户Id
     * @return
     */
    @RequestMapping(value = "/user/setting/{userId}", method = RequestMethod.GET)
    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "header", value = "用户id", name = "user_id", required = true),
//            @ApiImplicitParam(paramType = "header", value = "用户session", name = "user_session", required = true),
            @ApiImplicitParam(value = "用户id", name = "userId", required = true)
    })
    @ApiOperation(value = "获取设置界面信息", notes = "根据id获取信息")
    ResponseObj<UUserVO> getSettingInfo(@PathVariable("userId")Long userId){
        ResponseObj<UUserVO> res = new ResponseObj<UUserVO>();
        res = userService.getSettingInfo(userId);
        return res;
    }



}
