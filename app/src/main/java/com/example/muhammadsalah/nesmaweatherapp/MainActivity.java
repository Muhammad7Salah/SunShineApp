package com.example.muhammadsalah.nesmaweatherapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText mail,pass;SharedPreferences preferences;SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = getApplicationContext().getSharedPreferences("editing",MODE_PRIVATE);
        editor = preferences.edit();

        if(preferences.getBoolean("loggedIn", Boolean.parseBoolean(null))) {

            startActivity(new Intent(this,Weather.class));

        }


        mail = (EditText)findViewById(R.id.email);
        pass = (EditText)findViewById(R.id.password);

    }

    public void loginAction(View view) {

        if(mail.getText().toString().length()!=0 && pass.getText().toString().length()!=0) {
            if (mail.getText().toString().equals("nesma@android.com") && pass.getText().toString().equals("0000"))
            {
                Toast.makeText(getApplicationContext(),"Welcome Nesma!",Toast.LENGTH_LONG).show();
                editor.putBoolean("loggedIn",true);
                editor.commit();
                startActivity( new Intent(this,Weather.class));
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Please, Enter the right info in the above fields!",Toast.LENGTH_LONG).show();
            }

        }
        else {
            Toast.makeText(getApplicationContext(),"Enter Email and password",Toast.LENGTH_LONG).show();
        }

    }
}
