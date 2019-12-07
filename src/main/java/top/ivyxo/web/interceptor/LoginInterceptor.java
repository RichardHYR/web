package top.ivyxo.web.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import top.ivyxo.web.common.data.EStatusCode;
import top.ivyxo.web.common.data.RedisKeyPrefix;
import top.ivyxo.web.common.data.ResponseObj;
import top.ivyxo.web.service.UserService;
import top.ivyxo.web.service.utils.UserHolder;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器 Richard - 2019-12-3 22:08:33
 * @author HYR
 */
@Component
public class LoginInterceptor implements HandlerInterceptor{

    private static final Logger LOG = LoggerFactory.getLogger(LoginInterceptor.class);

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("已经进入了登录拦截器......." + JSONObject.toJSONString(request.getHeaderNames()));
        System.out.println("head信息:"
                + "\n host:" + request.getHeader("host")
                + "\n RequestURI:" + request.getRequestURI()
                + "\n ServletPath:" + request.getServletPath()
                + "\n connection:" + JSONObject.toJSONString(request.getHttpServletMapping())
                + "\n Session:" + JSONObject.toJSONString(request.getSession()));

        String userId = request.getHeader("user_id");
        String userSession = request.getHeader("user_session");
        ResponseObj res = new ResponseObj();
        if(userId == null || userSession == null
                || !userSession.equals(DigestUtils.md5Hex(userId))
                || redisTemplate.opsForValue().get(RedisKeyPrefix.USER + userId) == null){
            res.code = EStatusCode.NOTLOGIN.getCode();
            res.msg = EStatusCode.NOTLOGIN.getMsg();
            response.getOutputStream().write(JSON.toJSONBytes(res));
            return false;
        }
        //储存
        UserHolder.set(UserHolder.USER_ID_KEY, Long.valueOf(userId));
        UserHolder.set(UserHolder.USER_SESSION_KEY, userSession);
        //获取
        LOG.info("储存的user_id:{},\n储存的userSession:{}" ,UserHolder.get(UserHolder.USER_ID_KEY).toString()
                ,UserHolder.get(UserHolder.USER_SESSION_KEY).toString());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {


    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserHolder.clear();
    }

}
