package com.example.food_recepti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import com.example.food_recepti.User;

public class ProfileActivity extends AppCompatActivity {

    EditText tb_name, tb_username, tb_mail, tb_password;
    ImageButton btn_cName,btn_cUsername, btn_cMail, btn_cPassword;
    private final OkHttpClient httpClient = new OkHttpClient();
    private List<User> users = new ArrayList<User>();
    String resp = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tb_name = (EditText) findViewById(R.id.tb_name_profile);
        tb_username = (EditText) findViewById(R.id.tb_username_profile);
        tb_mail = (EditText) findViewById(R.id.tb_mail_profile);
        tb_password = (EditText) findViewById(R.id.tb_password_profile);
        btn_cName = (ImageButton) findViewById(R.id.btn_change_name);
        btn_cUsername = (ImageButton) findViewById(R.id.btn_change_username);
        btn_cMail = (ImageButton) findViewById(R.id.btn_change_mail);
        btn_cPassword = (ImageButton) findViewById(R.id.btn_change_password);

        Intent intent = getIntent();
        tb_username.setText(intent.getStringExtra("username"));
        tb_name.setText(intent.getStringExtra("username"));
        tb_mail.setText(intent.getStringExtra("email"));
        tb_password.setText(intent.getStringExtra("password"));

        /*try {
            //sendGET();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }*/

    }

    private void Fill(){

        tb_name.setText(users.get(0).username.toString());
        tb_username.setText(users.get(0).username);
        tb_password.setText(users.get(0).password);
        tb_mail.setText(users.get(0).email);
    }

    private void sendGET() throws IOException, InterruptedException {
        Request request = new Request.Builder()
                .url("https://spv-projekt.herokuapp.com/users")
                .build();

        final boolean[] isOver = {false};

        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try (ResponseBody responseBody = response.body()){
                    Gson gson = new Gson();
                    JSONArray arr = new JSONArray(responseBody.string());
                    for (int i = 0; i < arr.length(); i++) {
                        users.add(gson.fromJson(arr.get(i).toString(),User.class));
                    }
                    isOver[0] = true;

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        while(!isOver[0]){}
        Fill();
    }
}