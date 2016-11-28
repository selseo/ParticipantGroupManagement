package com.example.katesudal.participantgroupmanagement;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class EditParticipantActivity extends AppCompatActivity
        implements View.OnClickListener,
        ItemParticipantAdapter.ItemParticipantListener{

    private Button buttonGotoAddParticipant;
    private ListView listViewParticipant;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = Realm.getDefaultInstance();
        setContentView(R.layout.activity_edit_participant);
        buttonGotoAddParticipant = (Button) findViewById(R.id.buttonGotoAddParticipant);
        listViewParticipant = (ListView) findViewById(R.id.listViewParticipant);
        buttonGotoAddParticipant.setOnClickListener(this);
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
    }

    @Override
    public void deleteParticipantById(List<Participant> participantId, View view) {
        RealmResults<Participant> participant =realm.where(Participant.class)
                .equalTo("participantName",participantId.get(listViewParticipant.getPositionForView(view))
                        .getParticipantName())
                .findAll();
        realm.beginTransaction();
        participant.deleteAllFromRealm();
        realm.commitTransaction();
        viewParticipant(realm);
    }
}
