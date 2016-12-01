package com.example.katesudal.participantgroupmanagement.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.katesudal.participantgroupmanagement.Model.Participant;
import com.example.katesudal.participantgroupmanagement.R;

import io.realm.Realm;

public class AddParticipantActivity extends AppCompatActivity implements Realm.Transaction,View.OnClickListener{

    private EditText editTextParticipantName;
    private EditText editTextParticipantSex;
    private EditText editTextParticipantType;
    private Button buttonAddParticipant;
    private Button buttonCancelAddParticipant;
    private Participant participant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_participant);
        editTextParticipantName = (EditText) findViewById(R.id.editTextParticipantName);
        editTextParticipantSex = (EditText) findViewById(R.id.editTextParticipantSex);
        editTextParticipantType = (EditText) findViewById(R.id.editTextParticipantType);
        buttonAddParticipant = (Button) findViewById(R.id.buttonAddParticipant);
        buttonCancelAddParticipant = (Button) findViewById(R.id.buttonCancelAddParticipant);
        buttonAddParticipant.setOnClickListener(this);
        buttonCancelAddParticipant.setOnClickListener(this);
    }

    public void createParticipantRealm(Participant participant) {
        Realm.init(getApplicationContext());
        Realm realm = Realm.getDefaultInstance();
        this.participant = participant;
        this.participant.setParticipantID(generateParticipantID(realm));
        this.execute(realm);
    }

    private long generateParticipantID(Realm realm) {
        Number num = realm.where(Participant.class).max("participantID");
        if(num==null){
            return 1;
        }
        else{
            return (long)num+1;
        }
    }

    @Override
    public void execute(Realm realm) {
        realm.beginTransaction();
        realm.copyToRealm(participant);
        realm.commitTransaction();
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.buttonAddParticipant){
            String participantName = String.valueOf(editTextParticipantName.getText());
            String participantSex = String.valueOf(editTextParticipantSex.getText());
            String participantType = String.valueOf(editTextParticipantType.getText());
            Participant participant = new Participant(participantName,participantSex,participantType);
            createParticipantRealm(participant);
            Intent intent = new Intent(view.getContext(), EditParticipantActivity.class);
            startActivity(intent);
        }
        if(view.getId()==R.id.buttonCancelAddParticipant){
            onBackPressed();
        }
    }
}
