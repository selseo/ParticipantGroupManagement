package com.example.katesudal.participantgroupmanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import static com.example.katesudal.participantgroupmanagement.R.id.listViewParticipant;

public class CreateSectionNameActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText editTextProjectName;
    private EditText editTextSectionName;
    private Button buttoAddSectionNamee;
    private ListView listViewSectionName;
    List<String> sectionNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_section_name);
        editTextProjectName = (EditText) findViewById(R.id.editTextProjectName);
        editTextSectionName = (EditText) findViewById(R.id.editTextSectionName);
        buttoAddSectionNamee = (Button) findViewById(R.id.buttonAddSectionName);
        listViewSectionName = (ListView) findViewById(R.id.listViewSectionName);
        sectionNames = new ArrayList<String>();
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.buttonAddSectionName){
            String sectionName = String.valueOf(editTextSectionName.getText());
            sectionNames.add(sectionName);
            ItemSectionNameAdapter itemSectionNameAdapter = new ItemSectionNameAdapter(this,sectionNames);
            listViewSectionName.setAdapter(itemSectionNameAdapter);
        }
    }
}
