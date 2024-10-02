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
        ArrayList<String> easyRecordsList = intent.getStringArrayListExtra("easyRecords");
        ArrayList<String> mediumRecordsList = intent.getStringArrayListExtra("mediumRecords");
        ArrayList<String> hardRecordsList = intent.getStringArrayListExtra("hardRecords");

        TextView easyRecordsTextView = new TextView(this);
        easyRecordsTextView.setText("Easy records:");
        easyRecordsTextView.setTextSize(16);
        recordLinearLayout.addView(easyRecordsTextView);

        if (easyRecordsList != null && !easyRecordsList.isEmpty())
        {
            for (String record : easyRecordsList)
            {
                TextView recordTextView = new TextView(this);
                recordTextView.setText(record);
                recordTextView.setTextSize(16);

                recordLinearLayout.addView(recordTextView);
            }
        }

        if (mediumRecordsList != null && !mediumRecordsList.isEmpty())
        {
            TextView mediumRecordsTextView = new TextView(this);
            mediumRecordsTextView.setText("\nMedium records:");
            mediumRecordsTextView.setTextSize(16);
            recordLinearLayout.addView(mediumRecordsTextView);

            for (String record : mediumRecordsList)
            {
                TextView recordTextView = new TextView(this);
                recordTextView.setText(record);
                recordTextView.setTextSize(16);

                recordLinearLayout.addView(recordTextView);
            }
        }

        if (hardRecordsList != null && !hardRecordsList.isEmpty())
         {
            TextView hardRecordsTextView = new TextView(this);
            hardRecordsTextView.setText("\nHard records:");
            hardRecordsTextView.setTextSize(16);
            recordLinearLayout.addView(hardRecordsTextView);

            for (String record : hardRecordsList)
            {
                TextView recordTextView = new TextView(this);
                recordTextView.setText(record);
                recordTextView.setTextSize(16);

                recordLinearLayout.addView(recordTextView);
            }
        }


    }
}
