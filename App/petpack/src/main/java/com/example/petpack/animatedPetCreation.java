package com.example.petpack;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.Fade;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class animatedPetCreation extends Activity {
    Scene mAScene;
    Scene mAnotherScene;
    Scene swap;
    Transition mFadeTransition = new AutoTransition();
    LinearLayout container;
    AnimationDrawable anim;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animated_pet_creation);
        final Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onButton2Click();
            }
        });
        container = (LinearLayout) findViewById(R.id.container1);

        anim = (AnimationDrawable) container.getBackground();
        anim.setEnterFadeDuration(2500);
        anim.setExitFadeDuration(2500);

// Create the scene root for the scenes in this app
        ViewGroup mSceneRoot = (ViewGroup) findViewById(R.id.layoutbox);

// Create the scenes
        mAnotherScene = Scene.getSceneForLayout(mSceneRoot, R.layout.a_pet_creation_name_scene, this);
        mAScene = Scene.getSceneForLayout(mSceneRoot, R.layout.a_pet_creation_name_scene2, this);



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

    private void onButton2Click() {
        TransitionManager.go(mAnotherScene, mFadeTransition);
        swap=mAScene;
        mAScene=mAnotherScene;
        mAnotherScene=swap;
        final Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //not actual recursion
                onButton2Click();
            }
        });
    }
}
