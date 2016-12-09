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

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

public class AddParticipantActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.editTextParticipantName)
    EditText editTextParticipantName;
    @BindView(R.id.radioMale)
    RadioButton radioMale;
    @BindView(R.id.radioFemale)
    RadioButton radioFemale;
    @BindView(R.id.radioStaff)
    RadioButton radioStaff;
    @BindView(R.id.radioParticipant)
    RadioButton radioParticipant;
    @BindView(R.id.buttonAddParticipant)
    Button buttonAddParticipant;
    @BindView(R.id.buttonCancelAddParticipant)
    Button buttonCancelAddParticipant;

    Realm realm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_participant);
        ButterKnife.bind(this);
        Realm.init(getApplicationContext());
        realm = Realm.getDefaultInstance();
        buttonAddParticipant.setOnClickListener(this);
        buttonCancelAddParticipant.setOnClickListener(this);
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
    public void onClick(View view) {
        if (view.getId() == R.id.buttonAddParticipant) {
            addNewParticipant(view);
        }
        if (view.getId() == R.id.buttonCancelAddParticipant) {
            onBackPressed();
        }
    }

    private void addNewParticipant(View view) {
        long participantID = generateParticipantID(realm);
        String participantName = String.valueOf(editTextParticipantName.getText());
        String participantGender = setParticipantGender();
        String participantType = setParticipantType();

        if(ValidateUtil.isInvalidParticipantName(participantName)){
            showInvalidParticipantNameDialog();
            return;
        }
        if(!(participantGender.equals("Male")||participantGender.equals("Female"))){
            showUncheckedGenderOrTypeDialog();
            return;
        }
        if(!(participantType.equals("Staff")||participantType.equals("Participant"))){
            showUncheckedGenderOrTypeDialog();
            return;
        }
        if(ValidateUtil.isDuplicateParticipantName(participantName)){
            showDuplicateParticipantNameDialog();
            return;
        }

        addParticipantToRealm(participantID, participantName, participantGender, participantType);

        Intent intent = new Intent(view.getContext(), EditParticipantActivity.class);
        startActivity(intent);
    }

    private void addParticipantToRealm(long participantID, String participantName, String participantGender, String participantType) {
        Participant participant = new Participant(participantID,participantName, participantGender, participantType);
        realm.beginTransaction();
        realm.copyToRealm(participant);
        realm.commitTransaction();
    }

    private String setParticipantGender() {
        String gender="";
        if (radioMale.isChecked()) {
            gender =  "Male";
        } else if (radioFemale.isChecked()) {
            gender = "Female";
        }
        return gender;
    }
    private String setParticipantType() {
        String type="";
        if (radioStaff.isChecked()) {
            type =  "Staff";
        } else if (radioParticipant.isChecked()) {
            type = "Participant";
        }
        return type;
    }

    private void showDuplicateParticipantNameDialog() {
        AlertDialog.Builder dialogErrorBuilder = new AlertDialog.Builder(this);
        dialogErrorBuilder.setMessage("This Participant Name is Duplicate. \nPlease try another.");
        dialogErrorBuilder.setCancelable(false);
        dialogErrorBuilder.setPositiveButton("OK",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog,int which){
                dialog.dismiss();
            }
        });
        dialogErrorBuilder.show();
    }

    private void showUncheckedGenderOrTypeDialog() {
        AlertDialog.Builder dialogErrorBuilder = new AlertDialog.Builder(this);
        dialogErrorBuilder.setMessage("Gender or Type should be selected.");
        dialogErrorBuilder.setCancelable(false);
        dialogErrorBuilder.setPositiveButton("OK",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog,int which){
                dialog.dismiss();
            }
        });
        dialogErrorBuilder.show();
    }

    private void showInvalidParticipantNameDialog() {
        AlertDialog.Builder dialogErrorBuilder = new AlertDialog.Builder(this);
        dialogErrorBuilder.setMessage("Participant Name should begin with letter and contain only letter or number. \nPlease try another.");
        dialogErrorBuilder.setCancelable(false);
        dialogErrorBuilder.setPositiveButton("OK",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog,int which){
                dialog.dismiss();
            }
        });
        dialogErrorBuilder.show();
    }
}
