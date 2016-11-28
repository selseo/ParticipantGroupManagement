package com.example.katesudal.participantgroupmanagement;

import io.realm.RealmObject;

/**
 * Created by katesuda.l on 28/11/2559.
 */
public class Participant extends RealmObject {
    int participantID;
    String participantName;
    String participantSex;
    String participantType;

    public Participant(){
    }

    public Participant(String participantName, String participantSex, String participantType) {
        this.participantName = participantName;
        this.participantSex = participantSex;
        this.participantType = participantType;
    }

    public Participant(int participantID, String participantName, String participantSex, String participantType) {
        this.participantID = participantID;
        this.participantName = participantName;
        this.participantSex = participantSex;
        this.participantType = participantType;
    }

    public int getParticipantID() {
        return participantID;
    }

    public void setParticipantID(int participantID) {
        this.participantID = participantID;
    }

    public String getParticipantName() {
        return participantName;
    }

    public void setParticipantName(String participantName) {
        this.participantName = participantName;
    }

    public String getParticipantSex() {
        return participantSex;
    }

    public void setParticipantSex(String participantSex) {
        this.participantSex = participantSex;
    }

    public String getParticipantType() {
        return participantType;
    }

    public void setParticipantType(String participantType) {
        this.participantType = participantType;
    }
}
