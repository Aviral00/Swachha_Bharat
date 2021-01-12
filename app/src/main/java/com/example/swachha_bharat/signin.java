package com.example.swachha_bharat;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class signin extends AppCompatActivity {

    EditText em, pas;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        em = (EditText)findViewById(R.id.editTextTextPersonName);
        pas = (EditText)findViewById(R.id.editTextTextPassword);

    }

    public void loginbt(View view){
        email = em.getText().toString();
        String pass = pas.getText().toString();

        background bg = new background(this);
        bg.execute(email, pass);

    }

    public void signupbt(View view){
        Intent intent = new Intent(signin.this, signup.class);
        startActivity(intent);
    }

}