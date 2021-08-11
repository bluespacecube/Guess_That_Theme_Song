package com.thomasv.guessthatthemesong;

public class Question {
    private String[] answerChoices;
    private String songFileName;
    private int correctAnswerPos;

    public Question(String[] answerChoices, String songFileName, int correctAnswerPos){
        this.answerChoices = answerChoices;
        this.songFileName = songFileName;
        this.correctAnswerPos = correctAnswerPos;
    }

    public String[] getAnswerChoices(){
        return answerChoices;
    }

    public int getCorrectAnswerPos(){
        return correctAnswerPos;
    }

    public String getSongFileName(){
        return songFileName;
    }
}
