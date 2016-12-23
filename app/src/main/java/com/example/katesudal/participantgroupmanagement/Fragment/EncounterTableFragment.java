package com.example.katesudal.participantgroupmanagement.Fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class EncounterTableFragment extends Fragment {

    private Realm realm;
    SortablePairEncounterTableView tableViewEncounter;
    List<PairEncounter> pairEncounters;

    public EncounterTableFragment() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Realm.init(getContext());
        realm = Realm.getDefaultInstance();
        String name = getArguments().getString("name");
        pairEncounters = new ArrayList<>();
        encounterCalculate(pairEncounters,name);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootv = inflater.inflate(R.layout.fragment_encounter_table, container, false);
        tableViewEncounter = (SortablePairEncounterTableView) rootv.findViewById(R.id.tableViewEncounter);
        createPairEncounterTableAdapter(tableViewEncounter,pairEncounters);

        return rootv;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private void createPairEncounterTableAdapter(SortablePairEncounterTableView tableViewEncounter, List<PairEncounter> pairEncounters) {
        if (tableViewEncounter != null) {
            final PairEncounterTableAdapter pairEncounterTableAdapter = new PairEncounterTableAdapter(getContext(), pairEncounters, tableViewEncounter);
            tableViewEncounter.setDataAdapter(pairEncounterTableAdapter);
        }
    }


    public void encounterCalculate(List<PairEncounter> pairEncounters,String name) {
        RealmResults<Participant> participants = realm.where(Participant.class).findAll();
        RealmResults<Section> sections = realm.where(Section.class).findAll();
        RealmResults<SpecialGroup> specialGroups = realm.where(SpecialGroup.class).findAll();

        if(name=="All Participants") createAllPairEncounterList(pairEncounters,participants);
        else{
            createSelectedPairEncounterList(pairEncounters,participants,name);
        }
        setEncounterFromSectionToEachPair(pairEncounters, sections);
        setEncounterFromSpecialGroupToEachPair(pairEncounters, specialGroups);
    }

    private void createSelectedPairEncounterList(List<PairEncounter> pairEncounters, RealmResults<Participant> participants,String name) {
        Participant participant = realm.where(Participant.class)
                .equalTo("participantName",name)
                .findFirst();
        for(int participantIndex = 0; participantIndex<participants.size();participantIndex++){
            if(!participants.get(participantIndex).getParticipantName().equals(name)){
                PairEncounter pairEncounter = new PairEncounter(0,participant,participants.get(participantIndex));
                pairEncounters.add(pairEncounter);
            }
        }
    }

    private void createAllPairEncounterList(List<PairEncounter> pairEncounters, List<Participant> participants) {
        for (int participantIndex = 0; participantIndex < participants.size() - 1; participantIndex++) {
            addPairParticipant(pairEncounters, participants, participantIndex);
        }
    }

    public void setEncounterFromSpecialGroupToEachPair(List<PairEncounter> pairEncounters, List<SpecialGroup> specialGroups) {
        for (int pairIndex = 0; pairIndex < pairEncounters.size(); pairIndex++) {
            int encounterCount = pairEncounters.get(pairIndex).getEncounterTimes();
            encounterCount = countEncounterInEachSpecialGroup(pairEncounters,specialGroups, pairIndex, encounterCount);
            pairEncounters.get(pairIndex).setEncounterTimes(encounterCount);
        }
    }

    public void setEncounterFromSectionToEachPair(List<PairEncounter> pairEncounters, List<Section> sections) {
        for (int pairIndex = 0; pairIndex < pairEncounters.size(); pairIndex++) {
            int encounterCount = pairEncounters.get(pairIndex).getEncounterTimes();
            encounterCount = countEncounterInEachSection(pairEncounters,sections, pairIndex, encounterCount);
            pairEncounters.get(pairIndex).setEncounterTimes(encounterCount);
        }
    }

    public void addPairParticipant(List<PairEncounter> pairEncounters, List<Participant> participants, int participantAIndex) {
        for (int participantBIndex = participantAIndex + 1; participantBIndex < participants.size(); participantBIndex++) {
            PairEncounter pairEncounter = new PairEncounter(
                    0,
                    participants.get(participantAIndex),
                    participants.get(participantBIndex));
            pairEncounters.add(pairEncounter);
        }
    }

    public int countEncounterInEachSpecialGroup(List<PairEncounter> pairEncounters, List<SpecialGroup> specialGroups, int pairIndex, int encounterCount) {
        for (int specialGroupIndex = 0; specialGroupIndex < specialGroups.size(); specialGroupIndex++) {
            List<Participant> participantInSpecialGroups = specialGroups.get(specialGroupIndex).getParticipantIDs();
            if (participantInSpecialGroups.contains(pairEncounters.get(pairIndex).getParticipantA())
                    && participantInSpecialGroups.contains(pairEncounters.get(pairIndex).getParticipantB())) {
                encounterCount++;
            }
        }
        return encounterCount;
    }

    public int countEncounterInEachSection(List<PairEncounter> pairEncounters, List<Section> sections, int pairIndex, int encounterCount) {
        for (int sectionIndex = 0; sectionIndex < sections.size(); sectionIndex++) {
            List<Participant> participantInSection = sections.get(sectionIndex).getParticipantIDs();
            if (participantInSection.contains(pairEncounters.get(pairIndex).getParticipantA())
                    && participantInSection.contains(pairEncounters.get(pairIndex).getParticipantB())) {
                encounterCount++;
            }
        }
        return encounterCount;
    }

}
