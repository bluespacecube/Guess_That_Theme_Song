package com.thomasv.guessthatthemesong;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OptionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OptionsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM = "OPTIONS";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnOptionClickedListener listener;

    public OptionsFragment() {
        // Required empty public constructor
    }

    public interface OnOptionClickedListener{
        public void OnOptionClicked(String option);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param options String Array of question choice options
     * @return A new instance of fragment OptionsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OptionsFragment newInstance(String[] options) {
        OptionsFragment fragment = new OptionsFragment();
        Bundle args = new Bundle();
        args.putStringArray(ARG_PARAM, options);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_options, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button optionb1 = (Button) getView().findViewById(R.id.answer1_button);
        Button optionb2 = (Button) getView().findViewById(R.id.answer2_button);
        Button optionb3 = (Button) getView().findViewById(R.id.answer3_button);
        Button optionb4 = (Button) getView().findViewById(R.id.answer4_button);
        optionb1.setOnClickListener(optionClicked);
        optionb2.setOnClickListener(optionClicked);
        optionb3.setOnClickListener(optionClicked);
        optionb4.setOnClickListener(optionClicked);
        List<String> options = Arrays.asList(getArguments().getStringArray(ARG_PARAM));
        Collections.shuffle(options);
        optionb1.setText(options.get(0));
        optionb2.setText(options.get(1));
        optionb3.setText(options.get(2));
        optionb4.setText(options.get(3));
    }

    //called once the fragment is associated with its activity
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //check if Activity has implemented the OnOptionClicked listener
        if (context instanceof OptionsFragment.OnOptionClickedListener) {
            listener = (OptionsFragment.OnOptionClickedListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement IncorrectAnswerFragment.ContinueClickListener");
        }
    }

    //OnClickListener for the option buttons
    View.OnClickListener optionClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Button b = (Button) view;
            //get button text
            String buttonText = b.getText().toString();
            //send event to OnOptionClicked listener
            listener.OnOptionClicked(buttonText);
        }
    };

    public void setProgressBar(int amount){
        ProgressBar pb = getView().findViewById(R.id.progressBar);
        pb.setProgress(amount);
    }

    public int[] getProgressBarProgress(){
        ProgressBar pb = getView().findViewById(R.id.progressBar);
        return new int[] {pb.getProgress(), pb.getMax()};
    }

}