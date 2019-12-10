package top.ivyxo.web.controller;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import top.ivyxo.web.common.data.ResponseObj;
import top.ivyxo.web.common.tools.DateUtil;
import top.ivyxo.web.dao.UUserDao;
import top.ivyxo.web.entity.UUserDO;

import javax.servlet.http.HttpServletRequest;

/**
 * @author HYR
 */
@RestController
@RequestMapping("/config")
public class ConfigController {

    private static final Logger LOG = LoggerFactory.getLogger(ConfigController.class);

    @Value("${server.port}")
    private String port;

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private UUserDao userDao;

    @RequestMapping(value = "/state",method = RequestMethod.GET)
    public ResponseObj<String> getConfigState(){
        LOG.error("测试测试:{}","测试成功");
        ResponseObj<String> res = new ResponseObj<>();
        String testRedisKey = "redis_test_key:" + port;
        try {
            redisTemplate.opsForValue().set(testRedisKey, DateUtil.getNowTime());
        }catch (Exception e){
            res.data = "连接不到redis";
            return res;
        }
        String userJsonStr = "";
        try {
            UUserDO userDO = userDao.selectByAccount("test");
            if(userDO != null){
                userJsonStr = JSONObject.toJSONString(userDO);
            }
        }catch (Exception e){
            res.data = "连接不到mysql";
            return res;
        }
        res.data = "项目运行端口:" + port
                + "\n访问域名:" + httpServletRequest.getServerName()
                + "\n访问端口:" + httpServletRequest.getServerPort()
                + "\n请求地址:" + httpServletRequest.getServletPath()
                + "\n获取redis值:" + redisTemplate.opsForValue().get(testRedisKey)
                + "\n获取mysql数据库值:" + userJsonStr;
        return res;
    }

}
