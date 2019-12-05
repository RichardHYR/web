package com.ivyxo.web.common.data;

import java.io.Serializable;

/**
 * 分页参数
 * @author HYR
 */
public class PageResponseObj<T> implements Serializable {

    private static final long serialVersionUID = -1931053342336543315L;

    public T list;

    public Meta meta = new Meta();

    public PageResponseObj(){

    }

    public PageResponseObj(Long total,Long count,Long per_page,Long current_page,Long total_pages,String next){
        super();
        this.meta.pagination.total = total;
        this.meta.pagination.count = count;
        this.meta.pagination.per_page = per_page;
        this.meta.pagination.current_page = current_page;
        this.meta.pagination.total_pages = total_pages;
        this.meta.pagination.links.next = next;
    }


//    "meta": {
//        "pagination": {
//            "total": 101,
//            "count": 2,
//            "per_page": 2,
//            "current_page": 1,
//            "total_pages": 51,
//            "links": {
//                "next": "http://api.example.com?page=2"
//            }
//        }
//    }

    public class Meta{

        /**
         * 分页信息
         */
        public Pagination pagination = new Pagination();

    }

    public class Pagination{

        /**
         * 总数
         */
        public Long total;

        /**
         * 当前页个数
         */
        public Long count;

        /**
         * 每页单数
         */
        public Long per_page;

        /**
         * 当前页
         */
        public Long current_page;

        /**
         * 总页数
         */
        public Long total_pages;

        /**
         * 链接参数
         */
        public Links links = new Links();

    }

    public class Links{

        /**
         * 下一页参数
         */
        public String next;

    }

}
