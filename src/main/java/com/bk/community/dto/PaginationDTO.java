package com.bk.community.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

/**
 * @author bear
 * @description community
 * @date 2020/3/29 12:34 下午
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaginationDTO<E> {
    /**
     * 当前页的数据列表
     */
    private List<E> data;

    /**
     * 是否有上一页
     */
    private Boolean hasPrevious;

    /**
     * 是否有下一页
     */
    private Boolean hasNext;

    /**
     * 是否有第一页
     */
    private Boolean hasFirst;

    /**
     * 是否有最后一页
     */
    private Boolean hasEnd;

    /**
     * 总条数
     */
    private Integer totalCount;

    /**
     * 当前页码
     */
    private Integer currentPage;

    /**
     * 总页数
     */
    private Integer pageCount;

    /**
     * 当前页码列表
     */
    private List<Integer> pageList;

    /**
     * 设置分页信息
     *
     * @param count 总个数
     * @param page  当前页码
     * @param size  每页显示的个数
     */
    public void setPagination(Integer count, Integer page, Integer size) {
        // 设置总页数
        this.pageCount = (count - 1) / size + 1;

        // 设置page临界值
        if (page < 1) {
            page = 1;
        }
        if (page > pageCount) {
            page = pageCount;
        }

        // 设置当前页
        this.currentPage = page;

        // 设置页码集合
        this.pageList = new LinkedList<>();
        this.pageList.add(page);
        for (int i = 1; i < 4; i++) {
            if (page - i > 0) {
                this.pageList.add(0, page - i);
            }
            if (page + i <= this.pageCount) {
                this.pageList.add(page + i);
            }
        }


        // 是否有上一页

        this.hasPrevious = page != 1;

        // 是否有下一页
        this.hasNext = !page.equals(this.pageCount);

        // 是否展示第一页
        this.hasFirst = this.pageList != null && !pageList.contains(1);

        // 是否展示最后一页
        this.hasEnd = this.pageList != null && !pageList.contains(this.pageCount);

    }
}
