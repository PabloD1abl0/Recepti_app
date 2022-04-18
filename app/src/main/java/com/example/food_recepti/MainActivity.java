package com.example.food_recepti;

import static android.provider.AlarmClock.EXTRA_MESSAGE;
import com.example.food_recepti.User;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaDrm;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    ImageButton btn_showPass;
    private final OkHttpClient httpClient = new OkHttpClient();
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btn_showPass = (ImageButton)findViewById(R.id.btn_showPassword);
        tb_username = (EditText) findViewById(R.id.tb_username_main);
        tb_password = (EditText) findViewById(R.id.tb_password_main);
        tb_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        btn_login = (Button) findViewById(R.id.btn_login);


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    sendPOST();
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(),"Prijava neuspešna!", Toast.LENGTH_LONG).show();
                }
            }
        });
        btn_showPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD) == tb_password.getInputType())
                    tb_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
                else
                    tb_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                tb_password.setSelection(tb_password.getText().length());
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
        final boolean[] isOver = {false};

        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    Gson gson = new Gson();
                    JSONObject arr = new JSONObject(responseBody.string());
                    user = new User(gson.fromJson(arr.toString(),User.class));

                    isOver[0] = true;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        while (!isOver[0]){}
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("username",user.getUsername());
        intent.putExtra("password",user.getPassword());
        intent.putExtra("email",user.getEmail());
        startActivity(intent);
        user = null;
        tb_password.setText("");
        tb_username.setText("");

        }


    }
