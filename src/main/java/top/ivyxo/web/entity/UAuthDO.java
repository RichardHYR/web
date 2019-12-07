package top.ivyxo.web.entity;

import top.ivyxo.web.common.data.BaseEntity;

/**
 * 权限表-实体类 - 2019-12-1 21:00:34
 * @author HYR
 */
public class UAuthDO extends BaseEntity {

    private static final long serialVersionUID = -4053029254692403409L;
    /**
     * 权限名
     */
    private String authName;

    /**
     * 是否能设置用户权限,0默认,1启用,2禁用
     */
    private Integer settingUser;

    /**
     * 是否能操作用户的康奈尔笔记,0默认,1启用,2禁用
     */
    private Integer settingNote;

    public String getAuthName() {
        return authName;
    }

    public void setAuthName(String authName) {
        this.authName = authName;
    }

    public Integer getSettingUser() {
        return settingUser;
    }

    public void setSettingUser(Integer settingUser) {
        this.settingUser = settingUser;
    }

    public Integer getSettingNote() {
        return settingNote;
    }

    public void setSettingNote(Integer settingNote) {
        this.settingNote = settingNote;
    }
}
