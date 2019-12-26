package top.ivyxo.web.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import top.ivyxo.web.common.data.ResponseObj;
import top.ivyxo.web.common.tools.RedisUtil;
import top.ivyxo.web.dao.SysConfigDao;
import top.ivyxo.web.entity.SySConfigDO;
import top.ivyxo.web.model.SySConfigVO;
import top.ivyxo.web.service.SysConfigService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统配置表服务 Richard - 2019-12-18 14:14:58
 * @author HYR
 */
@Service
public class SysConfigImpl implements SysConfigService {

    private static final Logger LOG = LoggerFactory.getLogger(SysConfigImpl.class);

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    private SysConfigDao sysConfigDao;

    @Override
    public ResponseObj<List<SySConfigVO>> list() {
        ResponseObj<List<SySConfigVO>> res = new ResponseObj<>();
        //获取web_开头的key值列表
        String key = "web_";
        //TODO:提取转换列表实体方法
        List<SySConfigDO> list = listByKey(key);
        List<SySConfigVO> listVO = new ArrayList<>();
        for(SySConfigDO sySConfigDO:list){
            SySConfigVO vo = new SySConfigVO();
            BeanUtils.copyProperties(sySConfigDO,vo);
            listVO.add(vo);
        }

        res.data = listVO;
        return res;
    }

    @Override
    public ResponseObj<SySConfigVO> select(String key) {
        ResponseObj<SySConfigVO> res = new ResponseObj<>();
        SySConfigVO sySConfigVO = new SySConfigVO();
        SySConfigDO sySConfigDO = selectByKey(key);
        if(sySConfigDO == null){
            return res;
        }
        BeanUtils.copyProperties(sySConfigDO,sySConfigVO);
        res.data = sySConfigVO;
        return res;
    }

    private List<SySConfigDO> listByKey(String key){
        return sysConfigDao.listByKey(key);
    }

    private SySConfigDO selectByKey(String key){
        return sysConfigDao.selectByKey(key);
    }

}
