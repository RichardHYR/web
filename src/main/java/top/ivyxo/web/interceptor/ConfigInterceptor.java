package top.ivyxo.web.interceptor;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import top.ivyxo.web.common.data.ResponseObj;
import top.ivyxo.web.service.utils.UserHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 公用拦截器 Richard - 2019-12-3 22:08:33
 * @author HYR
 */
@Component
public class ConfigInterceptor implements HandlerInterceptor{

    private static final Logger LOG = LoggerFactory.getLogger(ConfigInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        LOG.info("执行公用拦截器");
        LOG.info("header values:{},\nparams values:{}"
                    ,JSONObject.toJSONString( request.getHeaderNames() )
                    ,JSONObject.toJSONString( request.getParameterMap() ) );
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
