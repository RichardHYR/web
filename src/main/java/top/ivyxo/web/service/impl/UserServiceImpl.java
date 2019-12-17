package top.ivyxo.web.service.impl;

import com.alibaba.fastjson.JSONObject;
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
import top.ivyxo.web.service.code.EUserServiceCode;
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
            res.code = EUserServiceCode.ACCOUNT_EXIST.getCode().toString();
            res.msg = EUserServiceCode.ACCOUNT_EXIST.getMsg();
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
            res.code = EUserServiceCode.FAIL.getCode().toString();
            res.msg = EUserServiceCode.FAIL.getMsg();
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
            res.code = EUserServiceCode.ACCOUNT_NOT_EXIST.getCode().toString();
            res.msg = EUserServiceCode.ACCOUNT_NOT_EXIST.getMsg();
            return res;
        }
        //2.密码和数据库是否吻合
        if(!userDO.getPassword().equals(DigestUtils.md5Hex(password))
                && !userDO.getPassword().equals(password)){
            LOG.warn("密码错误,输入的账户和密码分别为:{},{}",account,password);
            res.code = EUserServiceCode.PASSWORD_MISTAKE.getCode().toString();
            res.msg = EUserServiceCode.PASSWORD_MISTAKE.getMsg();
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
        res.data = EUserServiceCode.SUCCESS.getCode();
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
            res.code = EUserServiceCode.FAIL.getCode().toString();
            res.msg = EUserServiceCode.FAIL.getMsg();
            return res;
        }
        res.data = EUserServiceCode.SUCCESS.getCode();
        return res;
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