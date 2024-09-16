package com.rus.minesweeper;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class RecordActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        LinearLayout recordLinearLayout = findViewById(R.id.record_linear_layout);

        Intent intent = getIntent();
        ArrayList<String> recordsList = intent.getStringArrayListExtra("records");

        if (recordsList != null)
        {
            for (String record : recordsList)
            {
                TextView recordTextView = new TextView(this);
                recordTextView.setText(record);
                recordTextView.setTextSize(16);

                recordLinearLayout.addView(recordTextView);
            }
        }


    }
}
