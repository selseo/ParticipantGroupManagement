package com.example.katesudal.participantgroupmanagement.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.katesudal.participantgroupmanagement.Adapter.ItemSpecialGroupAdapter;
import com.example.katesudal.participantgroupmanagement.Model.SpecialGroup;
import com.example.katesudal.participantgroupmanagement.R;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class ManageSpecialGroup extends AppCompatActivity
        implements View.OnClickListener, ItemSpecialGroupAdapter.ItemSpecialGroupListener{
    private Button buttonBacktoMainFromManageSpecialGroup;
    private Button buttonAddSpecialGroup;
    private ListView listViewSpecialGroup;
    private EditText editTextSpecialGroupName;
    private String specialGroupName;
    private Realm realm;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = Realm.getDefaultInstance();
        setContentView(R.layout.activity_manage_special_group);
        buttonBacktoMainFromManageSpecialGroup = (Button) findViewById(R.id.buttonBacktoMainFromManageSpecialGroup);
        buttonBacktoMainFromManageSpecialGroup.setOnClickListener(this);
        buttonAddSpecialGroup = (Button) findViewById(R.id.buttonAddSpecialGroupName);
        buttonAddSpecialGroup.setOnClickListener(this);
        editTextSpecialGroupName = (EditText) findViewById(R.id.editTextSpecialGroupName);
        listViewSpecialGroup = (ListView) findViewById(R.id.listViewSpecialGroup);
        viewSpecialGroup();
    }

    private void viewSpecialGroup() {
        List<SpecialGroup> specialGroupList = new ArrayList<>();
        RealmResults<SpecialGroup> specialGroups = realm.where(SpecialGroup.class).findAll();
        for(int specialGroupIndex = 0; specialGroupIndex <specialGroups.size(); specialGroupIndex++){
            SpecialGroup specialGroup = specialGroups.get(specialGroupIndex);
            specialGroupList.add(specialGroup);
        }
        ItemSpecialGroupAdapter itemspecialGroupAdapter = new ItemSpecialGroupAdapter(this,specialGroupList,this,listViewSpecialGroup);
        listViewSpecialGroup.setAdapter(itemspecialGroupAdapter);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.buttonBacktoMainFromManageSpecialGroup){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }
        if(view.getId()==R.id.buttonAddSpecialGroupName){
            specialGroupName = String.valueOf(editTextSpecialGroupName.getText());
            Intent intent = new Intent(this,AddSpecialGroup.class);
            intent.putExtra("specialGroupName", specialGroupName);
            startActivity(intent);
        }
    }

    @Override
    public void deleteSpecialGroupById(List<SpecialGroup> projects, View view) {

    }

    @Override
    public void editSpecialGroupById(List<SpecialGroup> projects, View view) {

    }
}
