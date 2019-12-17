package top.ivyxo.web.service;

import top.ivyxo.web.common.data.ResponseObj;
import top.ivyxo.web.model.SySConfigVO;

import java.util.List;

/**
 * @author HYR
 */
public interface SysConfigService {

    /**
     * 获取web项目需要的配置信息 Richard - 2019-12-17 22:46:14
     * @return
     */
    ResponseObj<List<SySConfigVO>> list();

}
