package com.example.cognitive.Bean;

public class Report {
    private String beginDate;
    private String endDate;
    private String dailyCheck;
    private String sportCheck;

    public String getBeginDate(){
        return this.beginDate;
    }
    public String getEndDate(){
        return this.endDate;
    }
    public String getDailyCheck(){
        return this.dailyCheck;
    }
    public String getSportCheck(){
        return this.sportCheck;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }


    public void setDailyCheck(String dailyCheck) {
        this.dailyCheck = dailyCheck;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setSportCheck(String sportCheck) {
        this.sportCheck = sportCheck;
    }
    public void add(Report data){
        this.beginDate = data.beginDate;
        this.endDate = data.endDate;
        this.dailyCheck = data.dailyCheck;
        this.sportCheck = data.sportCheck;
    }
}
