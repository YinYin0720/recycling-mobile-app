package com.example.e_cynic.entity;

import androidx.annotation.Nullable;

public class UserReward {
    public Integer rewardId;
    public Integer userId;
    public Long date;
    public String rewardItem;
    public Integer points;

    public UserReward() {
    }

    public UserReward(@Nullable Integer rewardId, Integer userId, Long date, String rewardItem,
                      Integer points) {
        this.rewardId = rewardId;
        this.userId = userId;
        this.date = date;
        this.rewardItem = rewardItem;
        this.points = points;
    }

    @Override
    public String toString() {
        return "UserReward{" +
                "rewardId=" + rewardId +
                ", userId=" + userId +
                ", date=" + date +
                ", rewardItem='" + rewardItem + '\'' +
                ", points=" + points +
                '}';
    }
}
