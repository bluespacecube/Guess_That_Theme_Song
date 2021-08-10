package com.thomasv.guessthatthemesong;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {

    private ArrayList<Question> questionsList = new ArrayList<>();
    private int questionNum = 1;
    private int score;
    private boolean displayingChoices = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);
    }

    //Loads all questions
    private void loadQuestions(){
        questionsList.add(new Question(new String[] {"answer1", "answer2", "answer3", "answer4"}, "yes", "answer4"));
        questionsList.add(new Question(new String[] {"xanswer1", "xanswer2", "xanswer3", "xanswer4"}, "no", "xanswer4"));
    }

    //Starts the game
    private void start(){
        System.out.println("------------------- Game Started ------------------");
        loadQuestions();
        playQuestion();
    }

    private void playQuestion(){
        LinearLayout constL = findViewById(R.id.Lconst_layout);
        constL.removeAllViews();
        constL.addView(LayoutInflater.from(getApplication()).inflate(R.layout.music_screen, null));
        System.out.println("------------------- Question Started ------------------");
        String soundFileName = questionsList.get(questionNum-1).getSongFileName();
        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), getResources().getIdentifier(soundFileName, "raw", "com.thomasv.guessthatthemesong"));
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                displayAnswers();
            }
        });
        mp.start();
    }

    //Display the answer options
    private void displayAnswers(){
        displayingChoices = true;
        LinearLayout constL = findViewById(R.id.Lconst_layout);
        constL.removeAllViews();
        LinearLayout qConstL = (LinearLayout) LayoutInflater.from(getApplicationContext()).inflate(R.layout.question_screen, null);
        Button q1Button = qConstL.findViewById(R.id.answer1_button);
        Button q2Button = qConstL.findViewById(R.id.answer2_button);
        Button q3Button = qConstL.findViewById(R.id.answer3_button);
        Button q4Button = qConstL.findViewById(R.id.answer4_button);
        q1Button.setText(questionsList.get(questionNum-1).getAnswerChoices()[0]);
        q2Button.setText(questionsList.get(questionNum-1).getAnswerChoices()[1]);
        q3Button.setText(questionsList.get(questionNum-1).getAnswerChoices()[2]);
        q4Button.setText(questionsList.get(questionNum-1).getAnswerChoices()[3]);
        q1Button.setOnClickListener(checkAnswer);
        q2Button.setOnClickListener(checkAnswer);
        q3Button.setOnClickListener(checkAnswer);
        q4Button.setOnClickListener(checkAnswer);
        constL.addView(qConstL);
        new CountDownTimer(10000,1000){
            public void onTick(long milisUntillFinished){
                ProgressBar pb = qConstL.findViewById(R.id.progressBar);
                pb.setProgress((10000 - (int) milisUntillFinished) / 1000);
            }

            @Override
            public void onFinish() {
                if(displayingChoices){
                    constL.removeAllViews();
                    LinearLayout answerConstL = (LinearLayout) LayoutInflater.from(getApplicationContext()).inflate(R.layout.incorrect_answer_screen, null);
                    TextView tv = answerConstL.findViewById(R.id.inc_answer_textview);
                    tv.setText("Time Up! !he answer was " + questionsList.get(questionNum-1).getCorrectAnswer());
                    answerConstL.findViewById(R.id.inc_continue_button).setOnClickListener(onAnswerScreenClick);
                    constL.addView(answerConstL);
                    displayingChoices = false;
                }
            }
        }.start();
    }

    private View.OnClickListener checkAnswer = new View.OnClickListener() {
        @Override
        public void onClick(View view) { ;
        displayingChoices = false;
            Button answerButton = (Button) view;
            LinearLayout constL = findViewById(R.id.Lconst_layout);
            constL.removeAllViews();
            if(answerButton.getText() == questionsList.get(questionNum-1).getCorrectAnswer()){
                LinearLayout answerConstL = (LinearLayout) LayoutInflater.from(getApplicationContext()).inflate(R.layout.correct_answer_screen, null);
                answerConstL.findViewById(R.id.cor_continue_button).setOnClickListener(onAnswerScreenClick);
                constL.addView(answerConstL);
                score += 10;
            }else{
                LinearLayout answerConstL = (LinearLayout) LayoutInflater.from(getApplicationContext()).inflate(R.layout.incorrect_answer_screen, null);
                TextView tv = answerConstL.findViewById(R.id.inc_answer_textview);
                tv.setText("Incorrect! !he answer was " + questionsList.get(questionNum-1).getCorrectAnswer());
                answerConstL.findViewById(R.id.inc_continue_button).setOnClickListener(onAnswerScreenClick);
                constL.addView(answerConstL);
            }
        }
    };

    private View.OnClickListener onAnswerScreenClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(questionNum != questionsList.size()){
                questionNum += 1;
                playQuestion();
            }else{
                LinearLayout constL = findViewById(R.id.Lconst_layout);
                constL.removeAllViews();
                LinearLayout finishConstL = (LinearLayout) LayoutInflater.from(getApplicationContext()).inflate(R.layout.finish_screen, null);
                TextView tv = finishConstL.findViewById(R.id.finish_textview);
                tv.setText("Your score was" + score);
                constL.addView(finishConstL);
            }
        }
    };

    public void startOnClick(View view) {
        start();
    }
}