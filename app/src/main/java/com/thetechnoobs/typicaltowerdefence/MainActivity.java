 package com.thetechnoobs.typicaltowerdefence;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

 public class MainActivity extends AppCompatActivity {

     @Override
     protected void onCreate(@Nullable Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_main);
     }

     public void buttonPush(View view) {
         if(view.getId() == R.id.mapSelect){
             Intent goToSelectScreen = new Intent(getApplicationContext(), MapSelectScreen.class);
             startActivity(goToSelectScreen);
             finish();
         }
     }
 }