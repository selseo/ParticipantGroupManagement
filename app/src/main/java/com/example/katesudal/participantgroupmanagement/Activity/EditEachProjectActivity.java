package com.example.katesudal.participantgroupmanagement.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.katesudal.participantgroupmanagement.R;

public class EditEachProjectActivity extends AppCompatActivity {
    int projectID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_each_project);
        projectID = getIntent().getExtras().getInt("");
    }
}
