package com.example.easychatgpt;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import  android.view.*;
import android.widget.Button;

public class  indexScreen extends AppCompatActivity implements View.OnClickListener{
    Button exit,start;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_screen);
        exit = (Button) findViewById(R.id.exit);
        exit.setOnClickListener(this);
        start = (Button) findViewById(R.id.button2);
        start.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.equals(exit))
        {
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());

            System.exit(1);


        }
        if(view.equals(start))
        {
            startActivity(new Intent(this,MainActivity.class));
        }
    }
}