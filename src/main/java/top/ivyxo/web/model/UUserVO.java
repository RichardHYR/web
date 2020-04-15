package top.ivyxo.web.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import top.ivyxo.web.common.data.BaseEntity;

import java.util.Date;

/**
 * 用户信息表-显示实体类 - 2019-12-3 14:07:35
 * @author HYR
 */
@ApiModel(value = "用户信息实体类", description = "用户信息")
public class UUserVO extends BaseEntity {

    private static final long serialVersionUID = 916314103840294882L;

    /**
     * 权限id
     */
    @ApiModelProperty(value = "id")
    private Long authId = 0L;

    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称")
    private String name;

    /**
     * 账户
     */
    @ApiModelProperty(value = "账户")
    private String account;

    /**
     * 性别,0默认,1男,2女
     */
    @ApiModelProperty(value = "性别，0默认，1男，2女")
    private Integer sex;

    /**
     * 生日
     */
    @ApiModelProperty(value = "生日")
    private Date birthday;

    /**
     * 头像链接
     */
    @ApiModelProperty(value = "头像链接")
    private String avatarUrl;

    /**
     * 邮箱地址
     */
    @ApiModelProperty(value = "邮箱")
    private String email;

    /**
     * 微信号
     */
    @ApiModelProperty(value = "微信号")
    private String wechatAccount;

    /**
     * 用户加密session
     */
    @ApiModelProperty(value = "登录session")
    private String userSession;

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

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
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

    public String getUserSession() {
        return userSession;
    }

    public void setUserSession(String userSession) {
        this.userSession = userSession;
    }

    @Override
    public String toString() {
        return "UUserVO{" +
                "authId=" + authId +
                ", name='" + name + '\'' +
                ", account='" + account + '\'' +
                ", sex=" + sex +
                ", birthday=" + birthday +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", email='" + email + '\'' +
                ", wechatAccount='" + wechatAccount + '\'' +
                ", userSession='" + userSession + '\'' +
                '}';
    }

}
