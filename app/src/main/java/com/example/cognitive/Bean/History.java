package com.example.cognitive.Bean;

public class History {
    private String testScore;
    private String testDate;
    private String testTime;
    private String testName;
    private String strengthScore;
    private String healthScore;
    private String judgementScore;
    private String memoryScore;
    private String cognitionScore;
    public String getTestScore(){
        return testScore;
    }
    public String getTestDate(){
        return testDate;
    }
    public String getTestTime(){
        return testTime;
    }
    public String getTestName(){
        return testName;
    }
    public String getStrengthScore(){
        return strengthScore;
    }
    public String getHealthScore(){
        return healthScore;
    }
    public String getJudgementScore(){
        return judgementScore;
    }
    public String getMemoryScore(){
        return memoryScore;
    }
    public String getCognitionScore(){
        return cognitionScore;
    }
    public void setTestScore(String testScore){
        this.testScore = testScore;
    }
    public void setTestDate(String testDate){
        this.testDate = testDate;
    }
    public void setTestTime(String testTime){
        this.testTime = testTime;
    }
    public void setTestName(String testName){
        this.testName = testName;
    }
    public void setStrengthScore(String strengthScore){
        this.strengthScore = strengthScore;
    }
    public void setHealthScore(String healthScore){
        this.healthScore = healthScore;
    }
    public void setJudgementScore(String judgementScore){
        this.judgementScore = judgementScore;
    }
    public void setMemoryScore(String memoryScore){
        this.memoryScore = memoryScore;
    }
    public void setCognitionScore(String cognitionScore){
        this.cognitionScore = cognitionScore;
    }
    public void add(History data) {
        this.testScore = data.testScore;
        this.healthScore = data.healthScore;
        this.judgementScore = data.judgementScore;
        this.memoryScore = data.memoryScore;
        this.strengthScore = data.strengthScore;
        this.testTime = data.testTime;
        this.testName = data.testName;
        this.testDate = data.testDate;
        this.cognitionScore = data.cognitionScore;
    }
}
