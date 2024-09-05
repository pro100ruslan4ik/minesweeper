package com.rus.minesweeper;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    private Button easyGameButton;
    private Button mediumGameButton;
    private Button hardGameButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        initializeViews();
        setupListeners();
    }

    private void initializeViews()
    {
        easyGameButton =  findViewById(R.id.easy_game_button);
        mediumGameButton =  findViewById(R.id.medium_game_button);
        hardGameButton =  findViewById(R.id.hard_game_button);

    }
    private void setupListeners()
    {
        easyGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, GameActivity.class);
                intent.putExtra("difficulty",1);
                startActivity(intent);
            }
        });

        mediumGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, GameActivity.class);
                intent.putExtra("difficulty",2);
                startActivity(intent);
            }
        });

        hardGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, GameActivity.class);
                intent.putExtra("difficulty",3);
                startActivity(intent);
            }
        });

    }

}
