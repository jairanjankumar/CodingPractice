package com.github.DoneIn2Hours;

import java.util.List;

public class PricingService {

    private int page;
    private String nextPage;
    private int recordPerPage;
    private int totalRecords;
    private List<SecurityMarketPrice> data;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getNextPage() {
        return nextPage;
    }

    public void setNextPage(String nextPage) {
        this.nextPage = nextPage;
    }

    public int getRecordPerPage() {
        return recordPerPage;
    }

    public void setRecordPerPage(int recordPerPage) {
        this.recordPerPage = recordPerPage;
    }

    public int getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    public List<SecurityMarketPrice> getData() {
        return data;
    }

    public void setData(List<SecurityMarketPrice> data) {
        this.data = data;
    }
}
