package com.example.katesudal.participantgroupmanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonEditParticipant;
    private Button buttonCreateProject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Realm.init(getApplicationContext());
        setContentView(R.layout.activity_main);
        buttonCreateProject = (Button) findViewById(R.id.buttonCreateProject);
        buttonCreateProject.setOnClickListener(this);
        buttonEditParticipant = (Button) findViewById(R.id.buttonEditParticipant);
        buttonEditParticipant.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.buttonEditParticipant){
            Intent intent = new Intent(view.getContext(), EditParticipantActivity.class);
            startActivity(intent);
        }
        if(view.getId()==R.id.buttonCreateProject){
            Intent intent = new Intent(view.getContext(), CreateProjectActivity.class);
            startActivity(intent);
        }
    }
}
