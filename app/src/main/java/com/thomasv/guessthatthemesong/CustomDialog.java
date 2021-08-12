package com.thomasv.guessthatthemesong;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class CustomDialog extends Dialog implements android.view.View.OnClickListener {

    public Dialog d;
    public Button button1, button2;
    public Activity a;
    private OnClickListener listener;

    public CustomDialog(Activity a) {
        super(a);
        this.a = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);
        button1 = (Button) this.findViewById(R.id.dialog_button1);
        button2 = (Button) this.findViewById(R.id.dialog_button2);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dialog_button1:
                Intent intent = new Intent(a, MainActivity.class);
                a.startActivity(intent);
                break;
            case R.id.dialog_button2:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}
