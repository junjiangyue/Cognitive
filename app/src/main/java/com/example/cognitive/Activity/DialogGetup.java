package com.example.cognitive.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import com.example.cognitive.R;

import mehdi.sakout.fancybuttons.FancyButton;

public class DialogGetup extends AppCompatActivity {
    private FancyButton btnGetupCancel;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_getup);
        btnGetupCancel=findViewById(R.id.btn_getup_cancel);
        btnGetupCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                DialogGetup.this.finish();
            }
        });
    }
}
