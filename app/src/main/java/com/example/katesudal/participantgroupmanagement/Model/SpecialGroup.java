package com.example.katesudal.participantgroupmanagement.Model;

import io.realm.RealmList;
import io.realm.annotations.PrimaryKey;

/**
 * Created by katesuda.l on 06/12/2559.
 */

public class SpecialGroup {
    @PrimaryKey
    long specialGroupID;

    String specialGroupName;
    RealmList<Participant> participantIDs;

    public SpecialGroup(){

    }

    public SpecialGroup(RealmList<Participant> participantIDs, long specialGroupID, String specialGroupName) {
        this.participantIDs = participantIDs;
        this.specialGroupID = specialGroupID;
        this.specialGroupName = specialGroupName;
    }

    public RealmList<Participant> getParticipantIDs() {
        return participantIDs;
    }

    public void setParticipantIDs(RealmList<Participant> participantIDs) {
        this.participantIDs = participantIDs;
    }

    public long getSpecialGroupID() {
        return specialGroupID;
    }

    public void setSpecialGroupID(long specialGroupID) {
        this.specialGroupID = specialGroupID;
    }

    public String getSpecialGroupName() {
        return specialGroupName;
    }

    public void setSpecialGroupName(String specialGroupName) {
        this.specialGroupName = specialGroupName;
    }
}
