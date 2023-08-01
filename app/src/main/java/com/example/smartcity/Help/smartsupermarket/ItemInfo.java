package com.example.smartcity.Help.smartsupermarket;

import java.io.Serializable;

/**
 * @author Amy
 * @date -
 **/
public class ItemInfo implements Serializable {
    private String pic;
    private String pic1;
    private String pic2;
    private String id;
    private String goods;
    private String price;
    private String startDate;
    private String stopDate;
    private String _id;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPic1() {
        return pic1;
    }

    public void setPic1(String pic1) {
        this.pic1 = pic1;
    }

    public String getPic2() {
        return pic2;
    }

    public void setPic2(String pic2) {
        this.pic2 = pic2;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStopDate() {
        return stopDate;
    }

    public void setStopDate(String stopDate) {
        this.stopDate = stopDate;
    }

    @Override
    public String toString() {
        return "ItemInfo{" +
                "pic='" + pic + '\'' +
                ", pic1='" + pic1 + '\'' +
                ", pic2='" + pic2 + '\'' +
                ", id='" + id + '\'' +
                ", goods='" + goods + '\'' +
                ", price='" + price + '\'' +
                ", startDate='" + startDate + '\'' +
                ", stopDate='" + stopDate + '\'' +
                ", _id='" + _id + '\'' +
                '}';
    }
}


