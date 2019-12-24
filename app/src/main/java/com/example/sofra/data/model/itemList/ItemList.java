
package com.example.sofra.data.model.itemList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemList {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private ItemPagination data;

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

    public ItemPagination getData() {
        return data;
    }

    public void setData(ItemPagination data) {
        this.data = data;
    }

}
