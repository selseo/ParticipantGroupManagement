package com.example.katesudal.participantgroupmanagement.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import io.realm.Realm;
import io.realm.RealmResults;

public class ViewEncounterActivity extends AppCompatActivity implements View.OnClickListener {
    private Realm realm;
//    public List<PairEncounter> pairEncounters;
    private Button buttonBacktoMainFromViewEncounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_encounter);
        Realm.init(getApplicationContext());
        realm = Realm.getDefaultInstance();
        buttonBacktoMainFromViewEncounter = (Button) findViewById(R.id.buttonBacktoMainFromViewEncounter);
        buttonBacktoMainFromViewEncounter.setOnClickListener(this);
        List<PairEncounter> pairEncounters = new ArrayList<>();
        pairEncounters = encounterCalculate(pairEncounters);
        SortablePairEncounterTableView tableViewEncounter = (SortablePairEncounterTableView) findViewById(R.id.tableViewEncounter);
        createPairEncounterTableAdapter(tableViewEncounter,pairEncounters);
    }

    private void createPairEncounterTableAdapter(SortablePairEncounterTableView tableViewEncounter,List<PairEncounter> pairEncounters) {
        if (tableViewEncounter != null) {
            final PairEncounterTableAdapter pairEncounterTableAdapter = new PairEncounterTableAdapter(this, pairEncounters, tableViewEncounter);
            tableViewEncounter.setDataAdapter(pairEncounterTableAdapter);
        }
    }


    public List<PairEncounter> encounterCalculate(List<PairEncounter> pairEncounters) {
        //1. create mapping
        pairEncounters =  createPairEncounterList(pairEncounters);

        //2. retreive data
        RealmResults<Section> sections = realm.where(Section.class).findAll();
        RealmResults<SpecialGroup> specialGroups = realm.where(SpecialGroup.class).findAll();

        //2.1 count in section
        for (int pairIndex = 0; pairIndex < pairEncounters.size(); pairIndex++) {
            int encounterCount = 0;
            encounterCount = countEncounterInSection(pairEncounters,sections, pairIndex, encounterCount);
            pairEncounters.get(pairIndex).setEncounterTimes(encounterCount);
        }

        //2.2. count special
        for (int pairIndex = 0; pairIndex < pairEncounters.size(); pairIndex++) {
            int encounterCount = pairEncounters.get(pairIndex).getEncounterTimes();
            encounterCount = countEncounterInSpecialGroup(pairEncounters,specialGroups, pairIndex, encounterCount);
            pairEncounters.get(pairIndex).setEncounterTimes(encounterCount);
        }

        return pairEncounters;

    }

    private List<PairEncounter> createPairEncounterList(List<PairEncounter> pairEncounters) {
        RealmResults<Participant> participants = realm.where(Participant.class).findAll();
        for (int participantIndex = 0; participantIndex < participants.size() - 1; participantIndex++) {
            pairEncounters = addPairParticipant(pairEncounters, participants, participantIndex);
        }
        return pairEncounters;
    }

    public List<PairEncounter> addPairParticipant(List<PairEncounter> pairEncounters, List<Participant> participants, int participantAIndex) {
        for (int participantBIndex = participantAIndex + 1; participantBIndex < participants.size(); participantBIndex++) {
            PairEncounter pairEncounter = new PairEncounter(
                    0,
                    participants.get(participantAIndex),
                    participants.get(participantBIndex));
            pairEncounters.add(pairEncounter);
        }
        return pairEncounters;
    }

    public int countEncounterInSpecialGroup(List<PairEncounter> pairEncounters, List<SpecialGroup> specialGroups, int pairIndex, int encounterCount) {
        for (int specialGroupIndex = 0; specialGroupIndex < specialGroups.size(); specialGroupIndex++) {
            List<Participant> participantInSpecialGroups = specialGroups.get(specialGroupIndex).getParticipantIDs();
            if (participantInSpecialGroups.contains(pairEncounters.get(pairIndex).getParticipantA())
                    && participantInSpecialGroups.contains(pairEncounters.get(pairIndex).getParticipantB())) {
                encounterCount++;
            }
        }
        return encounterCount;
    }

    public int countEncounterInSection(List<PairEncounter> pairEncounters, List<Section> sections, int pairIndex, int encounterCount) {
        for (int sectionIndex = 0; sectionIndex < sections.size(); sectionIndex++) {
            List<Participant> participantInSection = sections.get(sectionIndex).getParticipantIDs();
            if (participantInSection.contains(pairEncounters.get(pairIndex).getParticipantA())
                    && participantInSection.contains(pairEncounters.get(pairIndex).getParticipantB())) {
                encounterCount++;
            }
        }
        return encounterCount;
    }



    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonBacktoMainFromViewEncounter) {
            onBackPressed();
        }
    }

}