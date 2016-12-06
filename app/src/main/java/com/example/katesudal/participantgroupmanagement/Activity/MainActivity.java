package com.example.katesudal.participantgroupmanagement.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.katesudal.participantgroupmanagement.R;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonGotoEditParticipant;
    private Button buttonGotoCreateProject;
    private Button buttonGotoViewEncounter;
    private Button buttonGotoEditProject;
    private Button buttonGotoEditSpecialSection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Realm.init(getApplicationContext());
        setContentView(R.layout.activity_main);
        buttonGotoCreateProject = (Button) findViewById(R.id.buttonGotoCreateProject);
        buttonGotoCreateProject.setOnClickListener(this);
        buttonGotoEditParticipant = (Button) findViewById(R.id.buttonGotoEditParticipant);
        buttonGotoEditParticipant.setOnClickListener(this);
        buttonGotoViewEncounter = (Button) findViewById(R.id.buttonGotoViewEncounter);
        buttonGotoViewEncounter.setOnClickListener(this);
        buttonGotoEditProject = (Button) findViewById(R.id.buttonGotoEditProject);
        buttonGotoEditProject.setOnClickListener(this);
        buttonGotoEditSpecialSection = (Button) findViewById(R.id.buttonGotoEditSpecialSection);
        buttonGotoEditSpecialSection.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.buttonGotoEditParticipant){
            Intent intent = new Intent(view.getContext(), EditParticipantActivity.class);
            startActivity(intent);
        }
        if(view.getId()==R.id.buttonGotoCreateProject){
            Intent intent = new Intent(view.getContext(), CreateSectionNameActivity.class);
            startActivity(intent);
        }
        if(view.getId()==R.id.buttonGotoViewEncounter){
            Intent intent = new Intent(view.getContext(),ViewEncounterActivity.class);
            startActivity(intent);
        }
        if(view.getId()==R.id.buttonGotoEditProject){
            Intent intent = new Intent(view.getContext(),EditProjectActivity.class);
            startActivity(intent);
        }
        if(view.getId()==R.id.buttonGotoEditSpecialSection){
            Intent intent = new Intent(view.getContext(),ManageSpecialGroup.class);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}
