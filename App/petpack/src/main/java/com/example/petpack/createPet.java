package com.example.petpack;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class createPet extends Activity {
    private static Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pet);
        this.context = getApplicationContext();
        final Button button = findViewById(R.id.createThePet);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText mEdit;
                //todo sanitize inputs
                mEdit = (EditText) findViewById(R.id.NameEdit);
                String name = mEdit.getText().toString();
                mEdit = (EditText) findViewById(R.id.ageEdit);
                int age = Integer.parseInt(mEdit.getText().toString());
                mEdit = (EditText) findViewById(R.id.ageEdit);
                try {
                    Date birthdate = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).parse(mEdit.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                packedPet pet = new packedPet(context, name);

            }
        });
    }
}

