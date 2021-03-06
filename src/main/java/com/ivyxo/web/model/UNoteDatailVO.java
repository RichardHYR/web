package com.ivyxo.web.model;

import com.ivyxo.web.common.data.BaseEntity;

/**
 * 笔记详情表,id为笔记id-显示实体类 - 2019-12-3 14:05:41
 * @author HYR
 */
public class UNoteDatailVO extends BaseEntity {

    private static final long serialVersionUID = 1844834282715131478L;
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 标题
     */
    private String title;

    /**
     * 关键词内容
     */
    private String keywordContent;

    /**
     * 总结内容
     */
    private String summaryContent;

    /**
     * 正文
     */
    private String mainContent;

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

    public String getKeywordContent() {
        return keywordContent;
    }

    public void setKeywordContent(String keywordContent) {
        this.keywordContent = keywordContent;
    }

    public String getSummaryContent() {
        return summaryContent;
    }

    public void setSummaryContent(String summaryContent) {
        this.summaryContent = summaryContent;
    }

    public String getMainContent() {
        return mainContent;
    }

    public void setMainContent(String mainContent) {
        this.mainContent = mainContent;
    }

    @Override
    public String toString() {
        return "UNoteDatailVO{" +
                "userId=" + userId +
                ", title='" + title + '\'' +
                ", keywordContent='" + keywordContent + '\'' +
                ", summaryContent='" + summaryContent + '\'' +
                ", mainContent='" + mainContent + '\'' +
                '}';
    }

}
