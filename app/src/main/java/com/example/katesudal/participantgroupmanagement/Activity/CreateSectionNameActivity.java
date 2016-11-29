package com.example.katesudal.participantgroupmanagement.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.katesudal.participantgroupmanagement.Adapter.ItemSectionNameAdapter;
import com.example.katesudal.participantgroupmanagement.R;

import java.util.ArrayList;
import java.util.List;

public class CreateSectionNameActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText editTextProjectName;
    private EditText editTextSectionName;
    private Button buttoAddSectionName;
    private ListView listViewSectionName;
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
        itemSectionNameAdapter = new ItemSectionNameAdapter(this);
        sectionNames = new ArrayList<String>();
        buttoAddSectionName.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.buttonAddSectionName){
            String sectionName = String.valueOf(editTextSectionName.getText());
            sectionNames.add(sectionName);
            itemSectionNameAdapter.setSectionNames(sectionNames);
            listViewSectionName.setAdapter(itemSectionNameAdapter);
            itemSectionNameAdapter.notifyDataSetChanged();
        }
    }
}
