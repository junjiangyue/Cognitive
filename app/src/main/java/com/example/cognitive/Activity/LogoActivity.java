package com.example.cognitive.Activity;

import androidx.appcompat.app.AppCompatActivity;
import com.example.cognitive.R;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class LogoActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private Button backButton;
    public static LogoActivity logoActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        logoActivity=this;
        progressBar = (ProgressBar) findViewById(R.id.pgBar);
        backButton = (Button) findViewById(R.id.btn_back);
        startActivity(new Intent(LogoActivity.this,MainActivity.class));

        backButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();

            }
        });

    }
}