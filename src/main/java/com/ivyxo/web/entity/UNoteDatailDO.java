package com.ivyxo.web.entity;

import com.ivyxo.web.common.data.BaseEntity;

/**
 * 笔记详情表,id为笔记id-实体类 - 2019-12-1 21:00:38
 * @author HYR
 */
public class UNoteDatailDO extends BaseEntity {

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

}
