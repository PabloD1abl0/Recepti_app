package com.example.food_recepti;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class PrijavaActivity extends AppCompatActivity {

    EditText tb_name, tb_username, tb_mail, tb_password, tb_repeatPassword;
    Button btn_Register;
    ImageButton btn_ShowPass, btn_ShowRepPass;
    private final OkHttpClient httpClient = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prijava);

        tb_name = (EditText)findViewById(R.id.tb_name);
        tb_username = (EditText)findViewById(R.id.tb_username);
        tb_repeatPassword = (EditText)findViewById(R.id.tb_repeat_password);
        tb_password = (EditText)findViewById(R.id.tb_password);
        btn_ShowPass = (ImageButton)findViewById(R.id.btn_showPass);
        btn_ShowRepPass = (ImageButton)findViewById(R.id.btn_ShowRepPass);
        btn_Register = (Button) findViewById(R.id.btn_register);
        btn_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    PrijavaActivity view1 = new PrijavaActivity();
                    view1.sendPOST();
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(),"Uporabnik že v aplikaciji!", Toast.LENGTH_LONG).show();
                }
            }
        });
        tb_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String txt = charSequence.toString();
                if(txt.length() < 6)
                    tb_password.setTextColor(Color.RED);
                else
                    tb_password.setTextColor((Color.BLACK));
            }

            @Override
            public void afterTextChanged(Editable editable){}
        });

        tb_repeatPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String txt = charSequence.toString();
                if(!txt.equals(tb_password.getText().toString()))
                    tb_repeatPassword.setTextColor(Color.RED);
                else
                    tb_repeatPassword.setTextColor((Color.BLACK));
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        btn_ShowPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD) == tb_password.getInputType())
                    tb_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
                else
                    tb_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                tb_password.setSelection(tb_password.getText().length());
            }
        });

        btn_ShowRepPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD) == tb_repeatPassword.getInputType())
                    tb_repeatPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
                else
                    tb_repeatPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                tb_repeatPassword.setSelection(tb_repeatPassword.getText().length());
            }
        });
    }

    private void sendPOST() throws IOException {

        FormBody formBody = new FormBody.Builder()
                .add("username", tb_username.getText().toString())  // add request headers
                .add("email", tb_mail.getText().toString())
                .add("password", tb_password.getText().toString())
                .build();

        Request request = new Request.Builder()
                .url("https://spv-projekt.herokuapp.com/users")
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
                        isSus[0] = false;
                }
            }
        });

        if(isSus[0])
            Toast.makeText(getApplicationContext(),"Uporabnik že v aplikaciji!", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(getApplicationContext(),"Uspešna registracija!", Toast.LENGTH_LONG).show();
    }
}