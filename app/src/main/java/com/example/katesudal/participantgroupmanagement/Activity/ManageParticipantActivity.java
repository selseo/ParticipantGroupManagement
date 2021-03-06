package com.example.katesudal.participantgroupmanagement.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.katesudal.participantgroupmanagement.Adapter.ItemParticipantAdapter;
import com.example.katesudal.participantgroupmanagement.Model.Participant;
import com.example.katesudal.participantgroupmanagement.R;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class ManageParticipantActivity extends AppCompatActivity
        implements View.OnClickListener,
        ItemParticipantAdapter.ItemParticipantListener {

    private Button buttonGotoAddParticipant;
    private LinearLayout buttonBacktoMain;
    private ListView listViewParticipant;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = Realm.getDefaultInstance();
        setContentView(R.layout.activity_edit_participant);
        buttonGotoAddParticipant = (Button) findViewById(R.id.buttonGotoAddParticipant);
        listViewParticipant = (ListView) findViewById(R.id.listViewParticipant);
        buttonBacktoMain = (LinearLayout) findViewById(R.id.buttonBacktoMain);
        listViewParticipant.setDividerHeight(20);
        buttonGotoAddParticipant.setOnClickListener(this);
        buttonBacktoMain.setOnClickListener(this);
        viewParticipant(realm);
    }

    public void viewParticipant(Realm realm){
        List<Participant> participantList = new ArrayList<Participant>();
        RealmResults<Participant> participants = realm.where(Participant.class).findAll();
        for(int participantIndex = 0; participantIndex < participants.size(); participantIndex++){
            Participant participant = participants.get(participantIndex);
            participantList.add(participant);
        }
        ItemParticipantAdapter itemParticipantAdapter = new ItemParticipantAdapter(participantList,this, this, listViewParticipant);
        listViewParticipant.setAdapter(itemParticipantAdapter);
    }


    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.buttonGotoAddParticipant){
            Intent intent = new Intent(view.getContext(), AddParticipantActivity.class);
            startActivity(intent);
        }
        if(view.getId()==R.id.buttonBacktoMain){
            onBackPressed();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void deleteParticipantById(final List<Participant> participants,final View view) {
        AlertDialog.Builder dialogErrorBuilder = new AlertDialog.Builder(this);
        dialogErrorBuilder.setMessage("Do you want to delete?");
        dialogErrorBuilder.setCancelable(false);
        dialogErrorBuilder.setPositiveButton("Delete",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                RealmResults<Participant> participant =realm.where(Participant.class)
                        .equalTo("participantID",participants.get(
                                listViewParticipant.getPositionForView(view))
                                .getParticipantID())
                        .findAll();
                realm.beginTransaction();
                participant.deleteAllFromRealm();
                realm.commitTransaction();
                viewParticipant(realm);
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
    public void editParticipantById(List<Participant> participants, View view) {
        long participantID = participants.get(listViewParticipant.getPositionForView(view)).getParticipantID();
        Intent intent = new Intent(this,EditParticipant.class);
        intent.putExtra("participantID",participantID);
        startActivity(intent);
    }
}
