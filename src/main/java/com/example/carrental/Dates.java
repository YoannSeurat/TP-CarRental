package com.example.carrental;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Dates {
    @Column(name = "begin_date")
    private String begin;
    @Column(name = "end_date")
    private String end;

    public String getBegin() { return begin; }
    public void setBegin(String begin) { this.begin = begin; }

    public String getEnd() { return end; }
    public void setEnd(String end) { this.end = end; }
}