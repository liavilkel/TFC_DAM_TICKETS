package com.example.tfc_dam_tickets.model;

import java.time.LocalDateTime;

public class Ticket {

    Long ticketId, catId, clientId;
    String userOpen, userClose, title, description, status, solution;
    LocalDateTime tsOpen, tsClose;

    public Ticket(Long ticketId, Long catId, Long clientId, String userOpen, String userClose,
                  String title, String description, String status, String solution,
                  LocalDateTime tsOpen, LocalDateTime tsClose) {
        this.ticketId = ticketId;
        this.catId = catId;
        this.clientId = clientId;
        this.userOpen = userOpen;
        this.userClose = userClose;
        this.title = title;
        this.description = description;
        this.status = status;
        this.solution = solution;
        this.tsOpen = tsOpen;
        this.tsClose = tsClose;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public Long getCatId() {
        return catId;
    }

    public void setCatId(Long catId) {
        this.catId = catId;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getUserOpen() {
        return userOpen;
    }

    public void setUserOpen(String userOpen) {
        this.userOpen = userOpen;
    }

    public String getUserClose() {
        return userClose;
    }

    public void setUserClose(String userClose) {
        this.userClose = userClose;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public LocalDateTime getTsOpen() {
        return tsOpen;
    }

    public void setTsOpen(LocalDateTime tsOpen) {
        this.tsOpen = tsOpen;
    }

    public LocalDateTime getTsClose() {
        return tsClose;
    }

    public void setTsClose(LocalDateTime tsClose) {
        this.tsClose = tsClose;
    }
}
