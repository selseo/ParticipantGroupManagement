package com.example.katesudal.participantgroupmanagement;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by katesuda.l on 28/11/2559.
 */

public class Project extends RealmObject {
    int projectID;
    String projectName;
    RealmList<Section> sectionIDs;

    public Project(){
    }

    public Project(String projectName, RealmList<Section> sectionIDs) {
        this.projectName = projectName;
        this.sectionIDs = sectionIDs;
    }

    public Project(int projectID, String projectName, RealmList<Section> sectionIDs) {
        this.projectID = projectID;
        this.projectName = projectName;
        this.sectionIDs = sectionIDs;
    }

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public List<Section> getSectionIDs() {
        return sectionIDs;
    }

    public void setSectionIDs(RealmList<Section> sectionIDs) {
        this.sectionIDs = sectionIDs;
    }
}
