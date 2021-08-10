package com.thomasv.guessthatthemesong;

import org.junit.Test;
import static org.junit.Assert.*;

public class QuestionTest {
    String[] answerChoices = {"answer1", "answer2", "answer3", "answer4"};


    @Test
    public void questionReturnsAnswers(){
        Question question = new Question(answerChoices, "song", "answer4");
        assertArrayEquals(answerChoices, question.getAnswerChoices());
    }

    @Test
    public void questionReturnsCorrectAnswer(){
        Question question = new Question(answerChoices, "song", "answer4");
        assertEquals("answer4", question.getCorrectAnswer());
    }

    @Test
    public void questionReturnsSongName(){
        Question question = new Question(answerChoices, "song", "answer4");
        assertEquals("song", question.getSongFileName());
    }
}
