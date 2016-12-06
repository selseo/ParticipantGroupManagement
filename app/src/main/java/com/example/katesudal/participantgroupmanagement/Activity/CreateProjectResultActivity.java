package com.example.katesudal.participantgroupmanagement.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.katesudal.participantgroupmanagement.Model.Project;
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
        setContentView(R.layout.activity_create_project_result);
        TextView textViewResult = (TextView) findViewById(R.id.textViewResult);
        buttonBacktoMainFromCreateProject = (Button) findViewById(R.id.buttonBacktoMainFromCreateProject);
        buttonBacktoMainFromCreateProject.setOnClickListener(this);
        String result ="";
        RealmResults<Project> projects = realm.where(Project.class).findAll();
        for(int projectIndex = 0; projectIndex < projects.size(); projectIndex++){
            result=result+"Project ID : "+projects.get(projectIndex).getProjectID()+"\n";
            result=result+"Project name : "+projects.get(projectIndex).getProjectName() +"\n"+"Section :\n";
            for(int sectionIndex = 0; sectionIndex < projects.get(projectIndex).getSectionIDs().size();sectionIndex++){
                result = result+projects.get(projectIndex).getSectionIDs().get(sectionIndex).getSectionID()
                        +" "+projects.get(projectIndex).getSectionIDs().get(sectionIndex).getSectionName()
                        +" : "+projects.get(projectIndex).getSectionIDs().get(sectionIndex).getParticipantIDs().size()+"\n";
            }
            result=result+"\n";
        }
        Log.d("Result",result);
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
