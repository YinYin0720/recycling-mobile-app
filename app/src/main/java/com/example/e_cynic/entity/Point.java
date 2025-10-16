package com.example.e_cynic.entity;

public class Point {
    public Integer pointId;
    public Integer userId;
    public Integer pointsEarned;
    public Long date;

    public Point() {}

    public Point(Integer pointId, Integer userId, Integer pointsEarned, Long date) {
        this.pointId = pointId;
        this.userId = userId;
        this.pointsEarned = pointsEarned;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Point{" +
                "pointId=" + pointId +
                ", pointsEarned=" + pointsEarned +
                ", date=" + date +
                '}';
    }
}