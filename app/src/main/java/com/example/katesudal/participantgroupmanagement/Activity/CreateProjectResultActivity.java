package com.example.katesudal.participantgroupmanagement.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.katesudal.participantgroupmanagement.Model.Project;
import com.example.katesudal.participantgroupmanagement.Model.Section;
import com.example.katesudal.participantgroupmanagement.R;

import io.realm.Realm;
import io.realm.RealmResults;

public class CreateProjectResultActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonBacktoMainFromCreateProject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Realm realm;
        realm = Realm.getDefaultInstance();
        long lastProjectID = getIntent().getExtras().getLong("projectID");
        setContentView(R.layout.activity_create_project_result);
        TextView textViewResult = (TextView) findViewById(R.id.textViewResult);
        buttonBacktoMainFromCreateProject = (Button) findViewById(R.id.buttonBacktoMainFromCreateProject);
        buttonBacktoMainFromCreateProject.setOnClickListener(this);
        Project lastProject = realm.where(Project.class)
                .equalTo("projectID",lastProjectID)
                .findFirst();
        String result ="Preoject : "+lastProject.getProjectName()+"\n";
        for(int sectionIndex = 0 ; sectionIndex<lastProject.getSectionIDs().size(); sectionIndex++){
            Section section = lastProject.getSectionIDs().get(sectionIndex);
            result = result +"   " +section.getSectionName()+" : ";
            for(int participantIndex = 0; participantIndex < section.getParticipantIDs().size(); participantIndex++){
                result = result + section.getParticipantIDs().get(participantIndex).getParticipantName()+", ";
            }
            result = result.substring(0,result.length()-2);
            result = result+"\n";
        }
        textViewResult.setText(result);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.buttonBacktoMainFromCreateProject){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }
    }
}
