package com.example.food_recepti;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

class User {
    String _id, username, name, email, password;
    int __v;
    public User() {
        _id = username = name = email = password = "";
        __v = 0;
    }
    public User(String i, String u, String n, String m, String p, int v){
        _id = i;
        username = u;
        //name = n;
        email = m;
        password = p;
        this.__v = v;
    }
    public User(User user){
        this._id = user._id;
        this.username = user.username;
        this.name = user.name;
        this.email = user.email;
        this.password = user.password;
        this.__v = user.__v;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int get__v() {
        return __v;
    }

    public void set__v(int __v) {
        this.__v = __v;
    }

    @Override
    public String toString() {
        return "Users{" +
                "id='" + _id + '\'' +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", mail='" + email + '\'' +
                ", password='" + password + '\'' +
                ", v=" + __v +
                '}';
    }
}

public class PrijavaActivity extends AppCompatActivity {

    EditText tb_name, tb_username, tb_mail, tb_password, tb_repeatPassword;
    Button btn_Register;
    private final OkHttpClient httpClient = new OkHttpClient();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prijava);
        tb_name = (EditText)findViewById(R.id.tb_name);
        tb_username = (EditText)findViewById(R.id.tb_username);
        tb_repeatPassword = (EditText)findViewById(R.id.tb_repeat_password);
        tb_password = (EditText)findViewById(R.id.tb_password);
        tb_repeatPassword = (EditText)findViewById(R.id.tb_repeat_password);
        btn_Register = (Button)findViewById(R.id.btn_register);
        tb_mail = (EditText)findViewById(R.id.tb_mail_register);
        btn_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    sendPOST();
                } catch (IOException e) {
                    e.printStackTrace();
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
                    tb_password.setTextColor((Color.WHITE));
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
                    tb_repeatPassword.setTextColor((Color.WHITE));
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }
    public void RegisterUser(){
        //get request
        //check if user exists
        //if true -> make.toast(Exists)
        //else post request v bazo
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
                    //tb_mail.setText("users.get(0).email");
                    //Gson gson = new Gson();

                    //tb_name.setText(response.body().string());
                    /*List<User> users = new ArrayList<User>();
                    JSONArray arr = new JSONArray(response.body().string());
                    int count = 1;
                    int index = 0;
                    for (int i = 0; i < arr.length(); i++) {
                        users.add(gson.fromJson(arr.get(i).toString(),User.class));
                    }
                    tb_username.setText(users.get(0).username);
                    tb_password.setText(users.get(0).password);
                    tb_mail.setText(users.get(0).email);*/

                }
            }
        });
        if(isSus[0]){
            Toast.makeText(getApplicationContext(),"Uporabnik že v aplikaciji!", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getApplicationContext(),"Uspešna registracija!", Toast.LENGTH_LONG).show();

        }


    }
}