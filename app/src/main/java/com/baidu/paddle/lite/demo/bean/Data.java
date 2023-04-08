package com.baidu.paddle.lite.demo.bean;


import java.util.List;

public class Data {
    // TODO: 2023/3/21 created;
    private static List<Data> dataList;
    private String account;
    private int actionId;
    private int actionCount;
    private double maxScore;
    private double minScore;
    private double aveScore;
    private String dateTime;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getActionId() {
        return actionId;
    }

    public void setActionId(int actionId) {
        this.actionId = actionId;
    }

    public int getActionCount() {
        return actionCount;
    }

    public void setActionCount(int actionCount) {
        this.actionCount = actionCount;
    }

    public double getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(double maxScore) {
        this.maxScore = maxScore;
    }

    public double getMinScore() {
        return minScore;
    }

    public void setMinScore(double minScore) {
        this.minScore = minScore;
    }

    public double getAveScore() {
        return aveScore;
    }

    public void setAveScore(double aveScore) {
        this.aveScore = aveScore;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public static List<Data> getDataList() {
        return dataList;
    }

    public static void setDataList(List<Data> dataList) {
        Data.dataList = dataList;
    }

    @Override
    public String toString() {
        return "Data{" +
                "account='" + account + '\'' +
                ", actionId=" + actionId +
                ", actionCount=" + actionCount +
                ", maxScore=" + maxScore +
                ", minScore=" + minScore +
                ", aveScore=" + aveScore +
                ", dateTime=" + dateTime +
                '}';
    }
}
