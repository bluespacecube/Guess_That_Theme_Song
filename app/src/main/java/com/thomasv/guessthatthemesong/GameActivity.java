package com.thomasv.guessthatthemesong;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

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

public class GameActivity extends AppCompatActivity implements OptionsFragment.OnOptionClickedListener, IncorrectAnswerFragment.ContinueClickListener, CorrectAnswerFragment.ContinueClickListener{

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
        questionsList.add(new Question(new String[] {"Sample Answer 1", "Sample Answer 2", "Sample Answer 3", "Sample Answer 4"}, "yes", "Sample Answer " +
                "4"));
        questionsList.add(new Question(new String[] {"Sample Answer 1", "Sample Answer 2", "Sample Answer 3", "Sample Answer 4"}, "no", "Sample Answer 4"));
    }

    //Starts the game
    private void start(){
        System.out.println("------------------- Game Started ------------------");
        loadQuestions();
        LinearLayout startll = (LinearLayout) findViewById(R.id.start_ll);
        startll.setVisibility(View.GONE);
        playQuestion();
    }

    private void playQuestion(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_lay, new MusicFragment(), "MUSIC_FRAGMENT");
        ft.commit();
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
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        OptionsFragment optionsFragment = OptionsFragment.newInstance(questionsList.get(questionNum-1).getAnswerChoices());
        ft.replace(R.id.frame_lay, optionsFragment, "OPTIONS_FRAGMENT");
        ft.commit();
        new CountDownTimer(10000,1000){
            public void onTick(long milisUntillFinished){
                if(displayingChoices){
                    OptionsFragment optionsFragment = (OptionsFragment) getSupportFragmentManager().findFragmentByTag("OPTIONS_FRAGMENT");
                    optionsFragment.setProgressBar((10000 - (int) milisUntillFinished) / 1000);
                }
            }

            @Override
            public void onFinish() {
                if(displayingChoices){
                    displayingChoices = true;
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_lay, new IncorrectAnswerFragment(), "INCORRECT_ANSWER_FRAGMENT").commit();
                    getSupportFragmentManager().executePendingTransactions();
                    IncorrectAnswerFragment incorrectAnswerFragment = (IncorrectAnswerFragment) getSupportFragmentManager().findFragmentByTag("INCORRECT_ANSWER_FRAGMENT");
                    incorrectAnswerFragment.setAnswer(questionsList.get(questionNum-1).getCorrectAnswer());
                    displayingChoices = false;
                }
            }
        }.start();
    }

    @Override
    public void OnOptionClicked(String option) {
        displayingChoices = false;
        if(option == questionsList.get(questionNum-1).getCorrectAnswer()){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame_lay, new CorrectAnswerFragment(), "CORRECT_ANSWER_FRAGMENT");
            ft.commit();
            score += 10;
        }else{
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame_lay, new IncorrectAnswerFragment(), "INCORRECT_ANSWER_FRAGMENT");
            ft.commit();
            getSupportFragmentManager().executePendingTransactions();
            IncorrectAnswerFragment incorrectAnswerFragment = (IncorrectAnswerFragment) getSupportFragmentManager().findFragmentByTag("INCORRECT_ANSWER_FRAGMENT");
            incorrectAnswerFragment.setAnswer(questionsList.get(questionNum-1).getCorrectAnswer());
        }
    }

    public void startOnClick(View view) {
        start();
    }

    @Override
    public void OnContinueClicked() {
        if(questionNum != questionsList.size()){
            questionNum += 1;
            playQuestion();
        }else{
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame_lay, new FinishFragment(), "FINISH_FRAGMENT");
            ft.commit();
            getSupportFragmentManager().executePendingTransactions();
            FinishFragment finishFragment = (FinishFragment) getSupportFragmentManager().findFragmentByTag("FINISH_FRAGMENT");
            finishFragment.setScore(score);
        }
    }

}