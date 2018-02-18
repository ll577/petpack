package com.example.petpack;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;

public class mainMenu extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        //main menu button for jumping to image loader
        final Button button = findViewById(R.id.gotoImageLoader);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), imgLoader.class);
                intent.putExtra("action", "loadImage");
                startActivity(intent);

            }
        });
        final Button button1 = findViewById(R.id.gotoCreatePet);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), createPet.class);
                startActivity(intent);

            }
        });







    }

}
