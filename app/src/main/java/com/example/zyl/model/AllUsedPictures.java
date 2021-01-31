package com.example.zyl.model;

import org.litepal.crud.DataSupport;

public class AllUsedPictures extends DataSupport {

    private String imgUrl;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
