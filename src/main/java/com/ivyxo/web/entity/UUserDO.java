package com.ivyxo.web.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ivyxo.web.common.data.BaseEntity;

import java.util.Date;

/**
 * 用户信息表-实体类 - 2019-12-1 21:02:50
 * @author HYR
 */
public class UUserDO extends BaseEntity {

    private static final long serialVersionUID = -2196944470191951114L;

    /**
     * 权限id
     */
    private Long authId;

    /**
     * 昵称
     */
    private String name;

    /**
     * 账户
     */
    private String account;

    /**
     * 账户密码
     */
    private String password;

    /**
     * 性别,0默认,1男,2女
     */
    private Integer sex;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 头像链接
     */
    private String avatarUrl;

    /**
     * 邮箱地址
     */
    private String email;

    /**
     * 微信号
     */
    private String wechatAccount;

    public Long getAuthId() {
        return authId;
    }

    public void setAuthId(Long authId) {
        this.authId = authId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWechatAccount() {
        return wechatAccount;
    }

    public void setWechatAccount(String wechatAccount) {
        this.wechatAccount = wechatAccount;
    }

    @Override
    public String toString() {
        super.toString();
        return "UUserDO{" +
                "authId=" + authId +
                ", name='" + name + '\'' +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", sex=" + sex +
                ", birthday=" + birthday +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", email='" + email + '\'' +
                ", wechatAccount='" + wechatAccount + '\'' +
                '}';
    }
}
