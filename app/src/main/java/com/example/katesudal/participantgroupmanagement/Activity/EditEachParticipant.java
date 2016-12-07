package com.example.katesudal.participantgroupmanagement.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.katesudal.participantgroupmanagement.Model.Participant;
import com.example.katesudal.participantgroupmanagement.R;
import com.example.katesudal.participantgroupmanagement.Util.ValidateUtil;

import io.realm.Realm;

public class EditEachParticipant extends AppCompatActivity implements View.OnClickListener{
    Realm realm;
    long participantID;
    private EditText editTextParticipantName;
    private RadioButton radioMale;
    private RadioButton radioFemale;
    private RadioButton radioStaff;
    private RadioButton radioParticipant;
    private Button buttonSaveParticipant;
    private Button buttonCancelEditParticipant;
    private String participantSex;
    private String participantType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = Realm.getDefaultInstance();
        setContentView(R.layout.activity_add_participant);
        participantID = getIntent().getExtras().getLong("participantID");
        editTextParticipantName = (EditText) findViewById(R.id.editTextParticipantName);
        radioMale = (RadioButton) findViewById(R.id.radioMale);
        radioFemale = (RadioButton) findViewById(R.id.radioFemale);
        radioStaff = (RadioButton) findViewById(R.id.radioStaff);
        radioParticipant = (RadioButton) findViewById(R.id.radioParticipant);
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
        if(participant.getParticipantSex().equals("Male")){
            radioMale.setChecked(true);
        }
        else if(participant.getParticipantSex().equals("Female")){
            radioFemale.setChecked(true);
        }
        if(participant.getParticipantType().equals("Staff")){
            radioStaff.setChecked(true);
        }
        else if(participant.getParticipantType().equals("Participant")){
            radioParticipant.setChecked(true);
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.buttonAddParticipant){
            saveEditedParticipant();
        }
        if(view.getId()==R.id.buttonCancelAddParticipant){
            onBackPressed();
        }

    }

    private void saveEditedParticipant() {
        String participantName = String.valueOf(editTextParticipantName.getText());
        if(radioMale.isChecked()){
            participantSex="Male";
        }
        else if(radioFemale.isChecked()){
            participantSex="Female";
            Log.d("SetSex","Female");
        }
        if(radioParticipant.isChecked()){
            participantType="Participant";
        }
        else if(radioStaff.isChecked()){
            participantType="Staff";
        }
        Participant participant =realm.where(Participant.class)
                .equalTo("participantID",participantID)
                .findFirst();
        if(ValidateUtil.isDuplicateParticipantName(participantName)
                &&!participant.getParticipantName().equals(participantName)){
            AlertDialog.Builder dialogErrorBuilder = new AlertDialog.Builder(this);
            dialogErrorBuilder.setMessage("This Participant Name is Duplicate. \nPlease try another.");
            dialogErrorBuilder.setCancelable(false);
            dialogErrorBuilder.setPositiveButton("OK",new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog,int which){
                    dialog.dismiss();
                }
            });
            dialogErrorBuilder.show();
            return;
        }
        realm.beginTransaction();
        participant.setParticipantName(participantName);
        participant.setParticipantSex(participantSex);
        participant.setParticipantType(participantType);
        realm.commitTransaction();
        Intent intent = new Intent(this,EditParticipantActivity.class);
        startActivity(intent);
    }
}
