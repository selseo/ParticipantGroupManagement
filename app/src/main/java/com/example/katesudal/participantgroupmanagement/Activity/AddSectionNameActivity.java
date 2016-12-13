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

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmList;

public class AddSectionNameActivity extends AppCompatActivity
        implements View.OnClickListener,
        ItemSectionNameAdapter.ItemSectionNameListener {

    @BindView(R.id.editTextProjectName)
    EditText editTextProjectName;
    @BindView(R.id.editTextSectionName)
    EditText editTextSectionName;
    @BindView(R.id.buttonAddSectionName)
    Button buttonAddSectionName;
    @BindView(R.id.listViewSectionName)
    ListView listViewSectionName;
    @BindView(R.id.buttonNextToCreateProject)
    LinearLayout buttonNextToCreateProject;
    @BindView(R.id.buttonCancelCreateSectionName)
    LinearLayout buttonCancelCreateSectionName;

    ItemSectionNameAdapter itemSectionNameAdapter;
    List<String> sectionNames;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_section_name);
        ButterKnife.bind(this);

        setListViewSectionNameDivider();
        itemSectionNameAdapter = new ItemSectionNameAdapter(this, this);
        sectionNames = new ArrayList<>();
        setListener();
    }

    private void setListener() {
        buttonAddSectionName.setOnClickListener(this);
        buttonNextToCreateProject.setOnClickListener(this);
        buttonCancelCreateSectionName.setOnClickListener(this);
    }

    private void setListViewSectionNameDivider() {
        listViewSectionName.setFooterDividersEnabled(true);
        listViewSectionName.setHeaderDividersEnabled(true);
        listViewSectionName.setDividerHeight(20);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonAddSectionName) {
            addNewSectionNameToAdapter();
        }
        if (view.getId() == R.id.buttonNextToCreateProject) {
            nextToCreateProject();
        }
        if (view.getId() == R.id.buttonCancelCreateSectionName) {
            onBackPressed();
        }
    }

    private void nextToCreateProject() {
        String projectName = String.valueOf(editTextProjectName.getText());
        if(ValidateUtil.isInvalidName(projectName)) {
            showDialogInvalidProjectName();
            return;
        }
        if(ValidateUtil.isDuplicateProjectName(projectName)){
            showDuplicateProjectName();
            return;
        }
        if(sectionNames.size()<1){
            showDialogNoSection();
            return;
        }
        Project project = new Project();
        project.setProjectName(projectName);
        RealmList<Section> sections = new RealmList<>();
        setSectionNameList(sections,sectionNames);
        project.setSectionIDs(sections);
        PreferencesService.savePreferences("Project", project, this);
        Intent intent = new Intent(this, CreateProjectActivity.class);
        startActivity(intent);
    }

    public void setSectionNameList(List<Section> sections,List<String> sectionNames) {
        for (int sectionNameIndex = 0; sectionNameIndex < sectionNames.size(); sectionNameIndex++) {
            Section section = new Section();
            section.setSectionName(sectionNames.get(sectionNameIndex));
            sections.add(section);
        }
    }

    private void showDialogNoSection() {
        AlertDialog.Builder dialogErrorBuilder = new AlertDialog.Builder(this);
        dialogErrorBuilder.setMessage("Please add any section");
        dialogErrorBuilder.setCancelable(false);
        dialogErrorBuilder.setPositiveButton("OK",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog,int which){
                dialog.dismiss();
            }
        });
        dialogErrorBuilder.show();
    }

    private void showDuplicateProjectName() {
        AlertDialog.Builder dialogErrorBuilder = new AlertDialog.Builder(this);
        dialogErrorBuilder.setMessage("This Project Name is Duplicate. \nPlease try another.");
        dialogErrorBuilder.setCancelable(false);
        dialogErrorBuilder.setPositiveButton("OK",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog,int which){
                dialog.dismiss();
            }
        });
        dialogErrorBuilder.show();
    }

    private void showDialogInvalidProjectName() {
        AlertDialog.Builder dialogErrorBuilder = new AlertDialog.Builder(this);
        dialogErrorBuilder.setMessage("Project Name should begin with alphabet or number. \nPlease try another.");
        dialogErrorBuilder.setCancelable(false);
        dialogErrorBuilder.setPositiveButton("OK",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog,int which){
                dialog.dismiss();
            }
        });
        dialogErrorBuilder.show();
    }

    private void addNewSectionNameToAdapter() {
        String sectionName = String.valueOf(editTextSectionName.getText());
        if(ValidateUtil.isInvalidName(sectionName)) {
            showDialogInvalidSectionName();
            return;
        }
        if(ValidateUtil.isDuplicateSectionName(sectionName,sectionNames)){
            showDialogDuplicateSectionName();
            return;
        }
        sectionNames.add(sectionName);
        itemSectionNameAdapter.setSectionNames(sectionNames);
        listViewSectionName.setAdapter(itemSectionNameAdapter);
        itemSectionNameAdapter.notifyDataSetChanged();
        editTextSectionName.setText("");
    }

    private void showDialogDuplicateSectionName() {
        AlertDialog.Builder dialogErrorBuilder = new AlertDialog.Builder(this);
        dialogErrorBuilder.setMessage("This Section Name is Duplicate. \nPlease try another.");
        dialogErrorBuilder.setCancelable(false);
        dialogErrorBuilder.setPositiveButton("OK",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog,int which){
                dialog.dismiss();
            }
        });
        dialogErrorBuilder.show();
    }

    private void showDialogInvalidSectionName() {
        AlertDialog.Builder dialogErrorBuilder = new AlertDialog.Builder(this);
        dialogErrorBuilder.setMessage("Section Name should begin with letter or number. \nPlease try another.");
        dialogErrorBuilder.setCancelable(false);
        dialogErrorBuilder.setPositiveButton("OK",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog,int which){
                dialog.dismiss();
            }
        });
        dialogErrorBuilder.show();
    }

    @Override
    public void deleteSectionName(View view, String sectionName) {
        sectionNames.remove(sectionName);
        itemSectionNameAdapter.setSectionNames(sectionNames);
        listViewSectionName.setAdapter(itemSectionNameAdapter);
        itemSectionNameAdapter.notifyDataSetChanged();
    }




}
