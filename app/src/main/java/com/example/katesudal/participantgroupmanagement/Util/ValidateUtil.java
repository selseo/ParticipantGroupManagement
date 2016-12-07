package com.example.katesudal.participantgroupmanagement.Util;

import com.example.katesudal.participantgroupmanagement.Model.Participant;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by katesuda.l on 07/12/2559.
 */

public class ValidateUtil {
    public static boolean isDuplicateParticipantName(String name){
        Realm realm;
        realm = Realm.getDefaultInstance();
        RealmResults<Participant> participants = realm.where(Participant.class).findAll();
        for(int participantIndex = 0 ; participantIndex < participants.size(); participantIndex++){
            if(participants.get(participantIndex).getParticipantName().equals(name)){
                return true;
            }
        }
        return false;
    }
}
