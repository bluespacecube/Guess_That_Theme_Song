package com.thomasv.guessthatthemesong;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity implements OptionsFragment.OnOptionClickedListener, IncorrectAnswerFragment.ContinueClickListener, CorrectAnswerFragment.ContinueClickListener{

    private ArrayList<Question> questionsList = new ArrayList<>();
    private int questionNum = 1;
    private int score;
    private boolean displayingChoices = false;
    private MediaPlayer mp;
    private CountDownTimer cdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseMusicPlayer();
        this.onBackPressed();
    }

    //Loads all questions
    private void loadQuestions(){
        questionsList.add(new Question(new String[] {"Channel 4 News", "Channel 5 News", "ITV News", "BBC News"}, "bbcnews", 3));
        questionsList.add(new Question(new String[] {"Family Guy", "The Simpsons", "Rick And Morty", "South Park"}, "thesimpsonsopening", 1));
    }

    //Starts the game
    private void start(){
        System.out.println("------------------- Game Started ------------------");
        loadQuestions();
        LinearLayout startll = (LinearLayout) findViewById(R.id.start_ll);
        startll.setVisibility(View.GONE);
        playQuestion();
    }

    //Plays a theme song for the user to guess
    private void playQuestion(){
        //load question music playing fragment
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_lay, new MusicFragment(), "MUSIC_FRAGMENT");
        ft.commit();
        System.out.println("------------------- Question Started ------------------");
        String soundFileName = questionsList.get(questionNum-1).getSongFileName();
        //play the music
        mp = MediaPlayer.create(getApplicationContext(), getResources().getIdentifier(soundFileName, "raw", "com.thomasv.guessthatthemesong"));
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            //called when the music has finished playing
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                displayAnswers();
            }
        });
        getSupportFragmentManager().executePendingTransactions();
        MusicFragment musicFragment = (MusicFragment) getSupportFragmentManager().findFragmentByTag("MUSIC_FRAGMENT");
        int musicDuration = mp.getDuration();
        boolean[] startTick = {true}; //needs to be a array so can be acessed in countdt's onTick()
        musicFragment.setProgressBarMax(musicDuration);
        CountDownTimer countdt = new CountDownTimer(musicDuration, 1000){

            @Override
            public void onTick(long l) {
                if(!startTick[0]){
                    musicFragment.setProgressBar(musicFragment.getProgressBarProgress() +1000);
                }
                startTick[0] = false;
            }

            @Override
            public void onFinish() {

            }
        };
        mp.start();
        countdt.start();
        String s = questionsList.get(questionNum-1).getCorrectAnswer();
    }

    //Display the answer options
    private void displayAnswers(){
        displayingChoices = true;
        //display multiple choices fragment
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Question q = questionsList.get(questionNum-1);
        OptionsFragment optionsFragment = OptionsFragment.newInstance(questionsList.get(questionNum-1).getAnswerChoices());
        Question q1 = questionsList.get(questionNum-1);
        ft.replace(R.id.frame_lay, optionsFragment, "OPTIONS_FRAGMENT");
        ft.commit();
        Question sq1 = questionsList.get(questionNum-1);
        //start a countdown timer for choosing an option
        cdt = new CountDownTimer(10000,1000){
            public void onTick(long milisUntillFinished){
                //make a reference for the fragment
                OptionsFragment optionsFragment = (OptionsFragment) getSupportFragmentManager().findFragmentByTag("OPTIONS_FRAGMENT");
                //update its progress bar progress
                optionsFragment.setProgressBar((10000 - (int) milisUntillFinished) / 1000);
            }

            @Override
            public void onFinish() {
                //display incorrect answer fragment
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_lay, new IncorrectAnswerFragment(), "INCORRECT_ANSWER_FRAGMENT").commit();
                getSupportFragmentManager().executePendingTransactions();
                //make a reference for the fragment
                IncorrectAnswerFragment incorrectAnswerFragment = (IncorrectAnswerFragment) getSupportFragmentManager().findFragmentByTag("INCORRECT_ANSWER_FRAGMENT");
                //display the answer to the user
                incorrectAnswerFragment.setAnswer(questionsList.get(questionNum-1).getCorrectAnswer());
                //set the title to "Times Up!"
                incorrectAnswerFragment.setToTimeUp();

            }
        };
        Question q12 = questionsList.get(questionNum-1);
        cdt.start();
    }

    @Override
    public void OnOptionClicked(String option) {
        //cancel timer
        cdt.cancel();
        //check if the answer selected is correct (via the buttons text)
        String correctAns = questionsList.get(questionNum-1).getCorrectAnswer();
        if(option == correctAns){
            //display correct answer fragment
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame_lay, new CorrectAnswerFragment(), "CORRECT_ANSWER_FRAGMENT");
            ft.commit();
            //increment score
            score += 10;
        }else{
            //display incorrect answer fragment
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame_lay, new IncorrectAnswerFragment(), "INCORRECT_ANSWER_FRAGMENT");
            ft.commit();
            getSupportFragmentManager().executePendingTransactions();
            //make a reference for the fragment
            IncorrectAnswerFragment incorrectAnswerFragment = (IncorrectAnswerFragment) getSupportFragmentManager().findFragmentByTag("INCORRECT_ANSWER_FRAGMENT");
            //add the correct answer to the fragment
            incorrectAnswerFragment.setAnswer(correctAns);
        }
    }

    //OnButtonClick for the start button
    public void startOnClick(View view) {
        //start the game
        start();
    }

    //OnButtonClick for the continue button
    @Override
    public void OnContinueClicked() {
        //if game is not on the last question update current question number and do the next one
        if(questionNum != questionsList.size()){
            questionNum += 1;
            playQuestion();
        }else{
            //load the finish fragment
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame_lay, new FinishFragment(), "FINISH_FRAGMENT");
            ft.commit();
            getSupportFragmentManager().executePendingTransactions();
            //make a reference for the fragment
            FinishFragment finishFragment = (FinishFragment) getSupportFragmentManager().findFragmentByTag("FINISH_FRAGMENT");
            //add the score to the fragment
            finishFragment.setScore(score);
        }
    }

    private void releaseMusicPlayer(){
        if(mp != null){
            mp.stop();
            mp.release();
        }
    }

}