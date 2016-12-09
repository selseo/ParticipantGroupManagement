package com.example.katesudal.participantgroupmanagement.Model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by katesuda.l on 28/11/2559.
 */
public class Participant extends RealmObject {
    @PrimaryKey
    long participantID;
    String participantName;
    String participantGender;
    String participantType;

    public Participant(){
    }

    public Participant(String participantName, String participantGender, String participantType) {
        this.participantName = participantName;
        this.participantGender = participantGender;
        this.participantType = participantType;
    }

    public Participant(long participantID, String participantName, String participantGender, String participantType) {
        this.participantID = participantID;
        this.participantName = participantName;
        this.participantGender = participantGender;
        this.participantType = participantType;
    }

    public long getParticipantID() {
        return participantID;
    }

    public void setParticipantID(long participantID) {
        this.participantID = participantID;
    }

    public String getParticipantName() {
        return participantName;
    }

    public void setParticipantName(String participantName) {
        this.participantName = participantName;
    }

    public String getParticipantGender() {
        return participantGender;
    }

    public void setParticipantGender(String participantGender) {
        this.participantGender = participantGender;
    }

    public String getParticipantType() {
        return participantType;
    }

    public void setParticipantType(String participantType) {
        this.participantType = participantType;
    }
}
