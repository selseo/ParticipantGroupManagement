package com.example.katesudal.participantgroupmanagement.Util;

import com.example.katesudal.participantgroupmanagement.Model.Participant;
import com.example.katesudal.participantgroupmanagement.Model.Project;

import java.util.List;

import io.realm.Realm;
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
    public static boolean isDuplicateProjectName(String name){
        Realm realm;
        realm = Realm.getDefaultInstance();
        RealmResults<Project> projects = realm.where(Project.class).findAll();
        for(int projectIndex = 0 ; projectIndex < projects.size();projectIndex++){
            if(projects.get(projectIndex).getProjectName().equals(name)){
                return true;
            }
        }
        return false;
    }
    public static boolean isDuplicateSectionName(String name,List<String> names){
        for(int nameIndex = 0 ; nameIndex < names.size();nameIndex++){
            if(names.get(nameIndex).equals(name)){
                return true;
            }
        }
        return false;
    }
    public static boolean isInvalidName(String name){
        if(name.matches("^[a-zA-Z0-9]+.*")) return false;
        return true;
    }
    public static boolean isInvalidParticipantName(String name){
        if(name.matches("^[a-zA-Z][a-zA-Z0-9]*$")) return false;
        return true;
    }
}
