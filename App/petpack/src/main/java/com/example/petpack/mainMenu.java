package com.example.petpack;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.app.Activity;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.Button;

public class mainMenu extends Activity {
    ConstraintLayout container;
    AnimationDrawable anim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        container = findViewById(R.id.mainMenuBG);
        anim = (AnimationDrawable) container.getBackground();
        anim.setEnterFadeDuration(2500);
        anim.setExitFadeDuration(2500);

        //main menu button for jumping to image loader
        final Button button = findViewById(R.id.gotoImageLoader);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), animatedPetCreation.class);
                intent.putExtra("action", "loadImage");
                startActivity(intent);

            }
        });






    }
    @Override
    protected void onResume() {
        super.onResume();
        if (anim != null && !anim.isRunning())
            anim.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (anim != null && anim.isRunning())
            anim.stop();
    }

}
