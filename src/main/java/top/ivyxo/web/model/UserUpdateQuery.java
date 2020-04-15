package top.ivyxo.web.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 用户更新请求体 Richard - 2019-12-4 22:04:54
 * @author HYR
 */
@ApiModel(value = "更新请求体", description = "更新请求体")
public class UserUpdateQuery implements Serializable {

    private static final long serialVersionUID = 8557616444128063305L;

    /**
     * 更新类型 1用户信息,2密码更新
     */
    @ApiModelProperty(value = "更新类型 1用户信息,2密码更新", name = "type", required = true, example = "0")
    private Integer type;

    /**
     * 旧密码
     */
    @ApiModelProperty(value = "旧密码", name = "oldPsw", required = true, example = "oldPsw")
    private String oldPsw;

    /**
     * 新密码
     */
    @ApiModelProperty(value = "新密码", name = "newPsw", required = true, example = "newPsw")
    private String newPsw;

    /**
     * 确认密码
     */
    @ApiModelProperty(value = "确认密码", name = "confirmPsw", required = true, example = "confirmPsw")
    private String confirmPsw;

    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称", name = "name", required = true, example = "name")
    private String name;

    /**
     * 性别,0默认,1男,2女
     */
    @ApiModelProperty(value = "性别,0默认,1男,2女", name = "sex", required = true, example = "0")
    private Integer sex;

    /**
     * 生日
     */
    @ApiModelProperty(value = "生日", name = "birthday", required = true, example = "birthday")
    private String birthday;

    /**
     * 邮箱地址
     */
    @ApiModelProperty(value = "邮箱地址", name = "email", required = true, example = "email")
    private String email;

    /**
     * 微信号
     */
    @ApiModelProperty(value = "微信号", name = "wechatAccount", required = true, example = "wechatAccount")
    private String wechatAccount;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getOldPsw() {
        return oldPsw;
    }

    public void setOldPsw(String oldPsw) {
        this.oldPsw = oldPsw;
    }

    public String getNewPsw() {
        return newPsw;
    }

    public void setNewPsw(String newPsw) {
        this.newPsw = newPsw;
    }

    public String getConfirmPsw() {
        return confirmPsw;
    }

    public void setConfirmPsw(String confirmPsw) {
        this.confirmPsw = confirmPsw;
    }

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
                "type=" + type +
                ", oldPsw='" + oldPsw + '\'' +
                ", newPsw='" + newPsw + '\'' +
                ", confirmPsw='" + confirmPsw + '\'' +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                ", birthday='" + birthday + '\'' +
                ", email='" + email + '\'' +
                ", wechatAccount='" + wechatAccount + '\'' +
                '}';
    }
}
