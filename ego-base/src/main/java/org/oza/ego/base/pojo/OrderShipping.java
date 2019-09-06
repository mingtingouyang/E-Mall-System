package org.oza.ego.base.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

@TableName("tb_order_shipping")
public class OrderShipping {
    @TableId(value = "order_id", type = IdType.INPUT)
    private String orderId;
    @TableField("receiver_name")
    private String receiverName;
    //固定电话
    @TableField("receiver_phone")
    private String receiverPhone;
    //移动电话
    @TableField("receiver_mobile")
    private String receiverMobile;
    //省
    @TableField("receiver_state")
    private String receiverState;
    //市
    @TableField("receiver_city")
    private String receiverCity;
    //区
    @TableField("receiver_district")
    private String receiverDistrict;
    //收货地址
    @TableField("receiver_address")
    private String receiver_Address;
    //邮编
    @TableField("receiver_zip")
    private String receiverZip;
    private Date created;
    private Date updated;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public String getReceiverMobile() {
        return receiverMobile;
    }

    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile;
    }

    public String getReceiverState() {
        return receiverState;
    }

    public void setReceiverState(String receiverState) {
        this.receiverState = receiverState;
    }

    public String getReceiverCity() {
        return receiverCity;
    }

    public void setReceiverCity(String receiverCity) {
        this.receiverCity = receiverCity;
    }

    public String getReceiver_Address() {
        return receiver_Address;
    }

    public void setReceiver_Address(String receiver_Address) {
        this.receiver_Address = receiver_Address;
    }

    public String getReceiverZip() {
        return receiverZip;
    }

    public void setReceiverZip(String receiverZip) {
        this.receiverZip = receiverZip;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public String getReceiverDistrict() {
        return receiverDistrict;
    }

    public void setReceiverDistrict(String receiverDistrict) {
        this.receiverDistrict = receiverDistrict;
    }
}
