package com.example.food_recepti;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PrijavaActivity extends AppCompatActivity {

    EditText tb_name, tb_username, tb_mail, tb_password, tb_repeatPassword;
    Button btn_Register;

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




}