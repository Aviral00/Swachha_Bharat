package com.example.swachha_bharat;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;

public class signup extends AppCompatActivity {

    EditText nme, em, ph, pas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        nme = (EditText)findViewById(R.id.editTextTextPersonName3);
        em = (EditText)findViewById(R.id.editTextTextEmailAddress);
        ph = (EditText)findViewById(R.id.editTextPhone);
        pas = (EditText)findViewById(R.id.editTextTextPassword2);

    }

    public void signupbtn(View view){

        String name = nme.getText().toString();
        String email = em.getText().toString();
        String phone = ph.getText().toString();
        String pass = pas.getText().toString();
        background_signup back = new background_signup(this);
        back.execute(name, email, phone, pass);

    }

}