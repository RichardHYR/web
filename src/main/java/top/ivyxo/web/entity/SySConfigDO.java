package top.ivyxo.web.entity;

import top.ivyxo.web.common.data.BaseEntity;

/**
 * 系统配置表-实体类 - 2019-12-26 16:53:29
 * @author HYR
 */
public class SySConfigDO extends BaseEntity {

    private static final long serialVersionUID = 6211777769834425833L;
    /**
     * 系统key值
     */
    private String sysKey;

    /**
     * 系统标题值
     */
    private String sysTitle;

    /**
     * 系统value值
     */
    private String sysValue;

    /**
     * 是否生效,0默认,1生效,2禁用
     */
    private Integer use;

    /**
     * 是否删除,1删除
     */
    private Integer del;

    public String getSysTitle() {
        return sysTitle;
    }

    public void setSysTitle(String sysTitle) {
        this.sysTitle = sysTitle;
    }

    public String getSysKey() {
        return sysKey;
    }

    public void setSysKey(String sysKey) {
        this.sysKey = sysKey;
    }

    public String getSysValue() {
        return sysValue;
    }

    public void setSysValue(String sysValue) {
        this.sysValue = sysValue;
    }

    public Integer getUse() {
        return use;
    }

    public void setUse(Integer use) {
        this.use = use;
    }

    public Integer getDel() {
        return del;
    }

    public void setDel(Integer del) {
        this.del = del;
    }
}
