package top.ivyxo.web.common.data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页参数实体 Richard - 2020-1-19 14:34:53
 * @author HYR
 */
public class PageInfo implements Serializable {

    /**
     * 获取分页数据方法:
     * 1.总条数[total]:使用select count(*) from tableName查询
     * 2.当前页个数[count]:获取到的列表直接.length获取
     * 3.每页大小[perPage]:传进来的分页大小,没有则设置默认
     * 4.当前页[currentPage]:传进来的页码
     * 5.总页数[totalPages]:总条数[total]/每页大小[perPage]向上取整获取
     * 6.下一页参数[next]:下一页参数链接
     * 7.是否最后一页[last]:当前页[currentPage]==总页数[totalPages]
     * 8.是否首页[first]:当前页[currentPage]==1
     */

    private static final long serialVersionUID = -1931053342336543315L;

    public PageInfo(List list, Integer total, Integer page, Integer pageSize){
        this.list = list;
        this.total = total;
        this.page = page;
        this.page_size = pageSize;
        this.count = list.size();
        this.total_pages = (total%pageSize == 0?(total/pageSize):(total/pageSize)+1);
        this.last = this.total_pages == 0?true:this.page.equals(this.total_pages);
        this.first = (this.page == 1);
    }

    /**
     * 分页列表
     */
    private List list;

    /**
     * 总数
     */
    private Integer total;

    /**
     * 当前页
     */
    private Integer page;

    /**
     * 每页单数
     */
    private Integer page_size;

    /**
     * 当前页个数
     */
    private Integer count;

    /**
     * 总页数
     */
    private Integer total_pages;

    /**
     * 是否最后一页
     */
    private Boolean last;

    /**
     * 是否首页
     */
    private Boolean first;

    public List getList() {
        return list;
    }

    public Integer getTotal() {
        return total;
    }

    public Integer getPage() {
        return page;
    }

    public Integer getPage_size() {
        return page_size;
    }

    public Integer getCount() {
        return count;
    }

    public Integer getTotal_pages() {
        return total_pages;
    }

    public Boolean getLast() {
        return last;
    }

    public Boolean getFirst() {
        return first;
    }
}
