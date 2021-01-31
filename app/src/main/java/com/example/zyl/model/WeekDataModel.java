package com.example.zyl.model;

import org.litepal.crud.DataSupport;

public class WeekDataModel extends DataSupport {

    private String pic;
    private String str;

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }
}
