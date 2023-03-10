package com.example.socialappjava.data.model.user;

import com.example.socialappjava.data.model.user.Data;

import java.util.List;

public class OwnerAPIResponse {
    List<Data> data;
    int limit;
    int page;
    int total;

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
