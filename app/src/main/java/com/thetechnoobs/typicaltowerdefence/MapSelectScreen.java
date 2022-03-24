package com.thetechnoobs.typicaltowerdefence;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MapSelectScreen extends AppCompatActivity {

    ImageView mapOneIMG, mapTwoIMG, mapThreeIMG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_select_screen);

        settupIds();
    }

    private void settupIds() {
        mapOneIMG = findViewById(R.id.MapOneImg);
        mapTwoIMG = findViewById(R.id.MapTwoImg);
        mapThreeIMG = findViewById(R.id.MapThreeImg);
    }

    public void mapSelected(View v) {
        int id = v.getId();
        Intent goToGameView = new Intent(getApplicationContext(), GameViewActivity.class);

        if (id == R.id.MapOneImg) {
            goToGameView.putExtra("map", 1);
        } else if(id == R.id.MapTwoImg){
            goToGameView.putExtra("map", 2);
        }else if(id == R.id.MapThreeImg){
            goToGameView.putExtra("map", 3);
        }

        startActivity(goToGameView);
        finish();
    }
}