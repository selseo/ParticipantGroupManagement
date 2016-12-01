package com.example.katesudal.participantgroupmanagement;

import com.example.katesudal.participantgroupmanagement.Model.PairEncounter;

import java.util.Comparator;

/**
 * Created by katesuda.l on 01/12/2559.
 */

public class PairEncounterComparator {
    private PairEncounterComparator() {
    }

    public static Comparator<PairEncounter> getPairEncounterParticipantAComparator() {
        return new PairEncounterParticipantAComparator();
    }

    public static Comparator<PairEncounter> getPairEncounterParticipantBComparator() {
        return new PairEncounterParticipantBComparator();
    }

    public static Comparator<PairEncounter> getPairEncounterTimesComparator() {
        return new PairEncounterTimesComparator();
    }



    private static class PairEncounterParticipantAComparator implements Comparator<PairEncounter> {
        @Override
        public int compare(PairEncounter pairEncounterA1, PairEncounter pairEncounterA2) {
            return pairEncounterA1.getParticipantA().getParticipantName().compareTo(pairEncounterA2.getParticipantA().getParticipantName());
        }
    }

    private static class PairEncounterParticipantBComparator implements Comparator<PairEncounter> {
        @Override
        public int compare(PairEncounter pairEncounterB1, PairEncounter pairEncounterB2) {
            return pairEncounterB1.getParticipantB().getParticipantName().compareTo(pairEncounterB2.getParticipantB().getParticipantName());
        }
    }

    private static class PairEncounterTimesComparator implements Comparator<PairEncounter> {
        @Override
        public int compare(PairEncounter pairEncounterTimes1, PairEncounter pairEncounterTimes2) {
            if (pairEncounterTimes1.getEncounterTimes() < pairEncounterTimes2.getEncounterTimes()) return -1;
            if (pairEncounterTimes1.getEncounterTimes() > pairEncounterTimes2.getEncounterTimes()) return 1;
            return 0;
        }
    }
}
