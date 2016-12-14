package com.example.katesudal.participantgroupmanagement.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.katesudal.participantgroupmanagement.Adapter.ItemSpecialGroupAdapter;
import com.example.katesudal.participantgroupmanagement.Model.SpecialGroup;
import com.example.katesudal.participantgroupmanagement.R;
import com.example.katesudal.participantgroupmanagement.Util.ValidateUtil;

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
        Realm.init(getApplicationContext());
        realm = Realm.getDefaultInstance();
        setContentView(R.layout.activity_manage_special_group);
        buttonBacktoMainFromManageSpecialGroup = (Button) findViewById(R.id.buttonBacktoMainFromManageSpecialGroup);
        buttonBacktoMainFromManageSpecialGroup.setOnClickListener(this);
        buttonAddSpecialGroup = (Button) findViewById(R.id.buttonAddSpecialGroupName);
        buttonAddSpecialGroup.setOnClickListener(this);
        editTextSpecialGroupName = (EditText) findViewById(R.id.editTextSpecialGroupName);
        listViewSpecialGroup = (ListView) findViewById(R.id.listViewSpecialGroup);
        listViewSpecialGroup.setDividerHeight(20);
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
            if(ValidateUtil.isInvalidName(specialGroupName)){
                AlertDialog.Builder dialogErrorBuilder = new AlertDialog.Builder(this);
                dialogErrorBuilder.setMessage("Special Group Name should begin with letter or number. \nPlease try another.");
                dialogErrorBuilder.setCancelable(false);
                dialogErrorBuilder.setPositiveButton("OK",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog,int which){
                        dialog.dismiss();
                    }
                });
                dialogErrorBuilder.show();
                return;
            }
            Intent intent = new Intent(this,CreateSpecialGroupActivity.class);
            intent.putExtra("specialGroupName", specialGroupName);
            startActivity(intent);
        }
    }

    @Override
    public void deleteSpecialGroupById(final List<SpecialGroup> specialGroups,final View view) {
        AlertDialog.Builder dialogErrorBuilder = new AlertDialog.Builder(this);
        dialogErrorBuilder.setMessage("Do you want to delete?");
        dialogErrorBuilder.setCancelable(false);
        dialogErrorBuilder.setPositiveButton("Delete",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog,int which){
                RealmResults<SpecialGroup> specialGroup =realm.where(SpecialGroup.class)
                        .equalTo("specialGroupID",specialGroups.get(
                                listViewSpecialGroup.getPositionForView(view))
                                .getSpecialGroupID())
                        .findAll();
                realm.beginTransaction();
                specialGroup.deleteAllFromRealm();
                realm.commitTransaction();
                viewSpecialGroup();
                dialog.dismiss();
            }
        });
        dialogErrorBuilder.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogErrorBuilder.show();
    }

    @Override
    public void editSpecialGroupById(List<SpecialGroup> specialGroups, View view) {
        long specialGroupID = specialGroups.get(listViewSpecialGroup.getPositionForView(view)).getSpecialGroupID();
        Intent intent = new Intent(this, EditSpecialGroupActivity.class);
        intent.putExtra("specialGroupID", specialGroupID);
        startActivity(intent);
    }
}
