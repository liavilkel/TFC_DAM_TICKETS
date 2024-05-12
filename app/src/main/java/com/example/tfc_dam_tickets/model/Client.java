package com.example.tfc_dam_tickets.model;

public class Client {

    Long clientId;
    String name, nif, street, zipCode, province, municipality;

    public Client(Long clientId, String name, String nif, String street, String zipCode, String province, String municipality) {
        this.clientId = clientId;
        this.name = name;
        this.nif = nif;
        this.street = street;
        this.zipCode = zipCode;
        this.province = province;
        this.municipality = municipality;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }
}
