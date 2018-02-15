package com.example.petpack;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class splash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this switches to main activity from the splash screen, all stuff is done in XML
        //keep this activity blank.

        Intent intent = new Intent(this, imgLoader.class);
        startActivity(intent);
    }
}
