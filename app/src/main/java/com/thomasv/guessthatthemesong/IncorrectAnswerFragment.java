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
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IncorrectAnswerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IncorrectAnswerFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ContinueClickListener listener;

    public IncorrectAnswerFragment() {
        // Required empty public constructor
    }

    public interface ContinueClickListener{
        public void OnContinueClicked();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IncorrectAnswerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IncorrectAnswerFragment newInstance(String param1, String param2) {
        IncorrectAnswerFragment fragment = new IncorrectAnswerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_incorrect_answer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button b = (Button) getView().findViewById(R.id.inc_continue_button2);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnContinueClicked();
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ContinueClickListener) {
            listener = (ContinueClickListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement IncorrectAnswerFragment.ContinueClickListener");
        }
    }

    public void setAnswer(String answer){
        TextView tv = getView().findViewById(R.id.finish_textview2);
        tv.setText("The answer was " + answer);
    }

}