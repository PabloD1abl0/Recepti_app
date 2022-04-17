package com.example.food_recepti;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {

    EditText tb_username, tb_password;
    Button btn_login;
    private final OkHttpClient httpClient = new OkHttpClient();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tb_username = (EditText) findViewById(R.id.tb_username_main);
        tb_password = (EditText) findViewById(R.id.tb_password_main);
        btn_login = (Button) findViewById(R.id.btn_login);


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    sendPOST();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void OpenRegistration(View view){
        Intent intent = new Intent(this, PrijavaActivity.class);
        startActivity(intent);
    }
    public void OpenMain(View view){
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    private void sendPOST() throws IOException {

        FormBody formBody = new FormBody.Builder()
                .add("username", tb_username.getText().toString())  // add request headers
                .add("password", tb_password.getText().toString())
                .build();

        Request request = new Request.Builder()
                .url("https://spv-projekt.herokuapp.com/users/login")
                .post(formBody)
                .build();
        final boolean[] isSus = {true};

        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful())
                        tb_password.setText(response.body().string());
                        isSus[0] = false;
                }
            }
        });
        if(isSus[0]){
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(getApplicationContext(),"Prijava neuspešna!", Toast.LENGTH_LONG).show();

        }


    }

}