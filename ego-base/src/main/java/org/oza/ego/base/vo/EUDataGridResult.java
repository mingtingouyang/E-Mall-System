package org.oza.ego.base.vo;

import java.util.List;

/**
 * 用于 EasyUI 表格接收，将数据封装
 */
public class EUDataGridResult {
    private long total;
    private List<?> rows;

    public EUDataGridResult(long total, List<?> rows) {
        this.total = total;
        this.rows = rows;
    }

    public EUDataGridResult() {
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "EUDataGridResult{" +
                "total=" + total +
                ", rows=" + rows +
                '}';
    }
}
