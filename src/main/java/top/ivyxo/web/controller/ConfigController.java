package top.ivyxo.web.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import top.ivyxo.web.common.data.EStatusCode;
import top.ivyxo.web.common.data.RedisKeyPrefix;
import top.ivyxo.web.common.data.ResponseObj;
import top.ivyxo.web.common.tools.DateUtil;
import top.ivyxo.web.common.tools.RedisUtil;
import top.ivyxo.web.dao.SysConfigDao;
import top.ivyxo.web.entity.SySConfigDO;
import top.ivyxo.web.model.SySConfigVO;
import top.ivyxo.web.service.SysConfigService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author HYR
 */
@RestController
@RequestMapping("/config")
@Api(tags = "系统接口")
public class ConfigController {

    private static final Logger LOG = LoggerFactory.getLogger(ConfigController.class);

    @Value("${server.port}")
    private String port;

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private SysConfigService sysConfigService;

    @Autowired
    private SysConfigDao sysConfigDao;

    @RequestMapping(value = "/state",method = RequestMethod.GET)
    @ApiOperation(value = "获取项目运行状态", notes = "获取项目运行状态")
    public ResponseObj<String> getConfigState(){
        LOG.error("测试测试:{}","测试成功");
        ResponseObj<String> res = new ResponseObj<>();
        String testRedisKey = "redis_test_key:" + port;
        try {
            redisUtil.set(testRedisKey, DateUtil.getNowTime());
        }catch (Exception e){
            res.data = "连接不到redis";
            return res;
        }
        String daoJsonStr = "";
        try {
            SySConfigDO sySConfigDO = sysConfigDao.selectByKey("web_url");
            if(sySConfigDO != null){
                daoJsonStr = JSONObject.toJSONString(sySConfigDO);
            }
        }catch (Exception e){
            res.data = "连接不到mysql";
            return res;
        }
        res.data = "项目运行端口:" + port
                + "\n访问域名:" + httpServletRequest.getServerName()
                + "\n访问端口:" + httpServletRequest.getServerPort()
                + "\n请求地址:" + httpServletRequest.getServletPath()
                + "\n获取redis值:" + redisUtil.get(testRedisKey)
                + "\n获取mysql数据库值:" + daoJsonStr;
        return res;
    }


    @RequestMapping(value = "/info",method = RequestMethod.GET)
    @ApiOperation(value="获取网站信息", notes="直接获取")
    public ResponseObj<List<SySConfigVO>> info(){
        return sysConfigService.list();
    }

    /**
     * 首页检查用户是否登录 Richard - 2019-12-24 10:28:59
     * @return
     */
    @RequestMapping(value = "/checkLogin",method = RequestMethod.POST)
    @ApiOperation(value = "检查用户是否登录", notes = "检查用户是否登录")
    public ResponseObj<String> checkLogin(){
        ResponseObj<String> res = new ResponseObj<>();
        String userId = httpServletRequest.getParameter("userId");
        String userSession = httpServletRequest.getParameter("userSession");
        if(StringUtils.isAnyEmpty(userId, userSession)
                || !userSession.equals(DigestUtils.md5Hex(userId))
                || redisUtil.get(RedisKeyPrefix.USER + userId) == null){
            LOG.info("进入首页，用户未登录userId:{},userSession:{}", userId, userSession);
            res.code = EStatusCode.CHECK_NOT_LOGIN.getCode();
            res.msg = EStatusCode.CHECK_NOT_LOGIN.getMsg();
            return res;
        }
        return res;
    }
}
