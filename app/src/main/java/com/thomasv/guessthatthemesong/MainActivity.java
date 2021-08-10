package com.thomasv.guessthatthemesong;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void buttonClick(View view){
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
        //ConstraintLayout constL = findViewById(R.id.main);
        //LinearLayout l = findViewById(R.id.lin);
        //ConstraintLayout ll = (ConstraintLayout) LayoutInflater.from(getApplicationContext()).inflate(R.layout.question_screen, null);
        //l.addView(ll);
    }
    public void b(View view){
        //LinearLayout l = findViewById(R.id.lin);
        //LinearLayout ll = (LinearLayout) LayoutInflater.from(getApplicationContext()).inflate(R.layout.question_screen, null);
        //l.addView(ll);
        Intent intent = new Intent(this, Test.class);
        startActivity(intent);
    }
}