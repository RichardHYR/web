package top.ivyxo.web.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 用户注册请求实体 Richard - 2019-12-2 14:46:56
 * @author HYR
 */
@ApiModel(value = "用户注册实体类", description = "用户注册实体信息")
public class UserRegisterQuery implements Serializable{

    private static final long serialVersionUID = -7601045307935035053L;

    /**
     * 账户
     */
    @ApiModelProperty(value = "账户", name = "account", required = true, example = "account")
    private String account;

    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称", name = "name", required = true, example = "name")
    private String name;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码", name = "psw1", required = true, example = "psw1")
    private String psw1;

    /**
     * 二次确认密码
     */
    @ApiModelProperty(value = "二次确认密码", name = "psw2", required = true, example = "psw2")
    private String psw2;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPsw1() {
        return psw1;
    }

    public void setPsw1(String psw1) {
        this.psw1 = psw1;
    }

    public String getPsw2() {
        return psw2;
    }

    public void setPsw2(String psw2) {
        this.psw2 = psw2;
    }

    @Override
    public String toString() {
        return "UserRegisterQuery{" +
                "account='" + account + '\'' +
                ", name='" + name + '\'' +
                ", psw1='" + psw1 + '\'' +
                ", psw2='" + psw2 + '\'' +
                '}';
    }
}
