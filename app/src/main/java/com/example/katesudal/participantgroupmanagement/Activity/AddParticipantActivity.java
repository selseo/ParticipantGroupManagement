package com.example.katesudal.participantgroupmanagement.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.katesudal.participantgroupmanagement.Model.Participant;
import com.example.katesudal.participantgroupmanagement.R;
import com.example.katesudal.participantgroupmanagement.Util.ValidateUtil;

import io.realm.Realm;

public class AddParticipantActivity extends AppCompatActivity implements Realm.Transaction, View.OnClickListener {

    private EditText editTextParticipantName;
    private RadioButton radioMale;
    private RadioButton radioFemale;
    private RadioButton radioStaff;
    private RadioButton radioParticipant;
    private Button buttonAddParticipant;
    private Button buttonCancelAddParticipant;
    private Participant participant;
    private String participantSex;
    private String participantType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_participant);
        editTextParticipantName = (EditText) findViewById(R.id.editTextParticipantName);
        radioMale = (RadioButton) findViewById(R.id.radioMale);
        radioFemale = (RadioButton) findViewById(R.id.radioFemale);
        radioStaff = (RadioButton) findViewById(R.id.radioStaff);
        radioParticipant = (RadioButton) findViewById(R.id.radioParticipant);
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
        if (num == null) {
            return 1;
        } else {
            return num.longValue() + 1;
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
        if (view.getId() == R.id.buttonAddParticipant) {
            boolean sexIsChecked = false;
            boolean typeIsChecked = false;
            String participantName = String.valueOf(editTextParticipantName.getText());
            if(ValidateUtil.isInvalidParticipantName(participantName)){
                AlertDialog.Builder dialogErrorBuilder = new AlertDialog.Builder(this);
                dialogErrorBuilder.setMessage("Participant Name should begin with letter and contain only letter or number. \nPlease try another.");
                dialogErrorBuilder.setCancelable(false);
                dialogErrorBuilder.setPositiveButton("OK",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog,int which){
                        dialog.dismiss();
                    }
                });
                dialogErrorBuilder.show();
                return;
            }
            if (radioMale.isChecked()) {
                participantSex = "Male";
                sexIsChecked = true;
            } else if (radioFemale.isChecked()) {
                participantSex = "Female";
                sexIsChecked = true;
            }
            if (radioParticipant.isChecked()) {
                participantType = "Participant";
                typeIsChecked = true;
            } else if (radioStaff.isChecked()) {
                participantType = "Staff";
                typeIsChecked = true;
            }
            if(!typeIsChecked||!sexIsChecked){
                AlertDialog.Builder dialogErrorBuilder = new AlertDialog.Builder(this);
                dialogErrorBuilder.setMessage("Sex or Type should be selected.");
                dialogErrorBuilder.setCancelable(false);
                dialogErrorBuilder.setPositiveButton("OK",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog,int which){
                        dialog.dismiss();
                    }
                });
                dialogErrorBuilder.show();
                return;
            }
            Participant participant = new Participant(participantName, participantSex, participantType);
            if(ValidateUtil.isDuplicateParticipantName(participantName)){
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
            createParticipantRealm(participant);
            Intent intent = new Intent(view.getContext(), EditParticipantActivity.class);
            startActivity(intent);
        }
        if (view.getId() == R.id.buttonCancelAddParticipant) {
            onBackPressed();
        }
    }
}
