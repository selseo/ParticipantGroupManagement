package com.example.katesudal.participantgroupmanagement.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.katesudal.participantgroupmanagement.Adapter.ItemSectionNameAdapter;
import com.example.katesudal.participantgroupmanagement.Model.Project;
import com.example.katesudal.participantgroupmanagement.Model.Section;
import com.example.katesudal.participantgroupmanagement.PreferencesService;
import com.example.katesudal.participantgroupmanagement.R;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;

public class CreateSectionNameActivity extends AppCompatActivity
        implements View.OnClickListener,
        ItemSectionNameAdapter.ItemSectionNameListener {
    private EditText editTextProjectName;
    private EditText editTextSectionName;
    private Button buttoAddSectionName;
    private ListView listViewSectionName;
    private LinearLayout buttonCreateProject;
    private LinearLayout buttonCancelCreateSectionName;
    ItemSectionNameAdapter itemSectionNameAdapter;
    List<String> sectionNames;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_section_name);
        editTextProjectName = (EditText) findViewById(R.id.editTextProjectName);
        editTextSectionName = (EditText) findViewById(R.id.editTextSectionName);
        buttoAddSectionName = (Button) findViewById(R.id.buttonAddSectionName);
        listViewSectionName = (ListView) findViewById(R.id.listViewSectionName);
        buttonCreateProject = (LinearLayout) findViewById(R.id.buttonCreateProject);
        buttonCancelCreateSectionName = (LinearLayout) findViewById(R.id.buttonCancelCreateSectionName);
        listViewSectionName.setFooterDividersEnabled(true);
        listViewSectionName.setHeaderDividersEnabled(true);
        listViewSectionName.setDividerHeight(20);
//        listViewSectionName.setDivider(getResources().getDrawable(R.color.background));
        itemSectionNameAdapter = new ItemSectionNameAdapter(this, this);
        sectionNames = new ArrayList<String>();
        buttoAddSectionName.setOnClickListener(this);
        buttonCreateProject.setOnClickListener(this);
        buttonCancelCreateSectionName.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonAddSectionName) {
            String sectionName = String.valueOf(editTextSectionName.getText());
            sectionNames.add(sectionName);
            itemSectionNameAdapter.setSectionNames(sectionNames);
            listViewSectionName.setAdapter(itemSectionNameAdapter);
            itemSectionNameAdapter.notifyDataSetChanged();
        }
        if (view.getId() == R.id.buttonCreateProject) {
            String projectName = String.valueOf(editTextProjectName.getText());
            Project project = new Project();
            project.setProjectName(projectName);
            RealmList<Section> sections = new RealmList<>();
            for (int sectionNameIndex = 0; sectionNameIndex < sectionNames.size(); sectionNameIndex++) {
                Section section = new Section();
                section.setSectionName(sectionNames.get(sectionNameIndex));
                sections.add(section);
            }
            project.setSectionIDs(sections);
            PreferencesService.savePreferences("Project", project, this);
            Intent intent = new Intent(this, CreateProjectActivity.class);
            startActivity(intent);
        }
        if (view.getId() == R.id.buttonCancelCreateSectionName) {
            onBackPressed();
        }
    }

    @Override
    public void deleteSectionName(View view, String sectionName) {
        sectionNames.remove(sectionName);
        itemSectionNameAdapter.setSectionNames(sectionNames);
        listViewSectionName.setAdapter(itemSectionNameAdapter);
        itemSectionNameAdapter.notifyDataSetChanged();
    }




}
