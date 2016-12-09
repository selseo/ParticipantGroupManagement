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

public class AddParticipantActivity extends AppCompatActivity implements Realm.Transaction, View.OnClickListener {

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

    private Participant participant;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_participant);
        ButterKnife.bind(this);
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
            String participantName = String.valueOf(editTextParticipantName.getText());
            if(ValidateUtil.isInvalidParticipantName(participantName)){
                showInvalidParticipantNameDialog();
                return;
            }

            String participantGender = setParticipantGender();
            String participantType = setParticipantType();

            if(!(participantGender.equals("Male")||participantGender.equals("Female"))){
                showUncheckedGenderOrTypeDialog();
                return;
            }
            if(!(participantType.equals("Staff")||participantType.equals("Participant"))){
                showUncheckedGenderOrTypeDialog();
                return;
            }
            Participant participant = new Participant(participantName, participantGender, participantType);
            if(ValidateUtil.isDuplicateParticipantName(participantName)){
                showDuplicateParticipantNameDialog();
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
