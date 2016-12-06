package com.example.katesudal.participantgroupmanagement.Model;



import io.realm.RealmList;
import io.realm.RealmObject;


/**
 * Created by katesuda.l on 28/11/2559.
 */
public class Section extends RealmObject {
    int sectionID;
    String sectionName;
    RealmList<Participant> participantIDs;

    public Section(){
    }

    public Section(RealmList<Participant> participantIDs, int sectionID, String sectionName) {
        this.participantIDs = participantIDs;
        this.sectionID = sectionID;
        this.sectionName = sectionName;
    }

    public Section(RealmList<Participant> participantIDs, String sectionName) {
        this.participantIDs = participantIDs;
        this.sectionName = sectionName;
    }

    public RealmList<Participant> getParticipantIDs() {
        return participantIDs;
    }

    public void setParticipantIDs(RealmList<Participant> participantIDs) {
        this.participantIDs = participantIDs;
    }

    public int getSectionID() {
        return sectionID;
    }

    public void setSectionID(int sectionID) {
        this.sectionID = sectionID;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }
}
