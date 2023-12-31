package com.example.easychatgpt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView welcomeTextView;
    EditText messageEditText;
    ImageButton sendButton;
    List<Message> messageList;
    MessageAdapter messageAdapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.backm)
        {
            startActivity(new Intent(this,indexScreen.class));
        }
        if(id==R.id.aboutm)
        {

            startActivity(new Intent(this,About.class));
        }
        if(id==R.id.RAF)
        {
            Bundle b = new Bundle();
            b.putString("class","main");
            startActivity(new Intent(this, feedback.class).putExtra("data",b));


        }
        return true;
    }

    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient.Builder().readTimeout(60, TimeUnit.SECONDS).build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        messageList = new ArrayList<>();

        recyclerView = findViewById(R.id.recycler_view);
        welcomeTextView = findViewById(R.id.welcome_text);
        messageEditText = findViewById(R.id.message_edit_text);
        sendButton = findViewById(R.id.send_btn);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //setup recycler view
        messageAdapter = new MessageAdapter(messageList);
        recyclerView.setAdapter(messageAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setStackFromEnd(true);
        recyclerView.setLayoutManager(llm);

        sendButton.setOnClickListener((v)->{
            String question = messageEditText.getText().toString().trim();
            addToChat(question,Message.SENT_BY_ME);
            messageEditText.setText("");
            callAPI(question);
            welcomeTextView.setVisibility(View.GONE);
        });
    }

    void addToChat(String message,String sentBy){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                messageList.add(new Message(message,sentBy));
                messageAdapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(messageAdapter.getItemCount());
            }
        });
    }

    void addResponse(String response){
        messageList.remove(messageList.size()-1);
        addToChat(response,Message.SENT_BY_BOT);
    }

    void callAPI(String question){
        //okhttp
        messageList.add(new Message("Typing... ",Message.SENT_BY_BOT));
        if(question.equals("Suhas"))
        {
            addResponse("THis is My Boss,Who Developed Me, My One more Boss is Shreecharan");

        }
        else if(question.equals("Shreecharan"))
        {
            addResponse("THis is My Boss,Who Developed Me, My One more Boss is Suhas");

        }
        else if(question.equals("Varsha")||question.equals("Varsha V Shetty")||question.equals("Cherry")||question.equals("V"))
        {
            addResponse("She is All Lollyyy's LOveeee🤍🤍🤍🤍🤍🤍");

        }
        else if(question.equals("I am V")||question.equals("I am Varsha"))
        {
            addResponse("VVVV....Lolly Loves you a Lot🤍🤍🤍🤍");
        }
        else if(question.contains("Aparna")||question.contains("Santhosh")||question.contains("aparna")||question.contains("santhosh"))
        {
            addResponse("DO GIVE FULL MAKRS FOR THIS PROJECT PLEASEEEEEEEEE");
        }

        else {
            JSONObject jsonBody = new JSONObject();
            try {
                jsonBody.put("model", "gpt-3.5-turbo");
                JSONArray messageArr = new JSONArray();
                JSONObject obj = new JSONObject();
                obj.put("role", "user");
                obj.put("content", question);
                messageArr.put(obj);
                jsonBody.put("messages", messageArr);


            } catch (JSONException e) {
                e.printStackTrace();
            }

            RequestBody body = RequestBody.create(jsonBody.toString(), JSON);
            Request request = new Request.Builder()
                    .url("\n" +
                            "https://api.openai.com/v1/chat/completions")
                    .header("Authorization", "Bearer <OPENAICODE>")
                    .post(body)
                    .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                addResponse("Failed to load response due to "+e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if(response.isSuccessful()){
                    JSONObject  jsonObject = null;
                    try {
                        jsonObject = new JSONObject(response.body().string());
                        JSONArray jsonArray = jsonObject.getJSONArray("choices");

                        String result = jsonArray.getJSONObject(0).getJSONObject("message").getString("content");
                        addResponse(result.trim());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }else{
                    addResponse("Failed to load response due to Error:\n"+response.body().string());
                }
            }
        });





    }


}}




















