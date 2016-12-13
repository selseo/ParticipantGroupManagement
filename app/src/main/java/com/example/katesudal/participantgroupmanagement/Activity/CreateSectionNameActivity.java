package com.example.katesudal.participantgroupmanagement.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
import com.example.katesudal.participantgroupmanagement.Util.ValidateUtil;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;

public class CreateSectionNameActivity extends AppCompatActivity
        implements View.OnClickListener,
        ItemSectionNameAdapter.ItemSectionNameListener {
    private EditText editTextProjectName;
    private EditText editTextSectionName;
    private Button buttonAddSectionName;
    private ListView listViewSectionName;
    private LinearLayout buttonNextToCreateProject;
    private LinearLayout buttonCancelCreateSectionName;
    ItemSectionNameAdapter itemSectionNameAdapter;
    List<String> sectionNames;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_section_name);
        editTextProjectName = (EditText) findViewById(R.id.editTextProjectName);
        editTextSectionName = (EditText) findViewById(R.id.editTextSectionName);
        buttonAddSectionName = (Button) findViewById(R.id.buttonAddSectionName);
        listViewSectionName = (ListView) findViewById(R.id.listViewSectionName);
        buttonNextToCreateProject = (LinearLayout) findViewById(R.id.buttonNextToCreateProject);
        buttonCancelCreateSectionName = (LinearLayout) findViewById(R.id.buttonCancelCreateSectionName);
        listViewSectionName.setFooterDividersEnabled(true);
        listViewSectionName.setHeaderDividersEnabled(true);
        listViewSectionName.setDividerHeight(20);
//        listViewSectionName.setDivider(getResources().getDrawable(R.color.background));
        itemSectionNameAdapter = new ItemSectionNameAdapter(this, this);
        sectionNames = new ArrayList<>();
        buttonAddSectionName.setOnClickListener(this);
        buttonNextToCreateProject.setOnClickListener(this);
        buttonCancelCreateSectionName.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonAddSectionName) {
            String sectionName = String.valueOf(editTextSectionName.getText());
            if(ValidateUtil.isInvalidName(sectionName)) {
                AlertDialog.Builder dialogErrorBuilder = new AlertDialog.Builder(this);
                dialogErrorBuilder.setMessage("Section Name should begin with letter or number. \nPlease try another.");
                dialogErrorBuilder.setCancelable(false);
                dialogErrorBuilder.setPositiveButton("OK",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog,int which){
                        dialog.dismiss();
                    }
                });
                dialogErrorBuilder.show();
                return;
            }
            if(ValidateUtil.isDuplicateSectionName(sectionName,sectionNames)){
                AlertDialog.Builder dialogErrorBuilder = new AlertDialog.Builder(this);
                dialogErrorBuilder.setMessage("This Section Name is Duplicate. \nPlease try another.");
                dialogErrorBuilder.setCancelable(false);
                dialogErrorBuilder.setPositiveButton("OK",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog,int which){
                        dialog.dismiss();
                    }
                });
                dialogErrorBuilder.show();
                return;
            }
            sectionNames.add(sectionName);
            itemSectionNameAdapter.setSectionNames(sectionNames);
            listViewSectionName.setAdapter(itemSectionNameAdapter);
            itemSectionNameAdapter.notifyDataSetChanged();
            editTextSectionName.setText("");
        }
        if (view.getId() == R.id.buttonNextToCreateProject) {
            String projectName = String.valueOf(editTextProjectName.getText());
            if(ValidateUtil.isInvalidName(projectName)) {
                AlertDialog.Builder dialogErrorBuilder = new AlertDialog.Builder(this);
                dialogErrorBuilder.setMessage("Project Name should begin with alphabet or number. \nPlease try another.");
                dialogErrorBuilder.setCancelable(false);
                dialogErrorBuilder.setPositiveButton("OK",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog,int which){
                        dialog.dismiss();
                    }
                });
                dialogErrorBuilder.show();
                return;
            }
            if(ValidateUtil.isDuplicateProjectName(projectName)){
                AlertDialog.Builder dialogErrorBuilder = new AlertDialog.Builder(this);
                dialogErrorBuilder.setMessage("This Project Name is Duplicate. \nPlease try another.");
                dialogErrorBuilder.setCancelable(false);
                dialogErrorBuilder.setPositiveButton("OK",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog,int which){
                        dialog.dismiss();
                    }
                });
                dialogErrorBuilder.show();
                return;
            }
            if(sectionNames.size()<1){
                AlertDialog.Builder dialogErrorBuilder = new AlertDialog.Builder(this);
                dialogErrorBuilder.setMessage("Please add any section");
                dialogErrorBuilder.setCancelable(false);
                dialogErrorBuilder.setPositiveButton("OK",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog,int which){
                        dialog.dismiss();
                    }
                });
                dialogErrorBuilder.show();
                return;
            }
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
