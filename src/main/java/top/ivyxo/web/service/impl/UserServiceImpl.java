package top.ivyxo.web.service.impl;

import com.alibaba.fastjson.JSONObject;
import top.ivyxo.web.common.data.EStatusCode;
import top.ivyxo.web.common.data.RedisKeyPrefix;
import top.ivyxo.web.common.data.ResponseObj;
import top.ivyxo.web.common.tools.DateUtil;
import top.ivyxo.web.common.tools.RedisUtil;
import top.ivyxo.web.dao.UUserDao;
import top.ivyxo.web.entity.UUserDO;
import top.ivyxo.web.model.UUserVO;
import top.ivyxo.web.model.UserRegisterQuery;
import top.ivyxo.web.model.UserUpdateQuery;
import top.ivyxo.web.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 用户服务接口实现 Richard - 2019-12-1 23:42:15
 * @author HYR
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    RedisUtil redisUtil;

    long TWO_HOURS = 7200;

    @Autowired
    private UUserDao userDao;

    @Override
    public ResponseObj<UUserVO> register(UserRegisterQuery userRegisterQuery) {
        ResponseObj<UUserVO> res = new ResponseObj<>();
        //1.检查账户名是否重复
        if(select(userRegisterQuery.getAccount()) != null){
            LOG.warn("添加用户,账户已存在,请求实体为:{}",userRegisterQuery.toString());
            res.code = EStatusCode.ACCOUNT_EXIST.getCode();
            res.msg = EStatusCode.ACCOUNT_EXIST.getMsg();
            return res;
        }
        //2.用户密码加密储存
        UUserDO userDO = new UUserDO();
        userDO.setAccount(userRegisterQuery.getAccount());
        userDO.setName(userRegisterQuery.getName());
        userDO.setPassword(DigestUtils.md5Hex(userRegisterQuery.getPsw1()));
        //TODO:提取公用授权id
        userDO.setAuthId(0L);
        if(!insert(userDO)){
            LOG.warn("添加用户失败,请求实体为:{},存储实体为:{}"
                    ,userRegisterQuery.toString(),userDO.toString());
            res.code = EStatusCode.UNKNOWN_ERR.getCode();
            res.msg = EStatusCode.UNKNOWN_ERR.getMsg();
            return res;
        }
        UUserVO userVO = new UUserVO();
        userDO = select(userRegisterQuery.getAccount());
        BeanUtils.copyProperties(userDO,userVO);
        res.data = userVO;
        LOG.info("添加用户成功,返回实体:{}",userVO.toString());
        return res;
    }

    @Override
    public ResponseObj<UUserVO> login(String account, String password) {
        ResponseObj<UUserVO> res = new ResponseObj<>();
        //1.账户是否存在
        UUserDO userDO = select(account);
        if(userDO == null){
            LOG.warn("账户不存在:{}",account);
            res.code = EStatusCode.ACCOUNT_PASSWORD_MISTAKE.getCode();
            res.msg = EStatusCode.ACCOUNT_PASSWORD_MISTAKE.getMsg();
            return res;
        }
        //2.密码和数据库是否吻合
        if(!userDO.getPassword().equals(DigestUtils.md5Hex(password))
                && !userDO.getPassword().equals(password)){
            LOG.warn("密码错误,输入的账户和密码分别为:{},{}",account,password);
            res.code = EStatusCode.ACCOUNT_PASSWORD_MISTAKE.getCode();
            res.msg = EStatusCode.ACCOUNT_PASSWORD_MISTAKE.getMsg();
            return res;
        }
        UUserVO userVO = new UUserVO();
        BeanUtils.copyProperties(userDO,userVO);
        userVO.setUserSession(DigestUtils.md5Hex(userVO.getId().toString()));
        res.data = userVO;
        String userValue = JSONObject.toJSONString(userVO);
        redisUtil.set(RedisKeyPrefix.USER + userVO.getId(),userValue,TWO_HOURS);
        LOG.info("登录成功,用户信息为:{}",userValue);
        return res;
    }

    @Override
    public ResponseObj<Integer> loginOut(Long userId) {
        ResponseObj<Integer> res = new ResponseObj<>();
        //清除缓存
        redisUtil.del(RedisKeyPrefix.USER + userId);
        LOG.info("退出登录成功");
        return res;
    }

    @Override
    public ResponseObj<Integer> update(Long userId, UserUpdateQuery userUpdateQuery) {
        ResponseObj<Integer> res = new ResponseObj<>();
        //2.构建更新实体类
        UUserDO userDO = new UUserDO();
        BeanUtils.copyProperties(userUpdateQuery,userDO);
        userDO.setId(userId);
        // 转换日期类
        if(StringUtils.isNoneEmpty(userUpdateQuery.getBirthday())){
            userDO.setBirthday(DateUtil.parseDate(userUpdateQuery.getBirthday()));
        }
        //3.返回结果
        if(!update(userDO)){
            LOG.warn("更新失败,用户id为:{},请求实体为:{}",userId,userUpdateQuery.toString());
            res.code = EStatusCode.UNKNOWN_ERR.getCode();
            res.msg = EStatusCode.UNKNOWN_ERR.getMsg();
            return res;
        }
        LOG.info("更新成功");
        return res;
    }

    @Override
    public ResponseObj<Integer> updatePassword(Long userId, String oldPsw, String newPsw
            ,String confirmPsw) {
        ResponseObj<Integer> res = new ResponseObj<>();
        /**
         * 1.获取用户的记录，根据id
         * 2.获取用户的密码
         * 3.将加密后的旧密码与用户的密码对比
         * 4.相同则继续，不相同则返回旧密码错误
         * 5.确认新密码和旧密码是否相同，不同则继续，相同提示请不要设置与旧密码相同的密码
         * 6.确认新密码和确认密码是否相同，相同则更新，不同则提示二次确认密码错误
         * 7.更新，返回数据库操作
         */
        if(StringUtils.isAnyEmpty(userId + "", oldPsw, newPsw, confirmPsw)){
            LOG.warn("更新密码数据缺失操作:userId:{}, oldpsw:{}, newPsw:{}, confirmPsw:{}"
                    ,userId, oldPsw, newPsw, confirmPsw);
            res.code = EStatusCode.INVALID_PARAM.getCode();
            res.msg = EStatusCode.INVALID_PARAM.getMsg();
            return res;
        }
        if(oldPsw.equals(newPsw)){
            LOG.warn("新密码与旧密码相同:oldpsw:{}, newPsw:{}"
                    ,oldPsw, newPsw);
            res.code = EStatusCode.UPDATE_PASSWORD_REPEAT.getCode();
            res.msg = EStatusCode.UPDATE_PASSWORD_REPEAT.getMsg();
            return res;
        }
        if(!newPsw.equals(confirmPsw)){
            LOG.warn("二次确认密码不同:newPsw:{}, confirmPsw:{}"
                    ,newPsw, confirmPsw);
            res.code = EStatusCode.UPDATE_PASSWORD_NOT_SAME.getCode();
            res.msg = EStatusCode.UPDATE_PASSWORD_NOT_SAME.getMsg();
            return res;
        }
        UUserDO uUserDO = select(userId);
        if(uUserDO == null){
            LOG.warn("该用户id找不到用户信息:userId:{}",userId);
            res.code = EStatusCode.UNKNOWN_ERR.getCode();
            res.msg = EStatusCode.UNKNOWN_ERR.getMsg();
            return res;
        }
        if(!uUserDO.getPassword().equals(DigestUtils.md5Hex(oldPsw))){
            LOG.warn("旧密码错误:md5(psw):{}, md5(oldPsw):{}"
                    ,uUserDO.getPassword(), DigestUtils.md5Hex(oldPsw));
            res.code = EStatusCode.UPDATE_PASSWORD_FAIL.getCode();
            res.msg = EStatusCode.UPDATE_PASSWORD_FAIL.getMsg();
            return res;
        }
        UUserDO userDO = new UUserDO();
        userDO.setId(userId);
        userDO.setPassword(DigestUtils.md5Hex(newPsw));
        if(!update(userDO)){
            LOG.warn("更新密码失败:userDO:{}",userDO.toString());
            res.code = EStatusCode.UNKNOWN_ERR.getCode();
            res.msg = EStatusCode.UNKNOWN_ERR.getMsg();
            return res;
        }
        //退出登录
        redisUtil.del(RedisKeyPrefix.USER + userId);
        return res;
    }

    @Override
    public UUserVO selectById(Long id) {
        UUserDO userDO = select(id);
        if(userDO == null){
            return null;
        }
        UUserVO userVO = new UUserVO();
        BeanUtils.copyProperties(userDO, userVO);
        return userVO;
    }

    /**
     * 查询用户 Richard - 2019-12-3 13:44:28
     * @param id 用户Id
     * @return
     */
    private UUserDO select(Long id){
        UUserDO userDO = userDao.selectById(id);
        return userDO;
    }

    /**
     * 查询用户 Richard - 2019-12-3 13:44:52
     * @param account 账户
     * @return
     */
    private UUserDO select(String account){
        UUserDO userDO = userDao.selectByAccount(account);
        return userDO;
    }

    /**
     * 添加用户 Richard - 2019-12-3 20:31:18
     * @param userDO 用户信息
     * @return
     */
    private boolean insert(UUserDO userDO){
        Date date = new Date();
        userDO.setGmtCreate(date);
        userDO.setGmtModified(date);
        return userDao.insert(userDO) >= 1;
    }

    /**
     * 更新用户信息 Richard - 2019-12-3 20:38:11
     * @param userDO 用户信息
     * @return
     */
    private boolean update(UUserDO userDO){
        userDO.setGmtModified(new Date());
        return userDao.update(userDO) >= 1;
    }


}