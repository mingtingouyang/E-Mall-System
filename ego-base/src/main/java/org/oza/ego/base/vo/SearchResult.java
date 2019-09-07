package org.oza.ego.base.vo;

import java.util.List;

/**
 * 封装搜索结果
 */
public class SearchResult {
    private Long recordCount;
    private List<SearchItem> itemList;
    private Integer totalPages;
    private Integer curPage;

    public Long getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(Long recordCount) {
        this.recordCount = recordCount;
    }

    public List<SearchItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<SearchItem> itemList) {
        this.itemList = itemList;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getCurPage() {
        return curPage;
    }

    public void setCurPage(Integer curPage) {
        this.curPage = curPage;
    }

    @Override
    public String toString() {
        return "SearchResult{" +
                "recordCount=" + recordCount +
                ", itemList=" + itemList +
                ", totalPages=" + totalPages +
                ", curPage=" + curPage +
                '}';
    }
}
