package com.example.katesudal.participantgroupmanagement.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.katesudal.participantgroupmanagement.Model.Participant;
import com.example.katesudal.participantgroupmanagement.R;

import io.realm.Realm;
import io.realm.RealmResults;

public class EditEachParticipant extends AppCompatActivity implements View.OnClickListener{
    Realm realm;
    long participantID;
    private EditText editTextParticipantName;
    private EditText editTextParticipantSex;
    private EditText editTextParticipantType;
    private Button buttonSaveParticipant;
    private Button buttonCancelEditParticipant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = Realm.getDefaultInstance();
        setContentView(R.layout.activity_add_participant);
        participantID = getIntent().getExtras().getLong("participantID");
        editTextParticipantName = (EditText) findViewById(R.id.editTextParticipantName);
        editTextParticipantSex = (EditText) findViewById(R.id.editTextParticipantSex);
        editTextParticipantType = (EditText) findViewById(R.id.editTextParticipantType);
        buttonSaveParticipant = (Button) findViewById(R.id.buttonAddParticipant);
        buttonCancelEditParticipant = (Button) findViewById(R.id.buttonCancelAddParticipant);
        buttonSaveParticipant.setText("Save");
        buttonSaveParticipant.setOnClickListener(this);
        buttonCancelEditParticipant.setOnClickListener(this);
        showParticipantInformation();
    }

    private void showParticipantInformation(){
        Participant participant =realm.where(Participant.class)
                .equalTo("participantID",participantID)
                .findFirst();
        editTextParticipantName.setText(participant.getParticipantName());
        editTextParticipantSex.setText(participant.getParticipantSex());
        editTextParticipantType.setText(participant.getParticipantType());
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.buttonAddParticipant){
            saveEditedParticipant();
            Intent intent = new Intent(this,EditParticipantActivity.class);
            startActivity(intent);
        }
        if(view.getId()==R.id.buttonCancelAddParticipant){
            onBackPressed();
        }

    }

    private void saveEditedParticipant() {
        String participantName = String.valueOf(editTextParticipantName.getText());
        String participantSex = String.valueOf(editTextParticipantSex.getText());
        String participantType = String.valueOf(editTextParticipantType.getText());
        Participant participant =realm.where(Participant.class)
                .equalTo("participantID",participantID)
                .findFirst();
        realm.beginTransaction();
        participant.setParticipantName(participantName);
        participant.setParticipantSex(participantSex);
        participant.setParticipantType(participantType);
        realm.commitTransaction();
    }
}
