package top.ivyxo.web.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 笔记内容实体 Richard - 2020-1-19 16:13:21
 * @author HYR
 */
@ApiModel(value = "笔记添加信息实体", description = "添加笔记")
public class NoteQuery {

    /**
     * 标题
     */
    @ApiModelProperty(value = "标题", required = true, example = "title")
    private String title;

    /**
     * 关键词内容
     */
    @ApiModelProperty(value = "关键词内容", required = true, example = "keywordContent")
    private String keywordContent;

    /**
     * 总结内容
     */
    @ApiModelProperty(value = "总结内容", required = true, example = "summaryContent")
    private String summaryContent;

    /**
     * 正文
     */
    @ApiModelProperty(value = "正文", required = true, example = "mainContent")
    private String mainContent;

    /**
     * 是否公开可见,0不可见,1可见
     */
    @ApiModelProperty(value = "是否公开可见,0不可见,1可见", example = "0")
    private Integer show;

    /**
     * 是否删除,1删除
     */
    @ApiModelProperty(value = "是否删除,1删除", example = "0")
    private Integer del;

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

    public Integer getShow() {
        return show;
    }

    public void setShow(Integer show) {
        this.show = show;
    }

    public Integer getDel() {
        return del;
    }

    public void setDel(Integer del) {
        this.del = del;
    }

}
