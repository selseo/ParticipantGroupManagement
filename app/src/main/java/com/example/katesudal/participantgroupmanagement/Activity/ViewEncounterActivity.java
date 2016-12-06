package com.example.katesudal.participantgroupmanagement.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.katesudal.participantgroupmanagement.Adapter.PairEncounterTableAdapter;
import com.example.katesudal.participantgroupmanagement.Model.PairEncounter;
import com.example.katesudal.participantgroupmanagement.Model.Participant;
import com.example.katesudal.participantgroupmanagement.Model.Section;
import com.example.katesudal.participantgroupmanagement.Model.SpecialGroup;
import com.example.katesudal.participantgroupmanagement.R;
import com.example.katesudal.participantgroupmanagement.SortablePairEncounterTableView;

import java.util.ArrayList;
import java.util.List;

import de.codecrafters.tableview.SortableTableView;
import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.listeners.SwipeToRefreshListener;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import io.realm.Realm;
import io.realm.RealmResults;

public class ViewEncounterActivity extends AppCompatActivity implements View.OnClickListener{
    private Realm realm;
    private List<PairEncounter> pairEncounters;
    private Button buttonBacktoMainFromViewEncounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_encounter);
        buttonBacktoMainFromViewEncounter = (Button) findViewById(R.id.buttonBacktoMainFromViewEncounter);
        buttonBacktoMainFromViewEncounter.setOnClickListener(this);
        encounterCalculate();
        SortablePairEncounterTableView tableViewEncounter = (SortablePairEncounterTableView) findViewById(R.id.tableViewEncounter);
        if (tableViewEncounter != null) {
            final PairEncounterTableAdapter pairEncounterTableAdapter = new PairEncounterTableAdapter(this, pairEncounters, tableViewEncounter);
            tableViewEncounter.setDataAdapter(pairEncounterTableAdapter);
        }
    }


    private void encounterCalculate(){
        pairEncounters = new ArrayList<>();
        realm = Realm.getDefaultInstance();
        RealmResults<Participant> participants = realm.where(Participant.class).findAll();
        for(int participantAIndex = 0 ; participantAIndex < participants.size() - 1 ; participantAIndex++){
            for(int participantBIndex = participantAIndex + 1 ; participantBIndex < participants.size() ; participantBIndex++){
                PairEncounter pairEncounter = new PairEncounter(
                        0,
                        participants.get(participantAIndex),
                        participants.get(participantBIndex));
                pairEncounters.add(pairEncounter);
            }
        }
        RealmResults<Section> sections = realm.where(Section.class).findAll();
        RealmResults<SpecialGroup> specialGroups = realm.where(SpecialGroup.class).findAll();
        for(int pairIndex = 0 ; pairIndex < pairEncounters.size() ;pairIndex++){
            int encounterCount=0;
            for(int sectionIndex = 0 ; sectionIndex < sections.size() ; sectionIndex++){
                List<Participant> participantInSection = sections.get(sectionIndex).getParticipantIDs();
                if(participantInSection.contains(pairEncounters.get(pairIndex).getParticipantA())
                        &&participantInSection.contains(pairEncounters.get(pairIndex).getParticipantB())){
                    encounterCount++;
                }
            }

            for(int specialGroupIndex = 0 ; specialGroupIndex < specialGroups.size() ; specialGroupIndex++){
                List<Participant> participantInSpecialGroups = specialGroups.get(specialGroupIndex).getParticipantIDs();
                if(participantInSpecialGroups.contains(pairEncounters.get(pairIndex).getParticipantA())
                        &&participantInSpecialGroups.contains(pairEncounters.get(pairIndex).getParticipantB())){
                    encounterCount++;
                }
            }
            pairEncounters.get(pairIndex).setEncounterTimes(encounterCount);
        }

    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.buttonBacktoMainFromViewEncounter){
            onBackPressed();
        }
    }

}