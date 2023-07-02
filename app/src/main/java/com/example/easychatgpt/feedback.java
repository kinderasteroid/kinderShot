package com.example.easychatgpt;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class feedback extends AppCompatActivity implements View.OnClickListener {
    TextView txtr;
    RatingBar rbar;
    TextView ctxt;
    Button b1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        txtr = (TextView) findViewById(R.id.ratetxt);
        rbar = (RatingBar) findViewById(R.id.ratingBar2);
        ctxt = (TextView) findViewById(R.id.ctxt);
        b1 = (Button) findViewById(R.id.button);
        b1.setOnClickListener(this);
        ctxt.setText("");
        rbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                txtr.setText("Rate "+v);
            }
        });


    };

    @Override
    public void onClick(View view) {
        if(view.equals(b1))
        {

            String msg = txtr.getText().toString()+ctxt.getText().toString();
            //EmailSender emailSender = new EmailSender("bhandarysuhas7@gmail.com", "tacmp12345");
          // emailSender.sendEmail("bhandarysuhas777@gmail.com", "Hello", msg);
            Toast.makeText(this,"Thanks For your Feedback",Toast.LENGTH_LONG).show();
            rbar.setRating(0.0F);
            ctxt.setText("");
            Bundle b = getIntent().getBundleExtra("data");
            if(b.getString("class").equals("main")) {
                startActivity(new Intent(this, MainActivity.class));
            }
            else {
                startActivity(new Intent(this, daVinci.class));
            }

        }
    }

}


class EmailSender {

    private final String username;
    private final String password;
    private final Session session;

    public EmailSender(String username, String password) {
        this.username = username;
        this.password = password;

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
    }

    public void sendEmail(String recipient, String subject, String body) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(recipient));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);

            System.out.println("Email sent successfully!");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
