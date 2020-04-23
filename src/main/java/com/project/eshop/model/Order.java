package com.project.eshop.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Entity
@Table(name = "orders")
@ApiModel(description = "Orders table containing list of orders made by customers")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "order id auto generated whenever customer makes an order")
    Integer id;
    @ApiModelProperty(notes = "enter valid amount")
    int purchase_amt;
    int customer_id;
    @ApiModelProperty(notes = "choose countries only from the available list")
    String location;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getPurchase_amt() {
        return purchase_amt;
    }

    public void setPurchase_amt(int purchase_amt) {
        this.purchase_amt = purchase_amt;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
