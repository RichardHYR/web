package top.ivyxo.web.model;

import top.ivyxo.web.common.data.BaseEntity;

/**
 * 系统配置表-显示实体类 - 2019-12-3 14:01:28
 * @author HYR
 */
public class SySConfigVO extends BaseEntity {

    private static final long serialVersionUID = 6211777769834425833L;

    /**
     * 系统key值
     */
    private String sysKey;

    /**
     * 系统value值
     */
    private String sysValue;

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

    @Override
    public String toString() {
        return "SySConfigVO{" +
                "sysKey='" + sysKey + '\'' +
                ", sysValue='" + sysValue + '\'' +
                '}';
    }

}
