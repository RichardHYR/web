package top.ivyxo.web.model;

/**
 * 笔记内容实体 Richard - 2020-1-19 16:13:21
 * @author HYR
 */
public class NoteQuery {

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

    /**
     * 是否公开可见,0不可见,1可见
     */
    private Integer show;

    /**
     * 是否删除,1删除
     */
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
