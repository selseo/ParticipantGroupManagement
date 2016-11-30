package com.example.katesudal.participantgroupmanagement.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.katesudal.participantgroupmanagement.Model.Participant;
import com.example.katesudal.participantgroupmanagement.Model.Section;
import com.example.katesudal.participantgroupmanagement.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.realm.Realm;
import io.realm.RealmResults;

public class ViewEncounterActivity extends AppCompatActivity {
    Map<Set<Participant>,Integer> encounters;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_encounter);
        Log.d("EncounterResult","Start Encounter");
        encounterCalculate();
    }

//    private void encounterCalculate(){
//        encounters = new HashMap<>();
//        realm = Realm.getDefaultInstance();
//        RealmResults<Participant> participants = realm.where(Participant.class).findAll();
//        for(int participantPairAIndex = 0; participantPairAIndex < participants.size()-1; participantPairAIndex++){
//            for(int participantPairBIndex = participantPairAIndex+1; participantPairBIndex < participants.size(); participantPairBIndex++){
//                Set<Participant> pairOfParticipants = new HashSet<>();
//                pairOfParticipants.add(participants.get(participantPairAIndex));
//                pairOfParticipants.add(participants.get(participantPairBIndex));
//                encounters.put(pairOfParticipants,0);
//            }
//        }
//        RealmResults<Section> sections = realm.where(Section.class).findAll();
//        for(int sectionIndex = 0; sectionIndex<sections.size();sectionIndex++){
//            List<Participant> participantsInSection = sections.get(sectionIndex).getParticipantIDs();
//            for(int participantPairAIndex = 0; participantPairAIndex < participantsInSection.size()-1; participantPairAIndex++){
//                for(int participantPairBIndex = participantPairAIndex + 1; participantPairBIndex < participantsInSection.size() ; participantPairBIndex++){
//                    Set<Participant> pairOfParticipants = new HashSet<>();
//                    pairOfParticipants.add(participantsInSection.get(participantPairAIndex));
//                    pairOfParticipants.add(participantsInSection.get(participantPairBIndex));
//                    encounters.put(pairOfParticipants,encounters.get(pairOfParticipants)+1);
//                }
//            }
//        }
//        String result="";
//        for(Set<Participant> key: encounters.keySet()){
//            result = result+key.toString()+":"+encounters.get(key)+"\n";
//        }
//        Log.d("EncounterResult = ",result);
//    }

    private void encounterCalculate(){
        List<PairEncounter> pairEncounters = new ArrayList<>();
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
        for(int pairIndex = 0 ; pairIndex < pairEncounters.size() ;pairIndex++){
            int encounterCount=0;
            for(int sectionIndex = 0 ; sectionIndex < sections.size() ; sectionIndex++){
                List<Participant> participantInSection = sections.get(sectionIndex).getParticipantIDs();
                if(participantInSection.contains(pairEncounters.get(pairIndex).getParticipantA())
                        &&participantInSection.contains(pairEncounters.get(pairIndex).getParticipantB())){
                    encounterCount++;
                }
            }
            pairEncounters.get(pairIndex).setEncounterTimes(encounterCount);
        }
        String result="";
        for(PairEncounter pairEncounter: pairEncounters){
            result = result+pairEncounter.getParticipantA().getParticipantName()+" & "
                    +pairEncounter.getParticipantB().getParticipantName()+" = "
                    +pairEncounter.getEncounterTimes()+"\n";
        }
        Log.d("EncounterResult = ",result);

    }

    class PairEncounter{
        Participant participantA;
        Participant participantB;
        int encounterTimes;

        public PairEncounter(int encounterTimes, Participant participantA, Participant participantB) {
            this.encounterTimes = encounterTimes;
            this.participantA = participantA;
            this.participantB = participantB;
        }

        public int getEncounterTimes() {
            return encounterTimes;
        }

        public void setEncounterTimes(int encounterTimes) {
            this.encounterTimes = encounterTimes;
        }

        public Participant getParticipantA() {
            return participantA;
        }

        public void setParticipantA(Participant participantA) {
            this.participantA = participantA;
        }

        public Participant getParticipantB() {
            return participantB;
        }

        public void setParticipantB(Participant participantB) {
            this.participantB = participantB;
        }
    }
}