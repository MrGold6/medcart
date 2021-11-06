package com.boots.entity;

public abstract class Document {
    private Visit visit;

    public Visit getVisit() {
        return visit;
    }

    public void setVisit(Visit visit) {
        this.visit = visit;
    }
}
