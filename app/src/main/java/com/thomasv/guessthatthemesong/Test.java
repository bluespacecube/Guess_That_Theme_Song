package com.thomasv.guessthatthemesong;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public class Test extends AppCompatActivity implements BlankFragment.OnOptionClickedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        LinearLayout l = findViewById(R.id.Lconst_layout);
        LinearLayout c = (LinearLayout) LayoutInflater.from(getApplicationContext()).inflate(R.layout.question_screen,l,true);
        //l.addView(c);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_lay, new BlankFragment());
        ft.commit();
    }

    @Override
    public void onOptionClicked(String optionText) {
        System.out.println("-----------------------------------------");
        System.out.println(optionText);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_lay, new BlankFragment2());
        ft.commit();
    }
}