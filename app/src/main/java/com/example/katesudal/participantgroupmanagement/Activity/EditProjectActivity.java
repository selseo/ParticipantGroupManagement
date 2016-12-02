package com.example.katesudal.participantgroupmanagement.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.katesudal.participantgroupmanagement.Adapter.ItemProjectAdapter;
import com.example.katesudal.participantgroupmanagement.Model.Participant;
import com.example.katesudal.participantgroupmanagement.Model.Project;
import com.example.katesudal.participantgroupmanagement.R;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class EditProjectActivity extends AppCompatActivity
        implements View.OnClickListener,ItemProjectAdapter.ItemProjectListener {
    Realm realm;
    private ListView listViewProject;
    private Button buttonBacktoMainFromEditProject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = Realm.getDefaultInstance();
        setContentView(R.layout.activity_edit_project);
        listViewProject = (ListView) findViewById(R.id.listViewProject);
        buttonBacktoMainFromEditProject = (Button) findViewById(R.id.buttonBacktoMainFromEditProject);
        buttonBacktoMainFromEditProject.setOnClickListener(this);
        viewProject(realm);
    }

    public void viewProject(Realm realm){
        List<Project> projectList = new ArrayList<Project>();
        RealmResults<Project> projects = realm.where(Project.class).findAll();
        for(int projectIndex = 0; projectIndex <projects.size(); projectIndex++){
            Project project = projects.get(projectIndex);
            projectList.add(project);
        }
        ItemProjectAdapter itemProjectAdapter = new ItemProjectAdapter(this,projectList,this,listViewProject);
        listViewProject.setAdapter(itemProjectAdapter);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.buttonBacktoMainFromEditProject){
            onBackPressed();
        }
    }

    @Override
    public void deleteProjectById(List<Project> projects, View view) {
        RealmResults<Project> project =realm.where(Project.class)
                .equalTo("projectID",projects.get(
                        listViewProject.getPositionForView(view))
                        .getProjectID())
                .findAll();
        realm.beginTransaction();
        project.deleteAllFromRealm();
        realm.commitTransaction();
        viewProject(realm);
    }

    @Override
    public void editProjectById(List<Project> projects, View view) {
        long projectID = projects.get(listViewProject.getPositionForView(view)).getProjectID();
        Intent intent = new Intent(this, EditEachProjectActivity.class);
        intent.putExtra("projectID", projectID);
        startActivity(intent);
    }
}
