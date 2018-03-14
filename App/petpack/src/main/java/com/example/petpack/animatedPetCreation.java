package com.example.petpack;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateFormat;
import android.transition.AutoTransition;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import java.util.Calendar;

import static android.content.ContentValues.TAG;

public class animatedPetCreation extends FragmentActivity implements View.OnClickListener {
    private static final int NUMBER_OF_SCENES=3;
    Scene[] sceneIndex = new Scene[NUMBER_OF_SCENES];
    int scenei = 0;
    Transition mFadeTransition = new AutoTransition();
    ConstraintLayout container;
    AnimationDrawable anim;
    String petData="";
    int prevScene=0;
    //buttons

    @Override
    public void onClick(View V) {
        int id = V.getId();
        switch (id) {
            case R.id.gotoNextScene:
                ongotoNextSceneClick();
                break;
            case R.id.gotoPrevScene:
                ongotoPrevSceneClick();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animated_pet_creation);

        container = findViewById(R.id.container1);
        anim = (AnimationDrawable) container.getBackground();
        anim.setEnterFadeDuration(2500);
        anim.setExitFadeDuration(2500);

// Create the scene root for the scenes in this app
        ViewGroup mSceneRoot = (ViewGroup) findViewById(R.id.layoutbox);

// Create the scenes

        sceneIndex[1] = Scene.getSceneForLayout(mSceneRoot, R.layout.activity_scene_animatedpet_birthday, this);
        sceneIndex[0] = Scene.getSceneForLayout(mSceneRoot, R.layout.a_pet_creation_name_scene, this);
        sceneIndex[2] = Scene.getSceneForLayout(mSceneRoot, R.layout.a_pet_creation_color_scene, this);
        TransitionManager.go(sceneIndex[0], mFadeTransition);
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

    private void ongotoNextSceneClick() {
        prevScene=scenei;
        scenei++;
        if (scenei==NUMBER_OF_SCENES) {scenei--;}
        updateScene();

    }
    private void ongotoPrevSceneClick() {
        prevScene=scenei;
        scenei--;
        if (scenei==-1) {scenei++;}
        updateScene();
    }

    private void updateScene() {
        switch (prevScene) {
            case 0:
                doScene0Cleanup();
                break;
            case 1:
                doScene1Cleanup();
                break;
            case 2:
                doScene2Cleanup();
                break;
        }
        TransitionManager.go(sceneIndex[scenei], mFadeTransition);
        switch (scenei) {
            case 0:
                doScene0Setup();
                break;
            case 1:
                doScene1Setup();
                break;
            case 2:
                doScene2Setup();
                break;
        }
    }

    private void doScene2Cleanup() {
        EditText textBox=findViewById(R.id.textBox);
        petData=petData+textBox.getText().toString();
        Log.d(TAG, "scene2: "+ petData);

    }

    private void doScene1Cleanup() {
        EditText textBox=findViewById(R.id.textBox);
        petData=petData+textBox.getText().toString();
        Log.d(TAG, "scene1: "+ petData);
    }

    private void doScene0Cleanup() {
        EditText textBox=findViewById(R.id.textBox);
        petData=petData+textBox.getText().toString();
        Log.d(TAG, "scene3: "+ petData);
    }

    private void doScene2Setup() {

    }

    private void doScene1Setup() {

    }

    private void doScene0Setup() {

    }
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(),"datePicker");
    }


}
