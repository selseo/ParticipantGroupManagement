package com.example.katesudal.participantgroupmanagement.Model;

/**
 * Created by katesuda.l on 01/12/2559.
 */

public class PairEncounter{
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
