package com.taotao.pojo;

import java.util.List;

public class EUDataGridResult {

    private long total;
    private List<?> rows;

    public List<?> getRows() {
        return rows;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getTotal() {
        return total;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }
}
