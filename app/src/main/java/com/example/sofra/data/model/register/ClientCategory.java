
package com.example.sofra.data.model.register;

import com.example.sofra.data.model.category.CategoryData;
import com.example.sofra.data.model.category.CategoryPagination;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ClientCategory {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private List<ClientCategoryData> data = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<ClientCategoryData> getData() {
        return data;
    }

    public void setData(List<ClientCategoryData> data) {
        this.data = data;
    }

}
