package com.example.tfc_dam_tickets.model;

public class Category {

    Integer catId, order;
    String name;
    Long active;

    public Category(Integer catId, Integer order, String name, Long active) {
        this.catId = catId;
        this.order = order;
        this.name = name;
        this.active = active;
    }

    public Integer getCatId() {
        return catId;
    }

    public void setCatId(Integer catId) {
        this.catId = catId;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getActive() {
        return active;
    }

    public void setActive(Long active) {
        this.active = active;
    }
}
