package com.example.katesudal.participantgroupmanagement.Model;


import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by katesuda.l on 28/11/2559.
 */

public class Project extends RealmObject {
    @PrimaryKey
    long projectID;

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

    public long getProjectID() {
        return projectID;
    }

    public void setProjectID(long projectID) {
        this.projectID = projectID;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public RealmList<Section> getSectionIDs() {
        return sectionIDs;
    }

    public void setSectionIDs(RealmList<Section> sectionIDs) {
        this.sectionIDs = sectionIDs;
    }
}
