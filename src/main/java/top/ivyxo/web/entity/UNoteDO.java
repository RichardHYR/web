package top.ivyxo.web.entity;

import top.ivyxo.web.common.data.BaseEntity;

/**
 * 笔记表-实体类 - 2019-12-1 21:00:42
 * @author HYR
 */
public class UNoteDO extends BaseEntity {

    private static final long serialVersionUID = 7274447563893355214L;
    /**
     * 用户Id
     */
    private Long userId;

    /**
     * 标题
     */
    private String title;

    /**
     * 作者名
     */
    private String userName;

    /**
     * 是否删除,1删除
     */
    private Integer del;

    /**
     * 是否公开可见,0不可见,1可见
     */
    private Integer show;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getDel() {
        return del;
    }

    public void setDel(Integer del) {
        this.del = del;
    }

    public Integer getShow() {
        return show;
    }

    public void setShow(Integer show) {
        this.show = show;
    }

    @Override
    public String toString() {
        return "UNoteDO{" +
                "userId=" + userId +
                ", title='" + title + '\'' +
                ", userName='" + userName + '\'' +
                ", del=" + del +
                ", show=" + show +
                '}';
    }
}
