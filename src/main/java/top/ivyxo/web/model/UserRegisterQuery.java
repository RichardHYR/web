package top.ivyxo.web.model;

import java.io.Serializable;

/**
 * 用户注册请求实体 Richard - 2019-12-2 14:46:56
 * @author HYR
 */
public class UserRegisterQuery implements Serializable{

    private static final long serialVersionUID = -7601045307935035053L;

    /**
     * 账户
     */
    private String account;

    /**
     * 昵称
     */
    private String name;

    /**
     * 密码
     */
    private String psw1;

    /**
     * 二次确认密码
     */
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
