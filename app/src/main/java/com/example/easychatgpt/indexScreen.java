package com.example.easychatgpt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import  android.view.*;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class  indexScreen extends AppCompatActivity implements View.OnClickListener{
    Button exit,start;
    RadioButton r1,r2;
    int counter = 0;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_screen);
        exit = (Button) findViewById(R.id.exit);
        exit.setOnClickListener(this);
        start = (Button) findViewById(R.id.button2);
        start.setOnClickListener(this);
        r1 = (RadioButton)findViewById(R.id.radioButton);
        r2 = (RadioButton) findViewById(R.id.radioButton2);

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
            if(r1.isChecked()&&r2.isChecked())
            {
                Toast.makeText(this,"Choose AnyOne",Toast.LENGTH_LONG).show();
                startActivity(new Intent(this,indexScreen.class));
            }
            else if(r1.isChecked()) {
                startActivity(new Intent(this, MainActivity.class));
            }
            else if(r2.isChecked())
            {
                startActivity(new Intent(this, daVinci.class));

            }
            else {
                Toast.makeText(this,"Choose the Engine",Toast.LENGTH_LONG).show();
            }
        }

    }
}