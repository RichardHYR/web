package top.ivyxo.web.model;

import top.ivyxo.web.common.data.BaseEntity;

/**
 * 笔记表-显示实体类 - 2019-12-3 14:07:02
 * @author HYR
 */
public class UNoteVO extends BaseEntity {

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

    public Integer getShow() {
        return show;
    }

    public void setShow(Integer show) {
        this.show = show;
    }

    @Override
    public String toString() {
        return "UNoteVO{" +
                "userId=" + userId +
                ", title='" + title + '\'' +
                ", userName='" + userName + '\'' +
                ", show=" + show +
                '}';
    }

}
