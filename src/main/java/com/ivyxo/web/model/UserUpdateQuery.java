package com.ivyxo.web.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户更新请求体 Richard - 2019-12-4 22:04:54
 * @author HYR
 */
public class UserUpdateQuery implements Serializable {

    private static final long serialVersionUID = 8557616444128063305L;

    /**
     * 昵称
     */
    private String name;

    /**
     * 性别,0默认,1男,2女
     */
    private Integer sex;

    /**
     * 生日
     */
    private String birthday;

    /**
     * 邮箱地址
     */
    private String email;

    /**
     * 微信号
     */
    private String wechatAccount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
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
        return "UserUpdateQuery{" +
                "name='" + name + '\'' +
                ", sex=" + sex +
                ", birthday=" + birthday +
                ", email='" + email + '\'' +
                ", wechatAccount='" + wechatAccount + '\'' +
                '}';
    }

}
