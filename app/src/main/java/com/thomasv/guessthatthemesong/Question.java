package com.thomasv.guessthatthemesong;

public class Question {
    private final String[] answerChoices;
    private String songFileName;
    private int correctAnswerPos;

    //constructor
    public Question(String[] answerChoices, String songFileName, int correctAnswerPos){
        this.answerChoices = answerChoices;
        this.songFileName = songFileName;
        this.correctAnswerPos = correctAnswerPos;
    }

    public String[] getAnswerChoices(){
        return answerChoices;
    }

    public String getCorrectAnswer(){
        return answerChoices[correctAnswerPos];
    }

    public String getSongFileName(){
        return songFileName;
    }
}
