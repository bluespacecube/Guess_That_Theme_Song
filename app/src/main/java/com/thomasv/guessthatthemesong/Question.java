package com.thomasv.guessthatthemesong;

public class Question {
    private String[] answerChoices;
    private String songFileName;
    private String correctAnswer;

    public Question(String[] answerChoices, String songFileName, String correctAnswer){
        this.answerChoices = answerChoices;
        this.songFileName = songFileName;
        this.correctAnswer = correctAnswer;
    }

    public String[] getAnswerChoices(){
        return answerChoices;
    }

    public String getCorrectAnswer(){
        return correctAnswer;
    }

    public String getSongFileName(){
        return songFileName;
    }
}
