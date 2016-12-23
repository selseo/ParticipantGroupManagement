package com.example.katesudal.participantgroupmanagement.Activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.katesudal.participantgroupmanagement.Adapter.PairEncounterTableAdapter;
import com.example.katesudal.participantgroupmanagement.Fragment.EncounterTableFragment;
import com.example.katesudal.participantgroupmanagement.Model.PairEncounter;
import com.example.katesudal.participantgroupmanagement.Model.Participant;
import com.example.katesudal.participantgroupmanagement.Model.Section;
import com.example.katesudal.participantgroupmanagement.Model.SpecialGroup;
import com.example.katesudal.participantgroupmanagement.R;
import com.example.katesudal.participantgroupmanagement.SortablePairEncounterTableView;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class ViewEncounterActivity extends FragmentActivity
        implements View.OnClickListener
        ,EncounterTableFragment.OnFragmentInteractionListener {
    private Realm realm;
    Spinner spinnerParticipantName;
    Button buttonGo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_encounter);
        Realm.init(getApplicationContext());
        realm = Realm.getDefaultInstance();
        Button buttonBacktoMainFromViewEncounter = (Button) findViewById(R.id.buttonBacktoMainFromViewEncounter);
        spinnerParticipantName = (Spinner) findViewById(R.id.spinnerParticipantName);
        buttonGo = (Button) findViewById(R.id.buttonGo);

        buttonBacktoMainFromViewEncounter.setOnClickListener(this);
        buttonGo.setOnClickListener(this);

        setNameInSpinner();
    }

    private void setNameInSpinner() {
        List<String> nameList = new ArrayList<>();
        nameList.add("All Participants");
        RealmResults<Participant> participants = realm.where(Participant.class).findAll();
        for(int participantIndex = 0 ; participantIndex<participants.size();participantIndex++){
            nameList.add(participants.get(participantIndex).getParticipantName());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nameList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerParticipantName.setAdapter(dataAdapter);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonBacktoMainFromViewEncounter) {
            onBackPressed();
        }
        if(view.getId()==R.id.buttonGo){
            String name = String.valueOf(spinnerParticipantName.getSelectedItem());
            Bundle bundle = new Bundle();
            bundle.putString("name", name);
            EncounterTableFragment encounterTableFragment = new EncounterTableFragment();
            encounterTableFragment.setArguments(bundle);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.layoutFragmentTableView, encounterTableFragment);
            transaction.commit();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}